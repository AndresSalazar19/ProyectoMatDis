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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
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
    private Line currentLine = null;

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
                stackPane.setOnMousePressed(e -> handleMousePressed(e, fila, columna));
                stackPane.setOnMouseDragged(e -> handleMouseDragged(e));
                stackPane.setOnMouseReleased(e -> handleMouseReleased(e, fila, columna));
                grid.add(stackPane, j, i);
                nodos[i][j] = new Nodo(i, j, label, stackPane);
            }
        }
    }

    private void handleNodoClick(MouseEvent e, int fila, int columna) {
        Nodo nodo = nodos[fila][columna];
        if (!linkingMode) {
            if (!nodo.isCreated()) {
                String nodoName;
                if (numDisponibles.isEmpty()) {
                    nodoName = "N" + nodoCounter++;
                } else {
                    nodoName = "N" + numDisponibles.poll();
                }
                nodo.crearNodo(nodoName);
                convertirACirculo(nodo);
            } else {
                int nodoNumber = Integer.parseInt(nodo.getNombre().substring(1));
                numDisponibles.add(nodoNumber);
                nodo.borrarNodo();
                convertirAPunto(nodo);
            }
        }
    }

    private void handleMousePressed(MouseEvent e, int fila, int columna) {
        if (linkingMode) {
            Nodo nodo = nodos[fila][columna];
            if (nodo.isCreated()) {
                selectedNodo = nodo;
                convertirASeleccionado(nodo);
                currentLine = new Line();
                currentLine.setStartX(nodo.getStackPane().getLayoutX() + nodo.getStackPane().getWidth() / 2);
                currentLine.setStartY(nodo.getStackPane().getLayoutY() + nodo.getStackPane().getHeight() / 2);
                currentLine.setEndX(nodo.getStackPane().getLayoutX() + nodo.getStackPane().getWidth() / 2);
                currentLine.setEndY(nodo.getStackPane().getLayoutY() + nodo.getStackPane().getHeight() / 2);
                grid.getChildren().add(currentLine);
            }
        }
    }

    private void handleMouseDragged(MouseEvent e) {
        if (currentLine != null) {
            currentLine.setEndX(e.getX());
            currentLine.setEndY(e.getY());
        }
    }

    private void handleMouseReleased(MouseEvent e, int fila, int columna) {
        if (linkingMode && currentLine != null) {
            Nodo targetNodo = nodos[fila][columna];
            if (selectedNodo != null && targetNodo.isCreated() && selectedNodo != targetNodo) {
                crearOEliminarEnlace(selectedNodo, targetNodo);
            }
            grid.getChildren().remove(currentLine);
            currentLine = null;
            convertirACirculo(selectedNodo);
            selectedNodo = null;
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
        Label label = nodo.getLabel();
        label.setText(".");
        label.setStyle("-fx-alignment: center; -fx-border-color: black; -fx-border-width: 1px;");
        StackPane stackPane = nodo.getStackPane();
        if (stackPane != null) {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(label);
        }
    }

    private void convertirASeleccionado(Nodo nodo) {
        Label label = nodo.getLabel();
        Circle circle = new Circle(15, Color.DARKBLUE);
        Text text = new Text(nodo.getNombre());
        text.setStyle("-fx-font-size: 14;");
        StackPane stackPane = nodo.getStackPane();
        if (stackPane != null) {
            stackPane.getChildren().clear();
            stackPane.getChildren().addAll(circle, text);
        }
    }

    public void enableLinkingMode() {
        linkingMode = true;
        for (Nodo[] row : nodos) {
            for (Nodo nodo : row) {
                if (nodo.isCreated()) {
                    convertirACirculo(nodo);
                } else {
                    nodo.getStackPane().setVisible(false);
                }
            }
        }
    }

    private void crearOEliminarEnlace(Nodo from, Nodo to) {
        Line existingLine = findLine(from, to);
        if (existingLine != null) {
            grid.getChildren().remove(existingLine);
            Line reverseLine = findLine(to, from);
            if (reverseLine != null) {
                grid.getChildren().remove(reverseLine);
            }
        } else {
            crearEnlace(from, to);
        }
    }

    private Line findLine(Nodo from, Nodo to) {
        for (Line line : enlaces) {
            if ((line.getStartX() == from.getStackPane().getLayoutX() + from.getStackPane().getWidth() / 2 && 
                 line.getStartY() == from.getStackPane().getLayoutY() + from.getStackPane().getHeight() / 2) &&
                (line.getEndX() == to.getStackPane().getLayoutX() + to.getStackPane().getWidth() / 2 && 
                 line.getEndY() == to.getStackPane().getLayoutY() + to.getStackPane().getHeight() / 2)) {
                return line;
            }
        }
        return null;
    }

    private void crearEnlace(Nodo from, Nodo to) {
        double startX = from.getStackPane().getLayoutX() + from.getStackPane().getWidth() / 2;
        double startY = from.getStackPane().getLayoutY() + from.getStackPane().getHeight() / 2;
        double endX = to.getStackPane().getLayoutX() + to.getStackPane().getWidth() / 2;
        double endY = to.getStackPane().getLayoutY() + to.getStackPane().getHeight() / 2;
        Line line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(2);
        Polygon arrowHead = createArrowHead(line);
        Text distanceText = new Text(String.valueOf(calcularDistancia(from, to)));
        distanceText.setX((startX + endX) / 2);
        distanceText.setY((startY + endY) / 2);
        grid.getChildren().addAll(line, arrowHead, distanceText);
        enlaces.add(line);
        distancias.add(distanceText);
    }

    private Polygon createArrowHead(Line line) {
        Polygon arrowHead = new Polygon();
        double arrowHeadSize = 10;
        double angle = Math.atan2(line.getEndY() - line.getStartY(), line.getEndX() - line.getStartX());
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        double x1 = line.getEndX() - arrowHeadSize * cos + arrowHeadSize * sin;
        double y1 = line.getEndY() - arrowHeadSize * sin - arrowHeadSize * cos;
        double x2 = line.getEndX() - arrowHeadSize * cos - arrowHeadSize * sin;
        double y2 = line.getEndY() - arrowHeadSize * sin + arrowHeadSize * cos;

        arrowHead.getPoints().addAll(line.getEndX(), line.getEndY(), x1, y1, x2, y2);
        arrowHead.setFill(Color.BLACK);

        return arrowHead;
    }

    private double calcularDistancia(Nodo from, Nodo to) {
        double x1 = from.getStackPane().getLayoutX() + from.getStackPane().getWidth() / 2;
        double y1 = from.getStackPane().getLayoutY() + from.getStackPane().getHeight() / 2;
        double x2 = to.getStackPane().getLayoutX() + to.getStackPane().getWidth() / 2;
        double y2 = to.getStackPane().getLayoutY() + to.getStackPane().getHeight() / 2;
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
