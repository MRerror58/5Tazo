package com.example.cincuentazo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un participante de la partida (humano o máquina).
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class Jugador {
    /** Nombre representativo del jugador. */
    private final String nombre;
    
    /** Cartas actuales en la mano del jugador. */
    private final List<Carta> mano = new ArrayList<>();
    
    /** Bandera para diferenciar si es un oponente automático. */
    private final boolean esMaquina;
    
    /** Bandera para saber si el jugador ha quedado fuera de juego. */
    private boolean eliminado = false;

    /**
     * Construye un jugador asignándole su nombre y tipo.
     *
     * @param nombre Nombre del jugador.
     * @param esMaquina true si es un oponente máquina, false si es el jugador humano.
     */
    public Jugador(String nombre, boolean esMaquina) {
        this.nombre = nombre;
        this.esMaquina = esMaquina;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del participante.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene la mano de cartas del jugador.
     *
     * @return Lista de cartas en mano.
     */
    public List<Carta> getMano() { return mano; }

    /**
     * Indica si el jugador es controlado por la computadora.
     *
     * @return true si es máquina, false de lo contrario.
     */
    public boolean esMaquina() { return esMaquina; }

    /**
     * Indica si el jugador ha sido eliminado de la partida.
     *
     * @return true si fue eliminado, false de lo contrario.
     */
    public boolean estaEliminado() { return eliminado; }

    /**
     * Marca al jugador como eliminado del juego.
     */
    public void eliminar() { this.eliminado = true; }

    /**
     * Agrega una carta a la mano del jugador.
     *
     * @param carta Carta robada del mazo.
     */
    public void agregarCarta(Carta carta) { mano.add(carta); }

    /**
     * Remueve una carta específica de la mano del jugador al jugarla.
     *
     * @param carta Carta a remover.
     */
    public void removerCarta(Carta carta) { mano.remove(carta); }

    /**
     * Verifica si el jugador tiene alguna carta jugable que no sobrepase 50.
     *
     * @param sumaActual Valor acumulado actual de la mesa.
     * @return true si tiene al menos una carta jugable, false de lo contrario.
     */
    public boolean puedeJugar(int sumaActual) {
        return !obtenerCartasJugables(sumaActual).isEmpty();
    }

    /**
     * Retorna una lista con las cartas de la mano que pueden ser jugadas legalmente.
     *
     * @param sumaActual Valor acumulado actual de la mesa.
     * @return Lista de cartas jugables.
     */
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

    /**
     * Retorna la representación del jugador en formato texto.
     *
     * @return El nombre del jugador.
     */
    @Override
    public String toString() { return nombre; }
}
