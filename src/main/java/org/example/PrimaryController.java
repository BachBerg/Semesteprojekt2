package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");

        String[] EKGdata = new String[11];
        Sensor ree= new Sensor();
        EKGdata = ree.filter(EKGdata);

        for (int i = 0; i < 10; i++) {
            System.out.println(EKGdata[i]);
        }

    }
}
