import java.util.Scanner;

public class main{
    public static void main(String[] args) {
        System.out.println("Izaberite vrstu rasporeda:");
        System.out.println("1. Raspored kao kolekcija termina");
        System.out.println("2. Raspored na nedeljnom nivou u datom periodu");
        System.out.println("Unestite 1 ili 2");
        Scanner s = new Scanner(System.in);
        String raspored =  s.nextLine();
        Test test = null;
        Test2 test2 = null;
        if(raspored.equals("1"))
         test = new Test();
        else if (raspored.equals("2"))
         test2 = new Test2();
    }

}
