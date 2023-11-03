import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ostalo {

    private String kolona; //Kapacitet, Projektor
    private String vrednost; // 30, Ima,

    public Ostalo(String kolona, String vrednost) {
        this.kolona = kolona;
        this.vrednost = vrednost;
    }

    @Override
    public String toString() {
        return  vrednost;
    }
}
