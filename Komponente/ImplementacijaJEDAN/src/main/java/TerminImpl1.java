import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
public class TerminImpl1 extends Termin{
    public TerminImpl1(Date pocetak, Date kraj, Prostorija mesto) {
        super(pocetak, kraj, mesto);
    }
}
