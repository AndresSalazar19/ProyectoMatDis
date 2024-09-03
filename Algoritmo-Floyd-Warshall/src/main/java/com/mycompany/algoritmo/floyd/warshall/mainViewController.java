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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import modulo.FloydWarshall;
import modulo.RowData;

/**
 *
 * @author asala
 */
public class mainViewController implements Initializable{

    @FXML
    private Line e1Line;
    @FXML
    private Line e2Line;    
    @FXML
    private Line e3Line;
    @FXML
    private Line e4Line;    
    @FXML
    private Line e5Line;
    @FXML
    private Line e6Line;    
    @FXML
    private Line e7Line;
    @FXML
    private Line e8Line;      
    @FXML
    private Line e9Line;
    @FXML
    private Line e10Line;      
    
    @FXML
    private TextField origenTF;

    @FXML
    private TextField destinoTF;

    @FXML
    private TableView<RowData> adjacencyMatrixTable;
    @FXML
    private TableColumn<RowData, String> rowIndexColumn;
    @FXML
    private TableColumn<RowData, String> col1;
    @FXML
    private TableColumn<RowData, String> col2;
    @FXML
    private TableColumn<RowData, String> col3;
    @FXML
    private TableColumn<RowData, String> col4;
    @FXML
    private TableColumn<RowData, String> col5;
    @FXML
    private TableColumn<RowData, String> col6;
    
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
    
    @FXML
    private void e1() {
        e1Line.setVisible(false);
    }

    @FXML
    private void e2() {
        System.out.println("AAA");
        e2Line.setVisible(false);
    }

    @FXML
    private void e3() {
        System.out.println("AAA");
        e3Line.setVisible(false);
    }

    @FXML
    private void e4() {
        System.out.println("AAA");
        e4Line.setVisible(false);
    }    @FXML
    private void e5() {
        System.out.println("AAA");
        e5Line.setVisible(false);
    }

    @FXML
    private void e6() {
        System.out.println("AAA");
        e6Line.setVisible(false);
    }    @FXML
    private void e7() {
        System.out.println("AAA");
        e7Line.setVisible(false);
    }

    @FXML
    private void e8() {
        System.out.println("AAA");
        e8Line.setVisible(false);
    }    @FXML
    private void e9() {
        System.out.println("AAA");
        e9Line.setVisible(false);
    }

    @FXML
    private void e10() {
        System.out.println("AAA");
        e10Line.setVisible(false);
    }

    private void actualizarTabla() {
        ObservableList<RowData> data = FXCollections.observableArrayList();

        for (int i = 0; i < grafo.length; i++) {
            data.add(new RowData(
                String.valueOf(i + 1), // Índice de la fila
                grafo[i][0] == INF ? "INF" : String.valueOf(grafo[i][0]),
                grafo[i][1] == INF ? "INF" : String.valueOf(grafo[i][1]),
                grafo[i][2] == INF ? "INF" : String.valueOf(grafo[i][2]),
                grafo[i][3] == INF ? "INF" : String.valueOf(grafo[i][3]),
                grafo[i][4] == INF ? "INF" : String.valueOf(grafo[i][4]),
                grafo[i][5] == INF ? "INF" : String.valueOf(grafo[i][5])
            ));
        }

        adjacencyMatrixTable.setItems(data);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar las columnas de la tabla con las propiedades del RowData
        rowIndexColumn.setCellValueFactory(cellData -> cellData.getValue().rowIndexProperty());
        col1.setCellValueFactory(cellData -> cellData.getValue().col1Property());
        col2.setCellValueFactory(cellData -> cellData.getValue().col2Property());
        col3.setCellValueFactory(cellData -> cellData.getValue().col3Property());
        col4.setCellValueFactory(cellData -> cellData.getValue().col4Property());
        col5.setCellValueFactory(cellData -> cellData.getValue().col5Property());
        col6.setCellValueFactory(cellData -> cellData.getValue().col6Property());

        // Llenar la tabla con la matriz de adyacencia
        actualizarTabla();
    }
}