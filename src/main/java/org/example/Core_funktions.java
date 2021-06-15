package org.example;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Core_funktions extends GenMetoder {
    final XYChart.Series EKGserie = new XYChart.Series();
    public int i = 0, y = 0;
    boolean yeet = true;
    String[] data = new String[500];
    Sensor S1;
    public LineChart<NumberAxis, NumberAxis> linechartT;
    public String textFieldT;
    ScheduledExecutorService Eventhandler = Executors.newSingleThreadScheduledExecutor();


    // udskriver indlæst data
    Thread m1 = new Thread(() -> { // Lambda Expression
        S1.filter(data);
        Platform.runLater(this::plotLineChart);
    });


    // thread til at at hente mållinger fra seriel porten kalder filter metoden og får 500? mållinger
    public void StartProgram(LineChart<NumberAxis, NumberAxis> linechart, String textField) {
        linechartT = linechart;
        textFieldT = textField;
        setupChart(linechartT);
        if (yeet) {
            S1 = new Sensor();
            yeet = false;
        }

        Eventhandler.scheduleAtFixedRate((m1), 0, 1, TimeUnit.SECONDS);
    }

    public void setupChart(LineChart<NumberAxis, NumberAxis> linechart) {
        EKGserie.setName("EKG");
        linechart.getData().clear();
        linechart.getData().addAll(EKGserie);
    }

    public void plotLineChart() {

        while (i < data.length) {
            if (data[i] != null) {
                double y_akse = Double.parseDouble(data[y]);

                EKGserie.getData().add(new XYChart.Data(i, y_akse));
            }
            i++;
            y++;
        }
        System.out.println("print succes");
    }

}
