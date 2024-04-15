package org.alvarowau.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.ParseException;

public class CalculadoraController {

    @FXML
    private Button btn0;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    @FXML
    private Button btnAlvaro;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnEntre;

    @FXML
    private Button btnEquals;

    @FXML
    private ImageView btnIgual;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnMas;

    @FXML
    private Button btnMenos;

    @FXML
    private Button btnMultiply;

    @FXML
    private TextField display;



    private boolean operacionesOn = true;
    private Double resultadoAnterior = 0.0;

    @FXML
    void ResultadoTotal(ActionEvent event) {
        hacerOperacion();
    }

    public void hacerOperacion() {
        String expresion = display.getText();
        try {
            double resultado = evaluarExpresion(expresion);
            display.setText(Double.toString(resultado));
        } catch (NumberFormatException e) {
            System.out.println("Expresión no válida: " + expresion);
        }
    }

    public double evaluarExpresion(String expresion) {
        // Utilizar expresiones regulares para separar números y operadores
        String[] partes = expresion.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");

        // Inicializar el resultado con el primer número
        double resultado = Double.parseDouble(partes[0]);

        // Iterar sobre la lista de partes
        for (int i = 1; i < partes.length; i++) {
            String parte = partes[i].trim(); // Eliminar espacios en blanco
            if (parte.matches("[+\\-*/]")) {
                // Si es un operador, almacenarlo para usarlo en la siguiente iteración
                char operador = parte.charAt(0);
                double siguienteNumero = Double.parseDouble(partes[i + 1]);
                // Realizar la operación correspondiente
                switch (operador) {
                    case '+':
                        resultado += siguienteNumero;
                        break;
                    case '-':
                        resultado -= siguienteNumero;
                        break;
                    case '*':
                        resultado *= siguienteNumero;
                        break;
                    case '/':
                        if (siguienteNumero != 0) {
                            resultado /= siguienteNumero;
                        } else {
                            throw new ArithmeticException("División por cero");
                        }
                        break;
                }
                // Saltar al siguiente número
                i++;
            } else {
                // Si es un número, continuar con la siguiente parte
                double numero = Double.parseDouble(parte);
                // Realizar la operación correspondiente con el número anterior
                resultado = operacion(resultado, numero);
            }
        }
        return resultado;
    }

    // Método auxiliar para realizar la operación adecuada
    private double operacion(double num1, double num2) {
        // Implementar aquí la lógica para realizar la operación adecuada
        // Por ejemplo, para sumar num2 a num1: return num1 + num2;
        return 0; // Actualizar con la lógica adecuada
    }


    @FXML
    void addOperaccion(ActionEvent event){
        String operacion = "";
        Button botonPresionado = (Button) event.getSource();

        // Determinar qué botón se ha presionado
        switch (botonPresionado.getId()) {
            case "btnMas":
                operacion = "+";
                break;
            case "btnMenos":
                operacion = "-";
                break;
            case "btnMultiply":
                operacion = "*";
                break;
            case "btnEntre":
                operacion = "/";
                break;
        }
        display.setText(display.getText() + operacion);
        operacionesOn = false;
    }


    @FXML
    void addValue(ActionEvent event) {
        display.setText(display.getText() + ((Button) event.getSource()).getText());
        operacionesOn = true;
    }

    @FXML
    void elimnarValor(ActionEvent event){
        if (!display.getText().isEmpty()) {
            display.setText(display.getText().substring(0, display.getText().length() - 1));
        }
    }

    @FXML
    void elinarCompleto(ActionEvent event)  {
        display.clear();
        operacionesOn = true;
    }

    @FXML
    void visitarRepositorio(ActionEvent event) {
        System.out.println("visitarRepositorio");
    }

    public void limpiarDisplay(){

    }

    public void deleteCaracter(){

    }




    public void obtenerResultado(){

    }

}
