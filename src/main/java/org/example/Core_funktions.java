package org.example;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Core_funktions extends GenMetoder {

    XYChart.Series EKGserie = new XYChart.Series();
    Sensor S1;
    ScheduledExecutorService Eventhandler;

    public int i = 0, y = 0;
    boolean yeet = true;
    double[] data = new double[500];

    public LineChart linechartT;
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

    private final Thread LinechartThread = new Thread(() -> {
        plotLineChart(data);

    });


    // thread til at at hente mållinger fra seriel porten kalder filter metoden og får 500? mållinger
    public void StartProgram(LineChart<NumberAxis, NumberAxis> linechart, String textField) {

        if (yeet) {
            S1 = new Sensor();
            yeet = false;
            linechartT = linechart;
            textFieldT = textField;
            setupChart(linechartT);
        }
        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.execute(m1);
    }

    public void setupChart(LineChart<NumberAxis, NumberAxis> linechart) {
        EKGserie.setName("EKG");
        linechart.getData().clear();
        linechart.getData().addAll(EKGserie);
        System.out.println("opsætning af linechart succesfuld");
    }

    public void plotLineChart(double[] lokalArkiv) {
        while (i < lokalArkiv.length) {
            if(lokalArkiv[i] > 0){
                EKGserie.getData().add(new XYChart.Data(i, lokalArkiv[y]));
            }
            i++;
            y++;
        }
        System.out.println("plot linchart metode færdig");
    }
}
