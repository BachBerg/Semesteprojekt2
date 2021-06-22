package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class SQLTest {
    private static Connection connection;
    SQL mysql = new SQL();

    @Test
    void getSQLConnection() {
        mysql.getSQLConnection();

    }

    @Test
    void createNewPatient() throws SQLException {
        mysql.getSQLConnection();
        mysql.createNewPatient("1234567890");
    }

    @Test
    void insertIntoTable() {
        double[] test_EKGdata = new double[10];
        mysql.getSQLConnection();
        mysql.insertIntoTable("1234567890", test_EKGdata);
    }

    @Test
    void getEKGDataFromTable() {
        double[] test_EKGdata = new double[10];
        mysql.getSQLConnection();
        mysql.getEKGDataFromTable("1234567890", test_EKGdata);

    }

    @Test
    void stopSQLConnection() {
        mysql.getSQLConnection();
        mysql.stopSQLConnection();
    }
}