package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import Model.Zaposleni;
import Utils.Helper;

public class ZaposleniController {

    private List<Zaposleni> sviZaposleni = new ArrayList<>();

    public void dodajAdministratora(Zaposleni administrator) {
        administrator.setUloga(Helper.Uloga.Administrator);
        sviZaposleni.add(administrator);
    }

    public void dodajZaposlenog(Zaposleni zaposlen, Helper.Uloga trenutnaUloga) {
        // Provera da li je ulogovani korisnik administrator

        if (trenutnaUloga == Helper.Uloga.Administrator) {
            sviZaposleni.add(zaposlen);
            return; // Exit the loop after adding the new Zaposleni
        } else {
            System.err.println("Korisnik nije administrator");
            // TODO: raise error that user is not an administrator
        }
    }

    public void ukloniZaposlenog(String korisnickoIme, Helper.Uloga trenutnaUloga) {
        // Provera da li je ulogovani korisnik administrator
        if (trenutnaUloga == Helper.Uloga.Administrator) {
            Iterator<Zaposleni> iterator = sviZaposleni.iterator();
            while (iterator.hasNext()) {
                Zaposleni uklonjen = iterator.next();
                if (uklonjen.getKorisnickoIme().equals(korisnickoIme)) {
                    iterator.remove();
                    return; // Exit the method after removal
                }
            }
            System.err.println("Korisnik sa korisnickim imenom " + korisnickoIme + " nije pronađen.");
            return; // Exit the method if user is not found
        } else {
            System.err.println("Korisnik nije administrator");
            return; // Exit the method if user is not an administrator
        }
    }

    public List<Zaposleni> getZaposleni() {
        return sviZaposleni;
    }

    public void setSviZaposleni(List<Zaposleni> sviZaposleni){
        this.sviZaposleni = sviZaposleni;
    }

}
