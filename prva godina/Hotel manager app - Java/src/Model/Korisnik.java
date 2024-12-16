
package Model;

import Utils.Helper.Uloga;

public class Korisnik {

    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private int phone;
    private String adress;
    private Uloga uloga;

    public Korisnik() {
        super();
    }

    public Korisnik(String korisnickoIme, String lozinka, String ime, String lastName, String gender,
            String dateOfBirth, int phone, String adress, Uloga uloga) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.adress = adress;
        this.uloga = uloga;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public String getIme() {
        return ime;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getPhone() {
        return phone;
    }

    public String getAdress() {
        return adress;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    @Override
    public String toString() {
        return "Korisnik: \nkorisnickoIme=" + korisnickoIme + "\nlozinka=" + lozinka + "\nime=" + ime
                + "\nlastName=" + lastName + "\ngender=" + gender + "\ndateOfBirth=" + dateOfBirth + "\nphone=" + phone
                + "\nadress=" + adress + "\nuloga=" + uloga + "\n";
    }
}
