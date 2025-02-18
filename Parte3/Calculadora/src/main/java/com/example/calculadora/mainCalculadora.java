package com.example.calculadora;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class mainCalculadora extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainCalculadora.class.getResource("calculadora.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        // Cargar el archivo CSS
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Mi Aplicación JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}