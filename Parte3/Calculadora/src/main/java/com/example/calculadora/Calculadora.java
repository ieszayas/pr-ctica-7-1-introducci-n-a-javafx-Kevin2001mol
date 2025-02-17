package com.example.calculadora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.application.Platform;

public class Calculadora {
    @FXML
    private Label f_resultado;

    private String operador = "";
    private double primerNumero = 0;
    private boolean esOperacionRealizada = false;

    @FXML
    protected void onClickCalcular() {
        String textoResultado = f_resultado.getText();

        if (textoResultado.isEmpty() || operador.isEmpty()) {
            System.out.println("Faltan valores para realizar la operación");
        } else {
            realizarOperacion();
        }
    }

    public void realizarOperacion() {
        try {
            String textoResultado = f_resultado.getText();
            String[] partes = textoResultado.split("[-+X/%]");

            if (partes.length < 2) {
                System.out.println("Expresión incompleta");
                return;
            }

            double segundoNumero = Double.parseDouble(partes[1]);
            double resultado = 0;

            switch (operador) {
                case "+":
                    resultado = primerNumero + segundoNumero;
                    break;
                case "-":
                    resultado = primerNumero - segundoNumero;
                    break;
                case "X":
                    resultado = primerNumero * segundoNumero;
                    break;
                case "/":
                    if (segundoNumero != 0) {
                        resultado = primerNumero / segundoNumero;
                    } else {
                        mostrarAdvertencia("No puedes dividir por cero.");
                        return;
                    }
                    break;
                case "%":
                    resultado = primerNumero * (segundoNumero / 100);
                    break;
            }

            String resultadoFormateado = formatResult(resultado);
            f_resultado.setText(resultadoFormateado);
            operador = "";
            esOperacionRealizada = true;
        } catch (NumberFormatException e) {
            mostrarAdvertencia("Error al procesar la operación.");
            System.out.println("Error al procesar la operación: " + e.getMessage());
        }
    }

    private String formatResult(double result) {
        // Verificar si el resultado es un número entero
        if (result == (long) result) {
            return String.format("%d", (long) result); // No mostrar decimales si es un número entero
        } else {
            // Formatear el número a máximo 5 decimales
            return String.format("%.5f", result).replaceAll("\\.?0*$", ""); // Eliminar decimales innecesarios
        }
    }

    private boolean isPeriodic(String resultStr) {
        if (resultStr.contains(".")) {
            String decimalPart = resultStr.split("\\.")[1];
            for (int i = 0; i < decimalPart.length() - 1; i++) {
                String repeatingPart = decimalPart.substring(i, i + 1);
                if (decimalPart.substring(i + 1).startsWith(repeatingPart)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void limpiarSiError() {
        if (f_resultado.getText().equals("Error")) {
            f_resultado.setText("");
        }
    }

    private void agregarOperador(String nuevoOperador) {
        String textoActual = f_resultado.getText();

        // Validar que no se añadan operadores consecutivos
        if (!textoActual.isEmpty()) {
            char ultimoCaracter = textoActual.charAt(textoActual.length() - 1);

            // Verificar si el operador actual es igual al anterior
            if (ultimoCaracter == nuevoOperador.charAt(0)) {
                mostrarAdvertencia("No puedes agregar el mismo operador consecutivamente.");
                return;
            }

            // Validar que no haya operadores consecutivos, ni un porcentaje seguido de otro operador
            if (ultimoCaracter == '+' || ultimoCaracter == 'X' || ultimoCaracter == '/' || ultimoCaracter == '%' || ultimoCaracter == '-') {
                // Evitar operadores consecutivos
                if (ultimoCaracter == nuevoOperador.charAt(0) || ultimoCaracter == '%') {
                    return;
                }
            }
        }

        f_resultado.setText(textoActual + nuevoOperador);
    }

    private double convertirAEntero() {
        try {
            return Double.parseDouble(f_resultado.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el número: " + e.getMessage());
            return Double.NaN; // Devuelve un valor no válido en caso de error
        }
    }

    public void onClickDividir(ActionEvent mouseEvent) {
        limpiarSiError();
        operador = "/";
        primerNumero = convertirAEntero();
        if (Double.isNaN(primerNumero)) {
            return; // Si la conversión falló, no continuamos
        }
        agregarOperador("/");
    }

    public void onClickMultiplicar(ActionEvent mouseEvent) {
        limpiarSiError();
        operador = "X";
        primerNumero = convertirAEntero();
        if (Double.isNaN(primerNumero)) {
            return; // Si la conversión falló, no continuamos
        }
        agregarOperador("X");
    }

    public void onClickRestar(ActionEvent mouseEvent) {
        limpiarSiError();
        operador = "-";
        primerNumero = convertirAEntero();
        if (Double.isNaN(primerNumero)) {
            return; // Si la conversión falló, no continuamos
        }
        agregarOperador("-");
    }

    public void onClickSuma(ActionEvent mouseEvent) {
        limpiarSiError();
        operador = "+";
        primerNumero = convertirAEntero();
        if (Double.isNaN(primerNumero)) {
            return; // Si la conversión falló, no continuamos
        }
        agregarOperador("+");
    }

    public void onClickPulsarIgualar(ActionEvent mouseEvent) {
        limpiarSiError();
        if (!operador.isEmpty()) {
            realizarOperacion();
        }
    }

    public void onClickDecimal(ActionEvent mouseEvent) {
        String currentText = f_resultado.getText();
        if (!currentText.contains(".")) {
            f_resultado.setText(currentText + ".");
        }
    }

    public void onClickHacerPorcentaje(ActionEvent mouseEvent) {
        limpiarSiError();
        operador = "%";
        primerNumero = convertirAEntero();
        if (Double.isNaN(primerNumero)) {
            return; // Si la conversión falló, no continuamos
        }
        agregarOperador("%");
    }

    public void onClickPulsarCero(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "0");
    }

    public void onClickPulsarUno(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "1");
    }

    public void onClickPulsarDos(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "2");
    }

    public void onClickPulsarTres(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "3");
    }

    public void onClickPulsarCuatro(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "4");
    }

    public void onClickPulsarCinco(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "5");
    }

    public void onClickPulsarSeis(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "6");
    }

    public void onClickPulsarSiete(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "7");
    }

    public void onClickPulsarOcho(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "8");
    }

    public void onClickPulsarNueve(ActionEvent mouseEvent) {
        limpiarSiError();
        f_resultado.setText(f_resultado.getText() + "9");
    }

    public void onClickBorrar() {
        String currentText = f_resultado.getText();
        if (currentText.length() > 0) {
            f_resultado.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public void onClickBorrarTodo(ActionEvent mouseEvent) {
        f_resultado.setText("");
        operador = "";
        primerNumero = 0;
        esOperacionRealizada = false;
    }
    public void onClickParentesis(ActionEvent mouseEvent) {
        String textoActual = f_resultado.getText();

        // Contar paréntesis abiertos y cerrados
        long abiertos = textoActual.chars().filter(ch -> ch == '(').count();
        long cerrados = textoActual.chars().filter(ch -> ch == ')').count();

        if (abiertos > cerrados) {
            // Si hay más paréntesis abiertos, añadir un ")"
            f_resultado.setText(textoActual + ")");
        } else {
            // Si no, añadir un "("
            f_resultado.setText(textoActual + "(");
        }
    }

    private void mostrarAdvertencia(String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }
}
