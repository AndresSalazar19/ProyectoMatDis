/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo;

/**
 *
 * @author asala
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RowData {

    private final StringProperty rowIndex;
    private final StringProperty col1;
    private final StringProperty col2;
    private final StringProperty col3;
    private final StringProperty col4;
    private final StringProperty col5;
    private final StringProperty col6;

    public RowData(String rowIndex, String col1, String col2, String col3, String col4, String col5, String col6) {
        this.rowIndex = new SimpleStringProperty(rowIndex);
        this.col1 = new SimpleStringProperty(col1);
        this.col2 = new SimpleStringProperty(col2);
        this.col3 = new SimpleStringProperty(col3);
        this.col4 = new SimpleStringProperty(col4);
        this.col5 = new SimpleStringProperty(col5);
        this.col6 = new SimpleStringProperty(col6);
    }

    public String getRowIndex() {
        return rowIndex.get();
    }

    public StringProperty rowIndexProperty() {
        return rowIndex;
    }

    public String getCol1() {
        return col1.get();
    }

    public StringProperty col1Property() {
        return col1;
    }

    public String getCol2() {
        return col2.get();
    }

    public StringProperty col2Property() {
        return col2;
    }

    public String getCol3() {
        return col3.get();
    }

    public StringProperty col3Property() {
        return col3;
    }

    public String getCol4() {
        return col4.get();
    }

    public StringProperty col4Property() {
        return col4;
    }

    public String getCol5() {
        return col5.get();
    }

    public StringProperty col5Property() {
        return col5;
    }

    public String getCol6() {
        return col6.get();
    }

    public StringProperty col6Property() {
        return col6;
    }
}
