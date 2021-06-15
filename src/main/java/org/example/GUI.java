package org.example;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GUI {

    @FXML
    private LineChart<?, ?> EKGStart;

    @FXML
    private Button startMaaling;

    @FXML
    private Button stopMaaling;

    @FXML
    private TextField fødselsdato;

    @FXML
    private TextField fireCifre;

    @FXML
    private TableView<?> pulsSkema;

    @FXML
    private LineChart<?, ?> pulsStart;

    @FXML
    private TableView<?> Historik;

    @FXML
    private ChoiceBox<?> vælgDag;


    @FXML
    private LineChart<?, ?> EKGHistorik;

    @FXML
    private LineChart<?, ?> pulsHistorik;

    @FXML
    private Button retrieveData;

    @FXML
    private TextField CPRHistorik;

}
