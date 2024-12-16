import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Controller.SobariceController;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Font;

import Model.Korisnik;
import Model.Soba;
import Model.Sobarica;
import Utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class SobaricaPanel {

    private JTabbedPane taboviSobarice;
    private List<Soba> proslSobeList;
    private List<Sobarica> proslSobariceList;
    private SobariceController sobariceController;

    public SobaricaPanel(JTabbedPane taboviSobarice, List<Soba> proslSobeList, List<Sobarica> proslSobariceList, SobariceController sobariceController) {
        this.taboviSobarice = taboviSobarice;
        this.proslSobeList = proslSobeList;
        this.proslSobariceList = proslSobariceList;
        this.sobariceController = sobariceController;
    }

    public void initialize(Korisnik korisnik) {
        taboviSobarice.removeAll();
        JPanel sobaricaSobePanel = CreateSobaricaSobePanel(korisnik);
        taboviSobarice.add("Sobe", sobaricaSobePanel);
    }

    private JPanel CreateSobaricaSobePanel(Korisnik korisnik) {

        JPanel sobaricaSobePanel = new JPanel();
        sobaricaSobePanel.setLayout(null);
        sobaricaSobePanel.setBackground(new Color(173, 216, 230));

        JLabel naslov = new JLabel("Sobe");
        naslov.setBounds(50, 50, 300, 30);
        naslov.setFont(new Font("Times New Roman", Font.BOLD, 24));
        sobaricaSobePanel.add(naslov);

        JButton spremiSobuBtn = new JButton("Spremi sobu");
        spremiSobuBtn.setBounds(600, 50, 150, 30);
        sobaricaSobePanel.add(spremiSobuBtn);

        // Kreiram tabelu soba koje pripadaju sobarici

        List<Soba> sobeZaSpremanje = new ArrayList<>();
        Korisnik sobaricaKorisnik = korisnik;
        for (Sobarica sobarica1 : proslSobariceList) {
            if (sobarica1.getKorisnickoIme().equals(sobaricaKorisnik.getKorisnickoIme())) {
                sobeZaSpremanje = sobarica1.getSobeZaSpremanje();
            }
        }

        if (sobeZaSpremanje == null) {
            JLabel nemaSoba = new JLabel("Nema soba za spremanje");
            nemaSoba.setBounds(50, 100, 300, 30);
            sobaricaSobePanel.add(nemaSoba);
            return sobaricaSobePanel;
        }

        String[] kolone = { "Broj Sobe", "Tip Sobe", "Status Sobe" };
        DefaultTableModel model = new DefaultTableModel(kolone, 0);
        JTable table = new JTable(model);

        for (Soba soba : sobeZaSpremanje) {
            model.addRow(new Object[] { soba.getBrojSobe(), soba.getTipSobe(), soba.getStatusSobe() });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 700, 300);
        sobaricaSobePanel.add(scrollPane);

        // ListSelectionModel model1 = table.getSelectionModel();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        spremiSobuBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int brojSobe = Integer.parseInt(String.valueOf(model.getValueAt(selectedRow, 0)));
                // String brojSobe = String.valueOf(model.getValueAt(selectedRow, 0)); // Convert int to String

                if(sobariceController.spremiSobu(brojSobe, proslSobeList, proslSobariceList, korisnik)){

                    JOptionPane.showMessageDialog(null, "Soba je uspesno spremljena");
                    try {
                        App.upisiSobe("src/utils/sobe.csv", proslSobeList);
                    } 
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    model.setValueAt("Spremljena", selectedRow, 2);
                    
                }
                else{
                    // Show error in dialog box
                }
            }
        });

        return sobaricaSobePanel;
    }

}
