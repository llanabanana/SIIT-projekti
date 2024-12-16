package Test.java;

import org.junit.Before;
import org.junit.Test;

import Controller.SobariceController;
import Model.Korisnik;
import Model.Soba;
import Model.SobaZaSpremanje;
import Model.Sobarica;
import Utils.Helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class SobariceControllerTest {

    private SobariceController sobariceController;
    private List<SobaZaSpremanje> sobeZaSpremanjeList;
    private List<Soba> sobeList;
    private List<Sobarica> sobariceList;
    private Korisnik korisnik;

    @Before
    public void setUp() {
        sobariceController = new SobariceController();

        // Initialize test data
        sobeZaSpremanjeList = new ArrayList<>();
        sobeList = new ArrayList<>();
        sobariceList = new ArrayList<>();

        // Add sample data
        Soba soba1 = new Soba(101, Helper.Usluge.Jednokrevetna, Helper.StatusSobe.Spremanje, null);
        Soba soba2 = new Soba(102, Helper.Usluge.Dvokrevetna11, Helper.StatusSobe.Spremanje, null);
        sobeList.add(soba1);
        sobeList.add(soba2);

        SobaZaSpremanje szs1 = new SobaZaSpremanje("janajanic", LocalDate.now(), 101);
        SobaZaSpremanje szs2 = new SobaZaSpremanje("janajanic", LocalDate.now(), 102);
        sobeZaSpremanjeList.add(szs1);
        sobeZaSpremanjeList.add(szs2);

        Sobarica sobarica1 = new Sobarica();
        sobarica1.setKorisnickoIme("janajanic");
        sobarica1.setSobeZaSpremanje(new ArrayList<>(List.of(soba1, soba2)));
        sobariceList.add(sobarica1);

        korisnik = new Korisnik();
        korisnik.setKorisnickoIme("janajanic");
    }

    @Test
    public void testGetSobeZaSpremanje() {
        List<Soba> result = sobariceController.getSobeZaSpremanje("janajanic", sobeZaSpremanjeList, sobeList);
        assertEquals(2, result.size());
        assertTrue(result.contains(sobeList.get(0)));
        assertTrue(result.contains(sobeList.get(1)));
    }

    @Test
    public void testSobaricaSaNajmanjeSoba() {
        Sobarica result = sobariceController.sobaricaSaNajmanjeSoba(sobariceList);
        assertNotNull(result);
        assertEquals("janajanic", result.getKorisnickoIme());
    }

    @Test
    public void testSpremiSobu() {
        int brojSobe = 101;

        // Check initial status
        assertEquals(Helper.StatusSobe.Spremanje, sobeList.get(0).getStatusSobe());

        // Call the method
        boolean result = sobariceController.spremiSobu(brojSobe, sobeList, sobariceList, korisnik);

        // provera rezultata;  da li je promenjen status sobe i da li je soba uklinjena iz liste sobi za spremanje 
        // za sobaricu
        assertTrue(result);
        assertEquals(Helper.StatusSobe.Slobodna, sobeList.get(0).getStatusSobe());
        assertFalse(sobariceList.get(0).getSobeZaSpremanje().contains(sobeList.get(0)));
    }
}
