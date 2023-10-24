package Interfejsi;

import java.util.ArrayList;
import java.util.List;

public class Prostorija {

    private String nazivProstorije;
    private List<Osobina> osobine = new ArrayList<>();

    public Prostorija (ArrayList<Osobina> osobine, String nazivProstorije){
        this.nazivProstorije = nazivProstorije;
        this.osobine = osobine;
    }

}
