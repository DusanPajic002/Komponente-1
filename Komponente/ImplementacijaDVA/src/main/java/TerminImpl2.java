import java.time.LocalDate;
import java.util.Date;

public class TerminImpl2 extends Termin{

    private LocalDate datumPocetak;
    private LocalDate datumKraj;



    public TerminImpl2(Date vreme, Prostorija mesto) {
        super(vreme, mesto);
    }
}
