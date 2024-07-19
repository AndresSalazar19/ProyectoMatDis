module com.mycompany.algoritmo.floyd.warshall {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.algoritmo.floyd.warshall to javafx.fxml;
    exports com.mycompany.algoritmo.floyd.warshall;
    requires com.fazecast.jSerialComm;
}
