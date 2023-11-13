import javax.sound.midi.Soundbank;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test2 {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private RasporedAC rasporedAC = null;
    public Test2(){
        test();
    }

    private void test() {
        Scanner scanner = new Scanner(System.in);
        File cfg = new File("C:\\Users\\Korisnik\\Desktop\\Komponente\\ImplementacijaDVA\\config");
        rasporedAC = new RasporedImpl2(cfg);

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
            System.out.println("5. Premesti termin");
            System.out.println("6. Stampaj raspored");
            System.out.println("7. Export fajla");
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
                    for(Termin t: rasporedAC.getTermini())
                        System.out.println(t);
                    break;
                }
                case ("7"):{
                    sedam();
                    break;
                }
                case ("0"):{
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
        Scanner scanner = new Scanner(System.in);
        String line;
        int k = 0;
        boolean datumi = false;
        for (Config cfg: rasporedAC.getConfigs())
            if(cfg.getOriginal().equals("ostalo"))
                k++;
            else if (cfg.getOriginal().equals("end") || cfg.getOriginal().equals("start") )
                datumi = true;
        for(int i=0; i<k; i++){
            System.out.println("Unesite vrednom osobine");
            line = scanner.nextLine();
            termin.add(line);
        }

        if(datumi){
            System.out.println("Unesite datum od kada vazi termin (Format | dd-mm-yyyy)");
            line = scanner.nextLine();
            termin.add(line);
            System.out.println("Unesite datum do kada vazi termin (Format | dd-mm-yyyy)");
            line = scanner.nextLine();
            termin.add(line);
        }

        System.out.println("Unesite dan");
        line = scanner.nextLine();
        termin.add(line);

        System.out.println("Unesite vreme (Format | 00:00-00:00)");
        line = scanner.nextLine();
        termin.add(line);

        System.out.println("Unesite mesto");
        line = scanner.nextLine();
        termin.add(line);

        rasporedAC.dodajNovTermin(termin);
        scanner.close();
    }
    private void dva(){
        System.out.println("Unesite redni broj termina kog zelite da obrisete");
        Scanner scanner = new Scanner(System.in);
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
        scanner.close();
    }
    private void tri(){
        List<String> pretraga = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Unesite naziv kolone");
        String line = scanner.nextLine();
        pretraga.add(line);

        System.out.println("Unesite vrednost po kojoj pretrazujemo");
        line = scanner.nextLine();
        pretraga.add(line);

        List<Termin> termini =  rasporedAC.filtriraj(pretraga.get(0), pretraga.get(1));
        for(Termin t: termini)
            System.out.println(t);
        scanner.close();
    }
    private void cetri(){
        List<String> pretraga = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Unesite datum od kada filtrirate (Format | dd-mm-yyyy)");
        String line = scanner.nextLine();
        pretraga.add(line);

        System.out.println("Sada datum do kada filtrirate (Format | dd-mm-yyyy)");
        line = scanner.nextLine();
        pretraga.add(line);

        List<Termin> termini =  rasporedAC.filtriranoPoDatumu(pretraga.get(0), pretraga.get(1));
        for(Termin t: termini)
            System.out.println(t);
        scanner.close();
    }
    private void pet(){/// (Format | termin, kolona, vrednost)
        System.out.println("Unesite redni broj termina koji zelite da premestite");
        Scanner scanner = new Scanner(System.in);
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
            return;
        }
        System.out.println("Unesite naziv kolone koju menjate");
            String kolona = scanner.nextLine();

        System.out.println("Unesite u koju vrednost menjate");
            String vrednost = scanner.nextLine();

        System.out.println("----------------STR-NOV--------------------");
        System.out.println(rasporedAC.getTermini().get(brojKaoInt-1));
        System.out.println(rasporedAC.premestanjeTermina(rasporedAC.getTermini().get(brojKaoInt-1), kolona, vrednost));
        System.out.println("------------------------------------");
        scanner.close();
    }
    private void sedam(){
        System.out.println("Izaberite opciju");
        System.out.println("1. CSV");
        System.out.println("2. JSON");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String provera = line;
        System.out.println("Unesite putanju do fajla u koji zelite da radite Export");
        line = scanner.nextLine();
        File file = new File(line);
        if(provera.contains("1"))
            rasporedAC.JsonWriter(file);
        else if(provera.contains("2"))
            rasporedAC.CsvWriter(line);
        else
            System.out.println("Los unos");

        scanner.close();
    }



}
