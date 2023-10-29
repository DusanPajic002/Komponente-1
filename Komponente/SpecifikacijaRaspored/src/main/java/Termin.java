import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Termin {
    private Date pocetak;
    private Date kraj;
    private Prostorija mesto;
    private List<String> ostalo;

    public Termin(Date pocetak, Date kraj, Prostorija mesto, List<String> ostalo) {
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.mesto = mesto;
        this.ostalo = ostalo;
    }
}
