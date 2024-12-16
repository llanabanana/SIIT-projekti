package Test.java;

import org.junit.Before;
import org.junit.Test;

import Controller.RezervacijeController;
import Model.DodatnaUsluga;
import Model.Korisnik;
import Model.Rezervacija;
import Model.Soba;
import Utils.Helper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RezervacijeControllerTest {

    private RezervacijeController rezervacijeController;
    private List<Rezervacija> rezervacijeList;
    private Soba soba1;
    private Soba soba2;

    @Before
    public void setUp() {
        rezervacijeController = new RezervacijeController();

        // Initialize test data
        rezervacijeList = new ArrayList<>();

        // Add sample data
        Korisnik gost1 = new Korisnik();
        gost1.setKorisnickoIme("gost1");
        soba1 = new Soba(101, Helper.Usluge.Dvokrevetna2, Helper.StatusSobe.Spremanje, null);
        List<DodatnaUsluga> usluge1 = new ArrayList<>();
        usluge1.add(new DodatnaUsluga("Dorucak"));

        Rezervacija rezervacija1 = new Rezervacija(120, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 10),
                                      soba1, Helper.Usluge.Trokrevetna, gost1, Helper.StatusRezervacije.NaCekanju,
                                      usluge1, 5000.0, null, null);
        
        Korisnik gost2 = new Korisnik();
        gost2.setKorisnickoIme("gost2");
        Soba soba2 = new Soba(102, Helper.Usluge.Jednokrevetna, Helper.StatusSobe.Slobodna, null);
        List<DodatnaUsluga> usluge2 = new ArrayList<>();
        usluge2.add(new DodatnaUsluga("Bazen"));

        Rezervacija rezervacija2 = new Rezervacija(220, LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 10),
                                        soba2, Helper.Usluge.Dvokrevetna11, gost2, Helper.StatusRezervacije.NaCekanju,
                                                    usluge2, 6000.0, null, null);

        rezervacijeList.add(rezervacija1);
        rezervacijeList.add(rezervacija2);

        rezervacijeController.dodajRezervaciju(rezervacija1, Helper.Uloga.Recepcioner);
        rezervacijeController.dodajRezervaciju(rezervacija2, Helper.Uloga.Recepcioner);
    }

    @Test
    public void testRezervacijaZaGosta(){

        String ime = rezervacijeList.get(0).getGost().getKorisnickoIme();
        List<Rezervacija> gostRezervacije = rezervacijeController.getRezervacijeZaGosta(ime, Helper.Uloga.Recepcioner);

        assertEquals(1, gostRezervacije.size());
    }
    
}
