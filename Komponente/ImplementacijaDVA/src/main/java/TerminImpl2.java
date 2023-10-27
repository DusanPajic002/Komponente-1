import java.time.LocalDate;

public class TerminImpl2 extends Termin{

    private String datumPocetak;
    private String datumKraj;

    public TerminImpl2(LocalDate vreme, Prostorija mesto) {
        super(vreme, mesto);
    }
}
