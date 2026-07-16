package com.example.cincuentazo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Mazo {
    private final Stack<Carta> cartas = new Stack<>();

    public Mazo() {}

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

    public void barajar() { Collections.shuffle(cartas); }

    public Carta tomarCarta() {
        if (cartas.isEmpty()) { // no hay cartas para repartir
            throw new IllegalStateException("El mazo está vacío.");
        }
        return cartas.pop(); // saca la carta superior
    }

    public boolean estaVacio() { return cartas.isEmpty(); }
    public int cantidadCartas() { return cartas.size(); }

    public void agregarCartasAlFinal(List<Carta> nuevasCartas) {
        cartas.addAll(0, nuevasCartas); // las inserta al inicio (tope)
    }

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
