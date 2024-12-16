package Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Sobarica;
import Utils.Helper;
import Model.Korisnik;
import Model.Soba;
import Model.SobaZaSpremanje;

public class SobariceController {

    /*
     * Pretpostavka: Sobe za spremanje su uvek aktuelne za tekuci datum
     * Dakle, nakon odjave, soba se dodeljuje sobarici koja ima najmanje soba za
     * sremanje
     * Sobe za spremanje su samo one sobe koje u sobarica.csv imaju danasnji datum,
     * svi ostale su vece spremljene
     * 
     */

    public List<Soba> getSobeZaSpremanje(String sobaricaKorisnicko, List<SobaZaSpremanje> sobeZaSpremanjeList,
            List<Soba> sobeList) {
        List<Soba> sobariceSobeList = new ArrayList<>();
        LocalDate danasnjiDatum = LocalDate.now();

        for (SobaZaSpremanje sobaZaSpremanje : sobeZaSpremanjeList) {
            if (sobaZaSpremanje.getSobaricaKorisnicko().equals(sobaricaKorisnicko)
                    && sobaZaSpremanje.getDatum().equals(danasnjiDatum)) {
                for (Soba s : sobeList) {
                    if (s.getBrojSobe() == sobaZaSpremanje.getBrojSobe()
                            && s.getStatusSobe() == Helper.StatusSobe.Spremanje) {
                        sobariceSobeList.add(s);
                    }
                }
            }
        }

        return sobariceSobeList;
    }

    public Sobarica sobaricaSaNajmanjeSoba(List<Sobarica> sveSobarice) {
        Sobarica sobarica = null;
        int min = Integer.MAX_VALUE;

        for (Sobarica s : sveSobarice) {
            if (s.getSobeZaSpremanje() == null || s.getSobeZaSpremanje().size() == 0) {
                return s;
            } else if (s.getSobeZaSpremanje().size() < min) {
                min = s.getSobeZaSpremanje().size();
                sobarica = s;
            }
        }
        return sobarica;
    }

    public boolean spremiSobu(int brojSobe, List<Soba> sobeList, List<Sobarica> sobariceList, Korisnik korisnik) {

        for (Soba s : sobeList) {
            if (s.getBrojSobe() == brojSobe) { // Convert int to String
                s.setStatusSobe(Helper.StatusSobe.Slobodna);

                for (Sobarica sobarica : sobariceList) {
                    if (korisnik.getKorisnickoIme().equals(sobarica.getKorisnickoIme())) {
                        sobarica.getSobeZaSpremanje().removeIf(soba -> soba.getBrojSobe() == brojSobe);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static List<Soba> getSobeZaSpremanjeZaPeriod(String korisnicko, LocalDate PocetniDatum,
            LocalDate KrajnjiDatum,
            List<SobaZaSpremanje> sobeZaSpremanjeList, List<Soba> sobeList, List<Korisnik> korisniciList) {
        List<Soba> sobeZaSpremanjeZaPeriod = new ArrayList<>();
        for (Korisnik k : korisniciList) {
            if (k.getKorisnickoIme().equals(korisnicko)) {
                for (SobaZaSpremanje sobaZaSpremanje : sobeZaSpremanjeList) {
                    if ((sobaZaSpremanje.getDatum().isAfter(PocetniDatum)
                            || sobaZaSpremanje.getDatum().equals(PocetniDatum))
                            && (sobaZaSpremanje.getDatum().isBefore(KrajnjiDatum)
                                    || sobaZaSpremanje.getDatum().equals(KrajnjiDatum))
                            && sobaZaSpremanje.getSobaricaKorisnicko().equals(korisnicko)) {

                        for (Soba s : sobeList) {
                            if (s.getBrojSobe() == sobaZaSpremanje.getBrojSobe()
                                    && s.getStatusSobe() != Helper.StatusSobe.Spremanje) {
                                sobeZaSpremanjeZaPeriod.add(s);
                            }
                        }
                    }
                }

                break;

            }
        }
        return sobeZaSpremanjeZaPeriod;
    }

}