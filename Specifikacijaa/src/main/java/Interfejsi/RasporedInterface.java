package Interfejsi;

public interface RasporedInterface {

    public <T> T inicijalizacija();

    public <T> T dodajProstoriju(Prostorija prostorija);

    public <T> T dodajNovTermin(Termin termin); //treba da ide uz proveru o zauzetosti termina

    public <T> T brisanjeTermina(Termin termin);

    public <T> T premestanjeTermina(Termin termin);

    //Treba da odradimo nekako za filtriranje rasporeda
    //Ucitavanje i snimanje u fajl

}
