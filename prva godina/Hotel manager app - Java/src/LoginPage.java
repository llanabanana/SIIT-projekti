import javax.swing.*;

import Controller.SobeController;
import Controller.RezervacijeController;
import Controller.SobariceController;
import Model.DodatnaUsluga;
import Model.Korisnik;
import Model.Rezervacija;
import Model.Soba;
import Model.SobaZaSpremanje;
import Model.Sobarica;
import Utils.Helper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import Model.Cenovnik;

public class LoginPage implements ActionListener {
    private JFrame frame;
    private JTextField korisnickoField;
    private JPasswordField lozinkaField;
    private JLabel poruka;
    private JTabbedPane tabPocetna;
    private JTabbedPane taboviAdmin;
    private JTabbedPane taboviGost;
    private JTabbedPane taboviRecepcioner;
    private JTabbedPane taboviSobarice;
    private JLabel label = new JLabel("Dobrodošli u hotel!");
    private JMenuBar meniBar = new JMenuBar();
    private JMenu menu = new JMenu("Meni");
    private JMenuItem item1 = new JMenuItem("Izloguj se");
    private JMenuItem item2 = new JMenuItem("Izadji");
    private Korisnik korisnik = new Korisnik();
    private List<Korisnik> proslKorisniciList;
    private List<Rezervacija> proslRezervacijeList;
    private List<Soba> proslSobeList;
    private List<DodatnaUsluga> proslDodatneUslugeList;
    private List<Cenovnik> proslCenovnik;
    private List<Sobarica> proslSobariceList;
    private RezervacijeController rezervacijeController;
    private SobariceController sobariceController;
    private List<SobaZaSpremanje> sobaZaSpremanjeList;

    public LoginPage(JFrame frame, List<Korisnik> proslKorisniciList, List<Rezervacija> proslRezervacijeList,
            List<Soba> proslSobeList, List<DodatnaUsluga> proslDodatneUslugeList,
            List<Cenovnik> proslCenovnik, List<Sobarica> proslSobariceList,
            RezervacijeController rezervacijeController, SobariceController sobariceController,
            List<SobaZaSpremanje> sobaZaSpremanjeList) {
        this.frame = frame;
        this.proslKorisniciList = proslKorisniciList;
        this.proslRezervacijeList = proslRezervacijeList;
        this.proslSobeList = proslSobeList;
        this.proslDodatneUslugeList = proslDodatneUslugeList;
        this.proslCenovnik = proslCenovnik;
        this.proslSobariceList = proslSobariceList;
        this.rezervacijeController = rezervacijeController;
        this.sobariceController = sobariceController;
        this.sobaZaSpremanjeList = sobaZaSpremanjeList;
    }

    public void initialize(List<Korisnik> proslKorisniciList, List<Rezervacija> proslRezervacijeList,
            List<Soba> proslSobeList,
            List<DodatnaUsluga> proslDodatneUslugeList, List<Cenovnik> proslCenovnik,
            SobeController sobeController, SobariceController sobariceController, List<Sobarica> proslSobariceList) {
        tabPocetna = new JTabbedPane();
        taboviAdmin = new JTabbedPane();
        taboviGost = new JTabbedPane();
        taboviRecepcioner = new JTabbedPane();
        taboviSobarice = new JTabbedPane();

        tabPocetna.setBounds(0, 0, 1024, 768);
        taboviAdmin.setBounds(0, 0, 1024, 768);
        taboviGost.setBounds(0, 0, 1024, 768);
        taboviRecepcioner.setBounds(0, 0, 1024, 768);
        taboviSobarice.setBounds(0, 0, 1024, 768);

        Color FG = new Color(196, 39, 123);

        label.setText("Dobrodošli u hotel!");
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(FG);
        label.setFont(new Font("Times new roman", Font.BOLD, 25));
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(412, 35, 204, 200);
        label.setIconTextGap(10);

        ImageIcon icon = new ImageIcon("hotelLogo.png");
        Image originalImage = icon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        label.setIcon(scaledIcon);

        JPanel panelLogin = createLoginPanel();
        tabPocetna.add("Login", panelLogin);

        panelLogin.add(label);

        AdminPanel adminPanel = new AdminPanel(taboviAdmin, proslKorisniciList, proslRezervacijeList, proslSobeList,
                proslDodatneUslugeList, proslCenovnik, rezervacijeController, sobeController, sobaZaSpremanjeList);
        adminPanel.initialize();

        RecepcionerPanel recepcionerPanel = new RecepcionerPanel(taboviRecepcioner, proslKorisniciList,
                proslRezervacijeList, proslSobeList, proslDodatneUslugeList, proslCenovnik, sobeController,
                sobariceController, proslSobariceList, rezervacijeController, sobaZaSpremanjeList);

        recepcionerPanel.initialize();

        meniBar.add(menu);
        menu.setFont(new Font("Times New Roman", Font.BOLD, 16));
        item1.setFont(new Font("Times New Roman", Font.BOLD, 16));
        item2.setFont(new Font("Times New Roman", Font.BOLD, 16));
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                korisnickoField.setText("");
                lozinkaField.setText("");
                poruka.setText("");

                taboviAdmin.setVisible(false);
                taboviGost.setVisible(false);
                tabPocetna.setVisible(true);
                taboviRecepcioner.setVisible(false);
                taboviSobarice.setVisible(false);
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(item1);
        menu.add(item2);

        frame.setJMenuBar(meniBar);
        frame.add(tabPocetna);
        frame.add(taboviAdmin);
        frame.add(taboviGost);
        frame.add(taboviRecepcioner);
        frame.add(taboviSobarice);

        tabPocetna.setVisible(true);
        taboviAdmin.setVisible(false);
        taboviGost.setVisible(false);
        taboviRecepcioner.setVisible(false);
        taboviSobarice.setVisible(false);
    }

    private JPanel createLoginPanel() {
        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(null);
        panelLogin.setBackground(new Color(255, 214, 237));

        JLabel korisnickoLabel = new JLabel("Korisnicko ime:");
        korisnickoLabel.setBounds(50, 260, 120, 25);
        korisnickoLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        panelLogin.add(korisnickoLabel);

        JLabel lozinkaLabel = new JLabel("Lozinka:");
        lozinkaLabel.setBounds(50, 310, 120, 25);
        lozinkaLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        panelLogin.add(lozinkaLabel);

        korisnickoField = new JTextField();
        korisnickoField.setBounds(180, 260, 165, 25);
        panelLogin.add(korisnickoField);

        lozinkaField = new JPasswordField();
        lozinkaField.setBounds(180, 310, 165, 25);
        panelLogin.add(lozinkaField);

        JButton loginButton = new JButton("Uloguj se");
        loginButton.setBounds(50, 400, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        panelLogin.add(loginButton);

        JButton resetButton = new JButton("Resetuj");
        resetButton.setBounds(200, 400, 100, 25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
        panelLogin.add(resetButton);

        poruka = new JLabel("");
        poruka.setBounds(470, 400, 500, 25);
        poruka.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelLogin.add(poruka);

        return panelLogin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Resetuj")) {
            korisnickoField.setText("");
            lozinkaField.setText("");
        }

        if (e.getActionCommand().equals("Uloguj se")) {
            String korisnicko = korisnickoField.getText();
            String lozinka = String.valueOf(lozinkaField.getPassword());

            List<Korisnik> korisniciList = new ArrayList<>(); // Declare and initialize the korisniciList variable
            korisniciList = proslKorisniciList;

            for (Korisnik k : korisniciList) {
                if (k.getKorisnickoIme().equals(korisnicko)) {
                    if (k.getLozinka().equals(lozinka)) {
                        korisnik = k;
                        poruka.setForeground(Color.GREEN);
                        poruka.setText("Uspesno ste se ulogovali!");
                        tabPocetna.setVisible(false);
                        if (k.getUloga().equals(Helper.Uloga.Administrator)) {
                            taboviAdmin.setVisible(true);
                            return;
                        } else if (k.getUloga().equals(Helper.Uloga.Gost)) {
                            GuestPanel guestPanel = new GuestPanel(taboviGost, proslRezervacijeList, proslSobeList,
                                    proslDodatneUslugeList, proslCenovnik, rezervacijeController);
                            guestPanel.initialize(korisnik);
                            taboviGost.setVisible(true);
                            return;
                        } else if (k.getUloga().equals(Helper.Uloga.Recepcioner)) {
                            taboviRecepcioner.setVisible(true);
                            return;
                        } else if (k.getUloga().equals(Helper.Uloga.Sobarica)) {
                            SobaricaPanel sobaricaPanel = new SobaricaPanel(taboviSobarice, proslSobeList,
                                    proslSobariceList, sobariceController);
                            sobaricaPanel.initialize(korisnik);
                            taboviSobarice.setVisible(true);
                            return;
                        }
                    } else {
                        poruka.setForeground(Color.RED);
                        poruka.setText("Pogresna lozinka!");
                        return;
                    }
                } else {
                    poruka.setForeground(Color.RED);
                    poruka.setText("Pogresno korisnicko ime!");
                }

            }
        }
    }

}
