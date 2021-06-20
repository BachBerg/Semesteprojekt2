package org.example;

import Database.SQL;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Core_funktions extends GenMetoder {

    XYChart.Series<NumberAxis, NumberAxis> EKGserie = new XYChart.Series<>();
    XYChart.Series<NumberAxis, NumberAxis> arkivSerie = new XYChart.Series<>();
    Sensor S1;
    ScheduledExecutorService Eventhandler;
    SQL MySQL = new SQL();

    boolean doesConnectionExist = false;
    double[] data = new double[1001];
    double[] akrivData = new double[1001];

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
            int i = 5;
            Platform.runLater(LinechartThread);
            Platform.runLater(arkivThread);
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

    // thread til at at hente mållinger fra seriel porten kalder filter metoden og får 500? mållinger
    public void StartMållinger(LineChart<NumberAxis, NumberAxis> linechart, String textField, Label BPMdata) {
        if (!doesConnectionExist) {
            S1 = new Sensor();
            EKGchart = linechart;
            textFieldT = textField;
            BPM = BPMdata;
            // indstilling af linchart
            setupEKGChart(EKGchart);
            //indtilling af prioritet
            LinechartThread.setPriority(5);
            arkivThread.setPriority(1);
            doesConnectionExist = true;
        }
        // her indstilles og køres eventhandler, den modtager en runnable og run'er denne i det indstillede interval
        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(m1, 0, 4, TimeUnit.SECONDS);
    }
    public void startArkiv(String CPR){
        MySQL.getSQLConnection();
        MySQL.createNewPatient(CPR);

        for (int i = 0; i < data.length; i++) {
            MySQL.insertIntoTable(CPR, data[i]);
        }

        MySQL.stopSQLConnection();
    }
    public void getEKGArkiv(String CPR, LineChart<NumberAxis, NumberAxis> EKGHistorik){
        MySQL.getSQLConnection();
        //her hentes og plottes data
        MySQL.getEKGDataFromTable(CPR, akrivData);
        plotEKGarkivChart(akrivData, EKGHistorik);

        MySQL.stopSQLConnection();
    }

    private void plotEKGarkivChart(double[] arkiv, LineChart<NumberAxis, NumberAxis> EKGHistorik) {
        arkivSerie.setName("EKG");
        EKGHistorik.getData().clear();
        EKGHistorik.getData().addAll(arkivSerie);
        System.out.println("opsætning af linechart 1 succesfuld");
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
        double localMax = Arrays.stream(data).max().getAsDouble();
        for (int i = 0; i < data.length; i++) {
            if (data[i] > (localMax * 0.8)) {
                counter++;
            }
        }
        return counter;
    }


}
