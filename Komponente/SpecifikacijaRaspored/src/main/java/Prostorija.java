import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Prostorija {

    private String nazivProstorije;
    private List<Osobina> osobine = new ArrayList<>();

    public Prostorija (ArrayList<Osobina> osobine, String nazivProstorije){
        this.nazivProstorije = nazivProstorije;
        this.osobine = osobine;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prostorija that = (Prostorija) o;
        return Objects.equals(nazivProstorije, that.nazivProstorije);
    }
    @Override
    public String toString() {
        return nazivProstorije + ' ';
    }
}
