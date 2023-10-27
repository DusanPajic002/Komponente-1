import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class TerminImpl2 extends Termin{

    private Date datumPocetak;
    private Date datumKraj;

    public TerminImpl2(Date pocetak, Date kraj, Prostorija mesto) {
        super(pocetak, kraj, mesto);
    }
}
