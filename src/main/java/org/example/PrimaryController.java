package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController extends Core_funktions {

    @FXML
    private void switchToSecondary() throws IOException {

        App.setRoot("GUI");
    }
}
