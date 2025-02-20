package com.example.calculadora;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainControlador {
    @FXML
    private BorderPane BorderPane;

    @FXML
    public void cargarCalculadoraNormal() {
        try {
            Parent normal = FXMLLoader.load(getClass().getResource("calculadora.fxml"));
            BorderPane.setCenter(normal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cargarCalculadoraCientifica() {
        try {
            Parent cientifica = FXMLLoader.load(getClass().getResource("CalculadoraCientifica.fxml"));
            BorderPane.setCenter(cientifica);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
