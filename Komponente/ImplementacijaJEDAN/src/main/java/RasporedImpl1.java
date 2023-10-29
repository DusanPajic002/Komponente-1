

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RasporedImpl1 extends RasporedAC{

    private List<TerminImpl1> termini;

    public RasporedImpl1() {
        this.termini = new ArrayList<>();
    }


    //Kolona sa prostorijom je poslednja kolona, vreme jedna pre nje, dan dve pre, datum tri pre nje
    @Override
    public <T> T inicijalizacija(File file, String nazivRasporeda, LocalDate trajeOd, LocalDate trajeDo) {

        BufferedReader reader = null;
        int brojKolona = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            if (csvParser.iterator().hasNext()) {
                brojKolona = csvParser.iterator().next().size();
            }
            reader.close();
            reader = new BufferedReader(new FileReader(file));
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            int prviProlazak = 0;
            for (CSVRecord csvRecord : csvParser) {
                if(prviProlazak == 0){
                    for(int i=0; i<brojKolona; i++)
                        this.getKolone().add(csvRecord.get(i));
                    prviProlazak++;
                    continue;
                }
                dodajProstoriju(new Prostorija(null,csvRecord.get(brojKolona-1)));
            }
            reader.close();
            reader = new BufferedReader(new FileReader(file));
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            prviProlazak = 0;
            for(CSVRecord csvRecord : csvParser){
                if(prviProlazak == 0)
                    continue;
                List<String> zaTermin = new ArrayList<>();
                for(int i=0; i<brojKolona; i++){
                    zaTermin.add(csvRecord.get(i));
                }
                dodajNovTermin(zaTermin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public <T> T inicijalizacija(List<String> kolone, String nazivRasporeda, LocalDate trajeOd, LocalDate trajeDo) {
        return null;
    }


    @Override
    public <T> T dodajNovTermin(List<String> termin) {
        int brKolona = termin.size();
        boolean ima = false;
        for(Prostorija p : this.getProstorije()){
            if(p.getNazivProstorije().equals(termin.get(brKolona-1)))
                ima = true;
        }

        if(ima){
            List<String> zaOstalo = new ArrayList<>();
            for(int i=0; i < brKolona - 4; i++){
                zaOstalo.add(termin.get(i));
            }
            //brKolona -= 4;

            //Treba da zavrsim za vreme,dan,datum...


            //Termin novTermi = new TerminImpl1();
        } else {
            System.out.println("Ne postoji ovakva prostorija");
            return null; //moglo bi da se napravi neki exception nesto za ovo
        }
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
