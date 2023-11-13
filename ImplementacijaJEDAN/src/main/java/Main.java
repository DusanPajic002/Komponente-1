import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        File file1 = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\ImplementacijaJEDAN\\config");
        RasporedAC raspored = new RasporedImpl1(file1);
        File file = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\raspored111.csv");
        LocalDate pocetak = LocalDate.now();
        LocalDate kraj = LocalDate.now();
        //File file2 = new File("C:\\Users\\I L I J A\\Desktop\\json1.json");

        raspored.inicijalizacija("aaa",pocetak,kraj,null, LocalTime.parse("09:00"), LocalTime.parse("21:00"));
        raspored.CSVread(file);
        //raspored.JSONread(file);

        //System.out.println(raspored.getKolone());
        //System.out.println(raspored.getProstorije());

        System.out.println(raspored.getTermini());
        System.out.println("---------------------------");
        System.out.println("Slobodni termini: " + ((RasporedImpl1)raspored).filtrirajSlobodne("Med","22-2-2002"));
        /*System.out.println("-------------------");
        List<String> novTermin = new ArrayList<>();
        novTermin.add("zzz");
        novTermin.add("aaa");
        novTermin.add("22-05-2000");
        novTermin.add("CET");
        novTermin.add("15:00-17");
        novTermin.add("Med");
        //raspored.dodajProstoriju("Ucionica neka");
        raspored.dodajNovTermin(novTermin,true);
        System.out.println(raspored.getTermini());*/

        //File filewrite = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\jsonWrite.json");
        //rasporedAC.JSONread(filewrite);
        //System.out.println(rasporedAC.getTermini());
        //raspored.JsonWriter(filewrite);

        /*try {
            raspored.CsvWriter("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\csvwritetest.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}