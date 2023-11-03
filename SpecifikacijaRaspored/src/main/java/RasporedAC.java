import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public abstract class RasporedAC {

    private String nazivRasporeda;
    private LocalDate trajeOd;
    private LocalDate trajeDo;
    private List<String> prostorije;
    private List<String> kolone;
    private List<LocalDate> izuzetiDani;
    private List<Termin> termini;

    public RasporedAC() {
        this.prostorije = new ArrayList<>();
        this.kolone = new ArrayList<>();
        this.izuzetiDani = new ArrayList<>();
        this.termini = new ArrayList<>();
    }
    public abstract <T> T CSVread(File file);
    public abstract <T> T JSONread(File file);

    public <T> T inicijalizacija(String nazivRasporeda, LocalDate trajeOd, LocalDate trajeDo, List<LocalDate> izuzetiDani){
        this.setNazivRasporeda(nazivRasporeda);
        this.setTrajeOd(trajeOd);
        this.setTrajeDo(trajeDo);
        this.setIzuzetiDani(izuzetiDani);
        return null;
    }

    public <T> T dodajProstoriju(String prostorija){
        if(!this.getProstorije().contains(prostorija)){
            this.getProstorije().add(prostorija);
            return null;
        }
        return null;
    }

    public abstract <T> T dodajNovTermin(List<String> termin, Boolean oznacenDatum); //treba da ide uz proveru o zauzetosti termina

    public <T> T brisanjeTermina(Termin termin) {
        if(getTermini().contains(termin))
            getTermini().remove(termin);
        return null;
    }
    public abstract boolean proveriTermin(Termin termin);

    public abstract <T> T premestanjeTermina(Termin termin, Termin terminDrugi);

    //Treba da odradimo nekako za filtriranje rasporeda
    //Ucitavanje i snimanje u fajl
}

