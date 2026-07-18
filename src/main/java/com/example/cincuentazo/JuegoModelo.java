package com.example.cincuentazo;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de negocio que implementa las reglas de Cincuentazo.
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class JuegoModelo {
    /** Cantidad de cartas que se le reparten a cada jugador al comenzar. */
    public static final int CARTAS_POR_JUGADOR = 4;
    
    /** Valor límite acumulado que ningún jugador puede sobrepasar (50). */
    public static final int SUMA_MAXIMA = 50;

    /** Mazo principal de cartas de la partida. */
    private Mazo mazo;
    
    /** Lista con todos los participantes de la partida (humano y máquinas). */
    private final List<Jugador> jugadores = new ArrayList<>();
    
    /** Lista de cartas que han sido jugadas en la mesa. */
    private List<Carta> cartasMesa = new ArrayList<>();
    
    /** Suma acumulada total en la mesa actual. */
    private int sumaActual = 0;
    
    /** Índice que apunta al jugador que tiene el turno actual. */
    private int jugadorActualIndex = 0;
    
    /** Bandera para controlar si el juego ha finalizado. */
    private boolean juegoTerminado = false;

    /**
     * Constructor por defecto del modelo.
     */
    public JuegoModelo() {}

    /**
     * Prepara e inicia una nueva partida configurando jugadores, mazo y la carta de inicio.
     *
     * @param numMaquinas Cantidad de oponentes controlados por la computadora.
     */
    public void iniciarJuego(int numMaquinas) {
        jugadores.clear();
        cartasMesa.clear();
        sumaActual = 0;
        jugadorActualIndex = 0;
        juegoTerminado = false;

        mazo = Mazo.crearMazoCompleto();
        jugadores.add(new Jugador("Jugador", false));
        for (int i = 1; i <= numMaquinas; i++) { // crea N jugadores máquina
            jugadores.add(new Jugador("Máquina " + i, true));
        }

        for (Jugador jugador : jugadores) { // reparte mano inicial a cada jugador
            for (int i = 0; i < CARTAS_POR_JUGADOR; i++) { // 4 cartas por jugador
                jugador.agregarCarta(mazo.tomarCarta());
            }
        }

        Carta cartaInicial;
        do { // descarta cartas cuyo valor inicial no sea válido (9, A o J/Q/K)
            cartaInicial = mazo.tomarCarta();
        } while (cartaInicial.obtenerValor() != 0
                && cartaInicial.obtenerValor() != 1
                && cartaInicial.obtenerValor() != 10);
        cartasMesa.add(cartaInicial);
        sumaActual = cartaInicial.obtenerValor();
    }

    /**
     * Ejecuta la acción de jugar una carta y actualiza la suma acumulada de la mesa.
     *
     * @param jugador El participante que juega la carta.
     * @param carta La carta a ser jugada.
     * @param valorAs El valor numérico seleccionado en caso de que sea un As.
     */
    public void jugarCarta(Jugador jugador, Carta carta, int valorAs) {
        int valorCarta = carta.esAs() ? valorAs : carta.obtenerValor(); // AS toma el valor elegido
        if (sumaActual + valorCarta > SUMA_MAXIMA) { // valida que no supere 50
            throw new IllegalArgumentException("La carta excede la suma de 50.");
        }
        jugador.removerCarta(carta);
        cartasMesa.add(carta);
        sumaActual += valorCarta;
    }

    /**
     * Repone la mano del jugador dándole una carta del mazo. Recicla si es necesario.
     *
     * @param jugador El participante que toma la carta.
     * @return true si pudo robar carta, false de lo contrario.
     */
    public boolean tomarCartaDelMazo(Jugador jugador) {
        if (mazo.estaVacio()) { // si no quedan cartas, recicla las de la mesa
            cartasMesa = mazo.reciclarMesa(cartasMesa);
            if (mazo.estaVacio()) { // imposible continuar sin cartas
                return false;
            }
        }
        jugador.agregarCarta(mazo.tomarCarta());
        return true;
    }

    /**
     * Revisa si un jugador no tiene movimientos válidos y lo elimina de la partida.
     *
     * @param jugador El participante evaluado.
     * @return true si el jugador fue eliminado, false en caso contrario.
     */
    public boolean verificarEliminacion(Jugador jugador) {
        if (!jugador.puedeJugar(sumaActual)) { // sin jugables -> queda fuera
            jugador.eliminar();
            mazo.agregarCartasAlFinal(new ArrayList<>(jugador.getMano())); // pasa su mano al mazo
            jugador.getMano().clear();
            return true;
        }
        return false;
    }

    /**
     * Cambia el turno al siguiente jugador activo (no eliminado).
     */
    public void siguienteTurno() {
        do { // avanza al siguiente jugador activo
            jugadorActualIndex = (jugadorActualIndex + 1) % jugadores.size();
        } while (jugadores.get(jugadorActualIndex).estaEliminado() && !hayGanador());
    }

    /**
     * Determina si hay un ganador (solo queda un jugador activo).
     *
     * @return true si hay un ganador, false en caso contrario.
     */
    public boolean hayGanador() {
        long activos = jugadores.stream().filter(j -> !j.estaEliminado()).count(); // cuenta vivos
        if (activos <= 1) { // último en pie gana
            juegoTerminado = true;
            return true;
        }
        return false;
    }

    /**
     * Retorna el jugador que ganó la partida una vez terminado el juego.
     *
     * @return El jugador ganador, o null si el juego no ha terminado.
     */
    public Jugador obtenerGanador() {
        if (!juegoTerminado) return null; // sin fin de juego, no hay ganador
        return jugadores.stream().filter(j -> !j.estaEliminado()).findFirst().orElse(null);
    }

    /**
     * Retorna el jugador que posee el turno actual.
     *
     * @return Jugador actual.
     */
    public Jugador getJugadorActual() { return jugadores.get(jugadorActualIndex); }

    /**
     * Retorna la suma acumulada de la mesa.
     *
     * @return Valor acumulado actual.
     */
    public int getSumaActual() { return sumaActual; }

    /**
     * Retorna la carta que está visible en el tope de la mesa.
     *
     * @return La última carta jugada.
     */
    public Carta getCartaMesa() { return cartasMesa.isEmpty() ? null : cartasMesa.get(cartasMesa.size() - 1); }

    /**
     * Retorna la lista de todos los jugadores en la partida.
     *
     * @return Lista de jugadores.
     */
    public List<Jugador> getJugadores() { return jugadores; }

    /**
     * Retorna la instancia del mazo de cartas.
     *
     * @return El mazo del juego.
     */
    public Mazo getMazo() { return mazo; }

    /**
     * Indica si la partida ha finalizado.
     *
     * @return true si el juego terminó, false de lo contrario.
     */
    public boolean isJuegoTerminado() { return juegoTerminado; }

    /**
     * Retorna la lista de cartas acumuladas en la mesa.
     *
     * @return Lista de cartas jugadas.
     */
    public List<Carta> getCartasMesa() { return cartasMesa; }
}
