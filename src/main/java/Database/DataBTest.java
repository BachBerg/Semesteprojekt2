package Database;

public class DataBTest {

    public static void main(String[] args) {
        SQL MySQL = new SQL();
        MySQL.getSQLConnection();

        MySQL.createNewPatient("2302981771");

        //MySQL.insertIntoTable("2302981771", 1200);

        //MySQL.findEKGMeasureFromPatient(123456789);
        System.out.println("--------");

        MySQL.stopSQLConnection();

    }
}
