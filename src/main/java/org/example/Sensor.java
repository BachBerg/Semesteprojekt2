package org.example;
import jssc.SerialPort;
import jssc.SerialPortException;
import java.lang.*;


public class Sensor {
    public SerialPort port = new SerialPort("COM3");

    // kontruktør som initialisere port objektet
    public Sensor(){

        try {
            port.openPort();
            port.setParams(115200, 8, 1, 0);
            port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            port.purgePort(SerialPort.PURGE_TXCLEAR | SerialPort.PURGE_RXCLEAR);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    // læsning metode til at læse fra porten og returnere data
    public String readData(){
        String data = null;
        try {
            if(port.getInputBufferBytesCount() > 0){
                data = port.readString();
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return data;
    }

    // metode til at lægge data i et array med readData metoden
    public String[] filter(String[] EKGdata) {
        int i = 0;
        String buffer = "";


        while (i < 10) {
            // Der læses fra seriel porten
            String var = readData();

            // De forskellige bider data bliver sæt sammen til en streng
            if (var != null) {
                buffer = buffer + var;
            }

            // Når man har en fuldstændig streng, bliver den sorteret og bufferen tømmes.
            if (buffer.endsWith("R")) {
                EKGdata = buffer.split("R");

                buffer = "";
                i++;
            }
        }

        return EKGdata;
    }
}
