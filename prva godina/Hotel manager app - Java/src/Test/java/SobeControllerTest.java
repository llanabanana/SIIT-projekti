package Test.java;

import org.junit.Before;
import org.junit.Test;

import Controller.SobeController;
import Model.Soba;
import Utils.Helper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SobeControllerTest {

    private SobeController sobeController;
    private List<Soba> sobeList;

    @Before
    public void setUp() {
        sobeController = new SobeController();

        // Initialize test data
        sobeList = new ArrayList<>();

        // Add sample data
        Soba soba1 = new Soba(101, Helper.Usluge.Jednokrevetna, Helper.StatusSobe.Spremanje, null);
        Soba soba2 = new Soba(102, Helper.Usluge.Dvokrevetna11, Helper.StatusSobe.Spremanje, null);
        sobeList.add(soba1);
        sobeList.add(soba2);

        sobeController.setSveSobe(sobeList);
    }

    @Test
    public void testGetSveSobe() {
        List<Soba> result = sobeController.getSobe();
        assertEquals(sobeList.size(), result.size());
        assertTrue(result.containsAll(sobeList));
    }

    @Test
    public void testDodajSobu() {
        Soba novaSoba = new Soba(103, Helper.Usluge.Trokrevetna, Helper.StatusSobe.Slobodna, null);
        sobeController.dodajSobu(novaSoba, Helper.Uloga.Recepcioner);

        List<Soba> result = sobeController.getSobe();
        assertEquals(3, result.size());
        assertTrue(result.contains(novaSoba));
    }

    @Test
    public void testBrojSobe(){
        Soba tempSoba = new Soba(104, Helper.Usluge.Trokrevetna, Helper.StatusSobe.Slobodna, null);
        sobeController.dodajSobu(tempSoba, Helper.Uloga.Recepcioner);
        List<Soba> sobe = sobeController.getSobe();

        // Testiramo i getBrojSobe i getSveSobe i da li je soba na dobrom mestu u listi
        assertEquals(104, sobe.get(2).getBrojSobe());
    }

    @Test
    public void testStatusSobe(){
        Soba tempSoba = sobeList.get(0); // Uzmi sobu 1
        tempSoba.setStatusSobe(Helper.StatusSobe.Slobodna);

        assertTrue(tempSoba.getStatusSobe() == Helper.StatusSobe.Slobodna);
    }

}