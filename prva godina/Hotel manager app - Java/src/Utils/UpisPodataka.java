package Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.DodatnaUsluga;
import Model.Rezervacija;
import Model.SobaZaSpremanje;
import Utils.Helper.StatusRezervacije;

public class UpisPodataka {

    public static void upisiRezervacije(String filepath, List<Rezervacija> rezervacijeList) throws Exception {
        int brojRezervacije = 1;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {

            for (Rezervacija r : rezervacijeList) {

                List<String> dodatneUsluge = new ArrayList<>();

                for (DodatnaUsluga usluga : r.getDodatneUsluge()) {
                    dodatneUsluge.add(usluga.getNaziv());
                }

                // Join the string representations with a semicolon
                String dodatneUslugeStr = String.join(";", dodatneUsluge);

                if (r.getBrojSobe() == null) {
                    LocalDate datProm = r.getDatumPromeneStatusa();
                    StatusRezervacije promena = r.getPromena();

                    bw.write(String.valueOf(brojRezervacije) + "," + r.getGost().getKorisnickoIme() + ","
                            + r.getCheckInDatum()
                            + ","
                            + r.getCheckOutDatum() + "," + r.getBrojGostiju() + "," + "0" + ","
                            + r.getStatusRezervacije() + "," + dodatneUslugeStr + "," + r.getCena() + ","
                            + datProm + "," + promena);

                    bw.newLine();
                } else {
                    LocalDate datProm = r.getDatumPromeneStatusa();
                    StatusRezervacije promena = r.getPromena();
                    bw.write(String.valueOf(brojRezervacije) + "," + r.getGost().getKorisnickoIme() + ","
                            + r.getCheckInDatum()
                            + ","
                            + r.getCheckOutDatum() + "," + r.getBrojGostiju() + "," + r.getBrojSobe().getBrojSobe()
                            + ","
                            + r.getStatusRezervacije() + "," + dodatneUslugeStr + "," + r.getCena() + ","
                            + datProm + "," + promena);

                    bw.newLine();
                }

                brojRezervacije++;

            }
        }
    }

    public static void upisiSobeZaSpremanje(String filePath, List<SobaZaSpremanje> sobeZaSpremanjeList) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (SobaZaSpremanje s : sobeZaSpremanjeList) {
                bw.write(s.getSobaricaKorisnicko() + "," + s.getDatum() + "," + s.getBrojSobe());
                bw.newLine();
            }
        }
    }

}
