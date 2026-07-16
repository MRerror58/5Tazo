package com.example.cincuentazo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazoTest {

    private Mazo mazo;

    @BeforeEach
    void setUp() {
        mazo = Mazo.crearMazoCompleto();
    }

    @Test
    @DisplayName("Un mazo completo debe tener 52 cartas")
    void testMazoCompletoTiene52Cartas() {
        assertEquals(52, mazo.cantidadCartas());
    }

    @Test
    @DisplayName("Tomar una carta reduce el mazo en 1")
    void testTomarCartaReduceMazo() {
        int cartasAntes = mazo.cantidadCartas();
        Carta carta = mazo.tomarCarta();

        assertNotNull(carta);
        assertEquals(cartasAntes - 1, mazo.cantidadCartas());
    }

    @Test
    @DisplayName("Tomar carta de mazo vacío lanza IllegalStateException")
    void testTomarCartaMazoVacio() {
        Mazo mazoVacio = new Mazo();
        assertThrows(IllegalStateException.class, () -> mazoVacio.tomarCarta());
    }

    @Test
    @DisplayName("Un mazo nuevo (sin cartas) debe estar vacío")
    void testMazoVacio() {
        Mazo mazoVacio = new Mazo();
        assertTrue(mazoVacio.estaVacio());
    }

    @Test
    @DisplayName("Un mazo completo no debe estar vacío")
    void testMazoCompletoNoVacio() {
        assertFalse(mazo.estaVacio());
    }

    @Test
    @DisplayName("Barajar no cambia la cantidad de cartas")
    void testBarajarMantieneCantidad() {
        int cartasAntes = mazo.cantidadCartas();
        mazo.barajar();
        assertEquals(cartasAntes, mazo.cantidadCartas());
    }

    @Test
    @DisplayName("Agregar cartas al final incrementa el mazo")
    void testAgregarCartasAlFinal() {
        Mazo mazoPeq = new Mazo();
        List<Carta> cartas = new ArrayList<>();
        cartas.add(new Carta(Carta.Palo.CORAZONES, Carta.Valor.AS));
        cartas.add(new Carta(Carta.Palo.PICAS, Carta.Valor.K));

        mazoPeq.agregarCartasAlFinal(cartas);
        assertEquals(2, mazoPeq.cantidadCartas());
    }

    @Test
    @DisplayName("Reciclar mesa mueve cartas al mazo y deja la última")
    void testReciclarMesa() {
        Mazo mazoPeq = new Mazo();
        List<Carta> mesa = new ArrayList<>();
        mesa.add(new Carta(Carta.Palo.CORAZONES, Carta.Valor.DOS));
        mesa.add(new Carta(Carta.Palo.PICAS, Carta.Valor.TRES));
        mesa.add(new Carta(Carta.Palo.DIAMANTES, Carta.Valor.CUATRO));
        mesa.add(new Carta(Carta.Palo.TREBOLES, Carta.Valor.CINCO));

        List<Carta> nuevaMesa = mazoPeq.reciclarMesa(mesa);

        assertEquals(1, nuevaMesa.size());
        assertEquals(Carta.Valor.CINCO, nuevaMesa.get(0).getValor());
        assertEquals(3, mazoPeq.cantidadCartas());
    }

    @Test
    @DisplayName("Reciclar mesa con 1 carta no cambia nada")
    void testReciclarMesaUnaCarta() {
        Mazo mazoPeq = new Mazo();
        List<Carta> mesa = new ArrayList<>();
        mesa.add(new Carta(Carta.Palo.CORAZONES, Carta.Valor.AS));

        List<Carta> nuevaMesa = mazoPeq.reciclarMesa(mesa);

        assertEquals(1, nuevaMesa.size());
        assertTrue(mazoPeq.estaVacio());
    }
}
