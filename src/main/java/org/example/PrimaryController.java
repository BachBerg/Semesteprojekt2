package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");

        String[] EKGdata = new String[40];
        Sensor ree= new Sensor();
        EKGdata = ree.filter(EKGdata);
        System.out.println("first test");
        for (int i = 0; i < 20; i++) {
            System.out.println("EKG data punkt = " + EKGdata[i]);
        }

    }
}
