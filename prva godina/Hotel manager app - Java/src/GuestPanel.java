
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.RezervacijeController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;

import Model.DodatnaUsluga;
import Model.Korisnik;
import Model.Rezervacija;
import Model.Cenovnik;
import Model.Soba;
import Utils.Helper;
import Utils.Helper.atributiSobe;

public class GuestPanel {
    private JTabbedPane taboviGost;
    private List<Rezervacija> rezervacijeP;
    private List<Soba> sobeP;
    private List<DodatnaUsluga> dodatneUslugeP;
    private List<Cenovnik> cenovnikP;
    private RezervacijeController rezervacijeController;

    public GuestPanel(JTabbedPane taboviGost, List<Rezervacija> rezervacijeP, List<Soba> sobeP,
            List<DodatnaUsluga> dodatneUslugeP, List<Cenovnik> cenovnikP,
            RezervacijeController rezervacijeController) {
        this.taboviGost = taboviGost;
        this.rezervacijeP = rezervacijeP;

        this.sobeP = sobeP;
        this.dodatneUslugeP = dodatneUslugeP;
        this.cenovnikP = cenovnikP;
        this.rezervacijeController = rezervacijeController;

    }

    public void initialize(Korisnik korisnik) {
        taboviGost.removeAll();
        JPanel panelRezervacija = createRezervacijaPanel(korisnik);
        JPanel panelPregledajRezervacije = createPregledajRezervacijePanel(korisnik);

        taboviGost.add("Rezervacija", panelRezervacija);
        taboviGost.add("Pregledaj Rezervacije", panelPregledajRezervacije);

    }

    private JPanel createRezervacijaPanel(Korisnik korisnik) {

        List<String> sveDodatneUsluge = null;
        List<DodatnaUsluga> dodatneUsluge = dodatneUslugeP;
        sveDodatneUsluge = new ArrayList<>(dodatneUslugeToString(dodatneUsluge));

        JPanel panelRezervacija = new JPanel();
        panelRezervacija.setLayout(null);
        panelRezervacija.setBackground(new Color(173, 216, 230));

        JLabel naslov = new JLabel("Dodaj Rezervaciju");
        naslov.setBounds(50, 50, 300, 30);
        naslov.setFont(new Font("Times New Roman", Font.BOLD, 24));
        panelRezervacija.add(naslov);

        JLabel lblTipSobe = new JLabel("Tip Sobe:");
        JLabel lblDatumPrijave = new JLabel("Datum Prijave:");
        JLabel lblDatumOdjave = new JLabel("Datum Odjave:");
        JLabel lblPosebniZahtevi = new JLabel("Dodatne Usluge:");
        JList listDodatneUsluge = new JList(sveDodatneUsluge.toArray());
        listDodatneUsluge.setVisibleRowCount(sveDodatneUsluge.size());
        listDodatneUsluge.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listDodatneUsluge.setLayoutOrientation(JList.VERTICAL);
        listDodatneUsluge.setSize(165, 75);
        JScrollPane scrollPane = new JScrollPane(listDodatneUsluge);
        scrollPane.setBounds(180, 300, 165, 75);

        lblTipSobe.setBounds(50, 100, 120, 25);
        lblDatumPrijave.setBounds(50, 150, 120, 25);
        lblDatumOdjave.setBounds(50, 200, 120, 25);
        lblPosebniZahtevi.setBounds(50, 300, 120, 25);

        lblTipSobe.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblDatumPrijave.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblDatumOdjave.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblPosebniZahtevi.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        Helper.Usluge[] tipoviSoba = Helper.Usluge.values();
        JComboBox<Helper.Usluge> cbTipSobe = new JComboBox<>(tipoviSoba);
        JTextField tfDatumPrijave = new JTextField();
        JTextField tfDatumOdjave = new JTextField();

        cbTipSobe.setBounds(180, 100, 165, 25);
        tfDatumPrijave.setBounds(180, 150, 165, 25);
        tfDatumOdjave.setBounds(180, 200, 165, 25);

        panelRezervacija.add(lblTipSobe);
        panelRezervacija.add(lblDatumPrijave);
        panelRezervacija.add(lblDatumOdjave);
        panelRezervacija.add(lblPosebniZahtevi);
        panelRezervacija.add(cbTipSobe);
        panelRezervacija.add(tfDatumPrijave);
        panelRezervacija.add(tfDatumOdjave);
        panelRezervacija.add(scrollPane);
        Helper.atributiSobe[] atributiSobe = Helper.atributiSobe.values();

        JComboBox<Helper.atributiSobe> filter = new JComboBox<Helper.atributiSobe>(atributiSobe);
        filter.setBounds(180, 250, 165, 25);
        panelRezervacija.add(filter);

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Helper.atributiSobe atribut = (Helper.atributiSobe) filter.getSelectedItem();
                List<String> usluge = new ArrayList<>();
                for (Helper.Usluge usluga : Helper.Usluge.values()) {
                    for (Soba soba : sobeP) {
                        if (soba.getAtributiSobe() == null)
                            continue;
                        if (soba.getTipSobe() == usluga && soba.getAtributiSobe().contains(atribut)) {
                            if (!usluge.contains(usluga.name())) {
                                usluge.add(usluga.name());
                            }
                        }
                    }
                }
                System.out.println(usluge);
                cbTipSobe.removeAllItems();
                for (String usluga : usluge) {
                    cbTipSobe.addItem(Helper.Usluge.valueOf(usluga));
                }

            }
        });

        JButton btnSubmit = new JButton("Dodaj Rezervaciju");
        btnSubmit.setBounds(180, 400, 165, 25);
        btnSubmit.setFocusable(false);
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve values from fields

                String tipSobe = String.valueOf(cbTipSobe.getSelectedItem());
                String datumPrijave = tfDatumPrijave.getText();
                String datumOdjave = tfDatumOdjave.getText();
                List<String> posebniZahtevi = listDodatneUsluge.getSelectedValuesList();

                // Validate and process the reservation data
                if (tipSobe != null && !datumPrijave.isEmpty() && !datumOdjave.isEmpty()) {
                    JOptionPane.showMessageDialog(panelRezervacija, "Rezervacija uspešno dodata!", "Uspeh",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Logic to store the reservation can be added here
                    Rezervacija rezervacija = new Rezervacija();
                    rezervacija.setBrojRezervacije(rezervacijeP.size() + 1);
                    rezervacija.setGost(korisnik);
                    rezervacija.setCheckInDatum(LocalDate.parse(datumPrijave));
                    rezervacija.setCheckOutDatum(LocalDate.parse(datumOdjave));
                    rezervacija.setDodatneUsluge(new ArrayList<>());
                    for (String usluga : posebniZahtevi) {
                        DodatnaUsluga dodatnaUsluga = new DodatnaUsluga();
                        dodatnaUsluga.setNaziv(usluga);
                        rezervacija.getDodatneUsluge().add(dodatnaUsluga);
                    }
                    rezervacija.setBrojGostiju(Helper.Usluge.valueOf(tipSobe));
                    rezervacija.setStatusRezervacije(Helper.StatusRezervacije.NaCekanju);
                    rezervacija.setCena(RezervacijeController.getCenaRezervacije2(rezervacija, cenovnikP));
                    rezervacija.setDatumPromeneStatusa(LocalDate.now());
                    rezervacija.setPromena(Helper.StatusRezervacije.NaCekanju);
                    tfDatumOdjave.setText("");
                    tfDatumPrijave.setText("");
                    cbTipSobe.setSelectedIndex(0);
                    listDodatneUsluge.clearSelection();
                    System.out.println("Rezervacije: " + rezervacijeP);

                    List<Rezervacija> sveNoveRezervacije = rezervacijeP;

                    try {
                        sveNoveRezervacije.add(rezervacija);
                        App.upisiRezervacije("src/utils/rezervacije.csv", sveNoveRezervacije);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(panelRezervacija, "Molimo popunite sva polja.", "Greška",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        panelRezervacija.add(btnSubmit);

        return panelRezervacija;
    }

    private JPanel createPregledajRezervacijePanel(Korisnik korisnik) {
        JPanel panelPregledajRezervacije = new JPanel();
        panelPregledajRezervacije.setLayout(null);
        panelPregledajRezervacije.setBackground(new Color(173, 216, 230));

        JLabel naslov = new JLabel("Pregledaj Rezervacije");
        naslov.setBounds(50, 50, 300, 30);
        naslov.setFont(new Font("Times New Roman", Font.BOLD, 24));
        naslov.setBackground(new Color(173, 216, 230));

        JButton btnRefresh = new JButton("Osveži");
        btnRefresh.setBounds(600, 50, 150, 30);
        btnRefresh.setFocusable(false);

        JButton btnOtkazi = new JButton("Otkaži");
        btnOtkazi.setBounds(600, 400, 150, 30);
        btnOtkazi.setFocusable(false);

        panelPregledajRezervacije.add(btnRefresh);

        panelPregledajRezervacije.add(naslov);

        // Logic to display reservations can be added here

        String[] columns = { "ID rezervacije", "Tip Sobe", "Datum Prijave", "Datum Odjave", "Dodatne Usluge",
                "Status Rezervacije",
                "Cena rezervacije" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        table.setDefaultEditor(Object.class, null);

        sp.setBounds(50, 100, 700, 300);
        panelPregledajRezervacije.add(sp);
        JOptionPane.showMessageDialog(null, "Korisnik: " + korisnik.getKorisnickoIme(), "Informacija",
                JOptionPane.INFORMATION_MESSAGE);

        List<Rezervacija> rezervacijeZaPregled = rezervacijeP;
        Double ukupnoNePlaceno = 0.0;
        for (Rezervacija rezervacija : rezervacijeZaPregled) {
            if (rezervacija.getGost().getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {
                model.addRow(convertRezervacijaToRow(rezervacija));
                if (rezervacija.getStatusRezervacije() == Helper.StatusRezervacije.NaCekanju
                        || rezervacija.getStatusRezervacije() == Helper.StatusRezervacije.Aktivna
                        || rezervacija.getStatusRezervacije() == Helper.StatusRezervacije.Potvrdjena
                        || rezervacija.getStatusRezervacije() == Helper.StatusRezervacije.Otkazana) {
                    ukupnoNePlaceno += rezervacija.getCena();
                }
            }
        }

        JLabel lblukupnoNePlaceno = new JLabel("Ukupno za naplatu: ");
        lblukupnoNePlaceno.setBounds(50, 400, 150, 30);
        panelPregledajRezervacije.add(lblukupnoNePlaceno);

        JLabel lblUkupnoNePlacenoInt = new JLabel(String.valueOf(ukupnoNePlaceno));
        lblUkupnoNePlacenoInt.setBounds(200, 400, 150, 30);
        panelPregledajRezervacije.add(lblUkupnoNePlacenoInt);

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double ukupnoNePlaceno = 0.0;
                model.setRowCount(0);
                List<Rezervacija> rezervacijeZaPregled = rezervacijeP;
                for (Rezervacija rezervacija : rezervacijeZaPregled) {
                    if (rezervacija.getGost().getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {
                        if (rezervacija.getStatusRezervacije() != Helper.StatusRezervacije.NaCekanju
                                || rezervacija.getStatusRezervacije() != Helper.StatusRezervacije.Aktivna
                                || rezervacija.getStatusRezervacije() != Helper.StatusRezervacije.Potvrdjena
                                || rezervacija.getStatusRezervacije() != Helper.StatusRezervacije.Otkazana) {
                            ukupnoNePlaceno += rezervacija.getCena();
                        }
                        model.addRow(convertRezervacijaToRow(rezervacija));
                    }

                }
                lblUkupnoNePlacenoInt.setText(String.valueOf(ukupnoNePlaceno));

            }
        });

        ListSelectionModel model1 = table.getSelectionModel();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnOtkazi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int brojRezervacije = Integer.parseInt((String) model.getValueAt(selectedRow, 0));
                    List<Rezervacija> sveRezervacije = rezervacijeP;
                    for (Rezervacija rezervacija : sveRezervacije) {
                        if (rezervacija.getBrojRezervacije() == brojRezervacije) {
                            if (rezervacija.getStatusRezervacije() != Helper.StatusRezervacije.NaCekanju) {
                                JOptionPane.showMessageDialog(panelPregledajRezervacije, "Rezervacija nije na cekanju",
                                        "Greška",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            // We ask the user to confirm the cancellation
                            int dialogResult = JOptionPane.showConfirmDialog(panelPregledajRezervacije,
                                    "Da li ste sigurni da želite da otkažete rezervaciju? U slucaju otkazivanja novac nece biti refundiran.",
                                    "Potvrda otkazivanja",
                                    JOptionPane.YES_NO_OPTION);
                            if (dialogResult == JOptionPane.NO_OPTION) {
                                return;
                            }

                            rezervacija.setStatusRezervacije(Helper.StatusRezervacije.Otkazana);
                            rezervacija.setDatumPromeneStatusa(LocalDate.now());
                            rezervacija.setPromena(Helper.StatusRezervacije.Otkazana);

                            model.setValueAt(Helper.StatusRezervacije.Otkazana, selectedRow, 5);

                            break;
                        }
                    }
                    try {
                        App.upisiRezervacije("src/utils/rezervacije.csv", sveRezervacije);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(panelPregledajRezervacije, "Molimo odaberite rezervaciju.", "Greška",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelPregledajRezervacije.add(btnOtkazi);

        return panelPregledajRezervacije;

    }

    private List<String> dodatneUslugeToString(List<DodatnaUsluga> dodatneUsluge) {
        List<String> usluge = new ArrayList<>();
        for (DodatnaUsluga usluga : dodatneUsluge) {
            usluge.add(usluga.getNaziv());
        }

        return usluge;
    }

    private String[] convertRezervacijaToRow(Rezervacija rezervacija) {
        List<String> dodatneUsluge = new ArrayList<>();

        for (DodatnaUsluga usluga : rezervacija.getDodatneUsluge()) {
            dodatneUsluge.add(usluga.getNaziv());
        }

        String dodatneUslugeStr = String.join(";", dodatneUsluge);
        return new String[] { String.valueOf(rezervacija.getBrojRezervacije()),
                String.valueOf(rezervacija.getBrojGostiju()), rezervacija.getCheckInDatum().toString(),
                rezervacija.getCheckOutDatum().toString(), dodatneUslugeStr,
                rezervacija.getStatusRezervacije().name(), String.valueOf(rezervacija.getCena()) };
    }

}