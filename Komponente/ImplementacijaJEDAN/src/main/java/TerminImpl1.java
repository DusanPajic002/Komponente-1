import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class TerminImpl1 extends Termin{

    private LocalDate datum;


    public TerminImpl1(LocalTime pocetak, LocalTime kraj, Prostorija mesto) {
        super(pocetak, kraj, mesto);
    }


    @Override
    public String toString() {
        return "Termin: " + ostalo(getOstalo()) + " | " + this.getSatPocetka() + "-" + this.getSatKraja() + " | " + getDatum() + " | " + getDan() + " | "  + getMesto() + "|\n";
    }

    private String ostalo(List<String> ostalo){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<ostalo.size()-1;i++)
            sb.append(ostalo.get(i) + " ");
        sb.append(ostalo.get(ostalo.size()-1));
        return sb.toString();
    }
}
