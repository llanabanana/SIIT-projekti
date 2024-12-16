package Utils;

import java.util.List;
import Model.Zaposleni;
import java.time.format.DateTimeFormatter;

public class Helper {
    public enum Uloga {
        Administrator,
        Recepcioner,
        Sobarica,
        Gost
    }

    public enum StrucnaSprema {
        OsnovnaSkola,
        SrednjaSkola,
        Fakultet,
        Master,
        Doktorat
    }

    public enum Usluge {
        Jednokrevetna,
        Dvokrevetna2,
        Dvokrevetna11,
        Trokrevetna,
    }

    public enum atributiSobe {
        Klima,
        TV,
        MiniBar,
        Pusacka,
        Balkon
    }

    public enum StatusSobe {
        Slobodna,
        Zauzeta,
        Spremanje
    }

    public enum StatusRezervacije {
        Potvrdjena,
        Otkazana,
        Odbijena,
        NaCekanju,
        Aktivna,
        Prosla
    }

    int idBrojac = 0;

    public static Helper.Uloga getTrenutnaUloga(String korisnickoIme, List<Zaposleni> zaposleniList) {
        for (Zaposleni z : zaposleniList) {
            if (z.getKorisnickoIme().equals(korisnickoIme)) {
                return z.getUloga();
            }
        }
        return null;
    }

    public static DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

}
