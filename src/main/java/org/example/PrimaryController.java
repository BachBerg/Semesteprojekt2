package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController extends Sensor{

    @FXML
    private void switchToSecondary() throws IOException {

        String[] data = new String[200];

        data = filter(data);

        System.out.println("first test");
        for (int i = 0; i < 20; i++) {
            System.out.println("EKG data punkt = " + data[i]);
        }
        data = null;

    }
}
