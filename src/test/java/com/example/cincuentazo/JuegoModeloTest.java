package com.example.cincuentazo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JuegoModeloTest {

    private JuegoModelo modelo;

    @BeforeEach
    void setUp() {
        modelo = new JuegoModelo();
        modelo.iniciarJuego(2);
    }

    @Test
    @DisplayName("Iniciar juego con 2 máquinas crea 3 jugadores")
    void testIniciarJuegoCreaJugadores() {
        assertEquals(3, modelo.getJugadores().size());
        assertFalse(modelo.getJugadores().get(0).esMaquina());
        assertTrue(modelo.getJugadores().get(1).esMaquina());
        assertTrue(modelo.getJugadores().get(2).esMaquina());
    }

    @Test
    @DisplayName("Cada jugador recibe 4 cartas al iniciar")
    void testCartasIniciales() {
        for (Jugador jugador : modelo.getJugadores()) {
            assertEquals(JuegoModelo.CARTAS_POR_JUGADOR, jugador.getMano().size());
        }
    }

    @Test
    @DisplayName("Debe haber una carta inicial en la mesa")
    void testCartaInicialMesa() {
        assertNotNull(modelo.getCartaMesa());
        assertFalse(modelo.getCartasMesa().isEmpty());
    }

    @Test
    @DisplayName("Jugar carta válida actualiza la suma")
    void testJugarCartaActualizaSuma() {
        Jugador humano = modelo.getJugadores().get(0);
        int sumaAntes = modelo.getSumaActual();

        Carta cartaJugable = null;
        int valorCarta = 0;
        for (Carta c : humano.getMano()) {
            int val = c.esAs() ? 1 : c.obtenerValor();
            if (sumaAntes + val <= JuegoModelo.SUMA_MAXIMA) {
                cartaJugable = c;
                valorCarta = val;
                break;
            }
        }

        if (cartaJugable != null) {
            modelo.jugarCarta(humano, cartaJugable, cartaJugable.esAs() ? 1 : 0);
            assertEquals(sumaAntes + valorCarta, modelo.getSumaActual());
        }
    }

    @Test
    @DisplayName("Jugar carta que excede 50 lanza IllegalArgumentException")
    void testJugarCartaExcede50() {
        assertThrows(IllegalArgumentException.class, () -> {
            JuegoModelo m = new JuegoModelo();
            m.iniciarJuego(1);
            Jugador j = m.getJugadores().get(0);

            while (m.getSumaActual() < 45) {
                Carta carta10 = new Carta(Carta.Palo.PICAS, Carta.Valor.DIEZ);
                j.agregarCarta(carta10);
                m.jugarCarta(j, carta10, 0);
            }
            Carta cartaFinal = new Carta(Carta.Palo.DIAMANTES, Carta.Valor.DIEZ);
            j.agregarCarta(cartaFinal);
            m.jugarCarta(j, cartaFinal, 0);
        });
    }

    @Test
    @DisplayName("Jugador sin cartas jugables es eliminado")
    void testEliminacionJugador() {
        Jugador jugadorTest = new Jugador("Test", true);
        jugadorTest.agregarCarta(new Carta(Carta.Palo.CORAZONES, Carta.Valor.DIEZ));
        jugadorTest.agregarCarta(new Carta(Carta.Palo.PICAS, Carta.Valor.OCHO));
        jugadorTest.agregarCarta(new Carta(Carta.Palo.DIAMANTES, Carta.Valor.SIETE));
        jugadorTest.agregarCarta(new Carta(Carta.Palo.TREBOLES, Carta.Valor.SEIS));

        assertTrue(jugadorTest.puedeJugar(44));
        assertFalse(jugadorTest.puedeJugar(50));
    }

    @Test
    @DisplayName("siguienteTurno() avanza al siguiente jugador")
    void testSiguienteTurno() {
        assertEquals("Jugador", modelo.getJugadorActual().getNombre());

        modelo.siguienteTurno();
        assertEquals("Máquina 1", modelo.getJugadorActual().getNombre());

        modelo.siguienteTurno();
        assertEquals("Máquina 2", modelo.getJugadorActual().getNombre());

        modelo.siguienteTurno();
        assertEquals("Jugador", modelo.getJugadorActual().getNombre());
    }

    @Test
    @DisplayName("Iniciar con 1 máquina crea 2 jugadores")
    void testIniciarCon1Maquina() {
        JuegoModelo modeloUno = new JuegoModelo();
        modeloUno.iniciarJuego(1);
        assertEquals(2, modeloUno.getJugadores().size());
    }

    @Test
    @DisplayName("Iniciar con 3 máquinas crea 4 jugadores")
    void testIniciarCon3Maquinas() {
        JuegoModelo modeloTres = new JuegoModelo();
        modeloTres.iniciarJuego(3);
        assertEquals(4, modeloTres.getJugadores().size());
    }

    @Test
    @DisplayName("El juego no ha terminado al iniciar")
    void testJuegoNoTerminadoAlInicio() {
        assertFalse(modelo.isJuegoTerminado());
        assertFalse(modelo.hayGanador());
    }
}
