package org.example;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Core_funktions {
    ScheduledExecutorService Eventhandler;

    public int i = 0, f=0;
    String[] data = new String[200];

    // thread til at at hente mållinger fra seriel porten kalder filter metoden og får 500? mållinger
    public void readThread(){
        Sensor S1 = new Sensor();


        while(true){
            S1.filter(data);
            m1.run();
            System.out.println(f);
        }


    }


    // udskriver indlæst data
    Thread m1 = new Thread(() -> { // Lambda Expression
        for (int j = 0; j < data.length; j++) {
            System.out.println(data[j]);

            try {
                Thread.sleep(500);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    });

    public void plotLineChart(LineChart<CategoryAxis, NumberAxis> linechart, TextField textField) {
        XYChart.Series EKGserie = new XYChart.Series();
        EKGserie.setName("EKG");
        linechart.getData().clear();
        linechart.getData().addAll(EKGserie);
        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(() ->
                Platform.runLater(() -> {
                    String x = String.valueOf(i);

                    double y = 0;
                    EKGserie.getData().add(new XYChart.Data(x, y));
                    i++;
                }), 0, 1, TimeUnit.SECONDS);




    }




    public void error(String message) {
        Stage allertStage = new Stage();
        StackPane allertLayout = new StackPane();
        Button allertButton = new Button();
        Label alertLabel = new Label();

        allertButton.setText("OK");
        alertLabel.setText(message);
        allertStage.setTitle("Alert");

        allertButton.setOnAction(p -> allertStage.close());
        allertLayout.getChildren().addAll(allertButton, alertLabel);
        Scene allertScene = new Scene(allertLayout, 200, 100);
        alertLabel.setTranslateY(-25);

        allertStage.setScene(allertScene);
        allertStage.initModality(Modality.APPLICATION_MODAL);
        allertStage.show();
    }
}
