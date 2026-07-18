package com.example._tazo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controlador de prueba para la interfaz de bienvenida por defecto.
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class HelloController {
    /** Etiqueta de texto para mostrar el mensaje de bienvenida. */
    @FXML
    private Label welcomeText;

    /**
     * Cambia el texto de la etiqueta al hacer clic en el botón de prueba.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
