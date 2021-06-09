package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

    static String url = "jdbc:mysql://localhost:3306/login";
    static String user = "root";
    static String password = "";
    static Connection myConn;
    static Statement myStatement;


    // metode til at oprette forbindelse til SQL databse
    // conneciton og statement obejkter bliver initialieret
    public void makeConnectionSQL() {
        try {
            myConn = DriverManager.getConnection(url, user, password);
            myStatement = myConn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // metode til at aflsutte forbindelsen til databasen når programmet slukker
    public void removeConnectionSQL() {
        try {
            if (!myConn.isClosed()) {
                myConn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
