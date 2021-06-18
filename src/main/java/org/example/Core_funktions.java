package org.example;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Core_funktions extends GenMetoder {

    XYChart.Series<NumberAxis, NumberAxis> EKGserie = new XYChart.Series<>();
    Sensor S1;
    ScheduledExecutorService Eventhandler;

    boolean doesConnectionExist = true;
    double[] data = new double[600];

    public LineChart<NumberAxis,NumberAxis> linechartT;
    public String textFieldT;


    // Tråd til at køre programmet parralelt med javvaFX tråden
    private final Thread m1 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("Der bliver kort en ny tråd");
            S1.filter(data);
            Platform.runLater(LinechartThread);
        }
    });

    private final Thread LinechartThread = new Thread(() -> plotLineChart(data));


    // thread til at at hente mållinger fra seriel porten kalder filter metoden og får 500? mållinger
    public void StartProgram(LineChart<NumberAxis, NumberAxis> linechart, String textField) {

        if (doesConnectionExist) {
            S1 = new Sensor();
            doesConnectionExist = false;
            linechartT = linechart;
            textFieldT = textField;
            setupChart(linechartT);
        }
        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(m1, 0, 4, TimeUnit.SECONDS);

        // der skal lave et metode som plotter puls


    }
    public void slukProgram(){
        Eventhandler.shutdown();
    }

    public void setupChart(LineChart<NumberAxis, NumberAxis> linechart) {
        EKGserie.setName("EKG");
        linechart.getData().clear();
        linechart.getData().addAll(EKGserie);
        System.out.println("opsætning af linechart succesfuld");
    }

    public void plotLineChart(double[] lokalArkiv) {
        EKGserie.getData().clear();
        for (int i = 0; i < lokalArkiv.length; i++) {
            EKGserie.getData().add(new XYChart.Data(i, lokalArkiv[i]));
        }
        System.out.println("plot linchart metode færdig");
    }
}
