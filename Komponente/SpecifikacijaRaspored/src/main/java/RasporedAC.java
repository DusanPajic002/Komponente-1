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
    private List<Prostorija> prostorije;
    private List<String> kolone;
    private Date trajeOd;
    private Date trajeDo;

    public RasporedAC() {
        this.prostorije = new ArrayList<>();
        this.kolone = new ArrayList<>();
    }

    public abstract <T> T inicijalizacija(File file,String nazivRasporeda, Date trajeOd,Date trajeDo);

    public abstract <T> T inicijalizacija(List<String> kolone, String nazivRasporeda, Date trajeOd,Date trajeDo);

    public <T> T dodajProstoriju(Prostorija prostorija){
        if(!this.getProstorije().contains(prostorija)){
            this.getProstorije().add(prostorija);
            return null;
        }
        return null;
    }

    public abstract <T> T dodajNovTermin(List<String> termin, Boolean oznacenDatum); //treba da ide uz proveru o zauzetosti termina

    public abstract <T> T brisanjeTermina(Termin termin);//mozda moze ovde

    public abstract <T> T premestanjeTermina(Termin termin, Termin terminDrugi);

    //Treba da odradimo nekako za filtriranje rasporeda
    //Ucitavanje i snimanje u fajl
}

