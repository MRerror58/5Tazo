package com.example.cincuentazo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaTest {

    @Test
    @DisplayName("Cartas 2-8 deben sumar su número")
    void testCartasNumericas() {
        Carta carta2 = new Carta(Carta.Palo.CORAZONES, Carta.Valor.DOS);
        Carta carta5 = new Carta(Carta.Palo.PICAS, Carta.Valor.CINCO);
        Carta carta8 = new Carta(Carta.Palo.DIAMANTES, Carta.Valor.OCHO);

        assertEquals(2, carta2.obtenerValor());
        assertEquals(5, carta5.obtenerValor());
        assertEquals(8, carta8.obtenerValor());
    }

    @Test
    @DisplayName("Carta 10 debe sumar 10")
    void testCartaDiez() {
        Carta carta10 = new Carta(Carta.Palo.TREBOLES, Carta.Valor.DIEZ);
        assertEquals(10, carta10.obtenerValor());
    }

    @Test
    @DisplayName("Carta 9 no debe sumar ni restar (valor 0)")
    void testCartaNueve() {
        Carta carta9 = new Carta(Carta.Palo.CORAZONES, Carta.Valor.NUEVE);
        assertEquals(0, carta9.obtenerValor());
    }

    @Test
    @DisplayName("Cartas J, Q, K deben restar 10")
    void testCartasFiguras() {
        Carta cartaJ = new Carta(Carta.Palo.PICAS, Carta.Valor.J);
        Carta cartaQ = new Carta(Carta.Palo.DIAMANTES, Carta.Valor.Q);
        Carta cartaK = new Carta(Carta.Palo.CORAZONES, Carta.Valor.K);

        assertEquals(-10, cartaJ.obtenerValor());
        assertEquals(-10, cartaQ.obtenerValor());
        assertEquals(-10, cartaK.obtenerValor());
    }

    @Test
    @DisplayName("As debe retornar 1 por defecto")
    void testCartaAs() {
        Carta cartaA = new Carta(Carta.Palo.TREBOLES, Carta.Valor.AS);
        assertEquals(1, cartaA.obtenerValor());
    }

    @Test
    @DisplayName("esAs() debe identificar correctamente los Ases")
    void testEsAs() {
        Carta cartaAs = new Carta(Carta.Palo.CORAZONES, Carta.Valor.AS);
        Carta cartaRey = new Carta(Carta.Palo.PICAS, Carta.Valor.K);
        Carta carta5 = new Carta(Carta.Palo.DIAMANTES, Carta.Valor.CINCO);

        assertTrue(cartaAs.esAs());
        assertFalse(cartaRey.esAs());
        assertFalse(carta5.esAs());
    }

    @Test
    @DisplayName("toString() debe mostrar valor y símbolo del palo")
    void testToString() {
        Carta carta = new Carta(Carta.Palo.CORAZONES, Carta.Valor.AS);
        assertEquals("A♥", carta.toString());

        Carta carta10 = new Carta(Carta.Palo.PICAS, Carta.Valor.DIEZ);
        assertEquals("10♠", carta10.toString());
    }

    @Test
    @DisplayName("Los palos deben tener el color correcto")
    void testColorPalo() {
        Carta cartaCorazones = new Carta(Carta.Palo.CORAZONES, Carta.Valor.AS);
        Carta cartaDiamantes = new Carta(Carta.Palo.DIAMANTES, Carta.Valor.DOS);
        Carta cartaTreboles = new Carta(Carta.Palo.TREBOLES, Carta.Valor.TRES);
        Carta cartaPicas = new Carta(Carta.Palo.PICAS, Carta.Valor.CUATRO);

        assertTrue(cartaCorazones.getPalo().esRojo());
        assertTrue(cartaDiamantes.getPalo().esRojo());
        assertFalse(cartaTreboles.getPalo().esRojo());
        assertFalse(cartaPicas.getPalo().esRojo());
    }
}
