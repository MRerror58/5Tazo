package com.example.cincuentazo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.io.IOException;

public class InicioControlador {
    @FXML private ToggleGroup grupoMaquinas;
    @FXML private RadioButton radio1, radio2, radio3;

    @FXML
    private void iniciarJuego(ActionEvent event) {
        int numMaquinas = radio2.isSelected() ? 2 : (radio3.isSelected() ? 3 : 1); // lee la opción elegida
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("juego-view.fxml"));
            Parent vistaJuego = loader.load();
            JuegoControlador controladorJuego = loader.getController();
            controladorJuego.inicializarJuego(numMaquinas);

            int anchoBase = 900;
            int anchoExtra = (numMaquinas == 2) ? 120 : (numMaquinas == 3 ? 260 : 0); // ajusta ancho según rivales
            Stage stage = (Stage) radio1.getScene().getWindow();
            stage.setScene(new Scene(vistaJuego, anchoBase + anchoExtra, 650));
            stage.setTitle("Cincuentazo - Jugando contra " + numMaquinas + " máquina(s)");
        } catch (IOException e) { // si falla la carga del FXML
            System.err.println("Error al cargar la vista del juego: " + e.getMessage());
        }
    }
    }