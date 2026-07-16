package com.example.cincuentazo;

public class Carta {
    public enum Palo {
        CORAZONES("♥"), DIAMANTES("♦"), TREBOLES("♣"), PICAS("♠");

        private final String simbolo;
        Palo(String simbolo) { this.simbolo = simbolo; }
        public String getSimbolo() { return simbolo; }
        public boolean esRojo() { return this == CORAZONES || this == DIAMANTES; }
    }

    public enum Valor {
        AS("A", 1), DOS("2", 2), TRES("3", 3), CUATRO("4", 4), CINCO("5", 5),
        SEIS("6", 6), SIETE("7", 7), OCHO("8", 8), NUEVE("9", 0), DIEZ("10", 10),
        J("J", -10), Q("Q", -10), K("K", -10);

        private final String texto;
        private final int valorNumerico;

        Valor(String texto, int valorNumerico) {
            this.texto = texto;
            this.valorNumerico = valorNumerico;
        }
        public String getTexto() { return texto; }
        public int getValorNumerico() { return valorNumerico; }
    }

    private final Palo palo;
    private final Valor valor;

    public Carta(Palo palo, Valor valor) {
        this.palo = palo;
        this.valor = valor;
    }

    public Palo getPalo() { return palo; }
    public Valor getValor() { return valor; }
    public int obtenerValor() { return valor.getValorNumerico(); }
    public boolean esAs() { return valor == Valor.AS; }

    @Override
    public String toString() { return valor.getTexto() + palo.getSimbolo(); }
}
