package org.example;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Threads extends Sensor{
    ScheduledExecutorService Eventhandler;

    int i = 0;
    String[] data = new String[200];

    // thread til at at hente mållinger fra seriel porten kalder filter metoden og får 500? mållinger
    public void readThread(){
        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(() ->
                Platform.runLater(() -> {
                    data = filter(data);
                    System.out.println("first test");
                    for (int i = 0; i < 20; i++) {
                        System.out.println("EKG data punkt = " + data[i]);
                    }
                    System.out.println("control " + i);
                    i++;

                    // måske skal dette implementers sådan, thread sleep skal begrænse mængden af null mållinger.
                    // kan lade sig gøre pga. seriel porte har en local cache og arduinoen har en bugger på 64 bit
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }), 0, 1, TimeUnit.SECONDS);
    }
}
