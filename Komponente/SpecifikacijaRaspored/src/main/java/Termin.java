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
    private Prostorija mesto;
    private List<String> ostalo;

    public Termin(LocalTime satPocetka, LocalTime satKraja, Prostorija mesto) {
        this.satPocetka = satPocetka;
        this.satKraja = satKraja;
        this.mesto = mesto;
        this.ostalo = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Termin:" + " " + satPocetka + " " + satKraja + " " + dan + " " + mesto + " " + ostalo + "\n";
    }
}
