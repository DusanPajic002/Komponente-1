import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Termin {

    public static int i = 0;
    private int brojTermina;
    private LocalTime satPocetka;
    private LocalTime satKraja;
    private String dan;
    private String mesto;
    private List<Ostalo> ostalo;
    private LocalDate datumPocetak;
    private LocalDate datumKraj;

    public Termin(LocalTime satPocetka, LocalTime satKraja, String dan, String mesto, LocalDate datumPocetak, LocalDate datumKraj) {
        i++;
        brojTermina = i;
        this.satPocetka = satPocetka;
        this.satKraja = satKraja;
        this.dan = dan;
        this.mesto = mesto;
        this.datumPocetak = datumPocetak;
        this.datumKraj = datumKraj;
        ostalo = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Termin termin = (Termin) o;
        return Objects.equals(satPocetka, termin.satPocetka) && Objects.equals(satKraja, termin.satKraja) && Objects.equals(dan, termin.dan) && Objects.equals(mesto, termin.mesto) && Objects.equals(datumPocetak, termin.datumPocetak) && Objects.equals(datumKraj, termin.datumKraj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(satPocetka, satKraja, dan, mesto, ostalo, datumPocetak, datumKraj);
    }

    @Override
    public String toString() {
        return brojTermina + ". " + pom()  + datumPocetak + " - " + datumKraj + " | " + satPocetka + " - " + satKraja + " | " + dan + " | "  + mesto + "|\n";
    }

    private String pom (){
        StringBuilder sb = new StringBuilder();
        for (Ostalo o: ostalo)
            sb.append(o.getVrednost() + " | ");
        return sb.toString();
    }

}
