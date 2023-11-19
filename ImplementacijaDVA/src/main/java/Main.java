import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File cfg = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\ImplementacijaDVA\\config");
        RasporedAC rasporedAC = new RasporedImpl2(cfg);
        File file = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\raspored.json");
        LocalDate pocetak = LocalDate.now();
        LocalDate kraj = LocalDate.now();
        rasporedAC.inicijalizacija("Test", pocetak, kraj,null,null,null);
        rasporedAC.JSONread(file);
        System.out.println(rasporedAC.getTermini());
        //rasporedAC.filtriraj("Vreme", "11:14-17:01");
        //Termin t = rasporedAC.getTermini().get(0);
        //rasporedAC.premestanjeTermina(t,"U?ionica","Kolarac2");
        /*System.out.println("-------------------");
        List<String> novTermin = new ArrayList<>();
        novTermin.add("Linearna algebra");              //1
        novTermin.add("V");                             //2
        novTermin.add("Jovanovic Jelena");              //3
        novTermin.add("101, 102, 103");                 //4
        //novTermin.add("11-10-2002");                    //5
        //novTermin.add("11-10-2002");                    //6
        novTermin.add("SRE");                           //7
        novTermin.add("18:15-21:00");                   //8
        novTermin.add("Kolarac2");                      //9
        rasporedAC.dodajNovTermin(novTermin);
        System.out.println(rasporedAC.getTermini());*/
        System.out.println("-------------------");
        //rasporedAC.brisanjeTermina(rasporedAC.getTermini().get(6-1));
        //System.out.println(rasporedAC.getTermini());
        File filewrite = new File("C:\\Users\\I L I J A\\Desktop\\Komponente-1\\rasporedWrite123.json");
        //rasporedAC.JSONread(filewrite);
        //System.out.println(rasporedAC.getTermini());
        rasporedAC.JsonWriter(filewrite);



    }
}