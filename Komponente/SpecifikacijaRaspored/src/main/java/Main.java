// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
}
/*

package Interfejsi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RasporedImpl implements RasporedInterface{

    private List<Termin> termini;
    private List<Prostorija> prostorije;
    public RasporedImpl() {
        this.termini = new ArrayList<>();
        this.prostorije = new ArrayList<>();
    }

    @Override // Pravi raspored
    public <T> T inicijalizacija() {

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get("C:\\Users\\Korisnik\\Desktop\\Komponente\\csv.csv"));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : csvParser) {
                System.out.println(csvRecord.get(6));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        brisanjeTermina(termin);
        dodajNovTermin(terminDrugi);
        return null;
    }
}

 */