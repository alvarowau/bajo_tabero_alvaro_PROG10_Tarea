package org.alvarowau.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.alvarowau.Path;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Controlador de la calculadora.
 * Este controlador maneja la lógica de la calculadora JavaFX.
 * Se encarga de realizar operaciones matemáticas, mostrar resultados y manejar eventos de interfaz de usuario.
 * Además, proporciona funcionalidades como minimizar, cerrar la ventana y mostrar un repositorio de código.
 *
 * @author Álvaro Bajo
 * @version 1.0
 * @since 16 de abril de 2024
 */
public class CalculadoraController {

    @FXML
    private Button btnMinimizar;
    @FXML
    private Button btnCerrar;

    @FXML
    private TextField display;


    private boolean operacionesOn = true;
    private boolean hayQueBorrar = false;
    private boolean hayLetras = false;

    /**
     * Cierra la ventana de la aplicación.
     * Este método se invoca cuando se hace clic en el botón de cerrar.
     * Cierra la aplicación JavaFX.
     *
     * @param actionEvent Evento de acción generado por el botón de cerrar.
     */
    public void cerrarVentana(ActionEvent actionEvent) {
        // Cerrar la aplicación llamando a Platform.exit()
        Platform.exit();
    }

    /**
     * Minimiza la ventana de la aplicación.
     * Este método se invoca cuando se hace clic en el botón de minimizar.
     * Obtiene la referencia a la ventana actual y la minimiza.
     *
     * @param actionEvent Evento de acción generado por el botón de minimizar.
     */
    public void minimizarVentana(ActionEvent actionEvent) {
        // Obtener la referencia a la ventana actual
        Stage stage = (Stage) btnMinimizar.getScene().getWindow();
        // Minimizar la ventana
        stage.setIconified(true);
    }


    /**
     * Maneja el evento de hacer una operación.
     * Se invoca cuando se presiona el botón de igual (=).
     * Extrae la expresión del display y realiza la operación.
     */
    @FXML
    void ResultadoTotal(ActionEvent event) {
        hacerOperacion();
    }

    /**
     * Realiza la operación basada en la expresión en el display.
     * Si la expresión es válida, calcula el resultado y lo muestra en el display.
     * Si la expresión es inválida, muestra un mensaje de error en la consola.
     */
    public void hacerOperacion() {
        // Obtener la expresión del display
        String expresion = display.getText();

        // Verificar si la expresión no es solo un guion "-"
        if (!expresion.trim().equals("-")) {
            try {
                // Calcular el resultado de la expresión
                double resultado = calcularResultado(expresion);

                // Mostrar el resultado en el display
                display.setText(formatearResultado(resultado));

                // Indicar que se debe borrar el display en la próxima entrada
                hayQueBorrar = true;
            } catch (NumberFormatException e) {
                display.setText("Expresión no válida: " + expresion);
                hayLetras = true;
            }
        }
    }


    /**
     * Calcula el resultado de la expresión matemática dada.
     *
     * @param expresion La expresión matemática a evaluar.
     * @return El resultado de la operación.
     */
    private double calcularResultado(String expresion) {
        // Obtener la lista de elementos de la expresión
        List<String> elementos = obtenerElementos(expresion);
        // Procesar los signos negativos en la lista de elementos
        elementos = procesarSignosNegativos(elementos);
        // Realizar los cálculos en la lista de elementos y obtener el resultado
        double resultado = realizarCalculos(elementos);
        return resultado;
    }

    /**
     * Divide la expresión en partes utilizando los operadores como delimitadores y devuelve una lista de elementos.
     *
     * @param expresion La expresión matemática a dividir.
     * @return Una lista de elementos (números y operadores).
     */
    private List<String> obtenerElementos(String expresion) {
        // Dividir la expresión en partes utilizando los operadores como delimitadores
        String[] partes = expresion.split("(?<=[-+X÷])|(?=[-+X÷])");
        // Convertir el array de partes en una lista y devolverla
        return new ArrayList<>(Arrays.asList(partes));
    }

    /**
     * Procesa los signos negativos en la lista de elementos.
     * Si encuentra un signo negativo seguido de un número, concatena el signo con el número y elimina el signo.
     *
     * @param elementos La lista de elementos a procesar.
     * @return La lista de elementos modificada.
     */
    private List<String> procesarSignosNegativos(List<String> elementos) {
        if (elementos.size() > 1 && elementos.get(0).equals("-") && isNumeric(elementos.get(1))) {
            // Si el primer elemento es un signo negativo seguido de un número, concatena el signo con el número y elimina el signo
            elementos.set(1, "-" + elementos.get(1));
            elementos.remove(0);
        }
        return elementos;
    }

    /**
     * Realiza los cálculos en la lista de elementos.
     * Itera sobre la lista y realiza las operaciones de multiplicación, división, suma y resta.
     *
     * @param elementos La lista de elementos sobre la que realizar los cálculos.
     * @return El resultado final de las operaciones.
     */
    private double realizarCalculos(List<String> elementos) {
        while (elementos.size() > 1) {
            // Buscar el primer operador en la lista de elementos
            int index = encontrarOperador(elementos);
            if (index == -1) {
                // Si no se encuentra ningún operador, salir del bucle
                break;
            }
            // Calcular el resultado de la operación en el índice encontrado
            double resultado = calcularOperacion(elementos, index);
            // Actualizar la lista de elementos después de realizar la operación
            actualizarElementos(elementos, index, resultado);
        }
        // Devolver el resultado final de las operaciones
        return Double.parseDouble(elementos.get(0));
    }

    /**
     * Encuentra el índice del primer operador en la lista de elementos.
     *
     * @param elementos La lista de elementos en la que buscar el operador.
     * @return El índice del primer operador encontrado, o -1 si no se encontró ningún operador.
     */
    private int encontrarOperador(List<String> elementos) {
        // Buscar el índice del primer operador en la lista de elementos
        int index = elementos.indexOf("X");
        if (index == -1) {
            index = elementos.indexOf("÷");
        }
        if (index == -1) {
            index = elementos.indexOf("+");
            if (index == -1) {
                index = elementos.indexOf("-");
            }
        }
        return index;
    }

    /**
     * Calcula el resultado de la operación en el índice dado de la lista de elementos.
     *
     * @param elementos La lista de elementos en la que realizar la operación.
     * @param index     El índice del operador en la lista de elementos.
     * @return El resultado de la operación.
     */
    private double calcularOperacion(List<String> elementos, int index) {
        // Obtener los operandos y el operador en el índice dado
        double operand1 = Double.parseDouble(elementos.get(index - 1));
        double operand2 = Double.parseDouble(elementos.get(index + 1));
        String operador = elementos.get(index);
        double resultado = 0.0;
        // Realizar la operación correspondiente según el operador
        switch (operador) {
            case "X":
                resultado = operand1 * operand2;
                break;
            case "÷":
                if (operand2 == 0) {
                    mostrarAlerta("Error", "No se puede dividir por cero.");
                    return 0.0;
                }
                resultado = operand1 / operand2;
                break;
            case "+":
                resultado = operand1 + operand2;
                break;
            case "-":
                resultado = operand1 - operand2;
                break;
        }
        // Devolver el resultado de la operación
        return resultado;
    }

    /**
     * Actualiza la lista de elementos después de realizar la operación en el índice dado.
     *
     * @param elementos La lista de elementos a actualizar.
     * @param index     El índice del operador en la lista de elementos.
     * @param resultado El resultado de la operación.
     */
    private void actualizarElementos(List<String> elementos, int index, double resultado) {
        // Reemplazar los operandos y el operador con el resultado en la lista de elementos
        elementos.set(index - 1, Double.toString(resultado));
        elementos.remove(index + 1);
        elementos.remove(index);
        // Establecer la bandera hayQueBorrar en true para indicar que se debe borrar el display en la próxima entrada
        hayQueBorrar = true;
    }



    /**
     * Verifica si una cadena es un número.
     *
     * @param str La cadena a verificar.
     * @return true si la cadena es un número, false de lo contrario.
     */
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }


    /**
     * Formatea el resultado para mostrarlo en el display.
     * Elimina los ceros innecesarios de la parte decimal si es posible.
     *
     * @param resultado El resultado a formatear.
     * @return El resultado formateado como cadena.
     */
    private String formatearResultado(double resultado) {
        // Convertir el resultado a una cadena
        String resultadoCadena = Double.toString(resultado);

        // Dividir la cadena en partes enteras y decimales
        String[] partes = resultadoCadena.split("\\.");
        String parteEntera = partes[0];

        // Verificar si hay parte decimal
        if (partes.length > 1) {
            String parteDecimal = partes[1];

            // Verificar si todos los caracteres de la parte decimal son ceros
            boolean todosCeros = true;
            for (int i = 0; i < parteDecimal.length(); i++) {
                char caracter = parteDecimal.charAt(i);
                if (caracter != '0') {
                    todosCeros = false;
                    break;
                }
            }

            // Si todos los caracteres de la parte decimal son ceros, devolver solo la parte entera
            if (todosCeros) {
                return parteEntera;
            }
        }

        // Si contiene un punto decimal o la parte decimal no son todos ceros, devolver el resultado tal como está
        return resultadoCadena;
    }



    /**
     * Maneja el evento de agregar una operación al display.
     * Si hay letras en el display, borra el contenido antes de agregar la operación.
     * No permite agregar más de un operador consecutivo.
     *
     * @param actionEvent El evento de clic en un botón de operación.
     */
    @FXML
    public void addOperaccion(ActionEvent actionEvent) {
        // Si hay letras en el display, borra el contenido y restablece la bandera de letras a false
        if (hayLetras) {
            borrarDisplay();
            hayLetras = false;
        }

        // Obtener el botón presionado
        Button botonPresionado = (Button) actionEvent.getSource();

        // No se puede poner un operador cuando el display está vacío, excepto para el botón de resta (-)
        if (!display.getText().isEmpty() || botonPresionado.getId().equals("btnMenos")) {
            // Solo se permite agregar un operador si no hay operaciones pendientes
            if (operacionesOn) {
                String operacion = "";

                // Determinar qué botón se ha presionado y asignar la operación correspondiente
                switch (botonPresionado.getId()) {
                    case "btnMas":
                        operacion = "+";
                        break;
                    case "btnMenos":
                        operacion = "-";
                        break;
                    case "btnMultiply":
                        operacion = "X";
                        break;
                    case "btnEntre":
                        operacion = "÷";
                        break;
                }

                // Agregar la operación al display y actualizar las banderas
                display.setText(display.getText() + operacion);
                operacionesOn = false;
                hayQueBorrar = false;
            }
        }
    }



    /**
     * Maneja el evento de agregar un valor numérico al display.
     * Si hay letras en el display, borra el contenido antes de agregar el valor.
     * Si se debe borrar el contenido del display, lo borra antes de agregar el valor.
     *
     * @param event El evento de clic en un botón numérico.
     */
    @FXML
    void addValue(ActionEvent event) {
        // Si hay letras en el display, borra el contenido y restablece la bandera de letras a false
        if (hayLetras) {
            borrarDisplay();
            hayLetras = false;
        }

        // Si se debe borrar el contenido del display, lo borra
        if (hayQueBorrar) {
            display.setText("");
        }

        // Agrega el valor del botón presionado al display y actualiza las banderas
        display.setText(display.getText() + ((Button) event.getSource()).getText());
        operacionesOn = true;
    }




    /**
     * Elimina el último carácter del display.
     * Verifica si el display no está vacío antes de eliminar el último carácter.
     * Se invoca cuando se hace clic en el botón de borrar.
     *
     * @param actionEvent Evento de acción generado por el botón de borrar.
     */
    @FXML
    public void elimnarValor(ActionEvent actionEvent) {
        // Verificar si el display no está vacío antes de eliminar el último carácter
        if (!display.getText().isEmpty()) {
            // Eliminar el último carácter del display utilizando el método substring
            display.setText(display.getText().substring(0, display.getText().length() - 1));
        }
    }



    @FXML
    void elinarCompleto(ActionEvent event) {
        borrarDisplay();
    }

    /**
     * Borra completamente el contenido del display y restablece la bandera de operaciones.
     */
    private void borrarDisplay() {
        // Borra completamente el contenido del display
        display.clear();

        // Restablece la bandera de operaciones
        operacionesOn = true;
    }


    /**
     * Abre el repositorio de código de la calculadora en el navegador web predeterminado.
     * Este método se invoca cuando se hace clic en el botón para visitar el repositorio.
     * Utiliza la clase Desktop para abrir el enlace en el navegador.
     *
     * @param event Evento de acción generado por el botón de visitar el repositorio.
     */
    @FXML
    void visitarRepositorio(ActionEvent event) {
        try {
            // Abrir el enlace en el navegador web predeterminado
            Desktop.getDesktop().browse(new URI("https://github.com/alvarowau/Caculadora"));
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir al intentar abrir el enlace
            e.printStackTrace();
        }
    }

    /**
     * Muestra una ventana de alerta con un título y un mensaje personalizados.
     * Este método carga una ventana personalizada desde un archivo FXML y muestra un mensaje de error en ella.
     *
     * @param titulo  El título de la ventana de alerta.
     * @param mensaje El mensaje de error que se mostrará en la ventana.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        try {
            // Cargar el archivo FXML de la ventana personalizada de error
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.ERROR_VISTA));
            Parent root = loader.load();

            // Obtener el controlador de la ventana personalizada
            ErrorController controller = loader.getController();

            // Establecer el mensaje de error en el controlador
            controller.setMensajeError(mensaje);

            // Crear un nuevo Stage para la ventana de alerta y configurarlo
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // La ventana es modal, bloquea otras ventanas
            stage.show();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir al intentar mostrar la ventana de alerta
            e.printStackTrace();
        }
    }

    
}
