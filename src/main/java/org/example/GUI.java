package org.example;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;

public class GUI {
    Core_funktions c2 = new Core_funktions();

    @FXML
    private LineChart<?, ?> EKGStart;

    @FXML
    private TextField CPR;

    @FXML
    private TextField CPR2;

    @FXML
    private LineChart<?, ?> pulsStart;


    public void button1 (){
        // først skal denne funktion testes!
        //c2.readThread();


        /*
        if (c2.cprCheck2(CPR.getText())){
            c2.plotLineChart((LineChart<CategoryAxis, NumberAxis>) EKGStart, CPR);
            // her skal der yderligere være en metode som plotter puls linchart
            //c2.plotLineChart((LineChart<CategoryAxis, NumberAxis>) pulstart, CPR);
        }else{
            c2.error("git gud");
        }*/
    }
    public void button2(){
        // denne knap skal plotte graferne under Historik tap'en.
        // hente data med sql og alt muligt
        System.out.println("træls");
    }



}
