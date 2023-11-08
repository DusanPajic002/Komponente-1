
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RasporedImpl2 extends RasporedAC{
    public RasporedImpl2(File fileConfig) {
        config(fileConfig);
        System.out.println(this.getConfigs());
    }

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
                dodajNovTermin(linija);
            }
            System.out.println(this.getProstorije());
            System.out.println(this.getKolone());
            System.out.println(getTermini());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T JSONread(File file) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, String>> data = objectMapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
            int prviprolazak = 0;


            for (Map<String,String> appointmentData : data){
                List<String> nov = new ArrayList<>();
                nov.addAll(appointmentData.values());
                dodajProstoriju(nov.get(nov.size()-1));
            }
            for (Map<String, String> appointmentData : data){
                if(prviprolazak++ == 0)
                    getKolone().addAll(appointmentData.keySet());

                List<String> nov = new ArrayList<>();
                nov.addAll(appointmentData.values());

                dodajNovTermin(nov);
            }

            System.out.println(this.getProstorije());
            System.out.println(this.getKolone());
            System.out.println(getTermini());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public <T> T dodajNovTermin(List<String> linija) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalTime satPoc = null;
        LocalTime satKraj = null;
        LocalDate dK = this.getTrajeOd();
        LocalDate dP = this.getTrajeDo();
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
                    List<String> vreme = napraviVreme(linija.get(cfg.getIndex()));
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
                    dP = LocalDate.parse(napraviDatum(linija.get(cfg.getIndex())),formatter);
                    break;
                }case ("end"):{
                    dK = LocalDate.parse(napraviDatum(linija.get(cfg.getIndex())),formatter);
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
        if(!this.getProstorije().contains(termin.getMesto()))
            return false;
        for (Termin ter: this.getTermini())
            if (ter.getDan().equals(termin.getDan()) && ter.getMesto().equals(termin.getMesto())) {
                if (!ter.getDatumKraj().isBefore(termin.getDatumPocetak()) &&
                        !ter.getDatumPocetak().isAfter(termin.getDatumKraj())) {
                    if (!ter.getSatKraja().isBefore(termin.getSatPocetka()) &&
                            !ter.getSatPocetka().isAfter(termin.getSatKraja())) {
                        return false;
                    }
                }
            }
        this.getTermini().add(termin);
        return true;
    }

    private String dan(String danSpace){
        StringBuilder dan = new StringBuilder();
        for(int i=0 ;i < danSpace.length();i++)
            if(danSpace.charAt(i) > 'A' && danSpace.charAt(i) < 'Z')
                dan.append(danSpace.charAt(i));
        return dan.toString();
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

    public String napraviDatum(String datum){
        String[] delovi = datum.split("-");
        String dan =null;
        String mesec =null;
        if(delovi[0].length() == 1){
            dan= "0" + delovi[0];
        }else
            dan = delovi[0];

        if(delovi[1].length() == 1){
            mesec = "0" + delovi[1];
        }else
            mesec =  delovi[1];
        return dan + "-" + mesec + "-" + delovi[2];
    }

}