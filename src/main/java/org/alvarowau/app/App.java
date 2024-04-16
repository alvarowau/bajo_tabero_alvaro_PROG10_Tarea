package org.alvarowau.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.alvarowau.util.Path;

/**
 * Clase principal de la aplicación JavaFX.
 * Esta clase define el punto de entrada de la aplicación y configura la ventana principal de la calculadora.
 *
 * @author Álvaro Bajo
 * @version 1.0
 * @since 16 de abril de 2024
 */
public class App extends Application {

    /**
     * Método principal de la aplicación.
     * Este método inicia la aplicación JavaFX.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en esta aplicación)
     */


    /**
     * Método start de la aplicación JavaFX.
     * Este método configura la interfaz gráfica de la aplicación y muestra la ventana principal de la calculadora.
     *
     * @param primaryStage El escenario principal de la aplicación
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar la vista de la calculadora desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.CALCULADORA_VISTA));
            Parent root = loader.load();

            // Crear una escena con la vista cargada
            Scene scene = new Scene(root);

            // Quitar la decoración de la ventana (barra de título, botones de minimizar, maximizar y cerrar)
            primaryStage.initStyle(StageStyle.UNDECORATED);

            // Hacer que la ventana no sea redimensionable
            primaryStage.setResizable(false);

            // Ajustar la ventana al tamaño del contenido
            primaryStage.sizeToScene();

            // Establecer la escena en la ventana principal
            primaryStage.setScene(scene);

            // Mostrar la ventana principal
            primaryStage.show();
        } catch (Exception e) {
            // Manejar cualquier excepción que ocurra durante la inicialización de la aplicación
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
