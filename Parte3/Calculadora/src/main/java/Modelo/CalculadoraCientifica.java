package Modelo;

public class CalculadoraCientifica {

    // OPERACIONES TRIGONOMÉTRICAS (se asume que el ángulo está en grados)

    /**
     * Calcula el seno del ángulo dado (en grados).
     * @param angulo El ángulo en grados.
     * @return El valor del seno.
     */
    public double calcularSeno(double angulo) {
        return Math.sin(Math.toRadians(angulo));
    }

    /**
     * Calcula el coseno del ángulo dado (en grados).
     * @param angulo El ángulo en grados.
     * @return El valor del coseno.
     */
    public double calcularCoseno(double angulo) {
        return Math.cos(Math.toRadians(angulo));
    }

    /**
     * Calcula la tangente del ángulo dado (en grados).
     * @param angulo El ángulo en grados.
     * @return El valor de la tangente.
     */
    public double calcularTangente(double angulo) {
        return Math.tan(Math.toRadians(angulo));
    }

    // OPERACIONES EXPONENCIALES

    /**
     * Calcula la exponencial de un número.
     * @param exponente El exponente.
     * @return El resultado de e elevado al valor del exponente.
     */
    public double calcularExponencial(double exponente) {
        return Math.exp(exponente);
    }

    /**
     * Calcula el logaritmo natural (base e) de un número.
     * @param valor El valor del cual se quiere el logaritmo.
     * @return El logaritmo natural del valor.
     * @throws IllegalArgumentException si el valor es menor o igual a 0.
     */
    public double calcularLogaritmo(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor a 0 para calcular el logaritmo");
        }
        return Math.log(valor);
    }
}
