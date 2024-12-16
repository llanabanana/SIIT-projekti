import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.RezervacijeController;
import Controller.SobariceController;
import Controller.SobeController;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import Model.Cenovnik;
import Model.DodatnaUsluga;
import Model.Korisnik;
import Model.Rezervacija;
import Model.Soba;
import Model.SobaZaSpremanje;
import Model.Sobarica;
import Utils.Helper;
import Utils.UpisPodataka;

public class RecepcionerPanel {
    private JTabbedPane taboviRecepcioner = new JTabbedPane();
    private List<Korisnik> korisniciP;
    private List<Rezervacija> rezervacijeP;
    private List<Soba> sobeP;
    private List<DodatnaUsluga> dodatneUslugeP;
    private List<Cenovnik> cenovnikP;
    private SobeController sobeController;
    private SobariceController sobariceController;
    private List<Sobarica> proslSobariceList;
    private RezervacijeController rezervacijeController;
    private List<SobaZaSpremanje> sobeZaSpremanjeList;

    public RecepcionerPanel(JTabbedPane taboviRecepcioner, List<Korisnik> korisniciP, List<Rezervacija> rezervacijeP,
            List<Soba> sobeP, List<DodatnaUsluga> dodatneUslugeP, List<Cenovnik> cenovnikP,
            SobeController sobeController, SobariceController sobariceController, List<Sobarica> proslSobariceList,
            RezervacijeController rezervacijeController, List<SobaZaSpremanje> sobeZaSpremanjeList) {
        this.taboviRecepcioner = taboviRecepcioner;
        this.korisniciP = korisniciP;
        this.rezervacijeP = rezervacijeP;
        this.sobeP = sobeP;
        this.dodatneUslugeP = dodatneUslugeP;
        this.cenovnikP = cenovnikP;
        this.sobeController = sobeController;
        this.sobariceController = sobariceController;
        this.proslSobariceList = proslSobariceList;
        this.rezervacijeController = rezervacijeController;
        this.sobeZaSpremanjeList = sobeZaSpremanjeList;
    }

    public void initialize() {

        rezervacijeController.updateReservationStatuses(rezervacijeP);

        JPanel panelRezervacije = createRezervacijePanel();
        JPanel panelCheckIn = CreateCheckInPanel();
        JPanel panelCheckOut = CreateCheckOutPanel();
        JPanel panelSobe = CreateSobePanel();
        JPanel dodavanjeRegistracije = CreateDodavanjeRegistracijePanel();
        DefaultTableModel modelTab1 = new DefaultTableModel();

        panelCheckIn.setBackground(Color.PINK);
        panelCheckOut.setBackground(new Color(255, 218, 237));

        taboviRecepcioner.add("Rezervacije", panelRezervacije);
        taboviRecepcioner.add("Check-In", panelCheckIn);
        taboviRecepcioner.add("Check-Out", panelCheckOut);
        taboviRecepcioner.add("Sobe", panelSobe);
        taboviRecepcioner.add("Dodavanje Registracije", dodavanjeRegistracije);
    }

    private JPanel createRezervacijePanel() {
        JPanel panelRezervacije = new JPanel();
        panelRezervacije.setLayout(null);
        panelRezervacije.setBackground(new Color(255, 214, 237));

        JComboBox<String> tfBrojRezervacije = new JComboBox<>();
        tfBrojRezervacije.setBounds(180, 300, 165, 25);

        JLabel lblRezervacije = new JLabel("Rezervacije");
        lblRezervacije.setBounds(50, 50, 200, 25);
        lblRezervacije.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelRezervacije.add(lblRezervacije);

        String[] kolone = { "Broj Rezervacije", "Korisnik", "Datum Prijave", "Datum Odjave", "Tip Sobe", "Broj Sobe",
                "Status Rezervacije", "Dodatne usluge", "Cena" };
        DefaultTableModel modelTab1 = new DefaultTableModel(kolone, 0);
        JTable tabelaRezervacija = new JTable(modelTab1);
        int broj = 1;

        List<Rezervacija> rezervacijeZaRedove = rezervacijeP;
        int poziv = 0;
        for (Rezervacija rezervacija : rezervacijeZaRedove) {
            modelTab1.addRow(convertRezervacijaToRow(rezervacija, poziv));

            if (rezervacija.getStatusRezervacije().name().equals("NaCekanju")) {
                tfBrojRezervacije.addItem(String.valueOf(broj));
            }
            broj++;
        }

        JScrollPane sp = new JScrollPane(tabelaRezervacija);
        sp.setBounds(50, 100, 700, 100);
        panelRezervacije.add(sp);

        JButton btnRefresh = new JButton("Osveži");
        btnRefresh.setBounds(50, 350, 115, 25);
        panelRezervacije.add(btnRefresh);

        JLabel lblBrojRezervacije = new JLabel("Broj Rezervacije:");
        lblBrojRezervacije.setBounds(50, 300, 120, 25);
        lblBrojRezervacije.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panelRezervacije.add(lblBrojRezervacije);

        JComboBox<String> moguceSobe = new JComboBox<>();
        JLabel lblMoguceSobe = new JLabel("Moguce Sobe:");
        lblMoguceSobe.setBounds(50, 350, 120, 25);
        lblMoguceSobe.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panelRezervacije.add(lblMoguceSobe);
        lblMoguceSobe.setVisible(false);

        JComboBox<String> filter = new JComboBox<>();
        for (DodatnaUsluga usluga : dodatneUslugeP) {
            filter.addItem(usluga.getNaziv());
        }
        filter.addItem("Cena - rastuce");
        filter.addItem("Cena - opadajuce");
        for (Helper.Usluge usluga : Helper.Usluge.values()) {
            filter.addItem(usluga.name());
        }
        filter.addItem("Resetuj filtere");
        // filter at top right corner
        filter.setBounds(600, 50, 150, 25);
        panelRezervacije.add(filter);

        filter.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                if (filter.getSelectedItem().equals("Resetuj filtere")) {
                    modelTab1.setRowCount(0);
                    int poziv = 0;
                    List<Rezervacija> rezervacijeZaRedove2 = rezervacijeP;
                    // sort from smallest broj rezervacije to biggest
                    rezervacijeZaRedove2.sort((r1, r2) -> r1.getBrojRezervacije() - r2.getBrojRezervacije());
                    for (Rezervacija rezervacija : rezervacijeZaRedove2) {
                        modelTab1.addRow(convertRezervacijaToRow(rezervacija, poziv));

                    }
                } else if (filter.getSelectedItem().equals("Cena - rastuce")) {
                    modelTab1.setRowCount(0);
                    int poziv = 0;
                    List<Rezervacija> rezervacijeZaRedove2 = rezervacijeP;
                    rezervacijeZaRedove2.sort((r1, r2) -> Double.compare(r1.getCena(), r2.getCena()));
                    for (Rezervacija rezervacija : rezervacijeZaRedove2) {
                        modelTab1.addRow(convertRezervacijaToRow(rezervacija, poziv));
                    }
                } else if (filter.getSelectedItem().equals("Cena - opadajuce")) {
                    modelTab1.setRowCount(0);
                    int poziv = 0;
                    List<Rezervacija> rezervacijeZaRedove2 = rezervacijeP;
                    rezervacijeZaRedove2.sort((r1, r2) -> Double.compare(r2.getCena(), r1.getCena()));
                    for (Rezervacija rezervacija : rezervacijeZaRedove2) {
                        modelTab1.addRow(convertRezervacijaToRow(rezervacija, poziv));
                    }
                } else {
                    modelTab1.setRowCount(0);
                    int poziv = 0;
                    List<Rezervacija> rezervacijeZaRedove2 = rezervacijeP;
                    for (Rezervacija rezervacija : rezervacijeZaRedove2) {
                        if (rezervacija.getDodatneUsluge().stream()
                                .anyMatch(du -> du.getNaziv().equals(filter.getSelectedItem()))
                                || rezervacija.getBrojGostiju().name().equals(filter.getSelectedItem())) {
                            modelTab1.addRow(convertRezervacijaToRow(rezervacija, poziv));
                        }
                    }
                }

            }
        });

        panelRezervacije.add(tfBrojRezervacije);

        tfBrojRezervacije.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String brojRezervacije = (String) tfBrojRezervacije.getSelectedItem();
                List<Rezervacija> rezervacije2;
                List<Soba> sobeList;

                sobeList = sobeP;
                rezervacije2 = rezervacijeP;

                for (Rezervacija r : rezervacije2) {

                    if (String.valueOf(r.getBrojRezervacije()).equals(brojRezervacije)) {
                        List<Soba> slobodne = sobeController.getSlobodneSobe(sobeList, r.getCheckInDatum(),
                                r.getCheckOutDatum(), r.getBrojGostiju(), rezervacije2);

                        if (slobodne.size() == 0) {
                            JOptionPane.showMessageDialog(null, "Nema slobodnih soba za izabrani period");
                            r.setStatusRezervacije(Helper.StatusRezervacije.Odbijena);
                            r.setCena(0.0);
                            r.setDatumPromeneStatusa(LocalDate.now());
                            r.setPromena(Helper.StatusRezervacije.Odbijena);
                        } else {
                            JOptionPane.showMessageDialog(null, "Slobodne sobe za izabrani period: " + slobodne);
                            for (Soba s : slobodne) {
                                moguceSobe.addItem(String.valueOf(s.getBrojSobe()));
                            }

                            moguceSobe.setBounds(180, 350, 165, 25);

                            r.setStatusRezervacije(Helper.StatusRezervacije.Potvrdjena);
                            r.setDatumPromeneStatusa(LocalDate.now());
                            r.setPromena(Helper.StatusRezervacije.Potvrdjena);
                        }
                        break;
                    }
                }
                try {
                    App.upisiRezervacije("src/utils/rezervacije.csv", rezervacije2);
                    rezervacijeP = rezervacije2;

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                System.out.println("Refreshujem tabelu");
                tfBrojRezervacije.removeItem(brojRezervacije);
                Object[][] data = new Object[rezervacije2.size()][];
                int poziv = 0;
                for (int i = 0; i < rezervacije2.size(); i++) {
                    data[i] = convertRezervacijaToRow(rezervacije2.get(i), poziv);
                }
                String[] kolone = { "Broj Rezervacije", "Korisnik", "Datum Prijave", "Datum Odjave", "Tip Sobe",
                        "Broj Sobe", "Status Rezervacije" };
                DefaultTableModel model = new DefaultTableModel(data, kolone);
                tabelaRezervacija.setModel(model);

            }
        });

        panelRezervacije.add(tfBrojRezervacije);

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove action listeners temporarily
                ActionListener[] listeners = tfBrojRezervacije.getActionListeners();
                for (ActionListener listener : listeners) {
                    tfBrojRezervacije.removeActionListener(listener);
                }

                // Refresh the table
                List<Rezervacija> rezervacijeZaRedove = rezervacijeP;
                Object[][] data = new Object[rezervacijeZaRedove.size()][];
                int poziv = 0;
                for (int i = 0; i < rezervacijeZaRedove.size(); i++) {
                    data[i] = convertRezervacijaToRow(rezervacijeZaRedove.get(i), poziv);
                }
                String[] kolone = { "Broj Rezervacije", "Korisnik", "Datum Prijave", "Datum Odjave", "Tip Sobe",
                        "Broj Sobe", "Status Rezervacije", "Dodatne usluge", "Cena" };
                DefaultTableModel model = new DefaultTableModel(data, kolone);
                tabelaRezervacija.setModel(model);

                // Update the JComboBox items
                tfBrojRezervacije.removeAllItems();
                int broj = rezervacijeZaRedove.size();
                for (int i = 0; i < broj; i++) {
                    if (rezervacijeZaRedove.get(i).getStatusRezervacije().name().equals("NaCekanju")) {
                        tfBrojRezervacije.addItem(String.valueOf(i + 1));

                    }
                }

                // Re-add action listeners
                for (ActionListener listener : listeners) {
                    tfBrojRezervacije.addActionListener(listener);
                }
            }
        });

        return panelRezervacije;
    }

    private JPanel CreateCheckInPanel() {
        JPanel panelCheckIn = new JPanel();
        panelCheckIn.setLayout(null);
        panelCheckIn.setBackground(new Color(255, 214, 237));

        JLabel lblCheckIn = new JLabel("Check-In");
        lblCheckIn.setBounds(50, 50, 200, 25);
        lblCheckIn.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelCheckIn.add(lblCheckIn);

        String[] kolone = { "Broj Rezervacije", "Korisnik", "Datum Prijave", "Datum Odjave", "Tip Sobe" };

        DefaultTableModel model = new DefaultTableModel(kolone, 0);
        JTable tabelaCheckIn = new JTable(model);
        JScrollPane sp = new JScrollPane(tabelaCheckIn);

        sp.setBounds(50, 100, 700, 200);
        panelCheckIn.add(sp);
        int poziv;
        for (Rezervacija rezervacija : rezervacijeP) {
            if (rezervacija.getStatusRezervacije().name().equals("Potvrdjena")) {
                poziv = 1;
                model.addRow(convertRezervacijaToRow(rezervacija, poziv));
            }
        }

        JComboBox<String> slobodneSobeCB = new JComboBox<>();
        JLabel lblSlobodneSobe = new JLabel("Slobodne Sobe:");
        lblSlobodneSobe.setBounds(50, 300, 120, 25);
        lblSlobodneSobe.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panelCheckIn.add(lblSlobodneSobe);

        slobodneSobeCB.setBounds(180, 300, 165, 25);
        panelCheckIn.add(slobodneSobeCB);

        JButton btnOdaberi = new JButton("Potvrdi odabir");
        btnOdaberi.setBounds(50, 350, 115, 25);
        panelCheckIn.add(btnOdaberi);

        JButton btnRefresh = new JButton("Osveži");
        btnRefresh.setBounds(200, 350, 115, 25);
        panelCheckIn.add(btnRefresh);

        ListSelectionModel modelTabele = tabelaCheckIn.getSelectionModel();
        modelTabele.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnOdaberi.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                // Remove action listeners temporarily
                ActionListener[] listeners = slobodneSobeCB.getActionListeners();
                for (ActionListener listener : listeners) {
                    slobodneSobeCB.removeActionListener(listener);
                }

                int red = tabelaCheckIn.getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli");
                    return;
                } else {
                    String brojRezervacije = (String) tabelaCheckIn.getValueAt(red, 0);
                    String korisnicko = (String) tabelaCheckIn.getValueAt(red, 1);
                    String checkInDatum = (String) tabelaCheckIn.getValueAt(red, 2);
                    String checkOutDatum = (String) tabelaCheckIn.getValueAt(red, 3);
                    String tipSobe = (String) tabelaCheckIn.getValueAt(red, 4);

                    List<Soba> sobeList = sobeP;
                    List<Rezervacija> rezervacijeCI = rezervacijeP;

                    JLabel lblJosUsluga = new JLabel("Jos usluga:");
                    lblJosUsluga.setBounds(50, 400, 120, 25);
                    lblJosUsluga.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                    panelCheckIn.add(lblJosUsluga);

                    JList<String> listDodatneUsluge = new JList<>();
                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    listDodatneUsluge.setModel(listModel);
                    listDodatneUsluge.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    listDodatneUsluge.setLayoutOrientation(JList.VERTICAL);
                    listDodatneUsluge.setSize(165, 75);
                    JScrollPane scrollPane = new JScrollPane(listDodatneUsluge);
                    scrollPane.setBounds(180, 400, 165, 75);
                    panelCheckIn.add(scrollPane);

                    for (DodatnaUsluga usluga : dodatneUslugeP) {
                        listModel.addElement(usluga.getNaziv());
                    }

                    List<DodatnaUsluga> dodatneUslugeOdRanije = new ArrayList<>();

                    for (Rezervacija r : rezervacijeCI) {
                        if (String.valueOf(r.getBrojRezervacije()).equals(brojRezervacije)) {
                            dodatneUslugeOdRanije = r.getDodatneUsluge();
                        }
                    }

                    List<Integer> selectedIndices = new ArrayList<>();
                    for (int i = 0; i < listModel.size(); i++) {
                        if (dodatneUslugeOdRanije.size() > 0) {
                            for (DodatnaUsluga du : dodatneUslugeOdRanije) {
                                if (listModel.getElementAt(i).equals(du.getNaziv())) {
                                    selectedIndices.add(i);
                                }
                            }
                        }
                    }
                    int[] indices = selectedIndices.stream().mapToInt(i -> i).toArray();
                    listDodatneUsluge.setSelectedIndices(indices);
                    listDodatneUsluge.updateUI();
                    listDodatneUsluge.setSelectedValue(dodatneUslugeOdRanije, true);

                    List<Soba> slobodneSobe = new ArrayList<>();
                    LocalDate LDcheckInDatum = LocalDate.parse(checkInDatum);
                    LocalDate LDcheckOutDatum = LocalDate.parse(checkOutDatum);
                    slobodneSobe = sobeController.getSlobodneSobe(sobeList, LDcheckInDatum, LDcheckOutDatum,
                            Helper.Usluge.valueOf(tipSobe), rezervacijeCI);
                    for (Soba s : slobodneSobe) {
                        slobodneSobeCB.addItem(String.valueOf(s.getBrojSobe()));
                    }

                    slobodneSobeCB.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String brojSobe = (String) slobodneSobeCB.getSelectedItem();
                            List<String> dodatneUslugeStr = new ArrayList<>();
                            dodatneUslugeStr = listDodatneUsluge.getSelectedValuesList();
                            List<DodatnaUsluga> dodatneUsluge = new ArrayList<>();
                            for (String usluga : dodatneUslugeStr) {
                                for (DodatnaUsluga du : dodatneUslugeP) {
                                    if (du.getNaziv().equals(usluga)) {
                                        dodatneUsluge.add(du);
                                    }
                                }
                            }
                            List<Soba> sobeList = sobeP;
                            List<Rezervacija> rezervacijeCI = rezervacijeP;
                            List<Soba> slobodneSobe = new ArrayList<>();
                            slobodneSobe = sobeController.getSlobodneSobe(sobeList, LDcheckInDatum, LDcheckOutDatum,
                                    Helper.Usluge.valueOf(tipSobe), rezervacijeCI);
                            int redZaUkloniti = 0;
                            for (Soba s : slobodneSobe) {
                                if (String.valueOf(s.getBrojSobe()).equals(brojSobe)) {
                                    for (Rezervacija r : rezervacijeCI) {
                                        if (String.valueOf(r.getBrojRezervacije()).equals(brojRezervacije)) {
                                            r.setBrojSobe(s);
                                            r.setStatusRezervacije(Helper.StatusRezervacije.Aktivna);
                                            s.setStatusSobe(Helper.StatusSobe.Zauzeta);
                                            r.setDodatneUsluge(dodatneUsluge);
                                            JOptionPane.showMessageDialog(null, "Check-In uspesno obavljen");
                                            // model.removeRow(redZaUkloniti);
                                            slobodneSobeCB.removeAllItems();
                                            listDodatneUsluge.clearSelection();
                                            break;
                                        }
                                        redZaUkloniti++;

                                    }
                                    break;
                                }
                            }

                            try {
                                App.upisiRezervacije("src/utils/rezervacije.csv", rezervacijeCI);
                                rezervacijeP = rezervacijeCI;
                                App.upisiSobe("src/utils/sobe.csv", sobeList);
                                sobeP = sobeList;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    });

                }
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Rezervacija> rezervacijeCI = new ArrayList<>();
                for (Rezervacija rezervacija : rezervacijeP) {
                    if (rezervacija.getStatusRezervacije().name().equals("Potvrdjena")) {
                        rezervacijeCI.add(rezervacija);
                    }
                }
                Object[][] data = new Object[rezervacijeCI.size()][];
                int poziv = 1;
                for (int i = 0; i < rezervacijeCI.size(); i++) {
                    data[i] = convertRezervacijaToRow(rezervacijeCI.get(i), poziv);
                }
                String[] kolone = { "Broj Rezervacije", "Korisnik", "Datum Prijave", "Datum Odjave", "Tip Sobe" };
                DefaultTableModel model = new DefaultTableModel(data, kolone);
                tabelaCheckIn.setModel(model);

                // Update the JComboBox items
                slobodneSobeCB.removeAllItems();

            }

        });

        return panelCheckIn;
    }

    private JPanel CreateCheckOutPanel() {
        JPanel panelCheckOut = new JPanel();
        panelCheckOut.setLayout(null);
        panelCheckOut.setBackground(new Color(255, 214, 237));

        JLabel lblCheckOut = new JLabel("Check-Out");
        lblCheckOut.setBounds(50, 50, 200, 25);
        lblCheckOut.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelCheckOut.add(lblCheckOut);

        String[] kolone = { "Broj Rezervacije", "Korisnik", "Datum Prijave", "Datum Odjave", "Tip Sobe", "Broj Sobe",
                "Status Rezervacije" };

        DefaultTableModel model = new DefaultTableModel(kolone, 0);
        JTable tabelaCheckOut = new JTable(model);
        JScrollPane sp = new JScrollPane(tabelaCheckOut);

        sp.setBounds(50, 100, 700, 200);
        panelCheckOut.add(sp);
        int poziv;
        for (Rezervacija rezervacija : rezervacijeP) {
            if (rezervacija.getStatusRezervacije().name().equals("Aktivna")) {
                poziv = 0;
                model.addRow(convertRezervacijaToRow(rezervacija, poziv));
            }
        }

        JButton btnRefresh = new JButton("Osveži");
        btnRefresh.setBounds(50, 350, 115, 25);
        panelCheckOut.add(btnRefresh);

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Rezervacija> zavrseneRezervacije = new ArrayList<>();
                for (Rezervacija rezervacija : rezervacijeP) {
                    if (rezervacija.getStatusRezervacije().name().equals("Aktivna")) {
                        zavrseneRezervacije.add(rezervacija);
                    }
                }
                Object[][] data = new Object[zavrseneRezervacije.size()][];
                int poziv = 0;
                for (int i = 0; i < zavrseneRezervacije.size(); i++) {
                    data[i] = convertRezervacijaToRow(zavrseneRezervacije.get(i), poziv);
                }
                String[] kolone = { "Broj Rezervacije", "Korisnik", "Datum Prijave", "Datum Odjave", "Tip Sobe",
                        "Broj Sobe", "Status Rezervacije" };
                DefaultTableModel model = new DefaultTableModel(data, kolone);
                tabelaCheckOut.setModel(model);
            }

        });

        ListSelectionModel modelTabele = tabelaCheckOut.getSelectionModel();
        modelTabele.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnCheckOut = new JButton("Check-Out");
        btnCheckOut.setBounds(200, 350, 115, 25);
        panelCheckOut.add(btnCheckOut);

        btnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = tabelaCheckOut.getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli");
                    return;
                } else {
                    String brojRezervacije = (String) tabelaCheckOut.getValueAt(red, 0);
                    String brojSobe = (String) tabelaCheckOut.getValueAt(red, 5);

                    List<Soba> sobeList = sobeP;
                    List<Rezervacija> rezervacijeCO = rezervacijeP;

                    for (Rezervacija r : rezervacijeCO) {
                        if (String.valueOf(r.getBrojRezervacije()).equals(brojRezervacije)) {
                            for (Soba s : sobeList) {
                                if (String.valueOf(s.getBrojSobe()).equals(brojSobe)) {
                                    s.setStatusSobe(Helper.StatusSobe.Spremanje);
                                    r.setStatusRezervacije(Helper.StatusRezervacije.Prosla);

                                    Sobarica sobaricaZaCiscenje = sobariceController
                                            .sobaricaSaNajmanjeSoba(proslSobariceList);

                                    List<Soba> sobeZaSpremanjeNove = sobaricaZaCiscenje.getSobeZaSpremanje();
                                    if (sobeZaSpremanjeNove == null) {
                                        sobeZaSpremanjeNove = new ArrayList<>();
                                    }
                                    sobeZaSpremanjeNove.add(s);
                                    sobaricaZaCiscenje.setSobeZaSpremanje(sobeZaSpremanjeNove);

                                    SobaZaSpremanje szs = new SobaZaSpremanje(sobaricaZaCiscenje.getKorisnickoIme(),
                                            LocalDate.now(), Integer.parseInt(brojSobe));
                                    sobeZaSpremanjeList.add(szs);

                                    JOptionPane.showMessageDialog(null, "Soba " + brojSobe + " je spremna za ciscenje");

                                    try {
                                        // App.upisiSobarice("src/utils/sobarice.csv", proslSobariceList);
                                        UpisPodataka.upisiSobeZaSpremanje("src/utils/spremanje.csv",
                                                sobeZaSpremanjeList);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    ;

                                    JOptionPane.showMessageDialog(null, "Check-Out uspesno obavljen");
                                    break;
                                }
                            }
                            break;
                        }
                    }

                    try {
                        App.upisiRezervacije("src/utils/rezervacije.csv", rezervacijeCO);
                        rezervacijeP = rezervacijeCO;
                        App.upisiSobe("src/utils/sobe.csv", sobeList);
                        sobeP = sobeList;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        return panelCheckOut;
    }

    private JPanel CreateSobePanel() {
        JPanel panelSobe = new JPanel();
        panelSobe.setLayout(null);
        panelSobe.setBackground(new Color(255, 214, 237));

        JLabel lblSobe = new JLabel("Sobe");
        lblSobe.setBounds(50, 50, 200, 25);
        lblSobe.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelSobe.add(lblSobe);

        String[] kolone = { "Broj Sobe", "Tip Sobe", "Status Sobe" };

        DefaultTableModel model = new DefaultTableModel(kolone, 0);
        JTable tabelaSobe = new JTable(model);
        JScrollPane sp = new JScrollPane(tabelaSobe);

        sp.setBounds(50, 100, 700, 200);
        panelSobe.add(sp);

        for (Soba soba : sobeP) {
            model.addRow(convertSobaToRow(soba));
        }

        JButton btnRefresh = new JButton("Osveži");
        btnRefresh.setBounds(50, 350, 115, 25);
        panelSobe.add(btnRefresh);

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] data = new Object[sobeP.size()][];
                for (int i = 0; i < sobeP.size(); i++) {
                    data[i] = convertSobaToRow(sobeP.get(i));
                }
                String[] kolone = { "Broj Sobe", "Tip Sobe", "Status Sobe" };
                DefaultTableModel model = new DefaultTableModel(data, kolone);
                tabelaSobe.setModel(model);
            }
        });

        return panelSobe;
    }

    private JPanel CreateDodavanjeRegistracijePanel() {
        List<String> sveDodatneUsluge = null;

        List<DodatnaUsluga> dodatneUsluge = dodatneUslugeP;
        sveDodatneUsluge = new ArrayList<>(dodatneUslugeToString(dodatneUsluge));

        JPanel dodavanjeRegistracije = new JPanel();
        dodavanjeRegistracije.setLayout(null);
        dodavanjeRegistracije.setBackground(new Color(255, 214, 237));

        JLabel lblDodavanjeRegistracije = new JLabel("Nova Registracija");
        lblDodavanjeRegistracije.setBounds(50, 50, 200, 25);
        lblDodavanjeRegistracije.setFont(new Font("Times New Roman", Font.BOLD, 20));
        dodavanjeRegistracije.add(lblDodavanjeRegistracije);

        JLabel lblImeGosta = new JLabel("Ime Gosta:");
        JLabel lblPrezimeGosta = new JLabel("Prezime Gosta:");
        JLabel lblKorisnickoIme = new JLabel("Email:");
        JLabel lblLozinka = new JLabel("Broj pasosa:");
        JLabel lblTipSobe = new JLabel("Tip Sobe:");
        JLabel lblCheckInDatum = new JLabel("Datum Prijave:");
        JLabel lblCheckOutDatum = new JLabel("Datum Odjave:");
        JList<String> listDodatneUsluge = new JList<>(sveDodatneUsluge.toArray(new String[0]));
        listDodatneUsluge.setVisibleRowCount(sveDodatneUsluge.size());
        listDodatneUsluge.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listDodatneUsluge.setLayoutOrientation(JList.VERTICAL);
        listDodatneUsluge.setSize(165, 75);
        JScrollPane scrollPane = new JScrollPane(listDodatneUsluge);
        lblImeGosta.setBounds(50, 100, 120, 25);
        lblPrezimeGosta.setBounds(50, 150, 120, 25);
        lblKorisnickoIme.setBounds(50, 200, 120, 25);
        lblLozinka.setBounds(50, 250, 120, 25);
        lblTipSobe.setBounds(50, 300, 120, 25);
        lblCheckInDatum.setBounds(50, 350, 120, 25);
        lblCheckOutDatum.setBounds(50, 400, 120, 25);
        scrollPane.setBounds(180, 450, 165, 75);

        dodavanjeRegistracije.add(scrollPane);
        dodavanjeRegistracije.add(lblImeGosta);
        dodavanjeRegistracije.add(lblPrezimeGosta);
        dodavanjeRegistracije.add(lblKorisnickoIme);
        dodavanjeRegistracije.add(lblLozinka);
        dodavanjeRegistracije.add(lblTipSobe);
        dodavanjeRegistracije.add(lblCheckInDatum);
        dodavanjeRegistracije.add(lblCheckOutDatum);

        JTextField tfImeGosta = new JTextField();
        JTextField tfPrezimeGosta = new JTextField();
        JTextField tfKorisnickoIme = new JTextField();
        JTextField tfLozinka = new JTextField();
        JComboBox<String> tfTipSobe = new JComboBox<String>();
        JTextField tfCheckInDatum = new JTextField();
        JTextField tfCheckOutDatum = new JTextField();

        for (Helper.Usluge usluga : Helper.Usluge.values()) {
            tfTipSobe.addItem(usluga.name());
        }

        tfImeGosta.setBounds(180, 100, 165, 25);
        tfPrezimeGosta.setBounds(180, 150, 165, 25);
        tfKorisnickoIme.setBounds(180, 200, 165, 25);
        tfLozinka.setBounds(180, 250, 165, 25);
        tfTipSobe.setBounds(180, 300, 165, 25);
        tfCheckInDatum.setBounds(180, 350, 165, 25);
        tfCheckOutDatum.setBounds(180, 400, 165, 25);

        dodavanjeRegistracije.add(tfImeGosta);
        dodavanjeRegistracije.add(tfPrezimeGosta);
        dodavanjeRegistracije.add(tfKorisnickoIme);
        dodavanjeRegistracije.add(tfLozinka);
        dodavanjeRegistracije.add(tfTipSobe);
        dodavanjeRegistracije.add(tfCheckInDatum);
        dodavanjeRegistracije.add(tfCheckOutDatum);

        JButton btnDodajRegistraciju = new JButton("Dodaj Registraciju");
        btnDodajRegistraciju.setBounds(50, 550, 165, 25);
        dodavanjeRegistracije.add(btnDodajRegistraciju);

        btnDodajRegistraciju.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imeGosta = tfImeGosta.getText();
                String prezimeGosta = tfPrezimeGosta.getText();
                String korisnickoIme = tfKorisnickoIme.getText();
                String lozinka = tfLozinka.getText();
                String tipSobe = (String) tfTipSobe.getSelectedItem();
                String checkInDatum = tfCheckInDatum.getText();
                String checkOutDatum = tfCheckOutDatum.getText();
                List<String> dodatneUslugeStr = listDodatneUsluge.getSelectedValuesList();

                // check if korisnicko ime is unique
                for (Korisnik korisnik : korisniciP) {
                    if (korisnik.getKorisnickoIme().equals(korisnickoIme)) {
                        JOptionPane.showMessageDialog(null, "Gost je vec registrovan");
                        return;
                    }
                }

                List<DodatnaUsluga> dodatneUsluge = new ArrayList<>();
                for (String usluga : dodatneUslugeStr) {
                    for (DodatnaUsluga du : dodatneUslugeP) {
                        if (du.getNaziv().equals(usluga)) {
                            dodatneUsluge.add(du);
                        }
                    }
                }

                List<Rezervacija> rezervacijeDR = rezervacijeP;
                List<Korisnik> korisnici = korisniciP;
                LocalDate LDcheckInDatum = LocalDate.parse(checkInDatum);
                LocalDate LDcheckOutDatum = LocalDate.parse(checkOutDatum);

                Korisnik korisnik = new Korisnik();
                korisnik.setIme(imeGosta);
                korisnik.setLastName(prezimeGosta);
                korisnik.setKorisnickoIme(korisnickoIme);
                korisnik.setLozinka(lozinka);
                korisnik.setUloga(Helper.Uloga.Gost);

                Rezervacija novaRezervacija = new Rezervacija();
                novaRezervacija.setBrojRezervacije(rezervacijeDR.size() + 1);
                novaRezervacija.setGost(korisnik);
                novaRezervacija.setCheckInDatum(LDcheckInDatum);
                novaRezervacija.setCheckOutDatum(LDcheckOutDatum);
                novaRezervacija.setBrojGostiju(Helper.Usluge.valueOf(tipSobe));
                novaRezervacija.setStatusRezervacije(Helper.StatusRezervacije.NaCekanju);
                novaRezervacija.setDodatneUsluge(dodatneUsluge);
                novaRezervacija.setCena(RezervacijeController.getCenaRezervacije2(novaRezervacija, cenovnikP));
                novaRezervacija.setDatumPromeneStatusa(LocalDate.now());
                novaRezervacija.setPromena(Helper.StatusRezervacije.NaCekanju);

                rezervacijeDR.add(novaRezervacija);
                korisnici.add(korisnik);

                try {
                    App.upisiRezervacije("src/utils/rezervacije.csv", rezervacijeDR);
                    rezervacijeP = rezervacijeDR;
                    App.upisiKorisnike("src/utils/korisnici.csv", korisnici);
                    korisniciP = korisnici;

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Nova registracija uspesno dodata");

                tfImeGosta.setText("");
                tfPrezimeGosta.setText("");
                tfKorisnickoIme.setText("");
                tfLozinka.setText("");
                tfCheckInDatum.setText("");
                tfCheckOutDatum.setText("");

                listDodatneUsluge.clearSelection();
            }
        });

        return dodavanjeRegistracije;

    }

    private List<String> dodatneUslugeToString(List<DodatnaUsluga> dodatneUsluge) {
        List<String> usluge = new ArrayList<>();
        for (DodatnaUsluga usluga : dodatneUsluge) {
            usluge.add(usluga.getNaziv());
        }

        return usluge;
    }

    public static String[] convertSobaToRow(Soba soba) {
        String brojSobe = String.valueOf(soba.getBrojSobe());
        String tipSobe = soba.getTipSobe().name();
        String statusSobe = soba.getStatusSobe().name();

        return new String[] { brojSobe, tipSobe, statusSobe };
    }

    public static String[] convertRezervacijaToRow(Rezervacija rezervacija, int poziv) {
        String brojRezervacije = String.valueOf(rezervacija.getBrojRezervacije());
        String korisnicko = rezervacija.getGost().getKorisnickoIme().toString();
        String checkInDatum = rezervacija.getCheckInDatum().toString();
        String checkOutDatum = rezervacija.getCheckOutDatum().toString();
        String tipSobe = String.valueOf(rezervacija.getBrojGostiju());
        Soba sobica = (rezervacija.getBrojSobe());
        String brojSobe = "";
        if (sobica == null) {
            brojSobe = "N/A";
        } else {
            brojSobe = String.valueOf(sobica.getBrojSobe());
        }
        String status = rezervacija.getStatusRezervacije().name();

        List<String> dodatneUsluge = new ArrayList<>();

        for (DodatnaUsluga usluga : rezervacija.getDodatneUsluge()) {
            dodatneUsluge.add(usluga.getNaziv());
        }

        String dodatneUslugeStr = String.join(";", dodatneUsluge);

        if (poziv == 0) {
            return new String[] { brojRezervacije, korisnicko, checkInDatum, checkOutDatum, tipSobe, brojSobe, status,
                    dodatneUslugeStr, String.valueOf(rezervacija.getCena()) };
        } else if (poziv == 1) {
            return new String[] { brojRezervacije, korisnicko, checkInDatum, checkOutDatum, tipSobe };
        } else {
            return new String[] {};
        }
    }

}
