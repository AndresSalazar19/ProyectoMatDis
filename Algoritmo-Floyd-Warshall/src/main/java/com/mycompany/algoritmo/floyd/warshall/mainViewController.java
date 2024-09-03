/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algoritmo.floyd.warshall;
import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Line;
import modulo.FloydWarshall;
import modulo.RowData;

/**
 *
 * @author asala
 */
public class mainViewController implements Initializable{
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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
    private Label p1Label;
    
    @FXML
    private Label p2Label;
        
    @FXML
    private Label p3Label;
    @FXML
    private Label p4Label;

    @FXML
    private Label p5Label;

    @FXML
    private Label p6Label;

    @FXML
    private Label p7Label;

    @FXML
    private Label p8Label;

    @FXML
    private Label p9Label;

    @FXML
    private Label p10Label;
    
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
        {700, 0, 300, 200, INF, 400}, // Nodo 2
        {200, 300, 0, 700, 600, INF}, // Nodo 3
        {INF, 200, 700, 0, 300, 100}, // Nodo 4
        {INF, INF, 600, 300, 0, 500}, // Nodo 5
        {INF, 400, INF, 100, 500, 0}  // Nodo 6
    };
        
    private SerialPort serialPort;

    @FXML
    private Button connectButton;
    @FXML
    private Button ledOnButton;
    @FXML
    private Button ledOffButton;

    private Map<String, String> estadoAnterior = new HashMap<>();

@FXML
private void connectToBluetooth() {
    String portName = "COM5"; // Cambia esto por el puerto COM que anotaste

    serialPort = SerialPort.getCommPort(portName);
    serialPort.setComPortParameters(9600, 8, 1, 0);
    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

    if (serialPort.openPort()) {
        System.out.println("Conexión exitosa al módulo Bluetooth en " + portName);
        
        // Crear y arrancar un hilo que actúe como un loop constante
        Thread serialThread = new Thread(() -> {
            try {
                InputStream in = serialPort.getInputStream();
                Scanner scanner = new Scanner(in);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    Platform.runLater(() -> {
                        procesarComando(line); // Procesar los comandos inmediatamente
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        serialThread.setDaemon(true); // Permite que el hilo se detenga cuando se cierra la aplicación
        serialThread.start(); // Inicia el hilo

        // Programar la ejecución periódica de detectarCambioEstado
        scheduler.scheduleAtFixedRate(() -> Platform.runLater(this::verificarEstados), 0, 1, TimeUnit.SECONDS);
        
    } else {
        System.out.println("Error al conectar con el módulo Bluetooth en " + portName);
    }
}

    private void verificarEstados() {
        readSerial();
    }


    

    @FXML
    private void readSerial() {
        InputStream in = serialPort.getInputStream();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Platform.runLater(() -> {
                System.out.println("Mensaje recibido: " + line);
                procesarComando(line);
            });
        }
    }

    private void procesarComando(String comando) {
        if (comando.startsWith("e") && comando.length() >= 4) {
            int arista = Character.getNumericValue(comando.charAt(1));
            String estado = comando.substring(2).toUpperCase();

            switch (arista) {
                case 1:
                    if (estado.equals("ON")) {
                        e1(); // Llama al método e1
                    } else {
                        eliminarArista(0, 1);
                    }
                    break;
                case 2:
                    if (estado.equals("ON")) {
                        e2();
                    } else {
                        eliminarArista(1, 5);
                    }
                    break;
                case 3:
                    if (estado.equals("ON")) {
                        e3();
                    } else {
                        eliminarArista(1, 2);
                    }
                    break;
                case 4:
                    if (estado.equals("ON")) {
                        e4();
                    } else {
                        eliminarArista(1, 3);
                    }
                    break;
                case 5:
                    if (estado.equals("ON")) {
                        e5();
                    } else {
                        eliminarArista(3, 5);
                    }
                    break;
                case 6:
                    if (estado.equals("ON")) {
                        e6();
                    } else {
                        eliminarArista(4, 5);
                    }
                    break;
                case 7:
                    if (estado.equals("ON")) {
                        e7();
                    } else {
                        eliminarArista(0, 2);
                    }
                    break;
                case 8:
                    if (estado.equals("ON")) {
                        e8();
                    } else {
                        eliminarArista(2, 3);
                    }
                    break;
                case 9:
                    if (estado.equals("ON")) {
                        e9();
                    } else {
                        eliminarArista(3, 4);
                    }
                    break;
                case 10:
                    if (estado.equals("ON")) {
                        e10();
                    } else {
                        eliminarArista(2, 4);
                    }
                    break;
                default:
                    System.out.println("Comando no reconocido: " + comando);
            }
        } else {
            System.out.println("Comando no válido: " + comando);
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
            scheduler.shutdownNow(); // Detener el scheduler
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
            fw.printSolution(next);
            System.out.println("aass");
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
    

    
    public void insertarArista1(int peso){
        
    }
    
    @FXML
    private void e1() {
        if (grafo[0][1] == INF) { // Nodo 1 y Nodo 2
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e1: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(0, 1, peso);
                        e1Line.setVisible(true);
                        p1Label.setText(String.valueOf(peso));
                        p1Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(0, 1);
            e1Line.setVisible(false);
            p1Label.setVisible(false);
        }
    }

    @FXML
    private void e2() {
        if (grafo[1][5] == INF) { // Nodo 2 y Nodo 6
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e2: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(1, 5, peso);
                        e2Line.setVisible(true);
                        p2Label.setText(String.valueOf(peso));
                        p2Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(1, 5);
            e2Line.setVisible(false);
            p2Label.setVisible(false);
        }
    }

    @FXML
    private void e3() {
        if (grafo[1][2] == INF) {
            // Crear el diálogo de entrada de texto para ingresar el peso
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e3: ");
            dialog.setContentText("Peso:");

            // Mostrar el diálogo y esperar la respuesta del usuario
            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);

                    // Validar que el peso sea mayor que 0 y menor que 10000
                    if (peso > 0 && peso < 10000) {
                        insertarArista(1, 2, peso); // Insertar la arista con el peso proporcionado
                        e3Line.setVisible(true);
                        p3Label.setText(String.valueOf(peso));
                        p3Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(1, 2);
            e3Line.setVisible(false);
            p3Label.setVisible(false);
        }
    }

    @FXML
    private void e4() {
        if (grafo[1][3] == INF) { // Nodo 2 y Nodo 4
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e4: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(1, 3, peso);
                        e4Line.setVisible(true);
                        p4Label.setText(String.valueOf(peso));
                        p4Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(1, 3);
            e4Line.setVisible(false);
            p4Label.setVisible(false);
        }
    }


    @FXML
    private void e5() {
        if (grafo[3][5] == INF) { // Nodo 4 y Nodo 6
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e5: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(3, 5, peso);
                        e5Line.setVisible(true);
                        p5Label.setText(String.valueOf(peso));
                        p5Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(3, 5);
            e5Line.setVisible(false);
            p5Label.setVisible(false);
        }
    }

    @FXML
    private void e6() {
        if (grafo[4][5] == INF) { // Nodo 5 y Nodo 6
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e6: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(4, 5, peso);
                        e6Line.setVisible(true);
                        p6Label.setText(String.valueOf(peso));
                        p6Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(4, 5);
            e6Line.setVisible(false);
            p6Label.setVisible(false);
        }
    }

    @FXML
    private void e7() {
        if (grafo[0][2] == INF) { // Nodo 1 y Nodo 3
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e7: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(0, 2, peso);
                        e7Line.setVisible(true);
                        p7Label.setText(String.valueOf(peso));
                        p7Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(0, 2);
            e7Line.setVisible(false);
            p7Label.setVisible(false);
        }
    }

    @FXML
    private void e8() {
        if (grafo[2][3] == INF) { // Nodo 3 y Nodo 4
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e8: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(2, 3, peso);
                        e8Line.setVisible(true);
                        p8Label.setText(String.valueOf(peso));
                        p8Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(2, 3);
            e8Line.setVisible(false);
            p8Label.setVisible(false);
        }
    }

    @FXML
    private void e9() {
        if (grafo[3][4] == INF) { // Nodo 4 y Nodo 5
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e9: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(3, 4, peso);
                        e9Line.setVisible(true);
                        p9Label.setText(String.valueOf(peso));
                        p9Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(3, 4);
            e9Line.setVisible(false);
            p9Label.setVisible(false);
        }
    }

    @FXML
    private void e10() {
        if (grafo[2][4] == INF) { // Nodo 3 y Nodo 5
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Arista");
            dialog.setHeaderText("Ingrese el peso de la arista e10: ");
            dialog.setContentText("Peso:");

            dialog.showAndWait().ifPresent(response -> {
                try {
                    int peso = Integer.parseInt(response);
                    if (peso > 0 && peso < 10000) {
                        insertarArista(2, 4, peso);
                        e10Line.setVisible(true);
                        p10Label.setText(String.valueOf(peso));
                        p10Label.setVisible(true);
                    } else {
                        mostrarAdvertencia("Advertencia", "El peso debe ser mayor que 0 y menor que 10,000.");
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Debe ingresar un número válido para el peso.");
                }
            });
        } else {
            eliminarArista(2, 4);
            e10Line.setVisible(false);
            p10Label.setVisible(false);
        }
    }

    private void eliminarArista(int nodo1, int nodo2) {
        grafo[nodo1][nodo2] = INF; // Eliminar la arista en la matriz de adyacencia
        grafo[nodo2][nodo1] = INF; // Dado que es un grafo no dirigido, eliminamos ambas direcciones
        actualizarTabla();         // Actualizar la tabla para reflejar los cambios en la matriz
    }
    
    private void insertarArista(int nodo1, int nodo2, int peso) {
        grafo[nodo1][nodo2] = peso; // Insertar la arista en la matriz de adyacencia con el peso especificado
        grafo[nodo2][nodo1] = peso; // Para un grafo no dirigido, también actualiza la otra dirección
        actualizarTabla(); // Actualizar la tabla para reflejar los cambios
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
    
    private void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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