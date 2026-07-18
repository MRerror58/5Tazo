package com.example.cincuentazo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Representa el mazo de cartas utilizado en la partida.
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class Mazo {
    /** Pila que almacena las cartas que componen el mazo. */
    private final Stack<Carta> cartas = new Stack<>();

    /**
     * Constructor por defecto del mazo.
     */
    public Mazo() {}

    /**
     * Crea un mazo de 52 cartas barajadas y listo para jugar.
     *
     * @return El mazo completo barajado.
     */
    public static Mazo crearMazoCompleto() {
        Mazo mazo = new Mazo();
        for (Carta.Palo palo : Carta.Palo.values()) { // recorre los 4 palos
            for (Carta.Valor valor : Carta.Valor.values()) { // recorre los 13 valores
                mazo.cartas.push(new Carta(palo, valor));
            }
        }
        mazo.barajar();
        return mazo;
    }

    /**
     * Mezcla las cartas dentro del mazo de forma aleatoria.
     */
    public void barajar() { Collections.shuffle(cartas); }

    /**
     * Toma y remueve la carta que está en la parte superior del mazo.
     *
     * @return Carta superior extraída.
     */
    public Carta tomarCarta() {
        if (cartas.isEmpty()) { // no hay cartas para repartir
            throw new IllegalStateException("El mazo está vacío.");
        }
        return cartas.pop(); // saca la carta superior
    }

    /**
     * Verifica si el mazo se ha quedado sin cartas.
     *
     * @return true si está vacío, false de lo contrario.
     */
    public boolean estaVacio() { return cartas.isEmpty(); }

    /**
     * Retorna la cantidad de cartas que quedan en el mazo.
     *
     * @return Número de cartas restantes.
     */
    public int cantidadCartas() { return cartas.size(); }

    /**
     * Inserta cartas al final del mazo (en el tope/fondo según la estructura de datos).
     *
     * @param nuevasCartas Lista de cartas a añadir al mazo.
     */
    public void agregarCartasAlFinal(List<Carta> nuevasCartas) {
        cartas.addAll(0, nuevasCartas); // las inserta al inicio (tope)
    }

    /**
     * Recicla las cartas jugadas en la mesa para volver a llenar el mazo si se vacía.
     *
     * @param cartasMesa Lista de cartas actualmente jugadas en la mesa.
     * @return Lista con la última carta que queda visible en la mesa para continuar el juego.
     */
    public List<Carta> reciclarMesa(List<Carta> cartasMesa) {
        if (cartasMesa.size() <= 1) return cartasMesa; // nada que reciclar
        Carta ultimaCarta = cartasMesa.get(cartasMesa.size() - 1); // conserva la última
        List<Carta> cartasParaReciclar = new ArrayList<>(cartasMesa.subList(0, cartasMesa.size() - 1));
        Collections.shuffle(cartasParaReciclar); // mezcla antes de devolverlas
        cartas.addAll(cartasParaReciclar);
        List<Carta> nuevaMesa = new ArrayList<>();
        nuevaMesa.add(ultimaCarta);
        return nuevaMesa;
    }
}
