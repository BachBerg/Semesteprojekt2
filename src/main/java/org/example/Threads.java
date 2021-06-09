package org.example;

import javafx.application.Platform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Threads extends Sensor{
    ScheduledExecutorService Eventhandler;

    int i = 0;
    String[] data = new String[200];

    public void readThread(){

        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(() ->
                Platform.runLater(() -> {


                    data = filter(data);

                    System.out.println("first test");
                    for (int i = 0; i < data.length; i++) {
                        System.out.println("EKG data punkt = " + data[i]);
                    }

                    System.out.println("control " + i);
                    i++;
                }), 0, 1, TimeUnit.SECONDS);
    }
}
