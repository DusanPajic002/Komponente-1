import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Osobina {

    private String nazivOsobine; //Kapacitet, Projektor
    private String karakteristikaOsobine; // 30, Ima,

    public Osobina(String nazivOsobine, String karakteristikaOsobine) {
        this.nazivOsobine = nazivOsobine;
        this.karakteristikaOsobine = karakteristikaOsobine;
    }
}
