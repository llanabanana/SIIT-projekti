package Controller;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Model.Cenovnik;
import Utils.Helper.Uloga;

public class CenovnikController {
    private List<Cenovnik> cenovnici = new ArrayList<>();

    public void dodajCenu(Cenovnik cenovnik, Uloga trenutnaUloga) {
        if (trenutnaUloga == Uloga.Recepcioner) {
            cenovnici.add(cenovnik);
            return; // Exit the method after adding the new Cenovnik
        } else {
            System.err.println("Korisnik nije recepcioner");
            return; // Exit the method if user is not an administrator
        }
    }

    public void izmeniCenovnik(HashMap<String, Double> cene, String oznaka, double cena) {
        if (cene.containsKey(oznaka)) {
            cene.put(oznaka, cena);
        } else {
            System.err.println("Ne postoji oznaka u cenovniku");
        }
    }

    public List<Cenovnik> sortirajCenovnike(List<Cenovnik> cenovnici) {
        // Sort based on the highest price within each Cenovnik
        cenovnici.sort((cenovnik1, cenovnik2) -> {
            Double maxPrice1 = Collections.max(cenovnik1.getCenovnik().values());
            Double maxPrice2 = Collections.max(cenovnik2.getCenovnik().values());
            return maxPrice2.compareTo(maxPrice1); // Sort descending
        });

        return cenovnici;
    }

    public List<Cenovnik> getCenovnici() {
        return cenovnici;
    }

}
