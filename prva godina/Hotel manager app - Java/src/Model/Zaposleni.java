package Model;

import Utils.Helper;
import Utils.Helper.StrucnaSprema;
import Utils.Helper.Uloga;

public class Zaposleni extends Korisnik {

    private Helper.StrucnaSprema strucnaSprema;
    private int plata;
    private int radniStaz;

    public Zaposleni() {
        super();
    }

    public Zaposleni(String korisnickoIme, String lozinka, String ime, String lastName, String gender,
            String dateOfBirth, int phone, String adress, Uloga uloga, StrucnaSprema strucnaSprema, int radniStaz,
            int plata) {
        super(korisnickoIme, lozinka, ime, lastName, gender, dateOfBirth, phone, adress, uloga);
        this.strucnaSprema = strucnaSprema;
        this.radniStaz = radniStaz;
        this.plata = plata;
    }

    public StrucnaSprema getStrucnaSprema() {
        return strucnaSprema;
    }

    public int getStaz() {
        return radniStaz;
    }

    public int getPlata() {
        return plata;
    }

    public void setStrucnaSprema(StrucnaSprema strucnaSprema) {
        this.strucnaSprema = strucnaSprema;
    }

    public void setStaz(int radniStaz) {
        this.radniStaz = radniStaz;
    }

    public void setPlata(int plata) {
        this.plata = plata;
    }

}
