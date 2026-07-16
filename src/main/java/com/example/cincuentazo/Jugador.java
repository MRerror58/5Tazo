package com.example.cincuentazo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private final String nombre;
    private final List<Carta> mano = new ArrayList<>();
    private final boolean esMaquina;
    private boolean eliminado = false;

    public Jugador(String nombre, boolean esMaquina) {
        this.nombre = nombre;
        this.esMaquina = esMaquina;
    }

    public String getNombre() { return nombre; }
    public List<Carta> getMano() { return mano; }
    public boolean esMaquina() { return esMaquina; }
    public boolean estaEliminado() { return eliminado; }
    public void eliminar() { this.eliminado = true; }
    public void agregarCarta(Carta carta) { mano.add(carta); }
    public void removerCarta(Carta carta) { mano.remove(carta); }

    public boolean puedeJugar(int sumaActual) {
        return !obtenerCartasJugables(sumaActual).isEmpty();
    }

    public List<Carta> obtenerCartasJugables(int sumaActual) {
        List<Carta> jugables = new ArrayList<>();
        for (Carta carta : mano) { // evalúa cada carta de la mano
            if (carta.esAs()) { // AS: válido si entra como 1 o como 10
                if (sumaActual + 1 <= 50 || sumaActual + 10 <= 50) {
                    jugables.add(carta);
                }
            } else { // resto: válido si no se pasa de 50
                if (sumaActual + carta.obtenerValor() <= 50) {
                    jugables.add(carta);
                }
            }
        }
        return jugables;
    }

    @Override
    public String toString() { return nombre; }
}
