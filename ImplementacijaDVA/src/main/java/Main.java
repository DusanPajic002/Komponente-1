import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        File cfg = new File("C:\\Users\\Korisnik\\Desktop\\Komp\\ImplementacijaDVA\\config");
        RasporedAC rasporedAC = new RasporedImpl2(cfg);
        File file = new File("C:\\Users\\Korisnik\\Desktop\\Komp\\raspored.json");
        LocalDate pocetak = LocalDate.now();
        LocalDate kraj = LocalDate.now();
        rasporedAC.inicijalizacija("Test", pocetak, kraj,null);
        rasporedAC.JSONread(file);



        /*System.out.println("-------------------");
        List<String> novTermin = new ArrayList<>();
        novTermin.add("Linearna algebra");              //1
        novTermin.add("V");                             //2
        novTermin.add("Jovanovic Jelena");              //3
        novTermin.add("101, 102, 103");                 //4
        //novTermin.add("1-8-2023");                      //5
        //novTermin.add("22-8-2023");                     //6
        novTermin.add("ET");                            //7
        novTermin.add("18:15-21:00");                   //8
        novTermin.add("Raf20 (a)");                     //9
        rasporedAC.dodajNovTermin(novTermin,false);
        System.out.println(rasporedAC.getTermini());*/


    }
}