package com.example.calculadora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Calculadora {
    @FXML
    private Label f_resultado;

    @FXML

    protected void onClickCalcular() {
        if (f_resultado == null || f_resultado.toString().equals("")) {
            //meter toast
            System.out.println("SE ESTA REALIZANSO LA OPERACIK");
        }else{
            realizarOperacion(f_resultado);}
    }

    public void realizarOperacion(Label operacion) {

    }

    public void onClickDividir(ActionEvent mouseEvent) {
        f_resultado.setText(f_resultado.getText() + "/");
    }

    public void onClickMultiplicar(ActionEvent mouseEvent) {
        f_resultado.setText(f_resultado.getText()+"X");
    }

    public void onClickRestar(ActionEvent mouseEvent) {
        f_resultado.setText(f_resultado.getText()+"-");
    }

    public void onClickSuma(ActionEvent mouseEvent) {
        f_resultado.setText(f_resultado.getText()+"+");
    }

    public void onClickPulsarIgualar(ActionEvent mouseEvent) {
        f_resultado.setText(f_resultado.getText()+"=");
    }

    public void onClickDecimal(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarTres(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarSeis(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarNueve(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickHacerPorcentaje(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarCero(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarDos(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarCinco(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarOcho(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickBorrar(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarUno(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarCuatro(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }

    public void onClickPulsarSiete(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }


    public void onClickBorrarTodo(ActionEvent mouseEvent) {
        f_resultado.setText("Resultao");
    }
}