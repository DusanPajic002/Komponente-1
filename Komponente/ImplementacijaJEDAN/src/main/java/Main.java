import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RasporedAC raspored = new RasporedImpl1();
        File file = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\csv.csv");
        LocalDate pocetak = LocalDate.now();
        LocalDate kraj = LocalDate.of(2024,12,31);

        raspored.inicijalizacija(file,"Raspored za RAF", pocetak, kraj);

        System.out.println(raspored.getKolone());

        System.out.println(raspored.getProstorije());
    }
}