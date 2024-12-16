import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;

import javax.swing.JFrame;

import Model.DodatnaUsluga;
import Model.Korisnik;
import Model.Rezervacija;
import Model.Soba;
import Model.SobaZaSpremanje;
import Model.Sobarica;
import Model.Zaposleni;
import Model.Cenovnik;
import Utils.Helper;
import Utils.Helper.StatusRezervacije;
import Controller.SobeController;
import Controller.CenovnikController;
import Controller.RezervacijeController;
import Controller.SobariceController;

public class App {

    public static List<Korisnik> ucitajKorisnike(String filePath) throws Exception {
        List<Korisnik> korisniciList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    Korisnik korisnik;
                    if (data[4].equalsIgnoreCase("Gost")) {
                        korisnik = new Korisnik();
                        ((Korisnik) korisnik).setUloga(Helper.Uloga.valueOf(data[4]));
                    } else {
                        korisnik = new Zaposleni();
                        ((Zaposleni) korisnik).setUloga(Helper.Uloga.valueOf(data[4]));
                        ((Zaposleni) korisnik).setPlata(Integer.parseInt(data[5]));
                    }
                    korisnik.setKorisnickoIme(data[0]);
                    korisnik.setLozinka(data[1]);
                    korisnik.setIme(data[2]);
                    korisnik.setLastName(data[3]);
                    korisniciList.add(korisnik);
                }
            }
        }
        return korisniciList;
    }

    public static List<Soba> ucitajSobe(String filePath) throws Exception {
        List<Soba> sobeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Soba soba = new Soba();
                    soba.setBrojSobe(Integer.parseInt(data[0]));
                    soba.setStatusSobe(Helper.StatusSobe.valueOf(data[1]));
                    soba.setTipSobe(Helper.Usluge.valueOf(data[2]));
                    List<Helper.atributiSobe> atributiSobe = new ArrayList<>();
                    if (data[3].equals("null")) {
                        soba.setAtributiSobe(null);
                    } else {
                        String[] atributi = data[3].split(";");

                        for (String atribut : atributi) {
                            atributiSobe.add(Helper.atributiSobe.valueOf(atribut));
                        }
                        soba.setAtributiSobe(atributiSobe);

                    }
                    sobeList.add(soba);
                }
            }
        }
        return sobeList;
    }

    public static List<DodatnaUsluga> ucitajDodatneUsluge(String filePath) throws Exception {
        List<DodatnaUsluga> uslugeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 1) {
                    DodatnaUsluga usluga = new DodatnaUsluga();
                    usluga.setNaziv(data[0]);
                    uslugeList.add(usluga);
                }
            }
        }
        return uslugeList;
    }

    public static List<Cenovnik> ucitajCenovnik(String filePath) throws Exception {

        List<Cenovnik> cenovnikList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Cenovnik cenovnik = new Cenovnik();
                    HashMap<String, Double> cenovnikMap = new HashMap<>();
                    List<String> usluge = new ArrayList<>(Arrays.asList(data[0].split(";")));
                    for (String usluga : usluge) {

                        cenovnikMap.put(usluga.split(":")[0], Double.parseDouble(usluga.split(":")[1]));
                    }
                    cenovnik.setCenovnik(cenovnikMap);
                    cenovnik.setVaziOd(MonthDay.parse(data[1]));
                    cenovnik.setVaziDo(MonthDay.parse(data[2]));
                    cenovnikList.add(cenovnik);
                }
            }
        }
        return cenovnikList;

    }

    public static List<Rezervacija> ucitajRezervacije(String filepath) throws Exception {
        List<Rezervacija> rezervacijeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 11) {
                    Rezervacija rezervacija = new Rezervacija();

                    // Assuming you have a method to get Korisnik by email
                    rezervacija.setBrojRezervacije(Integer.parseInt(data[0]));

                    Korisnik gost = getKorisnikByEmail(data[1]);
                    rezervacija.setGost(gost);

                    rezervacija.setCheckInDatum(LocalDate.parse(data[2]));
                    rezervacija.setCheckOutDatum(LocalDate.parse(data[3]));
                    Helper.Usluge tipSobe = Helper.Usluge.valueOf(data[4]);
                    List<Soba> sobeList = ucitajSobe("src/utils/sobe.csv");
                    Soba soba = getSobaByBroj(sobeList, Integer.parseInt(data[5]));
                    rezervacija.setBrojSobe(soba);

                    rezervacija.setBrojGostiju(tipSobe); // Assuming a suitable constructor or method exists
                    rezervacija.setStatusRezervacije(Helper.StatusRezervacije.valueOf(data[6]));

                    // Assuming a method to parse dodatneUsluge
                    List<DodatnaUsluga> dodatneUsluge = parseDodatneUsluge(data[7]);
                    rezervacija.setDodatneUsluge(dodatneUsluge);

                    rezervacija.setCena(Double.parseDouble(data[8]));
                    rezervacija.setDatumPromeneStatusa(LocalDate.parse(data[9]));
                    rezervacija.setPromena(Helper.StatusRezervacije.valueOf(data[10]));

                    rezervacijeList.add(rezervacija);
                }
            }
        }
        return rezervacijeList;
    }

    // Example method to get Korisnik by email
    private static Korisnik getKorisnikByEmail(String email) {
        // Implement your logic to retrieve or create a Korisnik object by email
        Korisnik korisnik = new Korisnik();
        korisnik.setKorisnickoIme(email);
        // Set other properties of Korisnik as needed
        return korisnik;
    }

    // Example method to parse dodatneUsluge
    private static List<DodatnaUsluga> parseDodatneUsluge(String dodatneUslugeStr) {
        List<DodatnaUsluga> dodatneUsluge = new ArrayList<>();
        if (dodatneUslugeStr != null && !dodatneUslugeStr.isEmpty()) {
            List<String> dodatneUslugeStrList = Arrays.asList(dodatneUslugeStr.split(";"));
            for (String usluga : dodatneUslugeStrList) {
                // Assuming DodatnaUsluga has a constructor that accepts a String
                dodatneUsluge.add(new DodatnaUsluga(usluga));
            }
        }
        return dodatneUsluge;
    }

    private static Soba getSobaByBroj(List<Soba> sobeList, int brojSobe) {

        for (Soba s : sobeList) {
            if (s.getBrojSobe() == brojSobe) {
                return s;
            }

        }
        return null;
    }

    static void upisiKorisnike(String filePath, List<Korisnik> korisniciList) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Korisnik k : korisniciList) {
                bw.write(k.getKorisnickoIme() + "," + k.getLozinka() + "," + k.getIme() + "," + k.getLastName() + ","
                        + k.getUloga() + "," + ((k instanceof Zaposleni) ? ((Zaposleni) k).getPlata() : "0"));
                bw.newLine();
            }
        }
    }

    static void upisiRezervacije(String filepath, List<Rezervacija> rezervacijeList) throws Exception {
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

    static void upisiSobe(String filePath, List<Soba> sobeList) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Soba s : sobeList) {
                if (s.getAtributiSobe() == null) {
                    bw.write(s.getBrojSobe() + "," + s.getStatusSobe() + "," + s.getTipSobe() + "," + "null");
                    bw.newLine();
                } else {

                    bw.write(s.getBrojSobe() + "," + s.getStatusSobe() + "," + s.getTipSobe() + "," + String.join(";",
                            s.getAtributiSobe().toString().split("[\\[\\]]")[1].split(", ")));
                    bw.newLine();
                }
            }
        }
    }

    public static List<SobaZaSpremanje> ucitajSobeZaSpremanjeList(String filePath) throws Exception {
        List<SobaZaSpremanje> sobeZaSpremanjeList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String korisnicko = data[0];
                    LocalDate datum = LocalDate.parse(data[1], formatter);
                    int brojSobe = Integer.parseInt(data[2]);

                    SobaZaSpremanje sobaZaSpremanje = new SobaZaSpremanje(korisnicko, datum, brojSobe);
                    sobeZaSpremanjeList.add(sobaZaSpremanje);
                }
            }
        }
        return sobeZaSpremanjeList;
    }

    public static List<Sobarica> ucitajSobarice(List<Korisnik> korisniciList, List<Soba> sobeList,
            SobariceController sobariceController, List<SobaZaSpremanje> sobeZaSpremanjeList) throws Exception {
        List<Sobarica> sobarice = new ArrayList<>();
        for (Korisnik k : korisniciList) {
            if (k.getUloga() == Helper.Uloga.Sobarica) {
                Sobarica sobarica = new Sobarica();
                sobarica.setKorisnickoIme(k.getKorisnickoIme());
                sobarica.setLozinka(k.getLozinka());
                sobarica.setIme(k.getIme());
                sobarica.setLastName(k.getLastName());
                sobarica.setUloga(Helper.Uloga.Sobarica);
                List<Soba> sobeZS = sobariceController.getSobeZaSpremanje(sobarica.getKorisnickoIme(),
                        sobeZaSpremanjeList, sobeList);
                // sobeZS = getSobeByBroj(data[5], sobeList);
                sobarica.setSobeZaSpremanje(sobeZS);
                sobarice.add(sobarica);
            }
        }

        return sobarice;
    }

    // static private List<Soba> getSobeByBroj(String sobe, List<Soba> sobeList) {

    // List<Soba> sobeZaSpremanje = new ArrayList<>();
    // // Check if 'sobe' is null or empty
    // if (sobe.equals("null") || sobe.isEmpty()) {
    // return null;
    // }

    // // Split 'sobe' string and process
    // List<String> sobeZaSpremanjeStr = Arrays.asList(sobe.split(";"));

    // // Check if 'sobeZaSpremanjeStr' is empty
    // if (sobeZaSpremanjeStr.isEmpty()) {
    // return null;
    // }

    // else {
    // for (String s : sobeZaSpremanjeStr) {
    // Soba soba = new Soba();
    // soba.setBrojSobe(Integer.parseInt(s));
    // for (Soba s1 : sobeList) {
    // if (s1.getBrojSobe() == soba.getBrojSobe()) {
    // soba.setStatusSobe(s1.getStatusSobe());
    // soba.setTipSobe(s1.getTipSobe());
    // }
    // }
    // sobeZaSpremanje.add(soba);
    // }
    // return sobeZaSpremanje;
    // }

    // }

    static void upisiSobarice(String filePath, List<Sobarica> sobarice) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Sobarica s : sobarice) {
                List<String> sobeZaSpremanje = new ArrayList<>();
                if (s.getSobeZaSpremanje() == null) {
                    bw.write(
                            s.getKorisnickoIme() + "," + s.getLozinka() + "," + s.getIme() + "," + s.getLastName() + ","
                                    + s.getUloga() + "," + "null");
                    bw.newLine();

                } else {
                    for (Soba soba : s.getSobeZaSpremanje()) {
                        sobeZaSpremanje.add(String.valueOf(soba.getBrojSobe()));
                    }
                    String sobeZaSpremanjeStr = String.join(";", sobeZaSpremanje);

                    bw.write(
                            s.getKorisnickoIme() + "," + s.getLozinka() + "," + s.getIme() + "," + s.getLastName() + ","
                                    + s.getUloga() + "," + sobeZaSpremanjeStr);
                    bw.newLine();
                }

            }
        }
    }

    static void upisiDodatneUsluge(String filePath, List<DodatnaUsluga> uslugeList) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (DodatnaUsluga d : uslugeList) {
                bw.write(d.getNaziv());
                bw.newLine();
            }
        }
    }

    static void upisiCenovnik(String filePath, List<Cenovnik> cenovnikList) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Cenovnik c : cenovnikList) {
                HashMap<String, Double> cenovnik = c.getCenovnik();
                List<String> usluge = new ArrayList<>();
                for (String usluga : cenovnik.keySet()) {
                    usluge.add(usluga + ":" + cenovnik.get(usluga));
                }
                String uslugeStr = String.join(";", usluge);
                bw.write(uslugeStr + "," + c.getVaziOd() + "," + c.getVaziDo());
                bw.newLine();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SobeController sobeController = new SobeController();
        RezervacijeController rezervacijeController = new RezervacijeController();
        SobariceController sobariceController = new SobariceController();
        CenovnikController cenovnikController = new CenovnikController();
        List<Korisnik> korisniciList = ucitajKorisnike("src/utils/korisnici.csv");
        List<Soba> sobeList = ucitajSobe("src/utils/sobe.csv");
        List<DodatnaUsluga> uslugeList = ucitajDodatneUsluge("src/utils/dodatne_usluge.csv");
        List<Cenovnik> cenovnik = ucitajCenovnik("src/utils/cenovnik.csv");
        List<Rezervacija> rezervacijeList = ucitajRezervacije("src/utils/rezervacije.csv");
        // List<Sobarica> sobarice = ucitajSobarice("src/utils/sobarice.csv", sobeList);
        List<SobaZaSpremanje> sobeZaSpremanjeList = ucitajSobeZaSpremanjeList("src/utils/spremanje.csv");
        List<Sobarica> sobarice = ucitajSobarice(korisniciList, sobeList, sobariceController, sobeZaSpremanjeList);

        System.out.println("Korisnici: ");
        for (Korisnik k : korisniciList) {
            System.out.println(k);
        }

        System.out.println("Sobe: ");
        for (Soba s : sobeList) {
            System.out.println(s);
        }

        System.out.println("Dodatne usluge: ");
        for (DodatnaUsluga d : uslugeList) {
            System.out.println(d);
        }

        cenovnik = cenovnikController.sortirajCenovnike(cenovnik);

        System.out.println("Cenovnik: ");
        for (Cenovnik c : cenovnik) {
            System.out.println(c);
        }

        System.out.println("Rezervacije: ");
        for (Rezervacija r : rezervacijeList) {
            System.out.println(r);
            System.out.println("promena: " + r.getPromena());
        }
        upisiSobe("src/utils/sobe.csv", sobeList);
        JFrame frame = new JFrame("Hotel Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLayout(null);
        frame.setResizable(false);
        ImageIcon icon = new ImageIcon("hotelLogo.png");
        frame.setIconImage(icon.getImage());

        LoginPage loginPage = new LoginPage(frame, korisniciList, rezervacijeList, sobeList, uslugeList, cenovnik,
                sobarice, rezervacijeController, sobariceController, sobeZaSpremanjeList);
        loginPage.initialize(korisniciList, rezervacijeList, sobeList, uslugeList, cenovnik, sobeController,
                sobariceController, sobarice);

        frame.setVisible(true);

    }
}