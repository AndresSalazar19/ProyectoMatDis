/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algoritmo.floyd.warshall;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import modulo.*;
/**
 *
 * @author asala
 */
public class GridViewController {

    @FXML
    private GridPane grid;

    private Nodo[][] nodos;
    private int nodoCounter = 1;
    private PriorityQueue<Integer> numDisponibles = new PriorityQueue<>();
    private boolean linkingMode = false;
    private Nodo selectedNodo = null;
    private List<Line> enlaces = new ArrayList<>();
    private List<Text> distancias = new ArrayList<>();

    public void iniciarGrid(int filas, int columnas) {
        nodos = new Nodo[filas][columnas];
        grid.getChildren().clear();
        grid.setGridLinesVisible(true);
        grid.setHgap(10); // Espacio horizontal entre nodos
        grid.setVgap(10); // Espacio vertical entre nodos
        grid.setPadding(new javafx.geometry.Insets(10)); // Margen alrededor de la grilla

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                final int fila = i;
                final int columna = j;
                Label label = new Label(".");
                label.setMinSize(20, 20); // Tamaño mínimo del label
                label.setStyle("-fx-alignment: center; -fx-border-color: black; -fx-border-width: 1px;");
                StackPane stackPane = new StackPane(label);
                stackPane.setPrefSize(30, 30); // Tamaño preferido del contenedor
                stackPane.setStyle("-fx-alignment: center;");
                stackPane.setOnMouseClicked(e -> handleNodoClick(e, fila, columna));
                grid.add(stackPane, j, i);
                nodos[i][j] = new Nodo(i, j, label, stackPane);
            }
        }
    }

    private void handleNodoClick(javafx.scene.input.MouseEvent e, int fila, int columna) {
        Nodo nodo = nodos[fila][columna];
        if (!nodo.isCreated()) {
            String nodoName;
            if (numDisponibles.isEmpty()) {
                nodoName = "N" + nodoCounter++;
            } else {
                nodoName = "N" + numDisponibles.poll();
            }
            nodo.crearNodo(nodoName);
            convertirACirculo(nodo);
        } else if (!linkingMode) {
            int nodoNumber = Integer.parseInt(nodo.getNombre().substring(1));
            numDisponibles.add(nodoNumber);
            nodo.borrarNodo();
            convertirAPunto(nodo);
        } else {
            handleLinking(nodo);
        }
    }

    private void convertirACirculo(Nodo nodo) {
        Label label = nodo.getLabel();
        Circle circle = new Circle(15, Color.LIGHTBLUE);
        Text text = new Text(nodo.getNombre());
        text.setStyle("-fx-font-size: 14;");
        StackPane stackPane = nodo.getStackPane();
        if (stackPane != null) {
            stackPane.getChildren().clear();
            stackPane.getChildren().addAll(circle, text);
        }
    }

    private void convertirAPunto(Nodo nodo) {
        System.out.println("Convirtiendo a punto");
        Label label = nodo.getLabel();
        label.setText(".");
        label.setStyle("-fx-alignment: center; -fx-border-color: black; -fx-border-width: 1px;");
        StackPane stackPane = nodo.getStackPane();
        if (stackPane == null) {
            System.out.println("stackPane es null");
        } else {
            System.out.println("listo para actualizar");
            stackPane.getChildren().clear();
            stackPane.getChildren().add(label);
        }
    }

    public void enableLinkingMode() {
        linkingMode = true;
        grid.getChildren().removeIf(node -> node instanceof StackPane && !((StackPane) node).getChildren().get(0).getStyleClass().contains("created"));
    }

    private void handleLinking(Nodo nodo) {
        if (selectedNodo == null) {
            selectedNodo = nodo;
        } else {
            crearEnlace(selectedNodo, nodo);
            selectedNodo = null;
        }
    }

    private void crearEnlace(Nodo from, Nodo to) {
        Line line = new Line(from.getStackPane().getLayoutX(), from.getStackPane().getLayoutY(),
                to.getStackPane().getLayoutX(), to.getStackPane().getLayoutY());
        Text distanceText = new Text("1"); // Por defecto, la distancia es 1, se puede cambiar
        grid.getChildren().addAll(line, distanceText);
        enlaces.add(line);
        distancias.add(distanceText);
    }
}