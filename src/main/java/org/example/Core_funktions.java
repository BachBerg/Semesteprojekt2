package org.example;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Core_funktions extends GenMetoder {

    XYChart.Series<NumberAxis, NumberAxis> EKGserie = new XYChart.Series<>();
    XYChart.Series<NumberAxis, NumberAxis> arkivSerie = new XYChart.Series<>();
    Sensor S1;
    ScheduledExecutorService Eventhandler;
    ExecutorService EventhandlerSQL = Executors.newSingleThreadExecutor();
    SQL MySQL = new SQL();

    boolean doesConnectionExist = false;
    boolean yull = false;
    double[] data = new double[720];
    double[] akrivData = new double[720];

    public LineChart<NumberAxis, NumberAxis> EKGchart;
    public String textFieldT;
    public Label BPM;


    // Tråd til at køre programmet parralelt med javvaFX tråden
    // først henter den mållinger
    // derefter laver den udregninger på denne data for at finde puls
    // linechart tråeden bliver kørt af en platform runlater, dette er for at
    private final Thread m1 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("Der bliver kort en ny tråd");
            S1.filter(data);
            Platform.runLater(LinechartThread);
            EventhandlerSQL.execute(arkivThread);
        }
    });
    // denne tråd indholder de metoder som plotter vores grafer
    private final Thread LinechartThread = new Thread(() -> {
        plotEKGChart(data);
        BPM.setText(String.valueOf(pulsCalc(data)));
    });
    private final Thread arkivThread = new Thread(() -> {
        // metode til at gemme her
        startArkiv(textFieldT);
    });

    // thread til at at hente mållinger fra seriel porten kalder filter metoden
    public void StartMållinger(LineChart<NumberAxis, NumberAxis> linechart, String textField, Label BPMdata) {
        if (!doesConnectionExist) {
            S1 = new Sensor();
            EKGchart = linechart;
            textFieldT = textField;
            BPM = BPMdata;
            // indstilling af linchart
            setupEKGChart(EKGchart);
            //indtilling af prioritet
            //arkivThread.setPriority(5);
            doesConnectionExist = true;
        }
        // her indstilles og køres eventhandler, den modtager en runnable og run'er denne i det indstillede interval
        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(m1, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void startArkiv(String CPR) {
        MySQL.getSQLConnection();
        MySQL.createNewPatient(CPR);
        MySQL.insertIntoTable(CPR, data);
        MySQL.stopSQLConnection();
    }

    public void getEKGArkiv(String CPR, LineChart<NumberAxis, NumberAxis> EKGHistorik) {
        MySQL.getSQLConnection();

        //her hentes og plottes data
        MySQL.getEKGDataFromTable(CPR, akrivData);
        plotEKGarkivChart(akrivData, EKGHistorik);

        MySQL.stopSQLConnection();
    }

    void plotEKGarkivChart(double[] arkiv, LineChart<NumberAxis, NumberAxis> EKGHistorik) {
        if (!yull) {
            yull = true;
            arkivSerie.setName("EKG");
            EKGHistorik.getData().clear();
            EKGHistorik.getData().addAll(arkivSerie);
            System.out.println("opsætning af linechart 1 succesfuld");
        }

        arkivSerie.getData().clear();
        for (int i = 0; i < arkiv.length; i++) {
            arkivSerie.getData().add(new XYChart.Data(i, arkiv[i]));
        }
        System.out.println("plot linchart metode færdig");
    }

    public void slukProgram() {
        Eventhandler.shutdown();
        System.out.println("eventhandler shutdown");
    }

    // setup af linechart
    public void setupEKGChart(LineChart<NumberAxis, NumberAxis> linechart) {
        EKGserie.setName("EKG");
        linechart.getData().clear();
        linechart.getData().addAll(EKGserie);
        System.out.println("opsætning af linechart 1 succesfuld");
    }

    // metode som plotter EKGarkivet ind i linehcart
    public void plotEKGChart(double[] EKGarkiv) {
        EKGserie.getData().clear();
        for (int i = 0; i < EKGarkiv.length; i++) {
            EKGserie.getData().add(new XYChart.Data(i, EKGarkiv[i]));
        }
        System.out.println("plot linchart metode færdig");
    }

    // udregning af puls
    public double pulsCalc(double[] data) {
        int counter = 0;
        double puls = 0, punkt1 = 0;
        double localMax = Arrays.stream(data).max().getAsDouble();
        for (int i = 0; i < data.length; i++) {
            if (data[i] > (localMax * 0.9)) {
                punkt1 = i;
                counter++;
                i = i + 50;
            }
            if (counter == 2) {
                return puls = (i - punkt1) / 360 * 60 * 10;

            }
        }
        return puls;
    }


}
