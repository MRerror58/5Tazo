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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Controlador principal para manejar la interfaz y el flujo visual del juego.
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class JuegoControlador {
    /** Panel visual donde se muestran las cartas tapadas de las máquinas. */
    @FXML
    private HBox panelMaquinas;

    /**
     * Etiquetas de texto para mostrar la suma, carta en mesa, mazo, estado, turno y
     * temporizador.
     */
    @FXML
    private Label lblSuma, lblCartaMesa, lblCartasRestantes, lblEstado, lblJugadorHumano, lblTurno, lblTemporizador;

    /** Panel contenedor para las cartas del jugador humano. */
    @FXML
    private FlowPane panelCartasHumano;

    /**
     * Panel contenedor para la lista de estados de cada jugador (activo/eliminado).
     */
    @FXML
    private VBox panelEstadoJugadores;

    /**
     * Botón para regresar al inicio y comenzar una nueva partida al finalizar el
     * juego.
     */
    @FXML
    private Button btnNuevaPartida;

    /**
     * Instancia del modelo del juego que maneja las reglas y el estado de la
     * baraja.
     */
    private JuegoModelo modelo;

    /** Número de máquinas oponentes en la partida. */
    private int numMaquinas;

    /** Bandera para saber si es el turno del jugador humano. */
    private boolean turnoHumano;

    /** Hilo para el conteo de segundos del temporizador del turno del jugador. */
    private HiloTemporizador hiloTemporizador;

    /** Generador de números aleatorios para las decisiones de las máquinas. */
    private final Random random = new Random();

    /**
     * Inicializa el estado del controlador y prepara la partida.
     *
     * @param numMaquinas Cantidad de oponentes controlados por la computadora.
     */
    public void inicializarJuego(int numMaquinas) {
        this.numMaquinas = numMaquinas;
        this.modelo = new JuegoModelo();
        modelo.iniciarJuego(numMaquinas);
        turnoHumano = true;
        actualizarVista();
        lblEstado.setText("¡Tu turno! Selecciona una carta para jugar.");
        iniciarTemporizador();
    }

    /**
     * Actualiza todos los elementos gráficos de la pantalla con el estado actual
     * del modelo.
     */
    private void actualizarVista() {
        // Carta de la mesa
        Carta cartaMesa = modelo.getCartaMesa(); // Sincroniza todos los elementos visuales con el estado actual de la
                                                 // partida.
        if (cartaMesa != null) { // Cuando no hay cartas en mesa:
            lblCartaMesa.setText(cartaMesa.toString());
            lblCartaMesa.getStyleClass().removeAll("carta-roja", "carta-negra");
            lblCartaMesa.getStyleClass().add(cartaMesa.getPalo().esRojo() ? "carta-roja" : "carta-negra");
        }

        // Suma actual
        lblSuma.setText(String.valueOf(modelo.getSumaActual()));

        // Cartas humano ---
        panelCartasHumano.getChildren().clear();// eliminamos
        Jugador humano = modelo.getJugadores().get(0);
        if (humano.estaEliminado()) {
            lblJugadorHumano.setText("Jugador (ELIMINADO)");
        } else {
            lblJugadorHumano.setText("Tu mano:");

            List<Carta> cartasJugables = humano.obtenerCartasJugables(modelo.getSumaActual()); // Cartas jugables

            for (Carta carta : humano.getMano()) {// por cada carta en mano
                Label lblCarta = new Label(carta.toString());
                lblCarta.getStyleClass().addAll("carta", "carta-visible",
                        carta.getPalo().esRojo() ? "carta-roja" : "carta-negra");// que carta es

                if (turnoHumano && cartasJugables.contains(carta)) {// si es una carta jugable
                    lblCarta.getStyleClass().add("carta-jugable");
                    lblCarta.setOnMouseClicked(event -> manejarClickCarta(carta));// añadir control de click

                } else if (turnoHumano) {// si no, no tiee controlador de click
                    lblCarta.getStyleClass().add("carta-no-jugable");
                }
                panelCartasHumano.getChildren().add(lblCarta);
            }
        }

        // Cartas máquinas---
        panelMaquinas.getChildren().clear();
        for (int i = 1; i < modelo.getJugadores().size(); i++) {
            // Confirmaciones por si la maquina esta eliminada
            Jugador maquina = modelo.getJugadores().get(i);
            VBox panelM = new VBox(5);
            panelM.setAlignment(Pos.CENTER);
            Label lblNombre = new Label(maquina.getNombre() + (maquina.estaEliminado() ? " ❌" : ""));
            lblNombre.getStyleClass().addAll("maquina-nombre",
                    maquina.estaEliminado() ? "jugador-eliminado" : "jugador-activo");
            panelM.getChildren().add(lblNombre);

            // En caso de no estarlo
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

        // Panel info lateral (Turno actual y estado jugadores)---
        lblTurno.setText(modelo.getJugadorActual().getNombre());
        if (panelEstadoJugadores.getChildren().size() > 1) {
            panelEstadoJugadores.getChildren().remove(1, panelEstadoJugadores.getChildren().size());
        }
        for (Jugador j : modelo.getJugadores()) {
            Label lblJ = new Label((j.estaEliminado() ? "✗ " : "✓ ") + j.getNombre()
                    + (!j.estaEliminado() ? " (" + j.getMano().size() + " cartas)" : ""));
            lblJ.getStyleClass().add(j.estaEliminado() ? "jugador-eliminado" : "jugador-activo");
            panelEstadoJugadores.getChildren().add(lblJ);
        }

        // Cartas restantes---
        lblCartasRestantes.setText(String.valueOf(modelo.getMazo().cantidadCartas()));
    }

    /**
     * Procesa la carta seleccionada por el jugador humano y aplica las reglas del
     * turno.
     *
     * @param carta Carta seleccionada de la mano del jugador.
     */
    private void manejarClickCarta(Carta carta) {
        if (!turnoHumano)
            return; // ignora clicks fuera del turno humano, Evita acciones del usuario cuando el

        // AS
        int valorAs = 1; // el valor minimo para la confirmacion de tirar o no la carta
        if (carta.esAs()) { // el AS permite elegir 1 o 10
            valorAs = preguntarValorAs(carta);
            if (valorAs == -1)
                return; // canceló la selección
        }

        try {
            modelo.jugarCarta(modelo.getJugadores().get(0), carta, valorAs);
            lblEstado.setText("Jugaste: " + carta + " (+" + valorAs + ")");

            // Reponer mano
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

    /**
     * Muestra un cuadro de diálogo para que el usuario elija si el As vale 1 o 10.
     *
     * @param carta Carta de tipo As seleccionada.
     * @return El valor elegido (1 o 10) o -1 si se cancela la acción.
     */
    private int preguntarValorAs(Carta carta) {// Cuando se lanza el As salta ventana emerguente
        Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
        dialogo.setTitle("As lanzado");
        dialogo.setHeaderText("¿Qué valor deseas para el " + carta + "?");// texto de opciones
        dialogo.setContentText("Suma actual: " + modelo.getSumaActual() + "\n" +
                "Sumar 1 → " + (modelo.getSumaActual() + 1) + "\n" +
                "Sumar 10 → " + (modelo.getSumaActual() + 10));
        // Tipo de botones
        ButtonType boton1 = new ButtonType("Sumar 1");
        ButtonType boton10 = new ButtonType("Sumar 10");
        ButtonType cancelar = new ButtonType("Cancelar");
        dialogo.getButtonTypes().setAll(boton1, boton10, cancelar);

        Optional<ButtonType> resultado = dialogo.showAndWait();// Esperamos la respuesta
        if (resultado.isPresent()) {
            if (resultado.get() == boton1)
                return 1;// No se confirma si tiene suma maxima pq algo mas ya lo hace
            // Restriccion en caso de ser 10
            if (resultado.get() == boton10) {
                if (modelo.getSumaActual() + 10 > JuegoModelo.SUMA_MAXIMA) { // evita pasar de 50
                    mostrarAlerta("No permitido", "Sumar 10 excedería el límite de 50.");
                    return 1;
                }
                return 10;
            }
        }
        return -1; // canceló
    }

    /**
     * Inicia y ejecuta los turnos automáticos de los oponentes máquina de forma
     * asíncrona.
     */
    private void ejecutarTurnosMaquinas() {// Iniciamos el turno de maquina
        Thread hiloMaquina = new HiloMaquina();
        hiloMaquina.setDaemon(true);
        hiloMaquina.start();
    }

    /**
     * Hilo encargado de procesar las acciones y retrasos lógicos de las máquinas.
     *
     * @author Juan Camilo Valverde López
     * @version 1.0
     */
    private class HiloMaquina extends Thread {
        /**
         * Ejecuta la lógica automática para cada máquina en su respectivo turno.
         */
        @Override
        public void run() {
            try {
                while (!modelo.isJuegoTerminado() && modelo.getJugadorActual().esMaquina()) { // repite mientras sea
                                                                                              // turno de máquina
                    Jugador maquina = modelo.getJugadorActual(); //¿Cual maquina es?
                

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

                    List<Carta> jugables = maquina.obtenerCartasJugables(modelo.getSumaActual());// Cartas en su mano
                    if (!jugables.isEmpty()) { // juega una carta aleatoria de las válidas
                        Carta cartaElegida = jugables.get(random.nextInt(jugables.size()));

                        //Siempre juega el AS como 10 si no se pasa
                        int valorAs = (cartaElegida.esAs() && modelo.getSumaActual() + 10 <= JuegoModelo.SUMA_MAXIMA)
                                ? 10
                                : 1; // AS = 10 si no se pasa
                        modelo.jugarCarta(maquina, cartaElegida, valorAs);

                        Platform.runLater(() -> {
                            lblEstado.setText(maquina.getNombre() + " jugó: " + cartaElegida);
                            actualizarVista();
                        });

                        Thread.sleep(100 + random.nextInt(100));
                        modelo.tomarCartaDelMazo(maquina); // repone su mano
                        Platform.runLater(() -> actualizarVista());
                    }

                    modelo.siguienteTurno();
                    if (modelo.hayGanador()) {
                        Platform.runLater(() -> finalizarJuego());
                        return;
                    }
                }

                if (!modelo.isJuegoTerminado()) { // vuelve el turno al humano
                    Jugador humano = modelo.getJugadores().get(0);
                    if (modelo.verificarEliminacion(humano)) { // humano eliminado
                        Platform.runLater(() -> {
                            lblEstado.setText("¡No puedes jugar ninguna carta! Has sido ELIMINADO.");
                            actualizarVista();
                        });
                        Thread.sleep(150);
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

    /**
     * Hilo del temporizador que lleva el control del tiempo transcurrido en
     * segundos.
     *
     * @author Juan Camilo Valverde López
     * @version 1.0
     */
    private class HiloTemporizador extends Thread {
        /** Bandera para controlar el bucle principal de ejecución del hilo. */
        private volatile boolean corriendo = true;

        /** Bandera para pausar el conteo del tiempo. */
        private volatile boolean pausado = false;

        /** Cantidad de segundos transcurridos. */
        private int segundos = 0;

        /**
         * Bucle que incrementa los segundos cada segundo mientras esté corriendo y no
         * esté pausado.
         */
        public void run() {
            while (corriendo) { // bucle principal del temporizador
                try {
                    Thread.sleep(1000);
                    if (corriendo && !pausado) { // solo cuenta si no está pausado
                        segundos++;
                        int segs = segundos;
                        Platform.runLater(() -> lblTemporizador.setText(segs + "s"));// Actualiza el temporizador en la
                                                                                     // interfaz utilizando el hilo
                                                                                     // principal de JavaFX.
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        /**
         * Detiene definitivamente el temporizador.
         */
        public void detener() {
            corriendo = false;
            this.interrupt();
        } // termina el hilo

        /**
         * Pausa el conteo del tiempo.
         */
        public void pausar() {
            pausado = true;
        } // congela el contador

        /**
         * Reanuda el conteo del tiempo.
         */
        public void reanudar() {
            pausado = false;
        } // sigue contando

        /**
         * Reinicia el conteo de segundos a cero.
         */
        public void reiniciar() {
            segundos = 0;
            Platform.runLater(() -> lblTemporizador.setText("0s"));
        } // vuelve a 0
    }

    /**
     * Inicializa y arranca el hilo del temporizador.
     */
    private void iniciarTemporizador() {
        hiloTemporizador = new HiloTemporizador();
        hiloTemporizador.setDaemon(true);
        hiloTemporizador.start();
    }

    /**
     * Reinicia el temporizador de juego a cero segundos.
     */
    private void reiniciarTemporizador() {
        if (hiloTemporizador != null)
            hiloTemporizador.reiniciar();
    }

    /**
     * Pausa el conteo de tiempo del temporizador.
     */
    private void pausarTemporizador() {
        if (hiloTemporizador != null)
            hiloTemporizador.pausar();
    }

    /**
     * Reanuda el temporizador pausado previamente.
     */
    private void reanudarTemporizador() {
        if (hiloTemporizador != null)
            hiloTemporizador.reanudar();
    }

    /**
     * Declara el fin del juego, detiene el temporizador y muestra el ganador.
     */
    private void finalizarJuego() {// Fin
        if (hiloTemporizador != null) {
            hiloTemporizador.detener(); // ya no cuenta tiempo
        }
        turnoHumano = false;
        Jugador ganador = modelo.obtenerGanador(); // Obtiene el jugador que logró permanecer activo hasta el final de
                                                   // la partida.
        if (ganador != null) { // muestra resultado final
            String mensaje = "🏆 ¡" + ganador.getNombre() + " GANA la partida! 🏆";
            lblEstado.setText(mensaje);
            lblTurno.setText(ganador.getNombre());
            mostrarAlerta("¡Fin del juego!", mensaje);
        }
        btnNuevaPartida.setVisible(true); // opción para reiniciar
        actualizarVista();
    }

    /**
     * Regresa a la pantalla inicial para configurar e iniciar una nueva partida.
     */
    @FXML
    private void nuevaPartida() {
        if (hiloTemporizador != null) {
            hiloTemporizador.detener();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml")); // Carga nuevamente la
                                                                                            // pantalla inicial para
                                                                                            // permitir comenzar otra
                                                                                            // partida.
            Parent vistaInicio = loader.load();
            Stage stage = (Stage) btnNuevaPartida.getScene().getWindow();
            stage.setScene(new Scene(vistaInicio, 600, 500));
            stage.setTitle("Cincuentazo");
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de inicio: " + e.getMessage());
        }
    }

    /**
     * Muestra una ventana de alerta de tipo informativo con un título y mensaje
     * específico.
     *
     * @param titulo  Título de la alerta.
     * @param mensaje Detalle explicativo de la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
