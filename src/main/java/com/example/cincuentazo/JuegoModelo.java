package com.example.cincuentazo;

import java.util.ArrayList;
import java.util.List;

public class JuegoModelo {
    public static final int CARTAS_POR_JUGADOR = 4;
    public static final int SUMA_MAXIMA = 50;

    private Mazo mazo;
    private final List<Jugador> jugadores = new ArrayList<>();
    private List<Carta> cartasMesa = new ArrayList<>();
    private int sumaActual = 0;
    private int jugadorActualIndex = 0;
    private boolean juegoTerminado = false;

    public JuegoModelo() {}

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

    public void jugarCarta(Jugador jugador, Carta carta, int valorAs) {
        int valorCarta = carta.esAs() ? valorAs : carta.obtenerValor(); // AS toma el valor elegido
        if (sumaActual + valorCarta > SUMA_MAXIMA) { // valida que no supere 50
            throw new IllegalArgumentException("La carta excede la suma de 50.");
        }
        jugador.removerCarta(carta);
        cartasMesa.add(carta);
        sumaActual += valorCarta;
    }

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

    public boolean verificarEliminacion(Jugador jugador) {
        if (!jugador.puedeJugar(sumaActual)) { // sin jugables -> queda fuera
            jugador.eliminar();
            mazo.agregarCartasAlFinal(new ArrayList<>(jugador.getMano())); // pasa su mano al mazo
            jugador.getMano().clear();
            return true;
        }
        return false;
    }

    public void siguienteTurno() {
        do { // avanza al siguiente jugador activo
            jugadorActualIndex = (jugadorActualIndex + 1) % jugadores.size();
        } while (jugadores.get(jugadorActualIndex).estaEliminado() && !hayGanador());
    }

    public boolean hayGanador() {
        long activos = jugadores.stream().filter(j -> !j.estaEliminado()).count(); // cuenta vivos
        if (activos <= 1) { // último en pie gana
            juegoTerminado = true;
            return true;
        }
        return false;
    }

    public Jugador obtenerGanador() {
        if (!juegoTerminado) return null; // sin fin de juego, no hay ganador
        return jugadores.stream().filter(j -> !j.estaEliminado()).findFirst().orElse(null);
    }

    public Jugador getJugadorActual() { return jugadores.get(jugadorActualIndex); }
    public int getSumaActual() { return sumaActual; }
    public Carta getCartaMesa() { return cartasMesa.isEmpty() ? null : cartasMesa.get(cartasMesa.size() - 1); }
    public List<Jugador> getJugadores() { return jugadores; }
    public Mazo getMazo() { return mazo; }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public List<Carta> getCartasMesa() { return cartasMesa; }
}
