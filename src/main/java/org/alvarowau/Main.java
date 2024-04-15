package org.alvarowau;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(Path.CALCULADORA_VISTA));
        primaryStage.setTitle("Calculadora");

        // Establecer la escena
        Scene scene = new Scene(root);

        // Hacer que la ventana no sea redimensionable
        primaryStage.setResizable(false);

        // Adaptar la ventana al tamaño del contenido
        primaryStage.sizeToScene();

        // Establecer la escena en la ventana
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
