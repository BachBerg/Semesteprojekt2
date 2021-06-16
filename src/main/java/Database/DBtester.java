package Database;

public class DBtester {

    public static void main(String[] args) {

        Connection MySQL = new Connection();
        MySQL.getSQLConnection("bruger1", "kode1", "semesterprojekt2");
        MySQL.insertIntoTable(1);



            MySQL.findEKGMeasureFromPatient(55);
            System.out.println("--------");

    }
}
