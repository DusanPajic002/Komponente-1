import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class TerminImpl2 extends Termin{

    private LocalDate datumPocetak;
    private LocalDate datumKraj;

    public TerminImpl2(Date vreme, Prostorija mesto) {
        super(vreme, mesto);
    }
}
