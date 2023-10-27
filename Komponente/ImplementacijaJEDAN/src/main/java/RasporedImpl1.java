import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RasporedImpl1 extends RasporedAC{

    private List<TerminImpl1> termini;

    public RasporedImpl1() {
        this.termini = new ArrayList<>();
    }

    @Override
    public <T> T inicijalizacija(File file, String nazivRasporeda, Date trajeOd, Date trajeDo) {
        return null;
    }

    @Override
    public <T> T inicijalizacija(List<String> kolone, String nazivRasporeda, Date trajeOd, Date trajeDo) {
        return null;
    }

    @Override
    public <T> T dodajNovTermin(List<String> termin, Boolean oznacenDatum) {
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
