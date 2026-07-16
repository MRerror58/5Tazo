package com.example.cincuentazo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class JuegoControlador {
    @FXML private HBox panelMaquinas;
    @FXML private Label lblSuma, lblCartaMesa, lblCartasRestantes, lblEstado, lblJugadorHumano, lblTurno, lblTemporizador;
    @FXML private FlowPane panelCartasHumano;
    @FXML private VBox panelEstadoJugadores;
    @FXML private Button btnNuevaPartida;

    private JuegoModelo modelo;
    private int numMaquinas;
    private boolean turnoHumano;
    private HiloTemporizador hiloTemporizador;
    private final Random random = new Random();

    // Inicar el juego
    public void inicializarJuego(int numMaquinas) {
        this.numMaquinas = numMaquinas;
        this.modelo = new JuegoModelo();
        modelo.iniciarJuego(numMaquinas);
        turnoHumano = true;
        actualizarVista();
        lblEstado.setText("¡Tu turno! Selecciona una carta para jugar.");
        iniciarTemporizador();
    }

    //Actualizador de elementos
    private void actualizarVista() {
        // Carta de la mesa
        Carta cartaMesa = modelo.getCartaMesa();
        if (cartaMesa != null) { //Cuando no hay cartas en mesa:
            lblCartaMesa.setText(cartaMesa.toString());
            lblCartaMesa.getStyleClass().removeAll("carta-roja", "carta-negra");
            lblCartaMesa.getStyleClass().add(cartaMesa.getPalo().esRojo() ? "carta-roja" : "carta-negra");
        }

        // Suma actual
        lblSuma.setText(String.valueOf(modelo.getSumaActual()));

        // Cartas humano
        panelCartasHumano.getChildren().clear();//eliminamos
        Jugador humano = modelo.getJugadores().get(0);
        if (humano.estaEliminado()) {
            lblJugadorHumano.setText("Jugador (ELIMINADO)");
        } else {
            lblJugadorHumano.setText("Tu mano:");
            List<Carta> cartasJugables = humano.obtenerCartasJugables(modelo.getSumaActual());
            for (Carta carta : humano.getMano()) {//por cada carta en mano
                Label lblCarta = new Label(carta.toString());
                lblCarta.getStyleClass().addAll("carta", "carta-visible", carta.getPalo().esRojo() ? "carta-roja" : "carta-negra");//que carta es
                if (turnoHumano && cartasJugables.contains(carta)) {//si es una carta jugable
                    lblCarta.getStyleClass().add("carta-jugable");
                    lblCarta.setOnMouseClicked(event -> manejarClickCarta(carta));//añadir control de click
                } else if (turnoHumano) {//si no, no tiee controlador de click
                    lblCarta.getStyleClass().add("carta-no-jugable");
                }
                panelCartasHumano.getChildren().add(lblCarta);
            }
        }

        // Cartas máquinas
        panelMaquinas.getChildren().clear();
        for (int i = 1; i < modelo.getJugadores().size(); i++) {
            //Confirmaciones por si la maquina esta eliminada
            Jugador maquina = modelo.getJugadores().get(i);
            VBox panelM = new VBox(5);
            panelM.setAlignment(Pos.CENTER);
            Label lblNombre = new Label(maquina.getNombre() + (maquina.estaEliminado() ? " ❌" : ""));
            lblNombre.getStyleClass().addAll("maquina-nombre", maquina.estaEliminado() ? "jugador-eliminado" : "jugador-activo");
            panelM.getChildren().add(lblNombre);

            //En caso de no estarlo
            if (!maquina.estaEliminado()) {
                HBox cartasM = new HBox(5);
                cartasM.setAlignment(Pos.CENTER);
                for (int j = 0; j < maquina.getMano().size(); j++) {
                    Label lblC = new Label("🂠");
                    lblC.getStyleClass().addAll("carta", "carta-oculta");
                    cartasM.getChildren().add(lblC);
                }
                panelM.getChildren().add(cartasM);
            }
            panelMaquinas.getChildren().add(panelM);
        }

        // Panel info lateral (Turno actual y estado jugadores)
        lblTurno.setText(modelo.getJugadorActual().getNombre());
        if (panelEstadoJugadores.getChildren().size() > 1) {
            panelEstadoJugadores.getChildren().remove(1, panelEstadoJugadores.getChildren().size());
        }
        for (Jugador j : modelo.getJugadores()) {
            Label lblJ = new Label((j.estaEliminado() ? "✗ " : "✓ ") + j.getNombre() + (!j.estaEliminado() ? " (" + j.getMano().size() + " cartas)" : ""));
            lblJ.getStyleClass().add(j.estaEliminado() ? "jugador-eliminado" : "jugador-activo");
            panelEstadoJugadores.getChildren().add(lblJ);
        }

        // Cartas restantes
        lblCartasRestantes.setText(String.valueOf(modelo.getMazo().cantidadCartas()));
    }

    private void manejarClickCarta(Carta carta) {
        if (!turnoHumano) return; // ignora clicks fuera del turno humano
        int valorAs = 1;
        if (carta.esAs()) { // el AS permite elegir 1 o 10
            valorAs = preguntarValorAs(carta);
            if (valorAs == -1) return; // canceló la selección
        }

        try {
            modelo.jugarCarta(modelo.getJugadores().get(0), carta, valorAs);
            lblEstado.setText("Jugaste: " + carta + " (+" + valorAs + ")");
            modelo.tomarCartaDelMazo(modelo.getJugadores().get(0));

            turnoHumano = false;
            pausarTemporizador();
            actualizarVista();
            modelo.siguienteTurno();

            if (modelo.hayGanador()) { // alguien ganó, fin del juego
                finalizarJuego();
                return;
            }

            ejecutarTurnosMaquinas();
        } catch (IllegalArgumentException e) { // carta inválida por suma
            mostrarAlerta("Carta no válida", e.getMessage());
        }
    }

    private int preguntarValorAs(Carta carta) {// Cuando se lanza el As salta ventana emerguente
        Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
        dialogo.setTitle("As lanzado");
        dialogo.setHeaderText("¿Qué valor deseas para el " + carta + "?");//texto de opciones
        dialogo.setContentText("Suma actual: " + modelo.getSumaActual() + "\n" +
                "Sumar 1 → " + (modelo.getSumaActual() + 1) + "\n" +
                "Sumar 10 → " + (modelo.getSumaActual() + 10));
        //Tipo de botones
        ButtonType boton1 = new ButtonType("Sumar 1");
        ButtonType boton10 = new ButtonType("Sumar 10");
        ButtonType cancelar = new ButtonType("Cancelar");
        dialogo.getButtonTypes().setAll(boton1, boton10, cancelar);

        Optional<ButtonType> resultado = dialogo.showAndWait();//Esperamos la respuesta
        if (resultado.isPresent()) {
            if (resultado.get() == boton1) return 1;//No se confirma si tiene suma maxima pq algo mas ya lo hace
            if (resultado.get() == boton10) {//Indicamos al jugador si puede o no lanzar este valor
                if (modelo.getSumaActual() + 10 > JuegoModelo.SUMA_MAXIMA) { // evita pasar de 50
                    mostrarAlerta("No permitido", "Sumar 10 excedería el límite de 50.");
                    return 1;
                }
                return 10;
            }
        }
        return -1; // canceló
    }

    private void ejecutarTurnosMaquinas() {//Iniciamos el turno de maquina
        Thread hiloMaquina = new HiloMaquina();
        hiloMaquina.setDaemon(true);
        hiloMaquina.start();
    }

    private class HiloMaquina extends Thread {
        @Override
        public void run() {
            try {
                while (!modelo.isJuegoTerminado() && modelo.getJugadorActual().esMaquina()) { // repite mientras sea turno de máquina
                    Jugador maquina = modelo.getJugadorActual();

                    if (modelo.verificarEliminacion(maquina)) { // si no puede jugar, queda eliminada
                        Platform.runLater(() -> {
                            lblEstado.setText(maquina.getNombre() + " no puede jugar. ¡ELIMINADO!");
                            actualizarVista();
                        });
                        modelo.siguienteTurno();
                        if (modelo.hayGanador()) { // fin de juego
                            Platform.runLater(() -> finalizarJuego());
                            return;
                        }
                        continue; // pasa a la siguiente máquina
                    }

                    Platform.runLater(() -> lblEstado.setText(maquina.getNombre() + " está pensando..."));
                    Thread.sleep(350 + random.nextInt(500)); // simula "pensar"

                    List<Carta> jugables = maquina.obtenerCartasJugables(modelo.getSumaActual());//Cartas en su mano
                    if (!jugables.isEmpty()) { // juega una carta aleatoria de las válidas
                        Carta cartaElegida = jugables.get(random.nextInt(jugables.size()));
                        int valorAs = (cartaElegida.esAs() && modelo.getSumaActual() + 10 <= JuegoModelo.SUMA_MAXIMA) ? 10 : 1; // AS = 10 si no se pasa
                        modelo.jugarCarta(maquina, cartaElegida, valorAs);

                        Platform.runLater(() -> {
                            lblEstado.setText(maquina.getNombre() + " jugó: " + cartaElegida);
                            actualizarVista();
                        });

                        Thread.sleep(150 + random.nextInt(200));
                        modelo.tomarCartaDelMazo(maquina); // repone su mano
                        Platform.runLater(() -> actualizarVista());
                    }

                    modelo.siguienteTurno();
                    if (modelo.hayGanador()) {
                        Platform.runLater(() -> finalizarJuego());
                        return;
                    }
                    // No reiniciar temporizador aquí, se hace al volver al humano
                }

                if (!modelo.isJuegoTerminado()) { // vuelve el turno al humano
                    Jugador humano = modelo.getJugadores().get(0);
                    if (modelo.verificarEliminacion(humano)) { // humano eliminado
                        Platform.runLater(() -> {
                            lblEstado.setText("¡No puedes jugar ninguna carta! Has sido ELIMINADO.");
                            actualizarVista();
                        });
                        Thread.sleep(350);
                        modelo.siguienteTurno();
                        if (modelo.hayGanador()) {
                            Platform.runLater(() -> finalizarJuego());
                        } else { // sigue el juego entre máquinas
                            Platform.runLater(() -> {
                                reiniciarTemporizador();
                                ejecutarTurnosMaquinas();
                            });
                        }
                    } else { // habilita turno del humano
                        Platform.runLater(() -> {
                            turnoHumano = true;
                            lblEstado.setText("¡Tu turno! Selecciona una carta.");
                            reiniciarTemporizador();
                            reanudarTemporizador();
                            actualizarVista();
                        });
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class HiloTemporizador extends Thread {
        private volatile boolean corriendo = true;
        private volatile boolean pausado = false;
        private int segundos = 0;

        @Override
        public void run() {
            while (corriendo) { // bucle principal del temporizador
                try {
                    Thread.sleep(1000);
                    if (corriendo && !pausado) { // solo cuenta si no está pausado
                        segundos++;
                        int segs = segundos;
                        Platform.runLater(() -> lblTemporizador.setText(segs + "s"));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        public void detener() { corriendo = false; this.interrupt(); } // termina el hilo
        public void pausar() { pausado = true; } // congela el contador
        public void reanudar() { pausado = false; } // sigue contando
        public void reiniciar() { segundos = 0; Platform.runLater(() -> lblTemporizador.setText("0s")); } // vuelve a 0
    }

    //UTILIDADES del temporizador
    private void iniciarTemporizador() {
        hiloTemporizador = new HiloTemporizador();
        hiloTemporizador.setDaemon(true);
        hiloTemporizador.start();
    }
    private void reiniciarTemporizador() {
        if (hiloTemporizador != null) hiloTemporizador.reiniciar();
    }
    private void pausarTemporizador() {
        if (hiloTemporizador != null) hiloTemporizador.pausar();
    }
    private void reanudarTemporizador() {
        if (hiloTemporizador != null) hiloTemporizador.reanudar();
    }


    private void finalizarJuego() {//Fin
        if (hiloTemporizador != null) {
            hiloTemporizador.detener(); // ya no cuenta tiempo
        }
        turnoHumano = false;
        Jugador ganador = modelo.obtenerGanador();
        if (ganador != null) { // muestra resultado final
            String mensaje = "🏆 ¡" + ganador.getNombre() + " GANA la partida! 🏆";
            lblEstado.setText(mensaje);
            lblTurno.setText(ganador.getNombre());
            mostrarAlerta("¡Fin del juego!", mensaje);
        }
        btnNuevaPartida.setVisible(true); // opción para reiniciar
        actualizarVista();
    }

    @FXML
    private void nuevaPartida() {
        if (hiloTemporizador != null) {
            hiloTemporizador.detener();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
            Parent vistaInicio = loader.load();
            Stage stage = (Stage) btnNuevaPartida.getScene().getWindow();
            stage.setScene(new Scene(vistaInicio, 600, 500));
            stage.setTitle("Cincuentazo");
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de inicio: " + e.getMessage());
        }
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
