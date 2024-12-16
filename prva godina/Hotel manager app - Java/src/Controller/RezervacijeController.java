package Controller;

import Model.DodatnaUsluga;
import Model.Korisnik;
import Model.Rezervacija;
import Model.Cenovnik;
import Utils.Helper;
import Utils.UpisPodataka;
import Model.Zaposleni;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

public class RezervacijeController {
    private List<Rezervacija> sveRezervacije = new ArrayList<>();

    public void dodajRezervaciju(Rezervacija rezervacija, Helper.Uloga trenutnaUloga) {
        if (trenutnaUloga == Helper.Uloga.Recepcioner) {
            rezervacija.setStatusRezervacije(Helper.StatusRezervacije.NaCekanju);
            sveRezervacije.add(rezervacija);
            return; // Exit the method after adding the new Rezervacija
        } else {
            System.err.println("Korisnik nije recepcioner");
            return; // Exit the method if user is not an administrator
        }
    }

    public List<Rezervacija> getRezervacijeZaGosta(String korisnickoIme, Helper.Uloga trenutnaUloga) {
        if (trenutnaUloga == Helper.Uloga.Recepcioner) {
            List<Rezervacija> rezervacijeZaGosta = new ArrayList<>();
            for (Rezervacija r : sveRezervacije) {
                if (r.getGost().getKorisnickoIme().equals(korisnickoIme)) {
                    rezervacijeZaGosta.add(r);
                }
            }
            return rezervacijeZaGosta;
        }

        else {
            System.err.println("Korisnik nije recepcioner");
            return null; // Exit the method if user is not an administrator
        }

    }

    static public double getCenaRezervacije2(Rezervacija rezervacija, List<Cenovnik> cene) {
        double totalCena = 0;
        LocalDate datumPrijave = rezervacija.getCheckInDatum();
        LocalDate datumOdjave = rezervacija.getCheckOutDatum().minusDays(1);

        List<String> usluge = new ArrayList<>();
        for (DodatnaUsluga du : rezervacija.getDodatneUsluge()) {
            usluge.add(du.getNaziv());
        }
        usluge.add(rezervacija.getBrojGostiju().name());

        // Iterate over each day of the reservation period
        for (LocalDate date = datumPrijave; !date.isAfter(datumOdjave); date = date.plusDays(1)) {
            double dnevnaCena = getDnevnaCenaDatum(date, cene, usluge);
            totalCena += dnevnaCena;
        }
        // round to 1 decimal place
        totalCena = Math.round(totalCena * 10) / 10.0;
        return totalCena;
    }

    static public double getCenaSobe(Rezervacija rezervacija, List<Cenovnik> cene) {
        double totalCena = 0;
        LocalDate datumPrijave = rezervacija.getCheckInDatum();
        LocalDate datumOdjave = rezervacija.getCheckOutDatum().minusDays(1);

        List<String> usluge = new ArrayList<>();
        usluge.add(rezervacija.getBrojGostiju().name());
        int nocenja = datumOdjave.getDayOfYear() - datumPrijave.getDayOfYear();
        // Iterate over each day of the reservation period
        for (LocalDate date = datumPrijave; !date.isAfter(datumOdjave); date = date.plusDays(1)) {
            double dnevnaCena = getDnevnaCenaDatum(date, cene, usluge);
            totalCena += dnevnaCena;
        }
        // round to 1 decimal place
        totalCena = Math.round(totalCena * 10) / 10.0;
        return totalCena;
    }

    private static double getDnevnaCenaDatum(LocalDate date, List<Cenovnik> cenovnici, List<String> usluge) {

        for (Cenovnik cenovnik : cenovnici) {
            // Adjust the date ranges to account for possible year overlap
            MonthDay startDate = cenovnik.getVaziOd();
            MonthDay endDate = cenovnik.getVaziDo();

            LocalDate adjustedStartDate = startDate.atYear(date.getYear());
            LocalDate adjustedEndDate = endDate.atYear(date.getYear());

            // Handle year overlap (e.g., spans from Dec to Jan)
            if (adjustedEndDate.isBefore(adjustedStartDate)) {
                adjustedEndDate = adjustedEndDate.plusYears(1);
            }

            if ((date.isEqual(adjustedStartDate) || date.isAfter(adjustedStartDate)) &&
                    (date.isEqual(adjustedEndDate) || date.isBefore(adjustedEndDate))) {
                // return cenovnik.getCenovnik().get("cena");
                double ukupno_temp = 0;
                for (String usluga : usluge) {
                    double cena_temp = cenovnik.getCenovnik().get(usluga);
                    ukupno_temp += cena_temp;
                }
                return ukupno_temp;
            }
        }
        return 0;
    }

    // Lana - ne radi ako se preklapaju....
    // static public double getCenaRezervacije(Rezervacija rezervacija,
    // List<Cenovnik> cene) {
    // LocalDate checkInDatum = rezervacija.getCheckInDatum();
    // LocalDate checkOutDatum = rezervacija.getCheckOutDatum();
    // HashMap<String, Double> ceneUsluga = new HashMap<>();
    // for (Cenovnik cenovnik : cene) {
    // MonthDay vaziOd = MonthDay.from(cenovnik.getVaziOd());
    // MonthDay vaziDo = MonthDay.from(cenovnik.getVaziDo());
    // MonthDay checkInMonthDay = MonthDay.from(checkInDatum);
    // MonthDay checkOutMonthDay = MonthDay.from(checkOutDatum);

    // boolean wrapsAround = vaziDo.isBefore(vaziOd);

    // boolean checkInInRange = wrapsAround
    // ? (checkInMonthDay.isAfter(vaziOd) || checkInMonthDay.isBefore(vaziDo)
    // || checkInMonthDay.equals(vaziOd) || checkInMonthDay.equals(vaziDo))
    // : (checkInMonthDay.isAfter(vaziOd) && checkInMonthDay.isBefore(vaziDo)
    // || checkInMonthDay.equals(vaziOd) || checkInMonthDay.equals(vaziDo));

    // boolean checkOutInRange = wrapsAround
    // ? (checkOutMonthDay.isAfter(vaziOd) || checkOutMonthDay.isBefore(vaziDo)
    // || checkOutMonthDay.equals(vaziOd) || checkOutMonthDay.equals(vaziDo))
    // : (checkOutMonthDay.isAfter(vaziOd) && checkOutMonthDay.isBefore(vaziDo)
    // || checkOutMonthDay.equals(vaziOd) || checkOutMonthDay.equals(vaziDo));

    // if (checkInInRange && checkOutInRange) {
    // ceneUsluga = cenovnik.getCenovnik();
    // break;
    // }
    // }
    // int brojDana = checkOutDatum.getDayOfYear() - checkInDatum.getDayOfYear();
    // List<DodatnaUsluga> dodatneUsluge = rezervacija.getDodatneUsluge();

    // double cena = 0;
    // Helper.Usluge tipSobe = rezervacija.getBrojGostiju();

    // for (DodatnaUsluga du : dodatneUsluge) {
    // String nazivUsluge = du.getNaziv().toString();
    // cena += ceneUsluga.get(nazivUsluge);
    // }

    // cena += ceneUsluga.get(tipSobe.toString());
    // cena *= brojDana;

    // return cena;
    // }

    public List<Rezervacija> getSveRezervacije() {
        return sveRezervacije;
    }

    public void updateReservationStatuses(List<Rezervacija> rezervacije) {
        LocalDate today = LocalDate.now();
        for (Rezervacija rezervacija : rezervacije) {
            if (rezervacija.getStatusRezervacije() == Helper.StatusRezervacije.NaCekanju &&
                    rezervacija.getCheckInDatum().isBefore(today)) {
                rezervacija.setStatusRezervacije(Helper.StatusRezervacije.Odbijena);
                rezervacija.setCena(0);
                rezervacija.setDatumPromeneStatusa(today);
                rezervacija.setPromena(Helper.StatusRezervacije.Odbijena);
            }
        }

        try {
            UpisPodataka.upisiRezervacije("src/utils/rezervacije.csv", rezervacije);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Rezervacija> getRezervacijeZaOdredjeniPeriod(LocalDate datumOd, LocalDate datumDo,
            List<Rezervacija> sveRezervacije) {
        List<Rezervacija> rezervacijeZaPeriod = new ArrayList<>();
        for (Rezervacija rezervacija : sveRezervacije) {
            if (rezervacija.getCheckInDatum().isAfter(datumOd) && rezervacija.getCheckOutDatum().isBefore(datumDo)
                    && (rezervacija.getStatusRezervacije() == Helper.StatusRezervacije.Prosla
                            || rezervacija.getStatusRezervacije() == Helper.StatusRezervacije.Otkazana)) {
                rezervacijeZaPeriod.add(rezervacija);
            }
        }
        return rezervacijeZaPeriod;
    }

    public Double getPrihodiZaOdredjeniPeriod(List<Rezervacija> rezervZaPeriod) {
        Double prihodi = 0.0;
        for (Rezervacija rezervacija : rezervZaPeriod) {
            prihodi += rezervacija.getCena();
        }
        return prihodi;
    }

    public Double getRashodiZaOdredjeniPeriod(LocalDate datumOd, LocalDate datumDo, List<Korisnik> zaposleni) {
        // Rashodi su plate zaposlenih
        // Plate se dobijaju svakog prvog u mesecu
        Double rashodi = 0.0;
        for (Korisnik zaposlen : zaposleni) {
            if (zaposlen instanceof Zaposleni) {
                rashodi += brojPrvogUPeriodu(datumOd, datumDo) * ((Zaposleni) zaposlen).getPlata();

            } else {
                rashodi += 0;
            }

        }
        return rashodi;

    }

    private int brojPrvogUPeriodu(LocalDate datumOd, LocalDate datumDo) {
        int brojPrvog = 0;
        for (LocalDate date = datumOd; !date.isAfter(datumDo); date = date.plusDays(1)) {
            if (date.getDayOfMonth() == 1) {
                brojPrvog++;
            }
        }
        return brojPrvog;
        // vraca broj prvih dana u mesecu u periodu
    }

    public List<Rezervacija> getPromeneStatusaUPeriodu(LocalDate datumOd, LocalDate datumDo,
            List<Rezervacija> sveRezervacije) {
        List<Rezervacija> promeneStatusa = new ArrayList<>();
        for (Rezervacija rezervacija : sveRezervacije) {
            if (rezervacija.getDatumPromeneStatusa().isAfter(datumOd)
                    || rezervacija.getDatumPromeneStatusa().isEqual(datumOd)
                            && rezervacija.getDatumPromeneStatusa().isBefore(datumDo)
                    || rezervacija.getDatumPromeneStatusa().isEqual(datumDo)) {
                promeneStatusa.add(rezervacija);
            }
        }
        return promeneStatusa;
    }

}
