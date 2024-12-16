package Controller;

import java.util.ArrayList;
import java.util.List;
import Model.DodatnaUsluga;
import Utils.Helper;

public class DodatneUslugeController {
    private List<DodatnaUsluga> sveUsluge = new ArrayList<>();

    public void dodajUslugu(DodatnaUsluga usluga, Helper.Uloga trenutnaUloga) {
        if (trenutnaUloga == Helper.Uloga.Recepcioner) {
            sveUsluge.add(usluga);
            return; // Exit the method after adding the new Usluga
        } else {
            System.err.println("Korisnik nije recepcioner");
            return; // Exit the method if user is not an administrator
        }
    }

    public void obrisiUslugu(String nazivUsluge, Helper.Uloga trenutnaUloga) {
        if (trenutnaUloga == Helper.Uloga.Recepcioner) {
            for (DodatnaUsluga u : sveUsluge) {
                if (u.getNaziv().equals(nazivUsluge)) {
                    sveUsluge.remove(u);
                    return; // Exit the method after removing the Usluga
                }
            }
            System.err.println("Usluga sa nazivom " + nazivUsluge + " nije pronađena.");
            return; // Exit the method if Usluga is not found
        } else {
            System.err.println("Korisnik nije recepcioner");
            return; // Exit the method if user is not an administrator
        }
    }

    public List<DodatnaUsluga> getSveUsluge() {
        return sveUsluge;
    }

}
