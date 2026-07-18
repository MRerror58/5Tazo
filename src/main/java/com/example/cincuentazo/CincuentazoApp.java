package com.example.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal que arranca la aplicación Cincuentazo.
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class CincuentazoApp extends Application {

    /**
     * Inicia y configura el escenario principal de JavaFX.
     *
     * @param stage Escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar la vista de inicio desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                CincuentazoApp.class.getResource("inicio-view.fxml")
        );

        // Crear la escena con el tamaño de la pantalla de inicio
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);

        // Configurar la ventana principal
        stage.setTitle("Cincuentazo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.show();
    }

    /**
     * Método de entrada principal de la aplicación Java.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
