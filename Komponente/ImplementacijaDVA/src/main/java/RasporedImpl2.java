import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RasporedImpl2 extends RasporedAC{

    private List<TerminImpl2> termini;

    public RasporedImpl2() {
        this.termini = new ArrayList<>();
    }
    @Override
    public <T> T inicijalizacija(File file, String nazivRasporeda, Date trajeOd, Date trajeDo) {

        Reader reader = null;
        int jedanProlazk=0;
        this.setTrajeOd(trajeOd);
        this.setTrajeDo(trajeDo);

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
                dodajProstoriju(new Prostorija(null, prostorije.get(kolone - 1)));
            }

            reader.close(); reader = new FileReader(file);   csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for(CSVRecord prostorije:csvParser)
              dodajProstoriju(new Prostorija(null, prostorije.get(kolone-1)));

            reader.close(); reader = new FileReader(file);   csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            jedanProlazk=0;
            for (CSVRecord csvRecord : csvParser) {
                if(jedanProlazk++ == 0){
                    for(int i=0; i<kolone; i++)
                        this.getKolone().add(csvRecord.get(i));
                    continue;
                }
                List<String> termin = new ArrayList<>();
                for(int i=0; i<kolone;i++) {
                    termin.add(csvRecord.get(i));
                }
                dodajNovTermin(termin,false);
            }
            System.out.println(this.getProstorije());
            System.out.println(this.getKolone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public <T> T inicijalizacija(List<String> kolone, String nazivRasporeda, Date trajeOd, Date trajeDo) {
        return null;
    }
    @Override
    public <T> T dodajNovTermin(List<String> termini, Boolean oznacenDatum) {
        int duzina = termini.size();
        Prostorija prostorija = null;
        for(int i=0;i<getProstorije().size()-1; i++)
            if(termini.get(duzina-1).equals(getProstorije().get(i).getNazivProstorije()))
                prostorija = getProstorije().get(i);
        Termin termin = null;
        String vreme = termini.get(duzina-2);

        if(oznacenDatum) {
            termin = new Termin(null,null,prostorija);
            for (int i = 0; i < duzina - 4; i++)
                termin.getOstalo().add(termini.get(i));
        }else {
            termin = new Termin(getTrajeOd(),getTrajeDo(),prostorija);
            for (int i = 0; i < duzina - 6; i++)
                termin.getOstalo().add(termini.get(i));
        }
        System.out.println(termini);
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
