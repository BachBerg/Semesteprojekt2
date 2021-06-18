package org.example;

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
    Sensor S1;
    ScheduledExecutorService Eventhandler;

    boolean doesConnectionExist = true;
    double[] data = new double[1001];

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
        }
    });
    // denne tråd indholder de metoder som plotter vores grafer
    private final Thread LinechartThread = new Thread(() -> {
        plotEKGChart(data);
        BPM.setText(String.valueOf(pulsCalc(data)));
        // metode til at gemme her
    });

    // thread til at at hente mållinger fra seriel porten kalder filter metoden og får 500? mållinger
    public void StartMållinger(LineChart<NumberAxis, NumberAxis> linechart, String textField, Label BPMdata) {
        if (doesConnectionExist) {
            S1 = new Sensor();
            doesConnectionExist = false;
            EKGchart = linechart;
            textFieldT = textField;
            BPM = BPMdata;
            setupEKGChart(EKGchart);
        }
        // her indstilles og køres evenhandler, den modtager en runnable og run'er denne i det indstillede interval
        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(m1, 0, 4, TimeUnit.SECONDS);
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
