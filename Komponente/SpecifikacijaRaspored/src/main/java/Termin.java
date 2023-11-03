import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Termin {

    private LocalTime satPocetka;
    private LocalTime satKraja;
    private String dan;
    private String mesto;
    private List<Ostalo> ostalo;
    private LocalDate datumPocetak;
    private LocalDate datumKraj;

    public Termin(LocalTime satPocetka, LocalTime satKraja, String dan, String mesto, LocalDate datumPocetak, LocalDate datumKraj) {
        this.satPocetka = satPocetka;
        this.satKraja = satKraja;
        this.dan = dan;
        this.mesto = mesto;
        this.datumPocetak = datumPocetak;
        this.datumKraj = datumKraj;
        ostalo = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Termin:" + " " + satPocetka + " " + satKraja + " " + dan + " " + mesto + " " + ostalo + "\n";
    }
}
