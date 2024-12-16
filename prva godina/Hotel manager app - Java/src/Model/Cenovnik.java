package Model;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.HashMap;

public class Cenovnik {
    HashMap<String, Double> cenovnik = new HashMap<String, Double>();
    private MonthDay vaziOd;
    private MonthDay vaziDo;

    public Cenovnik() {
        super();
    }

    public Cenovnik(HashMap<String, Double> cenovnik, MonthDay vaziOd, MonthDay vaziDo) {
        this.cenovnik = cenovnik;
        this.vaziOd = vaziOd;
        this.vaziDo = vaziDo;
    }

    public HashMap<String, Double> getCenovnik() {
        return cenovnik;
    }

    public MonthDay getVaziOd() {
        return vaziOd;
    }

    public MonthDay getVaziDo() {
        return vaziDo;
    }

    public void setCenovnik(HashMap<String, Double> cenovnik) {
        this.cenovnik = cenovnik;
    }

    public void setVaziOd(MonthDay vaziOd) {
        this.vaziOd = vaziOd;
    }

    public void setVaziDo(MonthDay vaziDo) {
        this.vaziDo = vaziDo;
    }

    @Override
    public String toString() {
        return "Cenovnik{" +
                "cenovnik=" + cenovnik +
                ", vaziOd=" + vaziOd +
                ", vaziDo=" + vaziDo +
                '}';
    }
}
