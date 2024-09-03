/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algoritmo.floyd.warshall;
import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modulo.FloydWarshall;
import modulo.RowData;

/**
 *
 * @author asala
 */
public class mainViewController implements Initializable{

    @FXML
    private TextField origenTF;

    @FXML
    private TextField destinoTF;

    @FXML
    private TableView<RowData> adjacencyMatrixTable;
    
    final static int INF = Integer.MAX_VALUE;   
    
    int[][] grafo = {
        {0, 700, 200, INF, INF, INF}, // Nodo 1
        {700, 0, 300, 400, INF, INF}, // Nodo 2
        {200, 300, 0, 700, INF, INF}, // Nodo 3
        {INF, 400, 700, 0, 300, 100}, // Nodo 4
        {INF, INF, INF, 300, 0, 500}, // Nodo 5
        {INF, INF, INF, 100, 500, 0}  // Nodo 6
    };
        
    private SerialPort serialPort;

    @FXML
    private Button connectButton;
    @FXML
    private Button ledOnButton;
    @FXML
    private Button ledOffButton;

    @FXML
    private void connectToBluetooth() {
        String portName = "COM5"; 

        serialPort = SerialPort.getCommPort(portName);
        serialPort.setComPortParameters(9600, 8, 1, 0);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if (serialPort.openPort()) {
            System.out.println("Conexión exitosa al módulo Bluetooth en " + portName);
            new Thread(this::readSerial).start(); // Iniciar el hilo para leer datos
        } else {
            System.out.println("Error al conectar con el módulo Bluetooth en " + portName);
        }
    }

    @FXML
    private void readSerial() {
        InputStream in = serialPort.getInputStream();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Platform.runLater(() -> {
                System.out.println("Mensaje recibido: " + line);
            });
        }
    }


    @FXML
    private void sendLedOn() {
        sendCommand("1");
    }

    @FXML
    private void sendLedOff() {
        sendCommand("0");
    }

    private void sendCommand(String command) {
        if (serialPort != null && serialPort.isOpen()) {
            byte[] dataBytes = command.getBytes();
            serialPort.writeBytes(dataBytes, dataBytes.length);
            System.out.println("Comando enviado: " + command);
        } else {
            System.out.println("Conexión no establecida");
        }
    }

    @FXML
    private void disconnectBluetooth() {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
            System.out.println("Desconectado del módulo Bluetooth");
        }
    }

    @FXML
    public void hallarRutaMasCorta() {
        try {
            int origen = Integer.parseInt(origenTF.getText());
            int destino = Integer.parseInt(destinoTF.getText());

            // Validar que los nodos estén en el rango [1-6]
            if (origen < 1 || origen > 6 || destino < 1 || destino > 6) {
                mostrarAlerta("Error de entrada", "Los nodos deben estar entre 1 y 6.");
                return;
            }

            // Ajustar los valores para trabajar con índices de matriz (0-5)
            origen--;
            destino--;

            FloydWarshall fw = new FloydWarshall();
            // Ejecutar el algoritmo de Floyd-Warshall
            int[][] next = fw.floydWarshall(grafo);

            // Mostrar la ruta en la consola o en un componente gráfico
            fw.printPath(origen, destino, next);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de entrada", "Por favor, ingrese números válidos.");
        }
    }
 
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void eliminarArista1(){
    
    }
    
    public void insertarArista1(int peso){
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }
}