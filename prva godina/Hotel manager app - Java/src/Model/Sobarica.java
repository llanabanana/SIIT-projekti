package Model;

import java.util.List;

import Utils.Helper.StrucnaSprema;
import Utils.Helper.Uloga;

public class Sobarica extends Zaposleni {
    private List<Soba> sobeZaSpremanje;

    public Sobarica() {
        super();
    }

    public Sobarica(String korisnickoIme, 
                    String lozinka, String ime, String lastName, String gender,
                    String dateOfBirth, int phone, String adress, Uloga uloga, 
                    StrucnaSprema strucnaSprema, int radniStaz, int plata, List<Soba> sobeZaSpremanje) {
        super(korisnickoIme, lozinka, ime, lastName, gender, dateOfBirth, phone, adress, uloga, strucnaSprema,
                radniStaz, plata);

        this.sobeZaSpremanje = sobeZaSpremanje;
    }

    public List<Soba> getSobeZaSpremanje() {
        return sobeZaSpremanje;
    }

    public void setSobeZaSpremanje(List<Soba> sobeZaSpremanje) {
        this.sobeZaSpremanje = sobeZaSpremanje;
    }
}
