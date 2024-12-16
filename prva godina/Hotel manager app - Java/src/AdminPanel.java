import javax.swing.*;

import Controller.RezervacijeController;
import Controller.SobariceController;
import Controller.SobeController;
import Model.DodatnaUsluga;
import Model.Korisnik;
import Model.Zaposleni;
import Utils.Helper;
import Utils.Helper.Uloga;
import Model.Rezervacija;
import Model.Soba;
import Model.SobaZaSpremanje;
import Model.DodatnaUsluga;
import Model.Cenovnik;
import Model.Zaposleni;
import Model.Sobarica;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AdminPanel implements ActionListener {
    private JTabbedPane taboviAdmin;
    private List<Korisnik> korisniciList;
    private List<Rezervacija> rezervacijeList;
    private List<Soba> sobeList;
    private List<DodatnaUsluga> dodatneUslugeList;
    private List<Cenovnik> cenovniciList;
    private RezervacijeController rezervacijeController;
    private SobeController sobeController;
    private List<SobaZaSpremanje> sobeZaSpremanjeList;

    public AdminPanel(JTabbedPane taboviAdmin, List<Korisnik> korisniciList, List<Rezervacija> rezervacijeList,
            List<Soba> sobeList, List<DodatnaUsluga> dodatneUslugeList, List<Cenovnik> cenovniciList,
            RezervacijeController rezervacijeController, SobeController sobeController,
            List<SobaZaSpremanje> sobeZaSpremanjeList) {
        this.taboviAdmin = taboviAdmin;
        this.korisniciList = korisniciList;
        this.rezervacijeList = rezervacijeList;
        this.sobeList = sobeList;
        this.dodatneUslugeList = dodatneUslugeList;
        this.cenovniciList = cenovniciList;
        this.rezervacijeController = rezervacijeController;
        this.sobeController = sobeController;
        this.sobeZaSpremanjeList = sobeZaSpremanjeList;
    }

    public void initialize() {
        JPanel panelZaposleni = createZaposleniPanel();
        JPanel panelSobe = createSobePanel();
        JPanel panelDU = createDodatneUslPanel();
        JPanel panelCenovnik = createCenovnikPanel();
        JPanel panelPregled = createPregledajPanel();
        JPanel panelIzvestaj = createIzvestajiPanel();
        JPanel panelChartovi = new JPanel();

        panelSobe.setBackground(new Color(166, 227, 215));
        panelDU.setBackground(new Color(166, 180, 227));
        panelCenovnik.setBackground(new Color(222, 182, 227));

        taboviAdmin.add("CRU korisika", panelZaposleni);
        taboviAdmin.add("Sobe", panelSobe);
        taboviAdmin.add("Dodatne Usluge", panelDU);
        taboviAdmin.add("Cenovnici", panelCenovnik);
        taboviAdmin.add("Pregledaj", panelPregled);
        taboviAdmin.add("Izvestaji", panelIzvestaj);
        taboviAdmin.add("Chartovi", panelChartovi);
    }

    private JPanel createZaposleniPanel() {

        JButton dodajZaposlenogButton = new JButton("Dodaj zaposlenog");

        JPanel panelZaposleni = new JPanel();
        panelZaposleni.setLayout(null);
        panelZaposleni.setBackground(new Color(255, 214, 237));

        JLabel dodajZaposlenog = new JLabel("Dodaj zaposlenog");
        dodajZaposlenog.setBounds(50, 50, 200, 25);
        dodajZaposlenog.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelZaposleni.add(dodajZaposlenog);

        JLabel korisnickoZaposleni = new JLabel("Korisnicko ime:");
        JLabel lozinkaZaposleni = new JLabel("Lozinka:");
        JLabel imeZaposleni = new JLabel("Ime:");
        JLabel prezimeZaposleni = new JLabel("Prezime:");
        JLabel ulogaZaposleni = new JLabel("Uloga:");
        JLabel plataZaposleni = new JLabel("Plata:");

        korisnickoZaposleni.setBounds(50, 160, 120, 25);
        lozinkaZaposleni.setBounds(50, 210, 120, 25);
        imeZaposleni.setBounds(50, 260, 120, 25);
        prezimeZaposleni.setBounds(50, 310, 120, 25);
        ulogaZaposleni.setBounds(50, 410, 120, 25);
        plataZaposleni.setBounds(50, 460, 120, 25);

        korisnickoZaposleni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lozinkaZaposleni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        imeZaposleni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        prezimeZaposleni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        ulogaZaposleni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        plataZaposleni.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JTextField korisnickoZaposleniField = new JTextField();
        JPasswordField lozinkaZaposleniField = new JPasswordField();
        JTextField imeZaposleniField = new JTextField();
        JTextField prezimeZaposleniField = new JTextField();
        JTextField ulogaZaposleniField = new JTextField();
        JTextField plataZaposleniField = new JTextField();

        korisnickoZaposleniField.setBounds(180, 160, 165, 25);
        lozinkaZaposleniField.setBounds(180, 210, 165, 25);
        imeZaposleniField.setBounds(180, 260, 165, 25);
        prezimeZaposleniField.setBounds(180, 310, 165, 25);
        ulogaZaposleniField.setBounds(180, 410, 165, 25);
        plataZaposleniField.setBounds(180, 460, 165, 25);

        panelZaposleni.add(korisnickoZaposleni);
        panelZaposleni.add(lozinkaZaposleni);
        panelZaposleni.add(imeZaposleni);
        panelZaposleni.add(prezimeZaposleni);
        panelZaposleni.add(ulogaZaposleni);
        panelZaposleni.add(korisnickoZaposleniField);
        panelZaposleni.add(lozinkaZaposleniField);
        panelZaposleni.add(imeZaposleniField);
        panelZaposleni.add(prezimeZaposleniField);
        panelZaposleni.add(ulogaZaposleniField);
        panelZaposleni.add(plataZaposleni);
        panelZaposleni.add(plataZaposleniField);

        dodajZaposlenogButton.setBounds(50, 500, 200, 25);
        dodajZaposlenogButton.setFocusable(false);
        panelZaposleni.add(dodajZaposlenogButton);
        dodajZaposlenogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String korisnicko = korisnickoZaposleniField.getText();
                String lozinka = String.valueOf(lozinkaZaposleniField.getPassword());
                String ime = imeZaposleniField.getText();
                String prezime = prezimeZaposleniField.getText();
                String uloga = ulogaZaposleniField.getText();

                Zaposleni noviZaposleni = new Zaposleni();
                noviZaposleni.setKorisnickoIme(korisnicko);
                noviZaposleni.setLozinka(lozinka);
                noviZaposleni.setIme(ime);
                noviZaposleni.setLastName(prezime);
                ((Zaposleni) noviZaposleni).setPlata(Integer.parseInt(plataZaposleniField.getText()));
                // Check if korisnicko is unique
                for (Korisnik korisnik : korisniciList) {
                    if (korisnik.getKorisnickoIme().equals(korisnicko)) {
                        korisnickoZaposleniField.setText("Korisnicko ime vec postoji!");
                        return; // Exit the actionPerformed method early if the username already exists
                    }
                }

                if (uloga.equals("Administrator") || uloga.equals("Recepcioner") || uloga.equals("Sobarica")) {
                    noviZaposleni.setUloga(Helper.Uloga.valueOf(uloga));
                } else {
                    ulogaZaposleniField.setText("Unesi validnu ulogu!");

                    return; // Exit the actionPerformed method early if the role is invalid
                }

                if (!noviZaposleni.getKorisnickoIme().isEmpty() && !noviZaposleni.getLozinka().isEmpty() &&
                        !noviZaposleni.getIme().isEmpty() && !noviZaposleni.getLastName().isEmpty() &&
                        noviZaposleni.getUloga() != null && !plataZaposleniField.getText().isEmpty()) {

                    korisniciList.add(noviZaposleni);

                    try {
                        App.upisiKorisnike("src/utils/korisnici.csv", korisniciList);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    // Clear the fields after successful addition
                    korisnickoZaposleniField.setText("");
                    lozinkaZaposleniField.setText("");
                    imeZaposleniField.setText("");
                    prezimeZaposleniField.setText("");
                    ulogaZaposleniField.setText("");

                    // Message for successful addition
                    JOptionPane.showMessageDialog(null, "Uspesno ste dodali zaposlenog!");

                } else {
                    // Clear only the invalid fields
                    if (noviZaposleni.getKorisnickoIme().isEmpty())
                        korisnickoZaposleniField.setText("");
                    if (noviZaposleni.getLozinka().isEmpty())
                        lozinkaZaposleniField.setText("");
                    if (noviZaposleni.getIme().isEmpty())
                        imeZaposleniField.setText("");
                    if (noviZaposleni.getLastName().isEmpty())
                        prezimeZaposleniField.setText("");
                    if (noviZaposleni.getUloga() == null)
                        ulogaZaposleniField.setText("");
                }
            }
        });

        JLabel izmeniZaposlenog = new JLabel("Izmeni korisnika");
        izmeniZaposlenog.setBounds(450, 50, 200, 25);
        izmeniZaposlenog.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelZaposleni.add(izmeniZaposlenog);

        JLabel korisnickoZaposleniIzmeni = new JLabel("Korisnicko ime:");
        JLabel lozinkaZaposleniIzmeni = new JLabel("Lozinka:");
        JLabel imeZaposleniIzmeni = new JLabel("Ime:");
        JLabel prezimeZaposleniIzmeni = new JLabel("Prezime:");
        JLabel ulogaZaposleniIzmeni = new JLabel("Uloga:");
        JLabel plataZaposleniIzmeni = new JLabel("Plata:");

        korisnickoZaposleniIzmeni.setBounds(450, 160, 120, 25);
        lozinkaZaposleniIzmeni.setBounds(450, 210, 120, 25);
        imeZaposleniIzmeni.setBounds(450, 260, 120, 25);
        prezimeZaposleniIzmeni.setBounds(450, 310, 120, 25);
        ulogaZaposleniIzmeni.setBounds(450, 410, 120, 25);
        plataZaposleniIzmeni.setBounds(450, 460, 120, 25);

        korisnickoZaposleniIzmeni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lozinkaZaposleniIzmeni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        imeZaposleniIzmeni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        prezimeZaposleniIzmeni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        ulogaZaposleniIzmeni.setFont(new Font("Times New Roman", Font.BOLD, 16));
        plataZaposleniIzmeni.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JComboBox<String> korisnickoZaposleniFieldIzmeni = new JComboBox<>();

        for (Korisnik korisnik : korisniciList) {
            if (korisnik instanceof Zaposleni) {
                korisnickoZaposleniFieldIzmeni.addItem(korisnik.getKorisnickoIme());
            }
        }

        // When korisnicko is chosen, fill the other fields with the data of the
        // selected user

        JPasswordField lozinkaZaposleniFieldIzmeni = new JPasswordField();

        JTextField imeZaposleniFieldIzmeni = new JTextField();
        JTextField prezimeZaposleniFieldIzmeni = new JTextField();
        JTextField ulogaZaposleniFieldIzmeni = new JTextField();
        JTextField plataZaposleniFieldIzmeni = new JTextField();

        korisnickoZaposleniFieldIzmeni.setBounds(580, 160, 165, 25);
        lozinkaZaposleniFieldIzmeni.setBounds(580, 210, 165, 25);
        imeZaposleniFieldIzmeni.setBounds(580, 260, 165, 25);
        prezimeZaposleniFieldIzmeni.setBounds(580, 310, 165, 25);
        ulogaZaposleniFieldIzmeni.setBounds(580, 410, 165, 25);
        plataZaposleniFieldIzmeni.setBounds(580, 460, 165, 25);

        korisnickoZaposleniFieldIzmeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String korisnicko = korisnickoZaposleniFieldIzmeni.getSelectedItem().toString();

                for (Korisnik korisnik : korisniciList) {
                    if (korisnik.getKorisnickoIme().equals(korisnicko)) {
                        Zaposleni zaposleni = (Zaposleni) korisnik;
                        lozinkaZaposleniFieldIzmeni.setText(zaposleni.getLozinka());
                        imeZaposleniFieldIzmeni.setText(zaposleni.getIme());
                        prezimeZaposleniFieldIzmeni.setText(zaposleni.getLastName());
                        ulogaZaposleniFieldIzmeni.setText(zaposleni.getUloga().toString());
                        plataZaposleniFieldIzmeni.setText(String.valueOf(zaposleni.getPlata()));
                        break;
                    }
                }
            }
        });

        panelZaposleni.add(korisnickoZaposleniIzmeni);
        panelZaposleni.add(lozinkaZaposleniIzmeni);
        panelZaposleni.add(imeZaposleniIzmeni);
        panelZaposleni.add(prezimeZaposleniIzmeni);
        panelZaposleni.add(ulogaZaposleniIzmeni);
        panelZaposleni.add(plataZaposleniIzmeni);

        panelZaposleni.add(korisnickoZaposleniFieldIzmeni);
        panelZaposleni.add(lozinkaZaposleniFieldIzmeni);
        panelZaposleni.add(imeZaposleniFieldIzmeni);
        panelZaposleni.add(prezimeZaposleniFieldIzmeni);
        panelZaposleni.add(ulogaZaposleniFieldIzmeni);
        panelZaposleni.add(plataZaposleniFieldIzmeni);

        JButton izmeniZaposlenogButton = new JButton("Izmeni korisnika");
        izmeniZaposlenogButton.setBounds(450, 500, 200, 25);
        izmeniZaposlenogButton.setFocusable(false);
        panelZaposleni.add(izmeniZaposlenogButton);

        izmeniZaposlenogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String korisnicko = korisnickoZaposleniFieldIzmeni.getSelectedItem().toString();
                String lozinka = String.valueOf(lozinkaZaposleniFieldIzmeni.getPassword());
                String ime = imeZaposleniFieldIzmeni.getText();
                String prezime = prezimeZaposleniFieldIzmeni.getText();
                String uloga = ulogaZaposleniFieldIzmeni.getText();
                String plata = plataZaposleniFieldIzmeni.getText();

                Zaposleni izmenjenZaposleni = new Zaposleni();
                izmenjenZaposleni.setKorisnickoIme(korisnicko);
                izmenjenZaposleni.setLozinka(lozinka);
                izmenjenZaposleni.setIme(ime);
                izmenjenZaposleni.setLastName(prezime);
                izmenjenZaposleni.setPlata(Integer.parseInt(plata));

                if (uloga.equals("Administrator") || uloga.equals("Recepcioner") || uloga.equals("Sobarica")) {
                    izmenjenZaposleni.setUloga(Helper.Uloga.valueOf(uloga));
                } else {
                    ulogaZaposleniFieldIzmeni.setText("Unesi validnu ulogu!");

                    return; // Exit the actionPerformed method early if the role is invalid
                }

                if (!izmenjenZaposleni.getKorisnickoIme().isEmpty() && !izmenjenZaposleni.getLozinka().isEmpty() &&
                        !izmenjenZaposleni.getIme().isEmpty() && !izmenjenZaposleni.getLastName().isEmpty() &&
                        izmenjenZaposleni.getUloga() != null && !plata.isEmpty()) {

                    for (Korisnik korisnik : korisniciList) {
                        if (korisnik.getKorisnickoIme().equals(izmenjenZaposleni.getKorisnickoIme())) {
                            korisniciList.remove(korisnik);
                            korisniciList.add(izmenjenZaposleni);
                            break;
                        }
                    }

                    try {
                        App.upisiKorisnike("src/utils/korisnici.csv", korisniciList);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    // Clear the fields after successful addition

                    korisnickoZaposleniFieldIzmeni.setSelectedIndex(0);
                    lozinkaZaposleniFieldIzmeni.setText("");
                    imeZaposleniFieldIzmeni.setText("");
                    prezimeZaposleniFieldIzmeni.setText("");
                    ulogaZaposleniFieldIzmeni.setText("");
                    plataZaposleniFieldIzmeni.setText("");

                    // Message for successful update
                    JOptionPane.showMessageDialog(null, "Uspesno ste izmenili zaposlenog!");

                } else {

                    // Clear only the invalid fields
                    if (izmenjenZaposleni.getKorisnickoIme().isEmpty())
                        korisnickoZaposleniFieldIzmeni.setSelectedIndex(0);
                    if (izmenjenZaposleni.getLozinka().isEmpty())
                        lozinkaZaposleniFieldIzmeni.setText("");
                    if (izmenjenZaposleni.getIme().isEmpty())
                        imeZaposleniFieldIzmeni.setText("");
                    if (izmenjenZaposleni.getLastName().isEmpty())
                        prezimeZaposleniFieldIzmeni.setText("");
                    if (izmenjenZaposleni.getUloga() == null)
                        ulogaZaposleniFieldIzmeni.setText("");
                }

            }

        });

        JLabel obrisiZaposlenog = new JLabel("Korisnicko:");
        obrisiZaposlenog.setBounds(50, 550, 200, 25);
        obrisiZaposlenog.setFont(new Font("Times New Roman", Font.BOLD, 15));
        panelZaposleni.add(obrisiZaposlenog);
        JComboBox<String> korisnickoZaposleniFieldObrisi = new JComboBox<>();

        for (Korisnik korisnik : korisniciList) {
            if (korisnik instanceof Zaposleni) {
                korisnickoZaposleniFieldObrisi.addItem(korisnik.getKorisnickoIme());
            }
        }

        korisnickoZaposleniFieldObrisi.setBounds(50, 580, 165, 25);
        panelZaposleni.add(korisnickoZaposleniFieldObrisi);

        JButton obrisiZaposlenogButton = new JButton("Obrisi korisnika");
        obrisiZaposlenogButton.setBounds(50, 610, 200, 25);
        obrisiZaposlenogButton.setFocusable(false);
        panelZaposleni.add(obrisiZaposlenogButton);

        obrisiZaposlenogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String korisnicko = korisnickoZaposleniFieldObrisi.getSelectedItem().toString();

                for (Korisnik korisnik : korisniciList) {
                    if (korisnik.getKorisnickoIme().equals(korisnicko)) {
                        korisniciList.remove(korisnik);
                        // Message for successful deletion
                        JOptionPane.showMessageDialog(null, "Uspesno ste obrisali zaposlenog!");
                        break;
                    }
                }

                try {
                    App.upisiKorisnike("src/utils/korisnici.csv", korisniciList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                korisnickoZaposleniFieldObrisi.setSelectedIndex(0);
            }
        });

        return panelZaposleni;
    }

    // Creating a table with all users

    private JFrame createKorisniciPanel() {
        JFrame korisniciFrame = new JFrame();
        korisniciFrame.setSize(800, 600);

        korisniciFrame.setLayout(null);
        korisniciFrame.setBackground(new Color(220, 166, 227));

        String[] columnNames = { "Korisnicko ime", "Ime", "Prezime", "Uloga", "Plata" };

        Object[][] data = new Object[korisniciList.size()][5];

        for (int i = 0; i < korisniciList.size(); i++) {
            data[i][0] = korisniciList.get(i).getKorisnickoIme();
            data[i][1] = korisniciList.get(i).getIme();
            data[i][2] = korisniciList.get(i).getLastName();
            data[i][3] = korisniciList.get(i).getUloga();
            if (korisniciList.get(i) instanceof Zaposleni) {
                data[i][4] = ((Zaposleni) korisniciList.get(i)).getPlata();
            } else {
                data[i][4] = "";
            }
        }

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        korisniciFrame.add(scrollPane, BorderLayout.CENTER);

        scrollPane.setBounds(50, 50, 700, 400);

        JButton osveziButton = new JButton("Osveži");
        osveziButton.setBounds(50, 500, 200, 25);
        osveziButton.setFocusable(false);
        korisniciFrame.add(osveziButton);

        osveziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                korisniciFrame.dispose();
                Frame prikazKorisnika = createKorisniciPanel();
                prikazKorisnika.setVisible(true);
            }
        });

        return korisniciFrame;
    }

    private JFrame createRezervacijePanel() {
        JFrame rezervacijeFrame = new JFrame();
        rezervacijeFrame.setSize(800, 600);
        rezervacijeFrame.setLayout(null);
        rezervacijeFrame.setBackground(Color.ORANGE);

        // Making a table with all reservations
        String[] columnNames = { "ID", "Check-in", "Check-out", "Status", "Tip sobe", "Dodatne usluge", "Cena" };

        Object[][] data = new Object[rezervacijeList.size()][7];

        for (int i = 0; i < rezervacijeList.size(); i++) {

            List<String> dodatneUsluge = new ArrayList<>();

            for (DodatnaUsluga usluga : rezervacijeList.get(i).getDodatneUsluge()) {
                dodatneUsluge.add(usluga.getNaziv());
            }

            String dodatneUslugeStr = String.join(";", dodatneUsluge);

            data[i][0] = rezervacijeList.get(i).getBrojRezervacije();
            data[i][1] = rezervacijeList.get(i).getCheckInDatum();
            data[i][2] = rezervacijeList.get(i).getCheckOutDatum();
            data[i][3] = rezervacijeList.get(i).getStatusRezervacije();
            data[i][4] = rezervacijeList.get(i).getBrojGostiju();
            data[i][5] = dodatneUslugeStr;
            data[i][6] = rezervacijeList.get(i).getCena();
        }

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        rezervacijeFrame.add(scrollPane);

        scrollPane.setBounds(50, 50, 700, 400);

        JButton osveziButton = new JButton("Osveži");
        osveziButton.setBounds(50, 500, 200, 25);
        osveziButton.setFocusable(false);
        rezervacijeFrame.add(osveziButton);

        osveziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rezervacijeFrame.dispose();
                JFrame prikazRezervacija = createRezervacijePanel();
                prikazRezervacija.setVisible(true);
            }
        });

        return rezervacijeFrame;
    }

    private JPanel createSobePanel() {
        JPanel panelSobe = new JPanel();
        panelSobe.setLayout(null);
        panelSobe.setBackground(Color.CYAN);

        JComboBox<String> sveSobeCB = new JComboBox<>();
        for (Soba soba : sobeList) {
            sveSobeCB.addItem(String.valueOf(soba.getBrojSobe()));
        }

        JLabel lblDodajSobu = new JLabel("Dodaj sobu");
        lblDodajSobu.setBounds(50, 50, 200, 25);
        lblDodajSobu.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelSobe.add(lblDodajSobu);

        JLabel lblBrojSobe = new JLabel("Broj sobe:");
        JLabel lblTipSobe = new JLabel("Tip sobe:");
        JLabel lblAtributiSobe = new JLabel("Atributi sobe:");

        lblBrojSobe.setBounds(50, 160, 120, 25);
        lblTipSobe.setBounds(50, 210, 120, 25);
        lblAtributiSobe.setBounds(50, 260, 120, 25);

        lblBrojSobe.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblTipSobe.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblAtributiSobe.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JTextField brojSobeField = new JTextField();
        JComboBox<String> tipSobeField = new JComboBox<>();
        Helper.atributiSobe[] atributiSobe = Helper.atributiSobe.values();
        JList<Helper.atributiSobe> atributiSobeList = new JList<>(atributiSobe);
        atributiSobeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        atributiSobeList.setLayoutOrientation(JList.VERTICAL);
        atributiSobeList.setVisibleRowCount(5);
        JScrollPane atributiSobeScrollPane = new JScrollPane(atributiSobeList);
        atributiSobeScrollPane.setBounds(180, 260, 165, 100);

        for (Helper.Usluge usluga : Helper.Usluge.values()) {
            tipSobeField.addItem(usluga.toString());
        }

        brojSobeField.setBounds(180, 160, 165, 25);
        tipSobeField.setBounds(180, 210, 165, 25);

        panelSobe.add(lblBrojSobe);
        panelSobe.add(lblTipSobe);
        panelSobe.add(brojSobeField);
        panelSobe.add(tipSobeField);
        panelSobe.add(lblAtributiSobe);
        panelSobe.add(atributiSobeScrollPane);

        JButton dodajSobuButton = new JButton("Dodaj sobu");
        dodajSobuButton.setBounds(50, 400, 200, 25);
        dodajSobuButton.setFocusable(false);
        panelSobe.add(dodajSobuButton);

        dodajSobuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String brojSobe = brojSobeField.getText();
                String tipSobe = tipSobeField.getSelectedItem().toString();
                List<Helper.atributiSobe> atributiSobe = atributiSobeList.getSelectedValuesList();

                if (!brojSobe.isEmpty() && !tipSobe.isEmpty() && brojSobe.matches("[0-9]+")) {

                    // Parsing the room number to an integer
                    int brojSobeInt = Integer.parseInt(brojSobe);

                    // Is the room number taken?
                    for (Soba soba : sobeList) {
                        if (soba.getBrojSobe() == brojSobeInt) {
                            JOptionPane.showMessageDialog(null, "Soba sa tim brojem vec postoji!");
                            brojSobeField.setText("");
                            tipSobeField.setSelectedIndex(0);

                            return;
                        }
                    }

                    // Create a new room
                    Soba novaSoba = new Soba();
                    novaSoba.setBrojSobe(brojSobeInt);
                    novaSoba.setTipSobe(Helper.Usluge.valueOf(tipSobe));
                    novaSoba.setStatusSobe(Utils.Helper.StatusSobe.Slobodna);
                    if (atributiSobe.isEmpty()) {
                        atributiSobe = null;
                    } else {
                        novaSoba.setAtributiSobe(atributiSobe);
                    }
                    // Add it to the list of rooms
                    sobeList.add(novaSoba);

                    // Write the list of rooms to the file
                    try {
                        App.upisiSobe("src/utils/sobe.csv", sobeList);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    // Update the combo box for izmeniSobu
                    List<Integer> tempSveSobeCB = new ArrayList<>();
                    for (Soba soba : sobeList) {
                        tempSveSobeCB.add(soba.getBrojSobe());
                    }
                    sveSobeCB.removeAllItems();
                    for (Integer broj : tempSveSobeCB) {
                        sveSobeCB.addItem(String.valueOf(broj));
                    }

                    // Clear the fields after successful addition
                    brojSobeField.setText("");
                    tipSobeField.setSelectedIndex(0);

                    // Message for successful addition
                    JOptionPane.showMessageDialog(null, "Uspesno ste dodali sobu!");

                } else {
                    // Clear only the invalid fields
                    if (brojSobe.isEmpty())
                        brojSobeField.setText("");
                    if (tipSobe.isEmpty())
                        tipSobeField.setSelectedIndex(0);
                }
            }
        });

        JLabel lblIzmeniObrisiSobu = new JLabel("Izmeni ili obriši sobu");
        lblIzmeniObrisiSobu.setBounds(450, 50, 200, 25);
        lblIzmeniObrisiSobu.setFont(new Font("Times New Roman", Font.BOLD, 20));

        panelSobe.add(lblIzmeniObrisiSobu);

        JLabel lblBrojSobeIzmeniObrisi = new JLabel("Broj sobe:");
        JLabel lblTipSobeIzmeniObrisi = new JLabel("Tip sobe:");

        lblBrojSobeIzmeniObrisi.setBounds(450, 160, 120, 25);
        lblTipSobeIzmeniObrisi.setBounds(450, 210, 120, 25);

        lblBrojSobeIzmeniObrisi.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblTipSobeIzmeniObrisi.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JComboBox<String> brojSobeFieldIzmeniObrisi = sveSobeCB;

        JComboBox<String> tipSobeFieldIzmeniObrisi = new JComboBox<>();
        for (Helper.Usluge usluga : Helper.Usluge.values()) {
            tipSobeFieldIzmeniObrisi.addItem(usluga.toString());
        }

        brojSobeFieldIzmeniObrisi.setBounds(580, 160, 165, 25);
        tipSobeFieldIzmeniObrisi.setBounds(580, 210, 165, 25);

        panelSobe.add(lblBrojSobeIzmeniObrisi);
        panelSobe.add(lblTipSobeIzmeniObrisi);
        panelSobe.add(brojSobeFieldIzmeniObrisi);
        panelSobe.add(tipSobeFieldIzmeniObrisi);

        JButton izmeniSobuButton = new JButton("Izmeni sobu");
        izmeniSobuButton.setBounds(450, 260, 200, 25);
        izmeniSobuButton.setFocusable(false);
        panelSobe.add(izmeniSobuButton);

        JButton obrisiSobuButton = new JButton("Obriši sobu");
        obrisiSobuButton.setBounds(450, 310, 200, 25);
        obrisiSobuButton.setFocusable(false);
        panelSobe.add(obrisiSobuButton);

        izmeniSobuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String brojSobe = brojSobeFieldIzmeniObrisi.getSelectedItem().toString();
                String tipSobe = tipSobeFieldIzmeniObrisi.getSelectedItem().toString();

                if (!brojSobe.isEmpty() && !tipSobe.isEmpty() && brojSobe.matches("[0-9]+")) {

                    // Parsing the room number to an integer
                    int brojSobeInt = Integer.parseInt(brojSobe);

                    // Is the room number taken?
                    for (Soba soba : sobeList) {
                        if (soba.getBrojSobe() == brojSobeInt) {
                            soba.setTipSobe(Helper.Usluge.valueOf(tipSobe));
                            break;
                        }
                    }

                    // Write the list of rooms to the file
                    try {
                        App.upisiSobe("src/utils/sobe.csv", sobeList);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    // Update the combo box for izmeniSobu
                    List<Integer> tempSveSobeCB = new ArrayList<>();
                    for (Soba soba : sobeList) {
                        tempSveSobeCB.add(soba.getBrojSobe());
                    }
                    sveSobeCB.removeAllItems();
                    for (Integer broj : tempSveSobeCB) {
                        sveSobeCB.addItem(String.valueOf(broj));
                    }

                    // Clear the fields after successful addition
                    brojSobeFieldIzmeniObrisi.setSelectedIndex(0);
                    tipSobeFieldIzmeniObrisi.setSelectedIndex(0);

                    // Message for successful addition
                    JOptionPane.showMessageDialog(null, "Uspesno ste izmenili sobu!");

                } else {
                    // Clear only the invalid fields
                    if (brojSobe.isEmpty())
                        brojSobeFieldIzmeniObrisi.setSelectedIndex(0);
                    if (tipSobe.isEmpty())
                        tipSobeFieldIzmeniObrisi.setSelectedIndex(0);
                }
            }
        });

        obrisiSobuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String brojSobe = brojSobeFieldIzmeniObrisi.getSelectedItem().toString();

                if (!brojSobe.isEmpty() && brojSobe.matches("[0-9]+")) {

                    // Parsing the room number to an integer
                    int brojSobeInt = Integer.parseInt(brojSobe);

                    // Is the room number taken?
                    for (Soba soba : sobeList) {
                        if (soba.getBrojSobe() == brojSobeInt) {
                            sobeList.remove(soba);
                            break;
                        }
                    }

                    // Write the list of rooms to the file
                    try {
                        App.upisiSobe("src/utils/sobe.csv", sobeList);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    // Update the combo box for izmeniSobu
                    List<Integer> tempSveSobeCB = new ArrayList<>();
                    for (Soba soba : sobeList) {
                        tempSveSobeCB.add(soba.getBrojSobe());
                    }
                    sveSobeCB.removeAllItems();
                    for (Integer broj : tempSveSobeCB) {
                        sveSobeCB.addItem(String.valueOf(broj));
                    }

                    // Clear the fields after successful addition
                    brojSobeFieldIzmeniObrisi.setSelectedIndex(0);
                    tipSobeFieldIzmeniObrisi.setSelectedIndex(0);

                    // Message for successful addition
                    JOptionPane.showMessageDialog(null, "Uspesno ste obrisali sobu!");

                } else {
                    // Clear only the invalid fields
                    if (brojSobe.isEmpty())
                        brojSobeFieldIzmeniObrisi.setSelectedIndex(0);
                }
            }
        });

        return panelSobe;
    }

    private JPanel createDodatneUslPanel() {
        JPanel panelDU = new JPanel();
        panelDU.setLayout(null);
        panelDU.setBackground(new Color(166, 180, 227));

        JLabel lblDodajDU = new JLabel("Dodaj dodatnu uslugu");
        lblDodajDU.setBounds(50, 50, 250, 25);
        lblDodajDU.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelDU.add(lblDodajDU);

        JLabel lblNazivDU = new JLabel("Naziv usluge:");
        JLabel lblCenaDU = new JLabel("Osnovna cena usluge:");

        lblNazivDU.setBounds(50, 160, 120, 25);
        lblCenaDU.setBounds(50, 210, 200, 25);

        lblNazivDU.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblCenaDU.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JTextField nazivDUField = new JTextField();
        JTextField cenaDUField = new JTextField();

        nazivDUField.setBounds(220, 160, 165, 25);
        cenaDUField.setBounds(220, 210, 165, 25);

        panelDU.add(lblNazivDU);
        panelDU.add(lblCenaDU);
        panelDU.add(nazivDUField);
        panelDU.add(cenaDUField);

        JButton dodajDUButton = new JButton("Dodaj uslugu");
        dodajDUButton.setBounds(50, 260, 200, 25);
        dodajDUButton.setFocusable(false);

        panelDU.add(dodajDUButton);

        dodajDUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazivDU = nazivDUField.getText();
                String cenaDU = cenaDUField.getText();

                if (!nazivDU.isEmpty() && !cenaDU.isEmpty() && cenaDU.matches("[0-9]+")) {

                    // Parsing the room number to an integer
                    int cenaDUInt = Integer.parseInt(cenaDU);

                    DodatnaUsluga novaDU = new DodatnaUsluga();
                    novaDU.setNaziv(nazivDU);
                    boolean postoji = false;
                    // Does it already exist?
                    for (DodatnaUsluga usluga : dodatneUslugeList) {
                        if (usluga.getNaziv().equals(nazivDU)) {
                            JOptionPane.showMessageDialog(null, "Usluga sa tim nazivom vec postoji!");
                            nazivDUField.setText("");
                            cenaDUField.setText("");

                            JOptionPane.showMessageDialog(null, "Usluga ce biti samo izmenjena!");
                            postoji = true;
                        }
                    }
                    if (!postoji) {
                        dodatneUslugeList.add(novaDU);

                        // write dodatne usluga to file
                        try {
                            App.upisiDodatneUsluge("src/utils/dodatne_usluge.csv", dodatneUslugeList);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                    }

                    // We are going to make a mathematical function, we go through every cenovni,
                    // for first item we set change to 0&, then we compare the first item of second
                    // cenovnik with first item of first we see for how much % its bigger or smaller
                    // and we set that change to the second item of the first cenovnik, then we
                    // compare the second item of the second cenovnik with the second item of the
                    // first cenovnik and we set the change to the second item of the first cenovnik
                    // and so on
                    // We do this for every item in the cenovnik
                    // We do this for every cenovnik

                    List<Double> promene = new ArrayList<>();
                    promene.add(0.0);

                    if (cenovniciList.size() < 2) {
                        System.out.println("Not enough cenovniks to calculate changes.");
                        return;
                    }

                    for (int i = 1; i < cenovniciList.size(); i++) {
                        Cenovnik previous = cenovniciList.get(1 - 1);
                        Cenovnik current = cenovniciList.get(i);

                        HashMap<String, Double> previousPrices = previous.getCenovnik();
                        HashMap<String, Double> currentPrices = current.getCenovnik();

                        System.out.println("Changes from Cenovnik " + (1 - 1) + " to Cenovnik " + i + ":");

                        String keyPrev = previousPrices.keySet().toArray()[0].toString();
                        String keyCurr = currentPrices.keySet().toArray()[0].toString();

                        double previousPrice = previousPrices.get(keyPrev);
                        double currentPrice = currentPrices.get(keyCurr);

                        double change = previousPrice / currentPrice;

                        promene.add(change);

                    }

                    // adding dodatna usluga and price to every cenovnik

                    for (int i = 0; i < cenovniciList.size(); i++) {
                        Cenovnik cenovnik = cenovniciList.get(i);
                        Double promeneDouble = Double.valueOf(promene.get(i));
                        double doublecenaDUInt = Double.valueOf(cenaDUInt);
                        if (i == 0) {
                            cenovnik.getCenovnik().put(novaDU.getNaziv(), doublecenaDUInt);
                            continue;
                        }
                        Double cenaOneDecimal = cenaDUInt / promeneDouble;
                        cenaOneDecimal = Math.round(cenaOneDecimal * 10) / 10.0;
                        cenovnik.getCenovnik().put(novaDU.getNaziv(), cenaOneDecimal);
                    }
                    JOptionPane.showMessageDialog(null, "Usluga je dodata u sve cenovnike!");
                    // write cenovnik to file
                    try {
                        App.upisiCenovnik("src/utils/cenovnik.csv", cenovniciList);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                }
            }
        });

        return panelDU;

    }

    private JPanel createCenovnikPanel() {
        JPanel panelCenovnik = new JPanel();
        panelCenovnik.setLayout(null);
        panelCenovnik.setBackground(new Color(166, 227, 180));
        JComboBox<String> cenovniciCB = new JComboBox<>();

        JLabel lblDodajCenovnik = new JLabel("Dodaj cenovnik");
        lblDodajCenovnik.setBounds(50, 50, 250, 25);
        lblDodajCenovnik.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelCenovnik.add(lblDodajCenovnik);

        JLabel lblVaziOd = new JLabel("Vazi od (--MM-DD):");
        JLabel lblVaziDo = new JLabel("Vazi do (--MM-DD):");
        // Pogledamo sta je prva cena u cenovnicima
        Cenovnik prviCenovnik = cenovniciList.get(0);
        String prviKey = prviCenovnik.getCenovnik().keySet().toArray()[0].toString();

        JLabel lblCenaDorucka = new JLabel("Cena " + prviKey + ":");

        lblVaziOd.setBounds(50, 160, 200, 25);
        lblVaziDo.setBounds(50, 210, 200, 25);
        lblCenaDorucka.setBounds(50, 260, 200, 25);

        lblVaziOd.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblVaziDo.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblCenaDorucka.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JTextField vaziOdField = new JTextField();
        JTextField vaziDoField = new JTextField();
        JTextField cenaDoruckaField = new JTextField();

        vaziOdField.setBounds(260, 160, 165, 25);
        vaziDoField.setBounds(260, 210, 165, 25);
        cenaDoruckaField.setBounds(260, 260, 165, 25);

        panelCenovnik.add(lblVaziOd);
        panelCenovnik.add(lblVaziDo);
        panelCenovnik.add(lblCenaDorucka);
        panelCenovnik.add(vaziOdField);
        panelCenovnik.add(vaziDoField);
        panelCenovnik.add(cenaDoruckaField);

        JButton dodajCenovnikButton = new JButton("Dodaj cenovnik");
        dodajCenovnikButton.setBounds(50, 310, 200, 25);
        dodajCenovnikButton.setFocusable(false);

        panelCenovnik.add(dodajCenovnikButton);

        dodajCenovnikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vaziOd = vaziOdField.getText();
                String vaziDo = vaziDoField.getText();
                String cenaDorucka = cenaDoruckaField.getText();

                if (!vaziOd.isEmpty() && !vaziDo.isEmpty() && !cenaDorucka.isEmpty() && cenaDorucka.matches("[0-9]+")) {

                    int cenaDoruckaInt = Integer.parseInt(cenaDorucka);

                    // we need to check if dates overlap with existing cenovniks
                    MonthDay newVaziOd = MonthDay.parse(vaziOd, DateTimeFormatter.ofPattern("--MM-dd"));
                    MonthDay newVaziDo = MonthDay.parse(vaziDo, DateTimeFormatter.ofPattern("--MM-dd"));

                    if (isDateOverlap(newVaziOd, newVaziDo, cenovniciList)) {
                        JOptionPane.showMessageDialog(null, "Cenovnik sa tim datumima vec postoji!");
                        vaziOdField.setText("");
                        vaziDoField.setText("");
                        cenaDoruckaField.setText("");
                        return;
                    }
                    Cenovnik noviCenovnik = new Cenovnik();
                    noviCenovnik.setVaziOd(newVaziOd);
                    noviCenovnik.setVaziDo(newVaziDo);
                    // We calculate other prices based on the first price
                    HashMap<String, Double> cenovnik = new HashMap<>();

                    Cenovnik prviCenovnik = cenovniciList.get(0);
                    double prvaCena = prviCenovnik.getCenovnik().get(prviCenovnik.getCenovnik().keySet().toArray()[0]);
                    double razlika = prvaCena / cenaDoruckaInt;
                    // for item in haSHMAP
                    for (String keyS : prviCenovnik.getCenovnik().keySet()) {
                        Double cenaIzPrvog = prviCenovnik.getCenovnik().get(keyS);
                        Double novaCena = cenaIzPrvog / razlika;
                        Double cenaOneDecimal = Math.round(novaCena * 10) / 10.0;
                        cenovnik.put(keyS, cenaOneDecimal);

                    }
                    noviCenovnik.setCenovnik(cenovnik);
                    cenovniciList.add(noviCenovnik);

                    try {
                        App.upisiCenovnik("src/utils/cenovnik.csv", cenovniciList);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    vaziOdField.setText("");
                    vaziDoField.setText("");
                    cenaDoruckaField.setText("");

                    JOptionPane.showMessageDialog(null, "Uspesno ste dodali cenovnik!");

                    // Update ComboBox
                    cenovniciCB.removeAllItems();
                    for (Cenovnik c : cenovniciList) {
                        cenovniciCB.addItem(c.getVaziOd().toString() + " - " + c.getVaziDo().toString());
                    }

                }
            }
        });

        JLabel lblObrisiCenovnik = new JLabel("Obrisi cenovnik");
        lblObrisiCenovnik.setBounds(450, 50, 200, 25);
        lblObrisiCenovnik.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelCenovnik.add(lblObrisiCenovnik);

        for (Cenovnik cenovnik : cenovniciList) {
            cenovniciCB.addItem(cenovnik.getVaziOd().toString() + " - " + cenovnik.getVaziDo().toString());
        }

        cenovniciCB.setBounds(450, 100, 200, 25);
        panelCenovnik.add(cenovniciCB);

        JButton obrisiCenovnikButton = new JButton("Obrisi cenovnik");
        obrisiCenovnikButton.setBounds(450, 150, 200, 25);
        obrisiCenovnikButton.setFocusable(false);
        panelCenovnik.add(obrisiCenovnikButton);

        obrisiCenovnikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vaziOd = cenovniciCB.getSelectedItem().toString().split(" - ")[0];
                String vaziDo = cenovniciCB.getSelectedItem().toString().split(" - ")[1];

                MonthDay vaziOdMD = MonthDay.parse(vaziOd, DateTimeFormatter.ofPattern("--MM-dd"));
                MonthDay vaziDoMD = MonthDay.parse(vaziDo, DateTimeFormatter.ofPattern("--MM-dd"));

                for (Cenovnik cenovnik : cenovniciList) {
                    if (cenovnik.getVaziOd().equals(vaziOdMD) && cenovnik.getVaziDo().equals(vaziDoMD)) {
                        cenovniciList.remove(cenovnik);
                        break;
                    }
                }

                // Update ComboBox
                cenovniciCB.removeAllItems();
                for (Cenovnik cenovnik : cenovniciList) {
                    cenovniciCB.addItem(cenovnik.getVaziOd().toString() + " - " + cenovnik.getVaziDo().toString());
                }

                JOptionPane.showMessageDialog(null, "Uspesno ste obrisali cenovnik!");

                try {
                    App.upisiCenovnik("src/utils/cenovnik.csv", cenovniciList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                cenovniciCB.setSelectedIndex(0);
            }
        });

        return panelCenovnik;
    }

    private JPanel createIzvestajiPanel() {
        // like pregledaj panel
        JPanel panelIzvestaji = new JPanel();
        panelIzvestaji.setLayout(null);
        panelIzvestaji.setBackground(Color.PINK);

        JLabel lblIzvestaji = new JLabel("Odaberite opciju za izvestaj");
        lblIzvestaji.setBounds(50, 50, 300, 25);
        lblIzvestaji.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelIzvestaji.add(lblIzvestaji);

        JComboBox<String> opcije = new JComboBox<>();
        opcije.addItem("Prihodi i rashodi");
        opcije.addItem("Statusi rezervacija");
        opcije.addItem("Broj spremljenih soba");
        opcije.addItem("Informacije o sobama");

        opcije.setBounds(50, 100, 200, 25);
        panelIzvestaji.add(opcije);

        JButton prikaziButton = new JButton("Prikazi");
        prikaziButton.setBounds(50, 150, 200, 25);
        prikaziButton.setFocusable(false);
        panelIzvestaji.add(prikaziButton);

        prikaziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcija = opcije.getSelectedItem().toString();
                if (opcija.equals("Prihodi i rashodi")) {
                    JFrame prihodiRashodiFrame = prihodiRashodiFrame();
                    prihodiRashodiFrame.setVisible(true);
                } else if (opcija.equals("Statusi rezervacija")) {
                    JFrame brojStatusa = createRezervacijeIzvestajiFrame();
                    brojStatusa.setVisible(true);
                }

                else if (opcija.equals("Broj spremljenih soba")) {
                    JFrame brojSpremnjenihFrame = createSobariceIzvestajiFrame();
                    brojSpremnjenihFrame.setVisible(true);
                } else if (opcija.equals("Informacije o sobama")) {
                    JFrame informacijeOSobamaFrame = createSobeIzvestajiFrame();
                    informacijeOSobamaFrame.setVisible(true);
                }

            }
        });

        return panelIzvestaji;

    }

    private JFrame prihodiRashodiFrame() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setBackground(Color.CYAN);

        // Picking start and end date for calculation
        JLabel startDate = new JLabel("Start date (YYYY-MM-DD):");
        JLabel endDate = new JLabel("End date (YYYY-MM-DD):");
        startDate.setBounds(50, 50, 200, 25);
        endDate.setBounds(50, 100, 200, 25);
        frame.add(startDate);
        frame.add(endDate);

        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        startDateField.setBounds(250, 50, 200, 25);
        endDateField.setBounds(250, 100, 200, 25);
        frame.add(startDateField);
        frame.add(endDateField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(50, 150, 200, 25);
        calculateButton.setFocusable(false);
        frame.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startDateField.getText();
                String end = endDateField.getText();

                if (start.isEmpty() || end.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both dates.");
                    return;
                }

                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate = LocalDate.parse(end);
                List<Rezervacija> rezervacijeZaPeriod = rezervacijeController.getRezervacijeZaOdredjeniPeriod(startDate,
                        endDate, rezervacijeList);

                double prihodi = rezervacijeController.getPrihodiZaOdredjeniPeriod(rezervacijeZaPeriod);
                double rashodi = rezervacijeController.getRashodiZaOdredjeniPeriod(startDate, endDate, korisniciList);

                // Making a table with all valid reservations
                // first label is for prihodi, second for rashodi

                JLabel lblPrihodi = new JLabel("Prihodi: " + prihodi);
                JLabel lblRashodi = new JLabel("Rashodi: " + rashodi);

                lblPrihodi.setBounds(50, 200, 200, 25);
                lblRashodi.setBounds(400, 200, 200, 25);

                frame.add(lblPrihodi);
                frame.add(lblRashodi);

                // Making a table with all valid reservations under prihodi only
                String[] columnNames = { "Broj rezervacije", "Cena" };
                Object[][] data = new Object[rezervacijeZaPeriod.size()][2];

                for (int i = 0; i < rezervacijeZaPeriod.size(); i++) {
                    data[i][0] = rezervacijeZaPeriod.get(i).getBrojRezervacije();
                    data[i][1] = rezervacijeZaPeriod.get(i).getCena();
                }

                JTable table = new JTable(data, columnNames);
                table.setPreferredScrollableViewportSize(new Dimension(200, 70));
                table.setFillsViewportHeight(true);

                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);

                scrollPane.setBounds(50, 300, 300, 200);

                // Making a table with all korisnici that are instance of zaposleni under
                // rashodi only
                String[] columnNamesRashodi = { "Ime", "Prezime", "Plata" };
                List<Korisnik> zaposleni = new ArrayList<>();
                for (Korisnik korisnik : korisniciList) {
                    if (korisnik instanceof Zaposleni) {
                        zaposleni.add(korisnik);
                    }
                }
                Object[][] dataRashodi = new Object[zaposleni.size()][3];

                for (int i = 0; i < zaposleni.size(); i++) {
                    dataRashodi[i][0] = zaposleni.get(i).getIme();
                    dataRashodi[i][1] = zaposleni.get(i).getLastName();
                    dataRashodi[i][2] = ((Zaposleni) zaposleni.get(i)).getPlata();
                }

                JTable tableRashodi = new JTable(dataRashodi, columnNamesRashodi);
                tableRashodi.setPreferredScrollableViewportSize(new Dimension(200, 70));
                tableRashodi.setFillsViewportHeight(true);

                JScrollPane scrollPaneRashodi = new JScrollPane(tableRashodi);
                frame.add(scrollPaneRashodi);

                scrollPaneRashodi.setBounds(400, 300, 300, 200);

                frame.revalidate();
                frame.repaint();

            }
        });

        return frame;
    }

    private JFrame createRezervacijeIzvestajiFrame() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setBackground(Color.CYAN);

        // First we ask for the period
        JLabel startDate = new JLabel("Pocetak perioda (YYYY-MM-DD):");
        JLabel endDate = new JLabel("Kraj perioda (YYYY-MM-DD):");
        startDate.setBounds(50, 50, 200, 25);
        endDate.setBounds(50, 100, 200, 25);
        frame.add(startDate);
        frame.add(endDate);

        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        startDateField.setBounds(250, 50, 200, 25);

        endDateField.setBounds(250, 100, 200, 25);
        frame.add(startDateField);
        frame.add(endDateField);

        JButton calculateButton = new JButton("Izracunaj");
        calculateButton.setBounds(50, 150, 200, 25);
        calculateButton.setFocusable(false);
        frame.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startDateField.getText();
                String end = endDateField.getText();

                if (start.isEmpty() || end.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Molimo unesite oba datuma.");
                    return;
                }

                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate = LocalDate.parse(end);
                List<Rezervacija> rezervacijeZaPeriod = rezervacijeController.getPromeneStatusaUPeriodu(startDate,
                        endDate, rezervacijeList);
                System.out.println(rezervacijeZaPeriod.size());
                // Making a table with all valid reservations
                String[] columnNames = { "Status rezervacije", "Ukupno za period" };
                Object[][] data = new Object[3][2];
                int potvrdjenih = 0;
                int otkazanih = 0;
                int odbijenih = 0;
                for (int i = 0; i < rezervacijeZaPeriod.size(); i++) {

                    if (rezervacijeZaPeriod.get(i).getPromena().equals(Helper.StatusRezervacije.Potvrdjena)) {
                        potvrdjenih++;
                    } else if (rezervacijeZaPeriod.get(i).getPromena()
                            .equals(Helper.StatusRezervacije.Otkazana)) {
                        otkazanih++;
                    } else if (rezervacijeZaPeriod.get(i).getPromena()
                            .equals(Helper.StatusRezervacije.Odbijena)) {
                        odbijenih++;
                    }
                }
                data[0][0] = "Potvrdjene";
                data[0][1] = potvrdjenih;
                data[1][0] = "Otkazane";
                data[1][1] = otkazanih;
                data[2][0] = "Odbijene";
                data[2][1] = odbijenih;

                JTable table = new JTable(data, columnNames);
                table.setPreferredScrollableViewportSize(new Dimension(500, 70));
                table.setFillsViewportHeight(true);

                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);

                scrollPane.setBounds(50, 200, 700, 300);

                frame.revalidate();
                frame.repaint();

            }
        });

        return frame;
    }

    private JFrame createSobeIzvestajiFrame() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setBackground(Color.CYAN);

        // First we ask for the period
        JLabel startDate = new JLabel("Pocetak perioda (YYYY-MM-DD):");
        JLabel endDate = new JLabel("Kraj perioda (YYYY-MM-DD):");
        startDate.setBounds(50, 50, 200, 25);
        endDate.setBounds(50, 100, 200, 25);
        frame.add(startDate);
        frame.add(endDate);

        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        startDateField.setBounds(250, 50, 200, 25);

        endDateField.setBounds(250, 100, 200, 25);
        frame.add(startDateField);
        frame.add(endDateField);

        JButton calculateButton = new JButton("Izracunaj");
        calculateButton.setBounds(50, 150, 200, 25);
        calculateButton.setFocusable(false);
        frame.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startDateField.getText();
                String end = endDateField.getText();

                if (start.isEmpty() || end.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Molimo unesite oba datuma.");
                    return;
                }
                // Table for every room

                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate = LocalDate.parse(end);
                List<Rezervacija> rezervacijeZaPeriod = rezervacijeController.getPromeneStatusaUPeriodu(startDate,
                        endDate, rezervacijeList);

                // columnnames : broj sobe, tip sobe, prihod
                // data : broj sobe, tip sobe, prihod

                String[] columnNames = { "Broj sobe", "Tip sobe", "Broj Nocenja", "Prihod" };
                Object[][] data = new Object[sobeList.size()][4];

                for (Soba s : sobeList) {
                    // for evevry room we calculate its profit
                    data[s.getBrojSobe() - 1][0] = s.getBrojSobe();
                    data[s.getBrojSobe() - 1][1] = s.getTipSobe();
                    data[s.getBrojSobe() - 1][2] = SobeController.brojNocenjaPoPeriodu(s, rezervacijeZaPeriod);
                    data[s.getBrojSobe() - 1][3] = SobeController.prihodSobe(s, rezervacijeZaPeriod,
                            cenovniciList);
                    // data[s.getBrojSobe() - 1][2] = "";
                }

                JTable table = new JTable(data, columnNames);
                table.setPreferredScrollableViewportSize(new Dimension(500, 70));
                table.setFillsViewportHeight(true);

                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);

                scrollPane.setBounds(50, 200, 700, 300);

                frame.revalidate();
                frame.repaint();

            }
        });

        return frame;
    }

    private JFrame createSobariceIzvestajiFrame() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setBackground(Color.CYAN);

        // First we ask for the period
        JLabel startDate = new JLabel("Pocetak perioda (YYYY-MM-DD):");
        JLabel endDate = new JLabel("Kraj perioda (YYYY-MM-DD):");
        startDate.setBounds(50, 50, 200, 25);
        endDate.setBounds(50, 100, 200, 25);
        frame.add(startDate);
        frame.add(endDate);

        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        startDateField.setBounds(250, 50, 200, 25);

        endDateField.setBounds(250, 100, 200, 25);
        frame.add(startDateField);
        frame.add(endDateField);

        JButton calculateButton = new JButton("Izracunaj");
        calculateButton.setBounds(50, 150, 200, 25);
        calculateButton.setFocusable(false);
        frame.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startDateField.getText();
                String end = endDateField.getText();

                if (start.isEmpty() || end.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Molimo unesite oba datuma.");
                    return;
                }
                // Table for every room

                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate = LocalDate.parse(end);
                List<Rezervacija> rezervacijeZaPeriod = rezervacijeController.getPromeneStatusaUPeriodu(startDate,
                        endDate, rezervacijeList);

                // columnnames : broj sobe, tip sobe, prihod
                // data : broj sobe, tip sobe, prihod

                String[] columnNames = { "Ime sobarice", "Prezime sobarice", "Broj spremljenih soba" };
                int brojac = 0;
                Object[][] data = new Object[sobeList.size()][3];
                for (Korisnik korisnik : korisniciList) {
                    if (korisnik.getUloga().equals(Helper.Uloga.Sobarica)) {
                        data[brojac][0] = korisnik.getIme();
                        data[brojac][1] = korisnik.getLastName();
                        data[brojac][2] = SobariceController
                                .getSobeZaSpremanjeZaPeriod(korisnik.getKorisnickoIme(), startDate, endDate,
                                        sobeZaSpremanjeList, sobeList, korisniciList)
                                .size();
                        brojac++;
                    }

                }

                JTable table = new JTable(data, columnNames);
                table.setPreferredScrollableViewportSize(new Dimension(500, 70));
                table.setFillsViewportHeight(true);

                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);

                scrollPane.setBounds(50, 200, 700, 300);

                frame.revalidate();
                frame.repaint();

            }

        });

        return frame;
    }

    private JPanel createPregledajPanel() {
        JPanel panelPregled = new JPanel();
        panelPregled.setLayout(null);
        panelPregled.setBackground(Color.PINK);

        JLabel lblPregledaj = new JLabel("Odaberite opciju za pregled");
        lblPregledaj.setBounds(50, 50, 300, 25);
        lblPregledaj.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelPregled.add(lblPregledaj);
        JComboBox<String> opcije = new JComboBox<>();
        opcije.addItem("Korisnici");
        opcije.addItem("Rezervacije");
        opcije.addItem("Sobe");
        opcije.addItem("Cenovnik");
        opcije.setBounds(50, 100, 200, 25);
        panelPregled.add(opcije);

        JButton prikaziButton = new JButton("Prikazi");
        prikaziButton.setBounds(50, 150, 200, 25);
        prikaziButton.setFocusable(false);
        panelPregled.add(prikaziButton);

        prikaziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcija = opcije.getSelectedItem().toString();
                if (opcija.equals("Korisnici")) {
                    Frame prikazKorisnika = createKorisniciPanel();
                    prikazKorisnika.setVisible(true);
                } else if (opcija.equals("Rezervacije")) {
                    Frame prikazRezervacija = createRezervacijePanel();
                    prikazRezervacija.setVisible(true);
                } else if (opcija.equals("Sobe")) {
                    Frame prikazSoba = pregledSoba();
                    prikazSoba.setVisible(true);
                } else if (opcija.equals("Cenovnik")) {
                    Frame prikazCenovnika = pregledSvihCenovnika();
                    prikazCenovnika.setVisible(true);
                }
            }
        });

        return panelPregled;
    }

    private JFrame pregledSvihCenovnika() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setBackground(Color.CYAN);

        // Making a table with all rooms
        String[] columnNames = { "Vazi od", "Vazi do" };
        // Adding more coloumns for every item in hashmap cenovnik
        Cenovnik prviCenovnik = cenovniciList.get(0);
        for (String key : prviCenovnik.getCenovnik().keySet()) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            columnNames[columnNames.length - 1] = key;
        }

        Object[][] data = new Object[cenovniciList.size()][cenovniciList.get(0).getCenovnik().size() + 2]; // +2 for
                                                                                                           // vazi od
                                                                                                           // and vazi
                                                                                                           // do

        for (int i = 0; i < cenovniciList.size(); i++) {
            data[i][0] = cenovniciList.get(i).getVaziOd();
            data[i][1] = cenovniciList.get(i).getVaziDo();
            int j = 2;
            for (String key : cenovniciList.get(i).getCenovnik().keySet()) {
                data[i][j] = cenovniciList.get(i).getCenovnik().get(key);
                j++;
            }
        }

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        scrollPane.setBounds(50, 50, 700, 400);

        JButton osveziButton = new JButton("Osveži");
        osveziButton.setBounds(50, 500, 200, 25);
        osveziButton.setFocusable(false);
        frame.add(osveziButton);

        osveziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame prikazCenovnika = pregledSvihCenovnika();
                prikazCenovnika.setVisible(true);
            }
        });

        return frame;
    }

    private JFrame pregledSoba() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setBackground(Color.CYAN);

        // Making a table with all rooms
        String[] columnNames = { "Broj sobe", "Tip sobe", "Status" };

        Object[][] data = new Object[sobeList.size()][3];

        for (int i = 0; i < sobeList.size(); i++) {
            data[i][0] = sobeList.get(i).getBrojSobe();
            data[i][1] = sobeList.get(i).getTipSobe();
            data[i][2] = sobeList.get(i).getStatusSobe();
        }

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        scrollPane.setBounds(50, 50, 700, 400);

        JButton osveziButton = new JButton("Osveži");
        osveziButton.setBounds(50, 500, 200, 25);
        osveziButton.setFocusable(false);
        frame.add(osveziButton);

        osveziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame prikazSoba = pregledSoba();
                prikazSoba.setVisible(true);
            }
        });

        return frame;
    }

    public boolean isDateOverlap(MonthDay newVaziOd, MonthDay newVaziDo, List<Cenovnik> cenovniciList) {
        for (Cenovnik cenovnik : cenovniciList) {
            MonthDay existingVaziOd = cenovnik.getVaziOd();
            MonthDay existingVaziDo = cenovnik.getVaziDo();

            // Normalize date ranges
            boolean newWrapsAround = newVaziOd.isAfter(newVaziDo);
            boolean existingWrapsAround = existingVaziOd.isAfter(existingVaziDo);

            // Split wrapped around ranges into two parts
            MonthDay newStart1 = newVaziOd;
            MonthDay newEnd1 = newWrapsAround ? MonthDay.of(12, 31) : newVaziDo;
            MonthDay newStart2 = newWrapsAround ? MonthDay.of(1, 1) : newVaziDo;
            MonthDay newEnd2 = newVaziDo;

            MonthDay existingStart1 = existingVaziOd;
            MonthDay existingEnd1 = existingWrapsAround ? MonthDay.of(12, 31) : existingVaziDo;
            MonthDay existingStart2 = existingWrapsAround ? MonthDay.of(1, 1) : existingVaziDo;
            MonthDay existingEnd2 = existingVaziDo;

            // Check overlaps for first part of the split
            if (checkOverlap(newStart1, newEnd1, existingStart1, existingEnd1) ||
                    checkOverlap(newStart1, newEnd1, existingStart2, existingEnd2) ||
                    checkOverlap(newStart2, newEnd2, existingStart1, existingEnd1) ||
                    checkOverlap(newStart2, newEnd2, existingStart2, existingEnd2)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkOverlap(MonthDay start1, MonthDay end1, MonthDay start2, MonthDay end2) {
        return (start1.isBefore(end2) || start1.equals(end2)) &&
                (end1.isAfter(start2) || end1.equals(start2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
