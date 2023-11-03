import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RasporedAC raspored = new RasporedImpl1();
        File file = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\raspored1.csv");
        LocalDate pocetak = null;
        LocalDate kraj = null;

        raspored.inicijalizacija("aaa",pocetak,kraj,null);
        raspored.CSVread(file);

        //System.out.println(raspored.getKolone());
        //System.out.println(raspored.getProstorije());

        System.out.println(((RasporedImpl1)raspored).getTermini());
    }
}