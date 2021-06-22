package org.example;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class Core_funktionsTest {
    Core_funktions Cf = new Core_funktions();
    XYChart.Series<NumberAxis, NumberAxis> test_EKGserie = new XYChart.Series<>();
    XYChart.Series<NumberAxis, NumberAxis> test_arkivSerie = new XYChart.Series<>();
    Sensor S1;
    ScheduledExecutorService Eventhandler;
    ExecutorService EventhandlerSQL = Executors.newSingleThreadExecutor();
    SQL MySQL = new SQL();

    private final Thread m1 = new Thread(new Runnable(){

        @Override
        public void run() {

        }
    });
    private LineChart<NumberAxis, NumberAxis> EKGHistorik;


    @Test
    void startMållingerTest() {

        S1 = new Sensor();

        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(m1, 0, 2, TimeUnit.SECONDS);
        Assertions.assertTrue(!m1.isAlive());
    }

    @Test
    void startArkivTest() {
        String CPR = "2302981771";
        double[] data = new double[5];
        MySQL.getSQLConnection();
        MySQL.createNewPatient(CPR);
        MySQL.insertIntoTable(CPR, data);
        MySQL.stopSQLConnection();
        System.out.println(data[0] + data[1] + data[2] + data[3] + data[4]);
    }

    @Test
    void getEKGArkiv() {
        MySQL.getSQLConnection();
        String CPR = "2302981771";
        double[] akrivData = new double[5];

        MySQL.getEKGDataFromTable(CPR, akrivData);
        Assertions.assertFalse(akrivData[0] != akrivData[1]);

        MySQL.stopSQLConnection();
    }

    @Test
    void slukProgram() {
        Eventhandler = Executors.newSingleThreadScheduledExecutor();
        Eventhandler.scheduleAtFixedRate(m1, 0, 2, TimeUnit.SECONDS);
        Eventhandler.shutdown();
        Assertions.assertTrue(Eventhandler.isShutdown());
    }

    @Test
    void setupEKGChart() {
        test_EKGserie.setName("EKG");
        Assertions.assertTrue(test_EKGserie.getName().equals("EKG"));
    }

    @Test
    void plotEKGChart() {
        test_EKGserie.getData().clear();
        double[] EKGarkiv = new double[10];
        for (int i = 0; i < EKGarkiv.length; i++) {
            test_EKGserie.getData().add(new XYChart.Data(i, EKGarkiv[i]));
            System.out.println(EKGarkiv[i]);
        }

    }
}