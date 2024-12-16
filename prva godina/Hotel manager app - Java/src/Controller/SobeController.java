package Controller;

import Model.Cenovnik;
import Model.Rezervacija;
import Model.Soba;
import Utils.Helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SobeController {
    List<Soba> sveSobe = new ArrayList<>();

    public void dodajSobu(Soba soba, Helper.Uloga trenutnaUloga) {
        if (trenutnaUloga == Helper.Uloga.Recepcioner) {
            sveSobe.add(soba);
            return; // Exit the method after adding the new Soba
        } else {
            System.err.println("Korisnik nije recepcioner");
            return; // Exit the method if user is not an administrator
        }
    }

    public void izmeniSobu(Soba soba, Helper.Usluge noviTipSobe, Helper.StatusSobe noviStatusSobe,
            Helper.Uloga trenutnaUloga) {
        if (trenutnaUloga == Helper.Uloga.Recepcioner) {
            for (Soba s : sveSobe) {
                if (s.getBrojSobe() == soba.getBrojSobe()) {
                    if (noviTipSobe != null) {
                        s.setTipSobe(noviTipSobe);
                    }
                    if (noviStatusSobe != null) {
                        s.setStatusSobe(noviStatusSobe);
                    }

                    return; // Exit the method after updating the Soba
                }
            }
            System.err.println("Soba sa brojem " + soba.getBrojSobe() + " nije pronađena.");
            return; // Exit the method if Soba is not found
        } else {
            System.err.println("Korisnik nije recepcioner");
            return; // Exit the method if user is not an administrator
        }
    }

    public List<Soba> getSlobodneSobe(List<Soba> sobeList, LocalDate pocetakRezerv, LocalDate krajRezerv,
            Helper.Usluge tipSobe, List<Rezervacija> sveRezervacije) {

        List<Soba> odgovarajuceSobe = new ArrayList<>();
        for (Soba soba : sobeList) {
            if (soba.getTipSobe().equals(tipSobe)) {
                odgovarajuceSobe.add(soba);
            }
        }

        List<Rezervacija> odgovarajuceRezervacije = new ArrayList<>();
        for (Rezervacija rez : sveRezervacije) {
            if (rez.getBrojGostiju().equals(tipSobe)
                    && rez.getStatusRezervacije().equals(Helper.StatusRezervacije.Aktivna)) {
                odgovarajuceRezervacije.add(rez);
            }
        }

        List<Soba> dostupneSobe = new ArrayList<>();
        for (Soba soba : odgovarajuceSobe) {
            boolean dostupna = true;
            for (Rezervacija r : odgovarajuceRezervacije) {
                if (datumiSePreklapaju(pocetakRezerv, krajRezerv, r.getCheckInDatum(), r.getCheckOutDatum())
                        && r.getBrojSobe().getBrojSobe() == soba.getBrojSobe()) {
                    dostupna = false;
                    break;
                }
            }
            if (dostupna) {
                dostupneSobe.add(soba);
            }
        }

        return dostupneSobe;
    }

    public static boolean datumiSePreklapaju(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return (start1.isBefore(end2) || start1.equals(end2)) && (end1.isAfter(start2) || end1.equals(start2));
    }

    public static int brojNocenjaPoRezeravciji(LocalDate start, LocalDate end) {
        // racunamo nocenja a ne dane
        return (int) start.datesUntil(end).count();
    }

    public static int brojNocenjaPoPeriodu(Soba s, List<Rezervacija> odgovarajuceRezervacije) {
        int brojNocenja = 0;
        for (Rezervacija r : odgovarajuceRezervacije) {
            if (r.getBrojSobe() == null) {
                continue;
            }
            if (r.getBrojSobe().getBrojSobe() == s.getBrojSobe()
                    && r.getStatusRezervacije().equals(Helper.StatusRezervacije.Prosla)) {
                brojNocenja += brojNocenjaPoRezeravciji(r.getCheckInDatum(), r.getCheckOutDatum());

            }
        }

        return brojNocenja;
    }

    public static Double prihodSobe(Soba soba, List<Rezervacija> odgovarajuceRezerv, List<Cenovnik> sveCene) {
        double prihod = 0;

        for (Rezervacija r : odgovarajuceRezerv) {
            if (r.getBrojSobe() == null) {
                continue;
            }
            if (r.getBrojSobe().getBrojSobe() == soba.getBrojSobe()) {
                prihod += RezervacijeController.getCenaSobe(r, sveCene);
            }
        }
        return prihod;

    }

    public List<Soba> getSobe() {
        return sveSobe;
    }

    public void setSveSobe(List<Soba> sveSobe){
        this.sveSobe = sveSobe;
    }
}
