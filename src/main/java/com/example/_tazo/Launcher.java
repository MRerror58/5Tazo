package com.example._tazo;

import javafx.application.Application;

/**
 * Lanzador alternativo de la aplicación JavaFX en caso de problemas con módulos.
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class Launcher {
    /**
     * Punto de entrada secundario que inicia la aplicación HelloApplication.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
