package Database;

public class DataBTest {

    public static void main(String[] args) {
        SQL MySQL = new SQL();
        MySQL.getSQLConnection("bruger1", "kode1", "semesterprojekt2");
        MySQL.insertIntoTable(1);


                MySQL.findEKGMeasureFromPatient(123456789);
            System.out.println("--------");

    }
}
