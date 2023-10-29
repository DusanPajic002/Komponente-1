import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TerminImpl2 extends Termin{

    private LocalDate datumPocetak;
    private LocalDate datumKraj;

    public TerminImpl2(LocalTime pocetak, LocalTime kraj, Prostorija mesto, LocalDate datumPocetak, LocalDate datumKraj) {
        super(pocetak, kraj, mesto);
        this.datumPocetak = datumPocetak;
        this.datumKraj = datumKraj;
    }
    @Override
    public String toString() {
        return "Termin: " + ostalo(getOstalo()) + " | " + this.getSatPocetka() + "-" + this.getSatKraja() + " | " + getDan() + " | "  + getMesto() + "|\n";
              //  + "datum " + getDatumPocetak() + " - " + getDatumKraj()+ "\n";
    }

    private String ostalo(List<String> ostalo){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<ostalo.size()-1;i++)
           sb.append(ostalo.get(i) + " ");
        sb.append(ostalo.get(ostalo.size()-1));
        return sb.toString();
    }

}
