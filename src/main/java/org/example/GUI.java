package org.example;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ChoiceBox;
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

    @FXML
    private ChoiceBox wombocombo = new ChoiceBox();

    private int[] Id = new int[40];



    // start knappen fra historik scene kalder metode til at hente data fra arkiv
    public void retrieveData(){
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().add("Choice 1");
        choiceBox.getItems().add("Choice 2");
        choiceBox.getItems().add("Choice 3");

        // først kontrolleres om CPR eksitere
        if (c2.cprCheck2(CPR.getText())){
            // metode som finder mulige ID og indsætter dem i tabel
            c2.MySQL.getThatMcFattyPatty(CPR2.getText(),Id);
            for (int i = 0; i < Id.length; i++) {
                if(Id[i] != 0){
                    wombocombo.getItems().add(Id[i]);
                }

            }
            // hentes data fra arkiv og plottes til graf
            c2.getEKGArkiv(, EKGHistorik);
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
