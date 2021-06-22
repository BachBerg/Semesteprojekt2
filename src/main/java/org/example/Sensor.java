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
            port.setParams(57600, 8, 1, 0);
            port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            port.purgePort(SerialPort.PURGE_TXCLEAR | SerialPort.PURGE_RXCLEAR);

            System.out.println("opsætning af serielport succesfuld");
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
    public void filter(double[] EKGdata) {
        int i = 0;
        String buffer = "", var="";

        while (i <= EKGdata.length-1) {
            // Der læses fra seriel porten
            var = readData();
            if(var != null){
                buffer = buffer + var;
                System.out.println(buffer);
            }
            // Når man har en fuldstændig streng, bliver den sorteret og bufferen tømmes.
            if (buffer.endsWith("R")) {
                String[] aLilPieceOfData = buffer.split("R");
                System.out.println("lægger data over i array");
                for (int j = 0; j < aLilPieceOfData.length; j++) {

                    try {
                        EKGdata[i] = Double.parseDouble(aLilPieceOfData[j]);
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                        EKGdata[i] = 1;
                    }
                    //System.out.println("EKGdata " + EKGdata[i] + " var i = " + i);
                    i++;

                    // kontrol af antal mållinger
                    if (i>=EKGdata.length-1){
                        break;
                    }
                }
                if (i<EKGdata.length){
                    buffer = "";
                }
            }
            // Thread sleep kald for at reducere antal null mållinger
            // kan lade sig gøre pga. seriel porte har en local cache og arduinoen har en bugger på 64 bit
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
