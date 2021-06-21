package org.example;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GUI {
    Core_funktions c2 = new Core_funktions();

    @FXML
    private LineChart<NumberAxis, NumberAxis> EKGStart;

    @FXML
    private LineChart<NumberAxis, NumberAxis> EKGHistorik;

    @FXML
    private TextField CPR;

    @FXML
    private TextField CPR2;

    @FXML
    private Label BPMdata;

    // start knappen fra historik scene kalder metode til at hente data fra arkiv
    public void retrieveData(){

        if (c2.cprCheck2(CPR.getText())){
            c2.getEKGArkiv(CPR2.getText(), EKGHistorik);
        }else{
            c2.error("Indtast et korrekt CPR");
        }
    }

    public void button1 (){
        // først skal denne funktion testes!
        if (c2.cprCheck2(CPR.getText())){
            c2.StartMållinger(EKGStart, CPR.getText(), this.BPMdata);
        }else{
            c2.error("Indtast et korrekt CPR");
        }
    }
    public void button2(){
        c2.slukProgram();
    }
}
