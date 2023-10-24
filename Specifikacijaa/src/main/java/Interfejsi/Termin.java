package Interfejsi;

import java.util.Date;

public class Termin {

    private int sat;
    private int minuti;
    private int sekunde;
    private int godina;
    private int mesec;
    private int dan;
    private Prostorija mesto;

    public Termin(int sat, int minuti, int sekunde, int godina, int mesec, int dan, Prostorija mesto) {
        this.sat = sat;
        this.minuti = minuti;
        this.sekunde = sekunde;
        this.godina = godina;
        this.mesec = mesec;
        this.dan = dan;
        this.mesto = mesto;
    }
}
