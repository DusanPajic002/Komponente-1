import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RasporedImpl2 extends RasporedAC{

    private List<TerminImpl2> termini;

    public RasporedImpl2() {
        this.termini = new ArrayList<>();
    }

    @Override
    public <T> T inicijalizacija(File file, String nazivRasporeda, LocalDate trajeOd, LocalDate trajeDo) {
        return null;
    }

    @Override
    public <T> T inicijalizacija(List<String> kolone, String nazivRasporeda, LocalDate trajeOd, LocalDate trajeDo) {
        return null;
    }


    @Override
    public <T> T dodajProstoriju(Prostorija prostorija) {
        return null;
    }

    @Override
    public <T> T dodajNovTermin(Termin termin) {
        return null;
    }

    @Override
    public <T> T brisanjeTermina(Termin termin) {
        return null;
    }

    @Override
    public <T> T premestanjeTermina(Termin termin, Termin terminDrugi) {
        return null;
    }
}
