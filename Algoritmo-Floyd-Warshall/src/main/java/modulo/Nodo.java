/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo;

import javafx.scene.control.Label;

/**
 *
 * @author asala
 */
public class Nodo {
    private int fila;
    private int columna;
    private Label label;
    private boolean creado = false;
    private String nombre;

    public Nodo(int fila, int columna, Label label) {
        this.fila = fila;
        this.columna = columna;
        this.label = label;
    }

    public boolean isCreated() {
        return creado;
    }

    public void crearNodo(String nombre) {
        this.nombre = nombre;
        creado = true;
    }

    public void borrarNodo() {
        creado = false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public Label getLabel() {
        return label;
    }
}