package com.example.calculadora;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class mostrarAdvertencia {

    // Método para mostrar la advertencia con un mensaje personalizado
    public static void mostrar(String mensaje) {
        // Aseguramos que el código se ejecute en el hilo de la interfaz gráfica
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }
}
