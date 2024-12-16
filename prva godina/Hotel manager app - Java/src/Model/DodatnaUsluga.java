package Model;

public class DodatnaUsluga {
    private String naziv;

    public DodatnaUsluga() {
        super();
    }

    public DodatnaUsluga(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "DodatnaUsluga{" +
                "naziv='" + naziv;
    }
}
