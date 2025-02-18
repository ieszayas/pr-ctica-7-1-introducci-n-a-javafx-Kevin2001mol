package com.example.calculadora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Calculadora {
    @FXML
    private Label f_resultado;
    @FXML
    private Label label_historial; // Label para mostrar el historial

    private String operador = "";
    private double primerNumero = 0;
    private boolean esOperacionRealizada = false;
    private boolean esperandoSegundoOperando = false;
    // Guarda la parte ya ingresada de la operación (por ejemplo, "4+")
    private String historialOperacion = "";

    private CalculadoraModelo modelo = new CalculadoraModelo();

    @FXML
    public void initialize() {
        f_resultado.setText("");
        label_historial.setText("");
    }

    @FXML
    protected void onClickCalcular() {
        if (operador.isEmpty() || f_resultado.getText().isEmpty()) {
            mostrarAdvertencia("Faltan valores para realizar la operación");
        } else {
            // Actualizamos el historial con la operación completa (por ejemplo, "-8+(-3)=")
            label_historial.setText(historialOperacion + f_resultado.getText() + "=");
            realizarOperacion();
        }
    }

    /**
     * Maneja el uso del botón de paréntesis para ingresar números negativos en el segundo operando.
     * Solo se permite cuando se está esperando el segundo operando y el display está vacío.
     */
    @FXML
    public void onClickParentesis() {
        String current = f_resultado.getText();

        // Si ya se inició el número negativo y no está cerrado, permitimos cerrar
        if (current.startsWith("(-") && !current.endsWith(")")) {
            if (current.length() > 2) {  // Debe haber al menos un dígito después de "(-"
                f_resultado.setText(current + ")");
                label_historial.setText(historialOperacion + f_resultado.getText());
            } else {
                mostrarAdvertencia("Ingrese el número negativo antes de cerrar el paréntesis.");
            }
            return;
        }

        // Si se está en modo de espera para el segundo operando, permitimos abrir el paréntesis
        if (esperandoSegundoOperando) {
            if (current.isEmpty() || current.equals("0")) {
                f_resultado.setText("(-");
                label_historial.setText(historialOperacion + "(-");
            } else {
                mostrarAdvertencia("El número ya está iniciado. Para números negativos, inícielo con '(-'.");
            }
        } else {
            // En cualquier otro caso (por ejemplo, en el primer operando) mostramos el mensaje de error
            mostrarAdvertencia("Para número negativo en el primer operando, use el signo '-' al inicio.");
        }
    }





    @FXML
    public void onClickDecimal(ActionEvent event) {
        limpiarSiError();
        String textoActual = f_resultado.getText();
        if (textoActual.isEmpty()) {
            f_resultado.setText("0.");
            return;
        }
        if (!textoActual.contains(".")) {
            f_resultado.setText(textoActual + ".");
            if (!historialOperacion.isEmpty() && !esperandoSegundoOperando) {
                label_historial.setText(historialOperacion + f_resultado.getText());
            }
        }
    }

    /**
     * Realiza la operación, considerando la posibilidad de que el segundo operando
     * se haya ingresado entre paréntesis (por ejemplo, "(-3)").
     */
    private void realizarOperacion() {
        String text = f_resultado.getText();
        // Si el segundo operando se inició con "(-" pero no se cerró, se pide cerrar manualmente.
        if (text.startsWith("(-") && !text.endsWith(")")) {
            mostrarAdvertencia("Cierre el paréntesis para el operando negativo.");
            return;
        }
        // Si el número está entre paréntesis, se transforma a su representación negativa.
        if (text.startsWith("(-") && text.endsWith(")")) {
            text = "-" + text.substring(2, text.length() - 1);
        }
        text = text.replace(',', '.');
        double segundoNumero = modelo.convertirNumero(text);
        if (Double.isNaN(segundoNumero)) {
            f_resultado.setText("Error");
            return;
        }
        double resultado = modelo.calcular(primerNumero, segundoNumero, operador);
        if (Double.isNaN(resultado)) {
            f_resultado.setText("Error");
        } else {
            f_resultado.setText(CalculadoraUtils.formatResult(resultado));
        }
        operador = "";
        esOperacionRealizada = true;
        esperandoSegundoOperando = false;
        historialOperacion = "";
    }


    /**
     * Maneja la pulsación de un operador.
     * Para el primer operando, si el display está vacío y se pulsa '-' se tratará como signo negativo.
     */
    public void onClickOperacion(ActionEvent event) {
        limpiarSiError();
        String oper = ((Button) event.getSource()).getText();

        // Si el display está vacío y se pulsa "-" para el primer operando, se trata como signo negativo
        if (f_resultado.getText().isEmpty() && oper.equals("-")) {
            f_resultado.setText("-");
            return;
        }

        // Si ya se está esperando el segundo operando, se interpreta que se desea cambiar el operador
        if (esperandoSegundoOperando) {
            operador = oper;
            if (!historialOperacion.isEmpty()) {
                // Reemplaza el último carácter (el operador anterior) por el nuevo.
                historialOperacion = historialOperacion.substring(0, historialOperacion.length() - 1) + operador;
            } else {
                historialOperacion = f_resultado.getText() + operador;
            }
            label_historial.setText(historialOperacion);
            return;
        }

        if (esOperacionRealizada) {
            esOperacionRealizada = false;
        }

        String currentText = f_resultado.getText().trim();
        if (currentText.equals("Error") || currentText.isEmpty() || !currentText.matches("[+-]?\\d*([.,]\\d+)?")) {
            currentText = "0";
        } else {
            currentText = currentText.replace(',', '.');
        }

        primerNumero = modelo.convertirNumero(currentText);
        if (Double.isNaN(primerNumero)) {
            mostrarAdvertencia("Valor inválido para la operación");
            return;
        }

        operador = oper;
        // Actualizamos el historial con el operando actual y el operador
        historialOperacion = currentText + operador;
        label_historial.setText(historialOperacion);
        // Limpiamos el display para que el segundo operando empiece vacío
        f_resultado.setText("");
        esperandoSegundoOperando = true;
    }


    public void onClickNumero(ActionEvent event) {
        limpiarSiError();
        String numero = ((Button) event.getSource()).getText();
        if (esperandoSegundoOperando) {
            // Si el display ya contiene "(-", no lo reemplazamos sino que concatenamos el dígito.
            if (f_resultado.getText().startsWith("(-")) {
                f_resultado.setText(f_resultado.getText() + numero);
            } else {
                f_resultado.setText(numero);
            }
            esperandoSegundoOperando = false;
            label_historial.setText(historialOperacion + f_resultado.getText());
        } else {
            if (esOperacionRealizada) {
                f_resultado.setText(numero);
                label_historial.setText("");
                esOperacionRealizada = false;
            } else {
                if (f_resultado.getText().equals("0") || f_resultado.getText().isEmpty()) {
                    f_resultado.setText(numero);
                } else {
                    f_resultado.setText(f_resultado.getText() + numero);
                }
                if (!historialOperacion.isEmpty()) {
                    label_historial.setText(historialOperacion + f_resultado.getText());
                }
            }
        }
    }


    public void onClickBorrar() {
        String texto = f_resultado.getText();
        if (!texto.isEmpty()) {
            String nuevoTexto = texto.substring(0, texto.length() - 1);
            f_resultado.setText(nuevoTexto);
            if (!historialOperacion.isEmpty() && !esperandoSegundoOperando) {
                label_historial.setText(historialOperacion + nuevoTexto);
            }
        }
    }

    public void onClickBorrarTodo() {
        f_resultado.setText("");
        label_historial.setText("");
        operador = "";
        primerNumero = 0;
        esOperacionRealizada = false;
        esperandoSegundoOperando = false;
        historialOperacion = "";
    }

    private void limpiarSiError() {
        if (f_resultado.getText().equals("Error")) {
            f_resultado.setText("");
        }
    }

    /**
     * Muestra un "toast" no modal que se autodesvanece después de 2 segundos.
     */
    private void mostrarAdvertencia(String mensaje) {
        Platform.runLater(() -> {
            Popup popup = new Popup();
            Label toastLabel = new Label(mensaje);
            toastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"
                    + " -fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 5;");
            popup.getContent().add(toastLabel);
            Stage stage = (Stage) f_resultado.getScene().getWindow();
            popup.show(stage);
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> popup.hide());
            delay.play();
        });
    }
}
