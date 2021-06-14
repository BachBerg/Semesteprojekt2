package org.example;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GUI {
        Core_funktions c2 = new Core_funktions();
    @FXML
    private LineChart<?, ?> EKGStart;

    @FXML
    private Button StartMaaling;

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

    public void button1 (){
        if (fødselsdato == null){
            c2.error("git gud");
        }else{
            c2.plotLineChart((LineChart<CategoryAxis, NumberAxis>) pulsStart, fødselsdato);
            // her skal der yderligere være en metode som plotter puls linchart
        }
    }
    public void button2(){
        // denne knap skal plotte graferne under Historik tap'en.
    }



}
