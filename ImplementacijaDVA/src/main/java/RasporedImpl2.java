
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            System.out.println(getTermini());
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

        int duzina = linija.size();
        Termin termin = null;

        List<String> vreme = napraviVreme(linija.get(duzina-2));
        LocalTime satPoc = LocalTime.parse(vreme.get(0));
        LocalTime satKraj = LocalTime.parse(vreme.get(1));

        if(oznacenDatum) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dP = LocalDate.parse(napraviDatum(linija.get(duzina-5)),formatter);
            LocalDate dK = LocalDate.parse(napraviDatum(linija.get(duzina-4)),formatter);
            termin = new Termin(satPoc, satKraj, pom(linija.get(duzina - 3)), linija.get(duzina-1), dP, dK);
            for (int i = 0; i < duzina - 5; i++)
                termin.getOstalo().add(new Ostalo(getKolone().get(i),linija.get(i)));
        }else {
            termin = new Termin(satPoc, satKraj, pom(linija.get(duzina - 3)), linija.get(duzina-1) ,this.getTrajeOd(),this.getTrajeDo());
            for (int i = 0; i < duzina - 3; i++)
                termin.getOstalo().add(new Ostalo(getKolone().get(i),linija.get(i)));
        }
        proveriTermin(termin);
        return null;
    }
    private String pom(String danSpace){
        System.out.println(danSpace);
        StringBuilder dan = new StringBuilder();
        for(int i=0 ;i < danSpace.length();i++)
            if(danSpace.charAt(i) > 'A' && danSpace.charAt(i) < 'Z')
                dan.append(danSpace.charAt(i));
        return dan.toString();
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
                        System.out.println("zauzeto");
                        return false;
                    }
                }
            }
        System.out.println("nije zauzeto");
        this.getTermini().add(termin);
        return true;
    }
    @Override
    public <T> T premestanjeTermina(Termin termin, Termin terminDrugi) {
        brisanjeTermina(termin);
        proveriTermin(terminDrugi);
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
