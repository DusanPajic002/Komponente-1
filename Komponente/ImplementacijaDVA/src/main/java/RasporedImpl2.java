import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RasporedImpl2 extends RasporedAC{


    @Override
    public <T> T CSVread(File file) {

        Reader reader = null;
        int jedanProlazk=0;

        try {
            reader = new FileReader(file);  CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            int kolone = 0;
            if (csvParser.iterator().hasNext()) {
                kolone = csvParser.iterator().next().size();
            }

            reader.close(); reader = new FileReader(file);   csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for(CSVRecord prostorije:csvParser) {
                if(jedanProlazk++ == 0)
                    continue;
                dodajProstoriju(prostorije.get(kolone - 1));
            }

            reader.close(); reader = new FileReader(file);   csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for(CSVRecord prostorije:csvParser)
              dodajProstoriju(prostorije.get(kolone-1));

            reader.close(); reader = new FileReader(file);   csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            jedanProlazk=0;
            for (CSVRecord csvRecord : csvParser) {
                if(jedanProlazk == 0){
                    for(int i=0; i<kolone; i++)
                        this.getKolone().add(csvRecord.get(i));
                    jedanProlazk++;
                    continue;
                }
                List<String> linija = new ArrayList<>();
                for(int i=0; i<kolone;i++) {
                    linija.add(csvRecord.get(i));
                }
                dodajNovTermin(linija,false);
            }
            System.out.println(this.getProstorije());
            System.out.println(this.getKolone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T JSONread(File file) {
        return null;
    }

    @Override
    public <T> T dodajNovTermin(List<String> linija, Boolean oznacenDatum) {
        //System.out.println(termini);
        int duzina = linija.size();
        TerminImpl2 termin = null;
        Prostorija prostorija = null;
        for(int i=0;i<getProstorije().size()-1; i++)
            if(linija.get(duzina-1).equals(getProstorije().get(i).getNazivProstorije()))
                prostorija = getProstorije().get(i);
        List<String> vreme = napraviVreme(linija.get(duzina-2));
        LocalTime satPoc = LocalTime.parse(vreme.get(0));
        LocalTime satKraj = LocalTime.parse(vreme.get(1));
        if(oznacenDatum) {
            termin = new TerminImpl2(satPoc,satKraj, prostorija ,this.getTrajeDo(),this.getTrajeDo());
            for (int i = 0; i < duzina - 5; i++)
                termin.getOstalo().add(linija.get(i));
            termin.setDan(linija.get(duzina - 5));
        }else {
            termin = new TerminImpl2(satPoc,satKraj, prostorija ,this.getTrajeDo(),this.getTrajeDo());
            for (int i = 0; i < duzina - 3; i++)
                termin.getOstalo().add(linija.get(i));
            String danSpace = linija.get(duzina - 3);
            StringBuilder dan = new StringBuilder();
            for(int i=0 ;i < danSpace.length();i++)
                if(danSpace.charAt(i) > 'A' && danSpace.charAt(i) < 'Z')
                    dan.append(danSpace.charAt(i));
            termin.setDan(dan.toString());
            dan.delete(0,dan.length());
        }
        this.termini.add(termin);
        System.out.println(this.termini);

        return null;
    }
    public List<String> napraviVreme(String vreme){
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
