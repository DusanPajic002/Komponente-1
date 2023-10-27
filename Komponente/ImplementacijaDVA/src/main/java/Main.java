import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RasporedAC rasporedAC = new RasporedImpl2();
        File file = new File("C:\\Users\\Korisnik\\Komponente\\csv.csv");
        Date pocetak= null;
        Date kraj = null;
        rasporedAC.inicijalizacija(file,"Test", pocetak, kraj);
    }
}