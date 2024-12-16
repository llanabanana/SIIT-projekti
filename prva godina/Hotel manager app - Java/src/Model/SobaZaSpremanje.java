package Model;

import java.time.LocalDate;

public class SobaZaSpremanje {
    private String sobaricaKorisnicko;
    private LocalDate datum;
    private int brojSobe;

    public SobaZaSpremanje(String sobaricaKorisnicko, LocalDate datum, int brojSobe) {
        this.sobaricaKorisnicko = sobaricaKorisnicko;
        this.datum = datum;
        this.brojSobe = brojSobe;
    }

    public String getSobaricaKorisnicko() {
        return sobaricaKorisnicko;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public int getBrojSobe() {
        return brojSobe;
    }
}
