package Test.java;

import org.junit.Before;
import org.junit.Test;

import Controller.ZaposleniController;
import Model.Zaposleni;
import Utils.Helper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ZaposleniControllerTest {
    
    private ZaposleniController zaposleniController;
    private List<Zaposleni> zaposleniList;

    @Before
    public void setUp() {
        zaposleniController = new ZaposleniController();

        // Initialize test data
        zaposleniList = new ArrayList<>();

        Zaposleni zaposleni1 = new Zaposleni("miram", "mira123", "Mira", "Miric", "Z", "1983-04-01", 
                                            0, "", Helper.Uloga.Sobarica, Helper.StrucnaSprema.SrednjaSkola, 10, 52000);
        
        Zaposleni zaposleni2 = new Zaposleni("jovanj", "jovan123", "Jovan", "Jovic", "M", "1975-11-20", 
                                            0, "", Helper.Uloga.Recepcioner, Helper.StrucnaSprema.Fakultet, 20, 62000);
        zaposleniList.add(zaposleni1);
        zaposleniList.add(zaposleni2);

        zaposleniController.setSviZaposleni(zaposleniList);
    }


    @Test
    public void testGetAllEmployees() {
        List<Zaposleni> result = zaposleniController.getZaposleni();
        assertEquals(zaposleniList.size(), result.size());
        assertTrue(result.containsAll(zaposleniList));
    }

    @Test
    public void testUkloniZaposlenog() {
        String zaBrisanje = zaposleniList.get(0).getKorisnickoIme();
        zaposleniController.ukloniZaposlenog(zaBrisanje, Helper.Uloga.Administrator);

        assertEquals(1, zaposleniList.size());
    }

    @Test
    public void testDodajZaposlenog() {
        Zaposleni novi = new Zaposleni("petarp", "pera123", "Petar", "Petrovic", "M", "1980-05-15",
                                              0, "", Helper.Uloga.Administrator, Helper.StrucnaSprema.Master, 15, 70000);
        zaposleniController.dodajZaposlenog(novi, Helper.Uloga.Administrator);

        List<Zaposleni> result = zaposleniController.getZaposleni();
        assertEquals(3, result.size());
        assertTrue(result.contains(novi));
    }

    @Test
    public void testSetAdministrator(){
        Zaposleni temp = zaposleniList.get(0);
        zaposleniController.dodajAdministratora(temp);

        assertTrue(temp.getUloga() == Helper.Uloga.Administrator);
    }


}
