package com.example.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación Cincuentazo.
 * Extiende {@link Application} de JavaFX para iniciar la interfaz gráfica.
 *
 * <p>Esta clase es el punto de entrada de la aplicación.
 * Carga la vista de inicio donde el jugador puede seleccionar
 * la cantidad de máquinas contra las que desea jugar.</p>
 *
 * @author Equipo Cincuentazo
 */
public class CincuentazoApp extends Application {

    /**
     * Método principal de JavaFX que se ejecuta al iniciar la aplicación.
     * Carga el archivo FXML de la pantalla de inicio y configura la ventana.
     *
     * @param stage la ventana principal de la aplicación
     * @throws IOException si no se puede cargar el archivo FXML
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
     * Método main para iniciar la aplicación.
     * Llama a {@link Application#launch(String...)} que internamente
     * crea una instancia de esta clase y ejecuta {@link #start(Stage)}.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
