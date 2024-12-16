package Test.java;

import org.junit.Before;
import org.junit.Test;

import Model.Korisnik;
import Utils.Helper;

import static org.junit.Assert.*;

public class KorisnikTest {

    private Korisnik korisnik;

    @Before
    public void setUp() {
        korisnik = new Korisnik();
    }

    @Test
    public void testGetSetKorisnickoIme() {
        korisnik.setKorisnickoIme("testuser");
        assertEquals("testuser", korisnik.getKorisnickoIme());
    }

    @Test
    public void testGetSetLozinka() {
        korisnik.setLozinka("password123");
        assertEquals("password123", korisnik.getLozinka());
    }

    @Test
    public void testGetSetIme() {
        korisnik.setIme("John");
        assertEquals("John", korisnik.getIme());
    }

    @Test
    public void testGetSetLastName() {
        korisnik.setLastName("Doe");
        assertEquals("Doe", korisnik.getLastName());
    }

    @Test
    public void testGetSetUloga() {
        korisnik.setUloga(Helper.Uloga.Administrator);
        assertNotEquals(Helper.Uloga.Recepcioner, korisnik.getUloga());
    }
}
