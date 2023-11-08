import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RasporedAC raspored = new RasporedImpl1();
        File file = new File("C:\\Users\\istojmirovic4521rn\\IdeaProjects\\Komponente-1\\raspored.json");
        LocalDate pocetak = null;
        LocalDate kraj = null;
        //File file2 = new File("C:\\Users\\I L I J A\\Desktop\\json1.json");

        //raspored.inicijalizacija("aaa",pocetak,kraj,null);
        //raspored.CSVread(file);
        raspored.JSONread(file);

        System.out.println(raspored.getKolone());
        //System.out.println(raspored.getProstorije());

        System.out.println(raspored.getTermini());
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
    }
}