/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo;
import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author asala
 */
public class BluetoothCommunication {
    
    public static void establecerConexion(){
        // Encuentra el puerto serial correspondiente al módulo Bluetooth
        SerialPort comPort = SerialPort.getCommPorts()[0]; // Ajusta el índice según el puerto correcto
        comPort.setBaudRate(9600);

        if (comPort.openPort()) {
            System.out.println("Puerto Bluetooth abierto con éxito.");
        } else {
            System.out.println("Fallo al abrir el puerto Bluetooth.");
            
        }

        // Enviar datos al Arduino a través de Bluetooth
        try {
            String data = "1"; // Datos a enviar, por ejemplo, encender un LED
            comPort.getOutputStream().write(data.getBytes());
            comPort.getOutputStream().flush();
            System.out.println("Datos enviados: " + data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cerrar el puerto después de usarlo
        comPort.closePort();
    }
}