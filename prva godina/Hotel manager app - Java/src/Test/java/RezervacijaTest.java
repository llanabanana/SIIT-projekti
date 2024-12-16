package Test.java;

import org.junit.Before;
import org.junit.Test;

import Model.DodatnaUsluga;
import Model.Korisnik;
import Model.Rezervacija;
import Model.Soba;
import Utils.Helper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RezervacijaTest {

    private Rezervacija rezervacija;
    private Korisnik gost;
    private Soba soba;
    private List<DodatnaUsluga> dodatneUsluge;

    @Before
    public void setUp() {
        gost = new Korisnik();
        gost.setKorisnickoIme("gost1");
        gost.setIme("Ivan");
        gost.setLastName("Ivanovic");

        soba = new Soba(101, Helper.Usluge.Jednokrevetna, Helper.StatusSobe.Spremanje, null);

        dodatneUsluge = new ArrayList<>();
        dodatneUsluge.add(new DodatnaUsluga("Dorucak"));
        dodatneUsluge.add(new DodatnaUsluga("SPA"));

        rezervacija = new Rezervacija(111, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 10),
                                      soba, Helper.Usluge.Trokrevetna, gost,Helper.StatusRezervacije.NaCekanju,
                                      dodatneUsluge, 5000.0, null, null);
    }

    @Test
    public void testRezervacija(){
        
        assertEquals(101, rezervacija.getBrojSobe().getBrojSobe());
        assertNull(rezervacija.getPromena());
    }

    @Test
    public void testGetSetBrojRezervacije() {
        rezervacija.setBrojRezervacije(2);
        assertEquals(2, rezervacija.getBrojRezervacije());
    }

    @Test
    public void testGetSetGost() {
        Korisnik newGost = new Korisnik();
        newGost.setKorisnickoIme("gost2");
        rezervacija.setGost(newGost);
        assertEquals("gost2", rezervacija.getGost().getKorisnickoIme());
    }

    @Test
    public void testGetSetCheckInDatum() {
        LocalDate noviDatum = LocalDate.of(2024, 8, 1);
        rezervacija.setCheckInDatum(noviDatum);
        assertEquals(noviDatum, rezervacija.getCheckInDatum());
    }
}
