package org.example;
import jssc.SerialPort;
import jssc.SerialPortException;
import java.lang.*;


public class Sensor {
    public SerialPort port = new SerialPort("COM7");

    // kontruktør som initialisere port objektet
    private Sensor(){

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
}
