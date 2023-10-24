package Interfejsi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RasporedImpl implements RasporedInterface{


    @Override
    public <T> T inicijalizacija() {

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\csv.csv"));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : csvParser) {
                System.out.println(csvRecord.get(0));
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
    public <T> T premestanjeTermina(Termin termin) {
        return null;
    }
}
