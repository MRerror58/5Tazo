package com.example._tazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Aplicación de demostración inicial de JavaFX creada por defecto.
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class HelloApplication extends Application {
    /**
     * Carga y muestra la ventana de bienvenida básica.
     *
     * @param stage Escenario principal.
     * @throws IOException Si falla al cargar el FXML de prueba.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
