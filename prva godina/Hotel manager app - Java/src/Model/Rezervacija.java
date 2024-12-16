package Model;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDate;
import java.util.List;

import Utils.Helper;
import Utils.Helper.StatusRezervacije;

public class Rezervacija {
    private int brojRezervacije;
    private LocalDate checkInDatum;
    private LocalDate checkOutDatum;
    private Soba brojSobe;
    private Helper.Usluge brojGostiju;
    private Korisnik gost;
    private StatusRezervacije statusRezervacije;
    private List<DodatnaUsluga> dodatneUsluge;
    private double cena;
    private LocalDate datumPromeneStatusa;
    private StatusRezervacije promena;

    public Rezervacija() {
        super();
    }

    public Rezervacija(int brojRezervacije, LocalDate checkInDatum, LocalDate checkOutDatum, Soba brojSobe,
            Helper.Usluge brojGostiju, Korisnik gost, StatusRezervacije statusRezervacije, 
            List<DodatnaUsluga> dodatneUsluge, double cena,
            LocalDate datumPromeneStatusa, StatusRezervacije promena) {

        this.brojRezervacije = brojRezervacije;
        this.checkInDatum = checkInDatum;
        this.checkOutDatum = checkOutDatum;
        this.brojSobe = brojSobe;
        this.brojGostiju = brojGostiju;
        this.gost = gost;
        this.statusRezervacije = statusRezervacije;
        this.dodatneUsluge = dodatneUsluge;
        this.cena = cena;
        this.datumPromeneStatusa = datumPromeneStatusa;
        this.promena = promena;
    }

    public int getBrojRezervacije() {
        return brojRezervacije;
    }

    public LocalDate getCheckInDatum() {
        return checkInDatum;
    }

    public LocalDate getCheckOutDatum() {
        return checkOutDatum;
    }

    public Soba getBrojSobe() {
        return brojSobe;
    }

    public Helper.Usluge getBrojGostiju() {
        return brojGostiju;
    }

    public Korisnik getGost() {
        return gost;
    }

    public StatusRezervacije getStatusRezervacije() {
        return statusRezervacije;
    }

    public List<DodatnaUsluga> getDodatneUsluge() {
        return dodatneUsluge;
    }

    public double getCena() {
        return cena;
    }

    public LocalDate getDatumPromeneStatusa() {
        return datumPromeneStatusa;
    }

    public StatusRezervacije getPromena() {
        return promena;
    }

    public void setBrojRezervacije(int brojRezervacije) {
        this.brojRezervacije = brojRezervacije;
    }

    public void setCheckInDatum(LocalDate checkInDatum) {
        this.checkInDatum = checkInDatum;
    }

    public void setCheckOutDatum(LocalDate checkOutDatum) {
        this.checkOutDatum = checkOutDatum;
    }

    public void setBrojSobe(Soba brojSobe) {
        this.brojSobe = brojSobe;
    }

    public void setBrojGostiju(Helper.Usluge brojGostiju) {
        this.brojGostiju = brojGostiju;
    }

    public void setGost(Korisnik gost) {
        this.gost = gost;
    }

    public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
        this.statusRezervacije = statusRezervacije;
    }

    public void setDodatneUsluge(List<DodatnaUsluga> dodatneUsluge) {
        this.dodatneUsluge = dodatneUsluge;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setDatumPromeneStatusa(LocalDate datumPromeneStatusa) {
        this.datumPromeneStatusa = datumPromeneStatusa;
    }

    public void setPromena(StatusRezervacije promena) {
        this.promena = promena;
    }

    @Override
    public String toString() {
        return "Broj rezervacije" + brojRezervacije + "checkInDatum=" + checkInDatum +
                ", checkOutDatum=" + checkOutDatum +
                ", brojSobe=" + brojSobe +
                ", brojGostiju=" + brojGostiju +
                ", gost=" + gost.getKorisnickoIme() +
                ", statusRezervacije=" + statusRezervacije +
                ", dodatneUsluge=" + dodatneUsluge +
                ", cena=" + cena +
                ", datum promene= " + datumPromeneStatusa +
                ", promena = " + promena + "\n";
    }

}
