package com.example.cincuentazo;

/**
 * Representa una carta de la baraja francesa con un palo y un valor.
 *
 * @author Juan Camilo Valverde López
 * @version 1.0
 */
public class Carta {
    /**
     * Representa los cuatro palos de la baraja con su respectivo símbolo.
     *
     * @author Juan Camilo Valverde López
     * @version 1.0
     */
    public enum Palo {
        CORAZONES("♥"), DIAMANTES("♦"), TREBOLES("♣"), PICAS("♠");

        /** Símbolo visual del palo. */
        private final String simbolo;

        /**
         * Constructor para asignar el símbolo al palo.
         *
         * @param simbolo Símbolo visual de la carta.
         */
        Palo(String simbolo) { this.simbolo = simbolo; }

        /**
         * Retorna el símbolo del palo.
         *
         * @return Símbolo en formato String.
         */
        public String getSimbolo() { return simbolo; }

        /**
         * Valida si el palo de la carta es de color rojo.
         *
         * @return true si es rojo, false si es negro.
         */
        public boolean esRojo() { return this == CORAZONES || this == DIAMANTES; }
    }

    /**
     * Define los valores posibles de cada carta y su valor en el juego.
     *
     * @author Juan Camilo Valverde López
     * @version 1.0
     */
    public enum Valor {
        AS("A", 1), DOS("2", 2), TRES("3", 3), CUATRO("4", 4), CINCO("5", 5),
        SEIS("6", 6), SIETE("7", 7), OCHO("8", 8), NUEVE("9", 0), DIEZ("10", 10),
        J("J", -10), Q("Q", -10), K("K", -10);

        /** Texto que representa el valor de la carta. */
        private final String texto;
        /** Valor numérico que aporta a la suma del juego. */
        private final int valorNumerico;

        /**
         * Constructor para inicializar el texto y el valor del juego.
         *
         * @param texto Representación textual de la carta.
         * @param valorNumerico Valor que suma o resta al juego.
         */
        Valor(String texto, int valorNumerico) {
            this.texto = texto;
            this.valorNumerico = valorNumerico;
        }

        /**
         * Retorna el texto del valor.
         *
         * @return Texto del valor de la carta.
         */
        public String getTexto() { return texto; }

        /**
         * Retorna el valor numérico para el juego.
         *
         * @return Valor numérico.
         */
        public int getValorNumerico() { return valorNumerico; }
    }

    /** Palo asignado a la carta. */
    private final Palo palo;
    /** Valor asignado a la carta. */
    private final Valor valor;

    /**
     * Crea una carta con un palo y un valor específico.
     *
     * @param palo Palo de la carta.
     * @param valor Valor de la carta.
     */
    public Carta(Palo palo, Valor valor) {
        this.palo = palo;
        this.valor = valor;
    }

    /**
     * Retorna el palo de la carta.
     *
     * @return El palo correspondiente.
     */
    public Palo getPalo() { return palo; }

    /**
     * Retorna el valor de la carta.
     *
     * @return El valor correspondiente.
     */
    public Valor getValor() { return valor; }

    /**
     * Obtiene el valor numérico para sumar o restar en el juego.
     *
     * @return Valor numérico de la carta.
     */
    public int obtenerValor() { return valor.getValorNumerico(); }

    /**
     * Verifica si la carta es un As.
     *
     * @return true si es As, false de lo contrario.
     */
    public boolean esAs() { return valor == Valor.AS; }

    /**
     * Retorna la carta representada como texto para la interfaz.
     *
     * @return Texto con el valor y símbolo de la carta.
     */
    @Override
    public String toString() { return valor.getTexto() + palo.getSimbolo(); }
}
