

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

@Getter
@Setter
public class RasporedImpl1 extends RasporedAC{



    //Kolona sa prostorijom je poslednja kolona, vreme jedna pre nje, dan dve pre, datum tri pre nje
    @Override
    public <T> T CSVread(File file) {

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
                dodajProstoriju(csvRecord.get(brojKolona-1));
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

    //Kolona sa prostorijom je poslednja kolona, vreme jedna pre nje, dan dve pre, datum tri pre nje
    @Override
    public <T> T JSONread(File file) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, String>> data = objectMapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
            int prviprolazak = 0;
            for (Map<String, String> appointmentData : data){
                if(prviprolazak++ == 0)
                    getKolone().addAll(appointmentData.keySet());

                dodajNovTermin((List<String>) appointmentData.values(),true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }



    @Override
    public <T> T dodajNovTermin(List<String> termin, Boolean oznacenDatum) {

        int brKolona = termin.size();
            List<Ostalo> zaOstalo = new ArrayList<>();
            for(int i=0; i < brKolona - 4; i++)
                zaOstalo.add(new Ostalo(getKolone().get(i),termin.get(i)));

            // 11:00-13:00
            List<String> vreme = parsirajVreme(termin.get(brKolona-2));
            String dan = termin.get(brKolona-3);
            String dateString = termin.get(brKolona-4);
            String prostorija = termin.get(brKolona-1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);

            Termin termin1 = new Termin(LocalTime.parse(vreme.get(0)),LocalTime.parse(vreme.get(1)),dan,prostorija,date,null);
            termin1.setOstalo(zaOstalo);
            proveriTermin(termin1);
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
        if(getTermini().contains(termin))
            getTermini().remove(termin);
        return null;
    }

    @Override
    public <T> T premestanjeTermina(Termin termin, Termin terminDrugi) {
        brisanjeTermina(termin);
        proveriTermin(terminDrugi);
        return null;
    }

    @Override
    public boolean proveriTermin(Termin termin) {

        if(!getProstorije().contains(termin.getMesto()))
            return false;

        List<Termin> saIstomProstorijom = new ArrayList<>();

        for(Termin t : getTermini())
            if(termin.getMesto().equals(t.getMesto()) && termin.getDatumPocetak().equals(t.getDatumPocetak()))
                saIstomProstorijom.add(t);

        boolean zauzet = false;
        //start.isBefore(other.end) && end.isAfter(other.start)
        for(Termin t : saIstomProstorijom)
            if(t.getSatPocetka().isBefore(termin.getSatKraja()) && t.getSatKraja().isAfter(termin.getSatPocetka()))
                zauzet = true;

        if(zauzet == false)
            getTermini().add(termin);
        else
            System.out.println("zauzet");

        return zauzet;
    }
}
