package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController extends Threads{

    @FXML
    private void switchToSecondary() throws IOException {
        readThread();
    }
}
