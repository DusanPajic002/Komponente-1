

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

    public RasporedImpl1(File fileConfig) {
        config(fileConfig);
    }

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

                dodajNovTermin((List<String>) appointmentData.values());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }



    /*@Override
    public <T> T dodajNovTermin(List<String> termin) {

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
    }*/

    @Override
    public <T> T dodajNovTermin(List<String> linija) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalTime satPoc = null;
        LocalTime satKraj = null;
        LocalDate dK = null;
        LocalDate dP = null;
        String dan = null;
        String prostorija = null;
        List<Ostalo> ost = new ArrayList<>();

        for(int i=0; i<linija.size();i++) {
            String casee = "N";
            Config cfg = null;
            for(Config cus: this.getConfigs()){
                if(cus.vratiOrginalIndex(i)) {
                    casee = cus.getOriginal();
                    cfg = cus;
                    break;
                }
            }
            if(casee.equals("N")) {
                System.out.println("greska N");
                return null;
            }
            switch (casee) {
                case ("vreme"):{
                    List<String> vreme = parsirajVreme(linija.get(cfg.getIndex()));
                    satPoc = LocalTime.parse(vreme.get(0));
                    satKraj = LocalTime.parse(vreme.get(1));
                    break;
                }case ("dan"):{
                    dan = dan(linija.get(cfg.getIndex()));
                    break;
                }case ("prostorija"):{
                    prostorija = linija.get(cfg.getIndex());
                    break;
                }case ("start"):{
                    dP = LocalDate.parse(parsirajDatum(linija.get(cfg.getIndex())),formatter);
                    if(dan == null)
                        dan = String.valueOf(dP.getDayOfWeek());
                    break;
                }case ("end"):{
                    dK = LocalDate.parse(parsirajDatum(linija.get(cfg.getIndex())),formatter);
                    break;
                }
                default:{
                    ost.add(new Ostalo(cfg.getCustom(), linija.get(cfg.getIndex())));
                    break;
                }
            }
        }
        Termin t = new Termin(satPoc, satKraj, dan, prostorija, dP, dK);
        t.setOstalo(ost);
        proveriTermin(t);
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

    @Override
    public Termin premestanjeTermina(Termin termin, String kolona, String vrednost) {
        return null;
    }

    public List<String> filtrirajSlobodne(String prostorija, String datum){
        List<Termin> pom = new ArrayList<>();
        List<String> slobodni = new ArrayList<>();

        if(!getProstorije().contains(prostorija)){
            slobodni.add("Izabrali ste nepostojecu prostoriju");
            return slobodni;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dP = LocalDate.parse(parsirajDatum(datum),formatter);

        for (Termin t : getTermini())
            if(t.getMesto().equals(prostorija) && dP.isEqual(t.getDatumPocetak()))
                pom.add(t);

        if(pom.isEmpty()){
            slobodni.add(getPoctakRadnogVremena() + "-" + getKrajRadnogVremena());
            return slobodni;
        }
        List<LocalTime[]> zauzeti = new ArrayList<>();

        for(Termin t : pom)
            zauzeti.add(new LocalTime[]{t.getSatPocetka(),t.getSatKraja()});

        zauzeti.sort((z1,z2) -> z1[0].compareTo(z2[0]));

        List<LocalTime[]> slobodniTermini = new ArrayList<>();
        LocalTime tmp = getPoctakRadnogVremena();

        for(LocalTime[] zauzet : zauzeti){
            if(tmp.isBefore(zauzet[0]))
                slobodniTermini.add(new LocalTime[]{tmp, zauzet[0]});
            tmp = zauzet[1];
        }

        if(tmp.isBefore(getKrajRadnogVremena()))
            slobodniTermini.add(new LocalTime[]{tmp,getKrajRadnogVremena()});



        for(LocalTime[] lt : slobodniTermini)
            slobodni.add(lt[0] + "-" + lt[1]);

        return slobodni;
    }
}
