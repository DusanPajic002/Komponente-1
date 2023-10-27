import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
public class Termin {
    private Date vreme;
    private Prostorija mesto;

    public Termin(Date vreme, Prostorija mesto) {
        this.vreme = vreme;
        this.mesto = mesto;
    }
}
