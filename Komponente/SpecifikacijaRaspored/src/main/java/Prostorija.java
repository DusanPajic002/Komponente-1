import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Prostorija {

    private String nazivProstorije;
    private List<Osobina> osobine = new ArrayList<>();

    public Prostorija (ArrayList<Osobina> osobine, String nazivProstorije){
        this.nazivProstorije = nazivProstorije;
        this.osobine = osobine;
    }

}
