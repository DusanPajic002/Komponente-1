import javax.xml.crypto.Data;
import java.io.File;
import java.nio.file.Path;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RasporedAC rasporedAC = new RasporedImpl2();
        File file = new File("C:\\Users\\Korisnik\\Komponente\\csv.csv");
        LocalDate pocetak = LocalDate.now();
        LocalDate kraj = LocalDate.now();
        rasporedAC.inicijalizacija("Test", pocetak, kraj,null);
        rasporedAC.CSVread(file);
        System.out.println(pocetak.getDayOfWeek());
        System.out.println(kraj.getDayOfWeek());
    }
}