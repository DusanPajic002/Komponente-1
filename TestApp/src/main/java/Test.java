import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private RasporedAC rasporedAC = null;
    Scanner scanner = new Scanner(System.in);
    public Test() {
        test();
    }

    private void test() {

        //File cfg = new File("C:\\Users\\Korisnik\\Desktop\\Komponente\\ImplementacijaDVA\\config");
        File cfg = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\ImplementacijaJEDAN\\config");
        rasporedAC = new RasporedImpl1(cfg);

        System.out.println("Unesite naziv rasporeda:");
        String line = scanner.nextLine();
        String nazivRasporeda = line;

        System.out.println("Unesite pocetak rasporeda (Format | dd-mm-yyyy):");
        line = scanner.nextLine();
        line = rasporedAC.parsirajDatum(line);
        LocalDate dP = LocalDate.parse(line,formatter);

        System.out.println("Unesite kraj rasporeda (Format | dd-mm-yyyy):");
        line = scanner.nextLine();
        line = rasporedAC.parsirajDatum(line);
        LocalDate dK = LocalDate.parse(line,formatter);

        System.out.println("Unesite izuzete dane (Format | dd-mm-yyyy, dd-mm-yyyy, dd-mm-yyyy...):");
        line = scanner.nextLine();
        String[] izuzeti = line.split(",");
        List<LocalDate> izuzetiDani = new ArrayList<>();
        for(int i=0; i<izuzeti.length; i++) {
            LocalDate izuzetdan = LocalDate.parse(rasporedAC.parsirajDatum(izuzeti[i]),formatter);
            izuzetiDani.add(izuzetdan);
        }

        System.out.println("Unesite pocetak radnog vremena (Format | 01:01):");
        line = scanner.nextLine();
        LocalTime satPoc = LocalTime.parse(line);

        System.out.println("Unesite kraj radnog vremena (Format | 01:01):");
        line = scanner.nextLine();
        LocalTime satKraj = LocalTime.parse(line);

        rasporedAC.inicijalizacija(nazivRasporeda,dP,dK,izuzetiDani,satPoc,satKraj);
        System.out.println("Uredu, sada unesite putanju do fajla:");
        line = scanner.nextLine();
        File file = new File(line);
        if(line.contains(".json"))
            rasporedAC.JSONread(file);
        else if(line.contains(".csv"))
            rasporedAC.CSVread(file);
        else
            System.out.println("Nije dobro uneta putanja");
        while (true) {
            System.out.println("\n-------------------------------------------------\n");
            System.out.println("Unesite redni broj datih opcija");
            System.out.println("\n-------------------------------------------------\n");
            System.out.println("1. Dodaj termin");
            System.out.println("2. Brisanje termina");
            System.out.println("3. Filtriraj po tacnoj vrednosti");
            System.out.println("4. Filtriraj po datumu u odredjenom periodu");
            System.out.println("5. Filtriraj slobodne termine za prostoriju");
            System.out.println("6. Premesti termin");
            System.out.println("7. Stampaj raspored");
            System.out.println("8. Export fajla");
            System.out.println("0. Izadji");
            String opcija = scanner.nextLine();
            switch (opcija){
                case ("1"):{
                    jedan();
                    break;
                }
                case ("2"):{
                    dva();
                    break;
                }
                case ("3"):{
                    tri();
                    break;
                }
                case ("4"):{
                    cetri();
                    break;
                }
                case ("5"):{
                    pet();
                    break;
                }
                case ("6"):{
                    sest();
                    break;
                }
                case ("7"):{
                    for(Termin t: rasporedAC.getTermini())
                        System.out.println(t);
                    break;
                }
                case ("8"):{
                    osam();
                    break;
                }
                case ("0"):{
                    scanner.close();
                    return;
                } default:{
                    System.out.println("Nije dobro uneto, unesite samo redni broj");
                    break;
                }
            }
        }
    }

    private void jedan(){
        List<String> termin = new ArrayList<>();
        String line;
        int k = 0;
        boolean dan = false;
        for (Config cfg: rasporedAC.getConfigs())
            if(cfg.getOriginal().equals("ostalo"))
                k++;
            else if(cfg.getOriginal().equals("dan"))
                dan = true;
        for(int i=0; i<k; i++){
            System.out.println("Unesite rednom osobine");
            line = scanner.nextLine();
            termin.add(line);
        }

        System.out.println("Unesite datum za termin (Format | dd-mm-yyyy)");
        line = scanner.nextLine();
        termin.add(line);

        if(dan){
            System.out.println("Unesite dan");
            line = scanner.nextLine();
            termin.add(line);
        }

        System.out.println("Unesite vreme (Format | 00:00-00:00)");
        line = scanner.nextLine();
        termin.add(line);

        System.out.println("Unesite mesto");
        line = scanner.nextLine();
        termin.add(line);

        rasporedAC.dodajNovTermin(termin);
    }

    private void dva(){
        System.out.println("Unesite redni broj termina kog zelite da obrisete");
        String line = scanner.nextLine();
        int brojKaoInt = 0;
        try {
            brojKaoInt = Integer.parseInt(line);
            if(brojKaoInt > rasporedAC.getTermini().size() || brojKaoInt < 1){
                System.out.println("Broj je prevelik");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("String nije broj");
        }
        rasporedAC.brisanjeTermina(rasporedAC.getTermini().get(brojKaoInt - 1));
    }

    private void tri(){
        List<String> pretraga = new ArrayList<>();

        System.out.println("Unesite naziv kolone");
        String line = scanner.nextLine();
        pretraga.add(line);

        System.out.println("Unesite vrednost po kojoj pretrazujemo");
        line = scanner.nextLine();
        pretraga.add(line);

        List<Termin> termini =  rasporedAC.filtriraj(pretraga.get(0), pretraga.get(1));
        for(Termin t: termini)
            System.out.println(t);
    }

    private void cetri(){
        List<String> pretraga = new ArrayList<>();

        System.out.println("Unesite datum od kada filtrirate (Format | dd-mm-yyyy)");
        String line = scanner.nextLine();
        pretraga.add(line);

        System.out.println("Sada datum do kada filtrirate (Format | dd-mm-yyyy)");
        line = scanner.nextLine();
        pretraga.add(line);

        List<Termin> termini =  rasporedAC.filtriranoPoDatumu(pretraga.get(0), pretraga.get(1));
        for(Termin t: termini)
            System.out.println(t);
    }

    private void pet(){
        List<String> pretraga = new ArrayList<>();

        System.out.println("Unesite prostoriju za koju vas zanimaju slobodni termini");
        String line = scanner.nextLine();
        pretraga.add(line);

        System.out.println("Unesite datum za koji vas zanimaju slobodni termini (Format | dd-mm-yyyy)");
        line = scanner.nextLine();
        pretraga.add(line);

        System.out.println(rasporedAC.filtrirajSlobodne(pretraga.get(0), pretraga.get(1)));

    }

    private void sest(){
        System.out.println("Izaberite redni broj termina koji zelite da premestite");
        for(Termin t: rasporedAC.getTermini())
            System.out.println(t);

        String line = scanner.nextLine();
        int brojKaoInt = 0;
        try {
            brojKaoInt = Integer.parseInt(line);
            if(brojKaoInt > rasporedAC.getTermini().size() || brojKaoInt < 1){
                System.out.println("Broj je prevelik");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("String nije broj");
        }

        System.out.println("Unesite nove vrednosti za termin");

        List<String> termin = new ArrayList<>();
        String line1;
        int k = 0;
        boolean dan = false;
        for (Config cfg: rasporedAC.getConfigs())
            if(cfg.getOriginal().equals("ostalo"))
                k++;
            else if(cfg.getOriginal().equals("dan"))
                dan = true;
        for(int i=0; i<k; i++){
            System.out.println("Unesite rednom osobine");
            line1 = scanner.nextLine();
            termin.add(line1);
        }

        System.out.println("Unesite datum za termin (Format | dd-mm-yyyy)");
        line1 = scanner.nextLine();
        termin.add(line1);

        if(dan){
            System.out.println("Unesite dan");
            line1 = scanner.nextLine();
            termin.add(line1);
        }

        System.out.println("Unesite vreme (Format | 00:00-00:00)");
        line1 = scanner.nextLine();
        termin.add(line1);

        System.out.println("Unesite mesto");
        line1 = scanner.nextLine();
        termin.add(line1);

        rasporedAC.premestanjeTermina(rasporedAC.getTermini().get(brojKaoInt-1), termin);
    }

    private void osam(){
        System.out.println("Izaberite opciju");
        System.out.println("1. CSV");
        System.out.println("2. JSON");
        String line = scanner.nextLine();
        String provera = line;
        System.out.println("Unesite putanju do fajla u koji zelite da radite Export");
        line = scanner.nextLine();
        File file = new File(line);
        if(provera.contains("2"))
            rasporedAC.JsonWriter(file);
        else if(provera.contains("1"))
            rasporedAC.CsvWriter(line);
        else
            System.out.println("Los unos");
    }

}
