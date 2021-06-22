package org.example;

import java.sql.*;
import java.time.Instant;

public class SQL {

    private static Connection connection;
    static Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    // indstillinger, valg af database
    static String username = "root";
    static String password = "1234";
    static String Schema = "semesterprojekt2";
    static String url = "jdbc:mysql://localhost:3306/" + Schema;
    private String Sql;


    public void getSQLConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            if (connection != null) {
                System.out.println("Connected to MYSQL Schema:" + Schema);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // metode til at lave en ny patient
    public void createNewPatient(String CPR) {
        String write_to_database1 = "INSERT INTO patienter (CPR) values(?);";
        try {
            preparedStatement = connection.prepareStatement(write_to_database1);
            preparedStatement.setString(1, CPR);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            System.out.println("CPR eksisterer allerede i systemet.");
        }
    }


    // metode til at skrive data i databsen
    public void insertIntoTable(String CPR, double[] EKGdata) {
        Timestamp tid = Timestamp.from(Instant.now());
        // for løkke der appender streng

        //læg data i skema
        Sql = "insert into maalinger (CPR,EKGMeasure) values" + "(" + CPR + "," + EKGdata[0] + ")";

        for (int i = 1; i < EKGdata.length; i++) {
            Sql = Sql + ",(" + CPR + "," + EKGdata[i] + ")";
        }
        Sql = Sql + ";";
        try {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // metode til at hente EKG data fra tabel
    public void getEKGDataFromTable(String CPR, double[] arkiv) {
        String query1 = "SELECT * FROM semesterprojekt2.maalinger where CPR=" + CPR + ";";
        int i = 0;
        try {
            resultSet = statement.executeQuery(query1);
            while (resultSet.next() && i < 1000) {
                arkiv[i] = resultSet.getDouble("EKGMeasure");
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // stop forbindelsen til databasen
    public void stopSQLConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
