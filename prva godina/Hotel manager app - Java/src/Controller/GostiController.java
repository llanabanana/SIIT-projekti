package Controller;

import java.util.ArrayList;
import java.util.List;
import Model.Korisnik;
import Utils.Helper;

public class GostiController {
    private List<Korisnik> sviGosti = new ArrayList<>();
    // problems: NE VALJA ID

    public void dodajGosta(Korisnik gost, Helper.Uloga trenutnaUloga) {

        if (Helper.Uloga.Recepcioner == trenutnaUloga) {
            gost.setUloga(Helper.Uloga.Gost);
            sviGosti.add(gost);
            return; // Exit the method after adding the guest
        } else {
            System.err.println("Korisnik nije recepcioner");
            return; // Exit the method if the user is not a receptionist
        }

    }

    public List<Korisnik> getGosti() {
        return sviGosti;
    }
}
