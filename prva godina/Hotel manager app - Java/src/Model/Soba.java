package Model;

import Utils.Helper.StatusSobe;
import Utils.Helper.Usluge;
import Utils.Helper.atributiSobe;
import java.util.List;

public class Soba {

    private int brojSobe;
    private Usluge tipSobe;
    private StatusSobe statusSobe;
    private List<atributiSobe> atributiSobe;

    public Soba() {
        super();
    }

    public Soba(int brojSobe, Usluge tipSobe, StatusSobe statusSobe, List<atributiSobe> atributiSobe) {
        this.brojSobe = brojSobe;
        this.tipSobe = tipSobe;
        this.statusSobe = statusSobe;
        this.atributiSobe = atributiSobe;
    }

    public int getBrojSobe() {
        return brojSobe;
    }

    public Usluge getTipSobe() {
        return tipSobe;
    }

    public StatusSobe getStatusSobe() {
        return statusSobe;
    }

    public List<atributiSobe> getAtributiSobe() {
        return atributiSobe;
    }

    public void setBrojSobe(int brojSobe) {
        this.brojSobe = brojSobe;
    }

    public void setTipSobe(Usluge tipSobe) {
        this.tipSobe = tipSobe;
    }

    public void setStatusSobe(StatusSobe statusSobe) {
        this.statusSobe = statusSobe;
    }

    public void setAtributiSobe(List<atributiSobe> atributiSobe) {
        this.atributiSobe = atributiSobe;
    }

    @Override
    public String toString() {
        return "Soba{" +
                "brojSobe=" + brojSobe +
                ", tipSobe=" + tipSobe +
                ", statusSobe=" + statusSobe +
                ", atributiSobe=" + atributiSobe +
                '}';
    }
}
