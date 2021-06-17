package Database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class Connection {

    private static java.sql.Connection connection = null;
    private static String MYSQLDriver = "jdbc:mysql://" + "localhost:3306/";
    private static String url;


    public static java.sql.Connection getSQLConnection(String username, String password, String Schema) {
        url = MYSQLDriver + Schema + "?serverTimezone=Europe/Amsterdam&amp";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                System.out.println("Connected to MYSQL Schema:" + Schema);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void insertIntoTable(int id) {

        for (int n = 0; n < 10; n++) {

            int CPR=123456789;
            double randompuls = (20 + (Math.random() * 25));
            int EKG = (int) (100 - (100 * Math.random()));

            Timestamp tid= Timestamp.from(Instant.now());

            //læg data i skema
            String SQL = "insert into maalinger (CPR,EKGMaaling,time) values(?,?,?);";
            try {
                preparedStatement = connection.prepareStatement(SQL);
                preparedStatement.setDouble(1, CPR);
                preparedStatement.setInt(2, EKG);
                preparedStatement.setTimestamp(3, tid);
                preparedStatement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

   public void findEKGMeasureFromPatient(int ID){ //bliver brugt til at
        String SQL="SELECT * FROM semesterprojekt2.maalinger where measureID="+ID+";";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(SQL);
            while (resultSet.next()){
                System.out.println(
                        "ID: "+resultSet.getInt(1)+"\n"+
                                "EKGMaaling:"+resultSet.getInt("EKGMaaling")+"\n"+
                                 "CPR:"+resultSet.getInt("CPR")+"\n" +
                                "time:"+resultSet.getTimestamp("time")+"\n"

                );
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


