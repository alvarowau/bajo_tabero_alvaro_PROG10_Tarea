package org.alvarowau.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controlador para la ventana de error.
 * Esta clase controla la ventana de error en la aplicación, permitiendo mostrar mensajes de error y cerrar la ventana cuando sea necesario.
 *
 * @author Álvaro Bajo
 * @version 1.0
 * @since 16 de abril de 2024
 */
public class ErrorController {

    @FXML
    private VBox ventanaError;

    @FXML
    private Label mensajeLabel;

    @FXML
    private Button cerrarButton;

    /**
     * Maneja el evento de cerrar la ventana de error.
     * Cierra la ventana de error al hacer clic en el botón de cerrar.
     */
    @FXML
    void cerrarVentana() {
        // Cerrar la ventana de error
        ventanaError.getScene().getWindow().hide();
    }

    /**
     * Establece el mensaje de error en la etiqueta de mensaje.
     *
     * @param mensaje El mensaje de error a establecer.
     */
    public void setMensajeError(String mensaje) {
        mensajeLabel.setText(mensaje);
    }

}
