import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public abstract class RasporedAC {

    private List<Termin> termini;
    private List<Prostorija> prostorije;

    public RasporedAC() {
        this.termini = new ArrayList<>();
        this.prostorije = new ArrayList<>();
    }

    public abstract <T> T inicijalizacija();

    public abstract <T> T dodajProstoriju(Prostorija prostorija);

    public abstract <T> T dodajNovTermin(Termin termin); //treba da ide uz proveru o zauzetosti termina

    public abstract <T> T brisanjeTermina(Termin termin);

    public abstract <T> T premestanjeTermina(Termin termin, Termin terminDrugi);

    //Treba da odradimo nekako za filtriranje rasporeda
    //Ucitavanje i snimanje u fajl
}

