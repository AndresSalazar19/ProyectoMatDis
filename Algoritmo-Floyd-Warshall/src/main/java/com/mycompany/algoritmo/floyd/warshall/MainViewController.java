/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algoritmo.floyd.warshall;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modulo.*;
/**
 *
 * @author asala
 */
public class MainViewController {

    @FXML
    private TextField filasField;
    
    @FXML
    private TextField columnasField;

    private GridViewController gridController;
    private Stage gridStage;

    @FXML
    private void createGrid() throws IOException {
        String filasText = filasField.getText();
        String columnasText = columnasField.getText();
        
        if (filasText.isEmpty() || columnasText.isEmpty()) {
            showAlert("Error", "Los campos de filas y columnas no pueden estar vacíos.");
            return;
        }

        int filas;
        int columnas;

        try {
            filas = Integer.parseInt(filasText);
            columnas = Integer.parseInt(columnasText);

            if (filas <= 0 || columnas <= 0) {
                showAlert("Error", "Los valores de filas y columnas deben ser mayores que 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Los valores de filas y columnas deben ser enteros válidos.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gridView.fxml"));
        Parent gridRoot = loader.load();
        gridController = loader.getController();
        gridController.iniciarGrid(filas, columnas);

        gridStage = new Stage();
        gridStage.setTitle("Grid View");
        gridStage.setScene(new Scene(gridRoot));
        gridStage.show();
    }

    @FXML
    private void startLinking() {
        System.out.println("Modo enlace");
        if (gridController != null) {
            gridController.enableLinkingMode();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML 
    private void runFloydWarshall() {
        System.out.println("corriendo");
        BluetoothCommunication.establecerConexion();

    }
}