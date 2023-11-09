import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
public abstract class RasporedAC {

    private String nazivRasporeda;
    private LocalDate trajeOd;
    private LocalDate trajeDo;
    private List<String> prostorije;
    private List<String> kolone;
    private List<LocalDate> izuzetiDani;
    private List<Termin> termini;
    private List<Config> configs;

    public RasporedAC() {
        this.prostorije = new ArrayList<>();
        this.kolone = new ArrayList<>();
        this.izuzetiDani = new ArrayList<>();
        this.termini = new ArrayList<>();
        this.configs = new ArrayList<>();
    }
    public abstract <T> T CSVread(File file);
    public abstract <T> T JSONread(File file);

    public <T> T inicijalizacija(String nazivRasporeda, LocalDate trajeOd, LocalDate trajeDo, List<LocalDate> izuzetiDani){
        this.setNazivRasporeda(nazivRasporeda);
        this.setTrajeOd(trajeOd);
        this.setTrajeDo(trajeDo);
        this.setIzuzetiDani(izuzetiDani);
        return null;
    }

    public <T> T dodajProstoriju(String prostorija){
        if(!this.getProstorije().contains(prostorija)){
            this.getProstorije().add(prostorija);
            return null;
        }
        return null;
    }

    public abstract <T> T dodajNovTermin(List<String> termin);

    public <T> T brisanjeTermina(Termin termin) {
        if(getTermini().contains(termin))
            getTermini().remove(termin);
        return null;
    }

    public void config(File file){
        try {
            Scanner scanner = null;
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(" ", 3);
                this.configs.add(new Config(Integer.valueOf(splitLine[0]), splitLine[1], splitLine[2]));
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public abstract boolean proveriTermin(Termin termin);

    public abstract  <T> T premestanjeTermina(Termin termin, String kolona, String vrednost);

    public List<Termin> filtriraj(String kolona, String vrednost){
        List<Termin> filtrirani = new ArrayList<>();

        String casee = "N";
        Config cfg = null;
        for(Config cus: this.getConfigs()){
            if(cus.vratiOrginalCustom(kolona)) {
                casee = cus.getOriginal();
                cfg = cus;
                break;
            }
        }
        if(casee.equals("N"))
            return null;
        for (Termin t : termini){
            switch (casee) {
                case ("vreme"):{

                    List<String> vreme = parsirajVreme(vrednost);
                    LocalTime satPoc = LocalTime.parse(vreme.get(0));
                    LocalTime satKraj = LocalTime.parse(vreme.get(1));

                    if(satPoc.isBefore(t.getSatPocetka()) && satKraj.isAfter(t.getSatKraja()))
                        filtrirani.add(t);
                    break;
                }case ("dan"):{
                    if(t.getDan().equals(vrednost))
                        filtrirani.add(t);
                    break;
                }case ("prostorija"):{
                    if(t.getMesto().equals(vrednost))
                        filtrirani.add(t);
                    break;
                }case ("start"):{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate dP = LocalDate.parse(parsirajDatum(vrednost),formatter);

                    if(dP.isEqual(t.getDatumPocetak()))
                        filtrirani.add(t);
                    break;
                }case ("end"):{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate dK = LocalDate.parse(parsirajDatum(vrednost),formatter);

                    if(dK.isEqual(t.getDatumPocetak()))
                        filtrirani.add(t);
                    break;
                }
                default:{
                    for(Ostalo ost : t.getOstalo()){
                        if(ost.getVrednost().equals(vrednost))
                            filtrirani.add(t);
                    }
                    break;
                }
            }
        }

        System.out.println("-----------------------------------------");
        System.out.println(filtrirani);

        return filtrirani;
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

    public String parsirajDatum(String datum){
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

    public String dan(String danSpace){
        StringBuilder dan = new StringBuilder();
        for(int i=0 ;i < danSpace.length();i++)
            if(danSpace.charAt(i) > 'A' && danSpace.charAt(i) < 'Z')
                dan.append(danSpace.charAt(i));
        return dan.toString();
    }


    //Treba da odradimo nekako za filtriranje rasporeda
    //Ucitavanje i snimanje u fajl
}

