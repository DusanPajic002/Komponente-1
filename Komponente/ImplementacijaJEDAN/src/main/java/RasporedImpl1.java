

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RasporedImpl1 extends RasporedAC{

    private List<TerminImpl1> termini;

    public RasporedImpl1() {
        this.termini = new ArrayList<>();
    }


    //Kolona sa prostorijom je poslednja kolona, vreme jedna pre nje, dan dve pre, datum tri pre nje
    @Override
    public <T> T inicijalizacija(File file, String nazivRasporeda, LocalDate trajeOd, LocalDate trajeDo, List<Date> izuzetiDani) {



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
            System.out.println(this.getKolone());
            System.out.println(this.getProstorije());
            reader.close();
            reader = new BufferedReader(new FileReader(file));
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            prviProlazak = 0;
            for(CSVRecord csvRecord : csvParser){
                if(prviProlazak == 0){
                    prviProlazak++;
                    continue;
                }
                List<String> zaTermin = new ArrayList<>();
                for(int i=0; i<brojKolona; i++){
                    zaTermin.add(csvRecord.get(i));
                }
                dodajNovTermin(zaTermin, true);
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
    public <T> T inicijalizacija(List<String> kolone, String nazivRasporeda, LocalDate trajeOd, LocalDate trajeDo, List<Date> izuzetiDani) {
        this.setNazivRasporeda(nazivRasporeda);
        this.setTrajeOd(trajeOd);
        this.setTrajeDo(trajeDo);
        this.setIzuzetiDani(izuzetiDani);
        dodajNovTermin(kolone, true);
        return null;
    }


    @Override
    public <T> T dodajNovTermin(List<String> termin, Boolean oznacenDatum) {

        //ZAVRSI PROVERE!!!!!!!!!!!!
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

            // 11:00-13:00
            List<String> vreme = parsirajVreme(termin.get(brKolona-2));
            String dan = termin.get(brKolona-3);
            String dateString = termin.get(brKolona-4);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);

            Termin termin1 = new TerminImpl1(LocalTime.parse(vreme.get(0)),LocalTime.parse(vreme.get(1)),new Prostorija(null,termin.get(brKolona-1)));
            termin1.setOstalo(zaOstalo);
            termin1.setDan(dan);
            ((TerminImpl1)termin1).setDatum(date);
            this.termini.add((TerminImpl1) termin1);

            //jos datum
        } else {
            System.out.println("Ne postoji ovakva prostorija");
            return null; //moglo bi da se napravi neki exception nesto za ovo
        }
        return null;
    }

    public List<String> parsirajVreme(String vreme){
        List<String> parsirano = new ArrayList<>();
        String[] delovi = vreme.split("-");

        for(String deo : delovi){
            if(deo.contains(":")){
                if(deo.length() == 4)
                    parsirano.add("0" + deo);
                else
                    parsirano.add(deo);
            } else {
                if(deo.length() == 1)
                    parsirano.add("0" + deo + ":00");
                else
                    parsirano.add(deo + ":00");
            }
        }
        return  parsirano;
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
