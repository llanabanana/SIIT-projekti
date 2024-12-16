import datetime
import validacije
import pretrage
import izvestaji

# Kreiramo globalne matrice u kojima ćemo čuvati sve podatke
korisnici_matrica = []
filmovi_matrica = []
bioskopske_projekcije_matrica = []
karte_matrica = []
sale_za_projekcije_matrica = []
termini_projekcija_matrica = []
sedista_matrica = []

zanrovi = {'Drama', 'Triler', 'Komedija', 'Naucna Fantastika', 'Mjuzikl', 'Akcija', 'Horor'}
dani = ['Ponedeljak', 'Utorak', 'Sreda', 'Cetvrtak', 'Petak', 'Subota', 'Nedelja']
# Kreiramo globalnu promenljivu u kojoj čuvamo ulgoovanog korisnika, inicijalno zelimo imati neregistrovanog korisnika
# [0]-ime,  [1]-prezime,  [2]-korisnicko,  [3]-sifra, [4]-uloga
# Uloge: neregistrovan, kupac, prodavac, menadzer

prijavljeni_korisnik = ['', '', '', '', 'neregistrovan']
status_karte = ""


def ucitaj_podatke():
    global korisnici_matrica
    global filmovi_matrica
    global bioskopske_projekcije_matrica
    global sale_za_projekcije_matrica
    global karte_matrica
    # Otvaramo fajl kako bismo sve već postojeće podatke ubacili u matricu
    print('Učitavam podatke...')
    with open('korisnici.txt', 'r') as korisnici:
        for row in korisnici:
            korisnici_matrica.append(row.strip().split(';'))
    # Otvaramo fajl kako bismo sve već postojeće podatke ubacili u matricu
    with open('filmovi.txt', 'r') as filmovi:
        for row in filmovi:
            filmovi_matrica.append(row.strip().split(';'))
    # Otvaramo fajl kako bismo sve već postojeće podatke ubacili u matricu
    with open('bioskopske_projekcije.txt', 'r') as bioskopske_projekcije:
        for row in bioskopske_projekcije:
            bioskopske_projekcije_matrica.append(row.strip().split(';'))
    with open('sale_za_projekcije.txt', 'r') as sale_za_projekcije:
        for row in sale_za_projekcije:
            sale_za_projekcije_matrica.append(row.strip().split(';'))
    with open('bioskopske_karte.txt', 'r') as bioskopske_karte:
        for row in bioskopske_karte:
            karte_matrica.append(row.strip().split(';'))


### Automatsko popunjavanje termina projekcija
def popuni_termine_projekcija():
    # Termini ce se popunjavati za naredne dve nedelje od tekuceg datuma za sve bioskopske projekcije
    tekuci_datum = datetime.datetime.today()
    tekuci_dan_int = tekuci_datum.weekday()
    ascii_pocetak = 65
    for projekcija in bioskopske_projekcije_matrica:
        brojac = 0
        for i in range(1, 3):
            for dan in projekcija[4].split(','):
                dan_num = dani.index(dan.strip())
                if dan_num < tekuci_dan_int:
                    delta = datetime.timedelta(days=((dan_num - tekuci_dan_int) + 7 * i))
                    datum_termina = tekuci_datum + delta
                else:
                    delta = datetime.timedelta(days=((dan_num - tekuci_dan_int) + 7 * (i - 1)))
                    datum_termina = tekuci_datum + delta
                formatiran_datum = datum_termina.strftime("%d.%m.%Y")
                brojac += 1
                prvi_karakter = ascii_pocetak + (brojac - 1) // 26
                drugi_karakter = ascii_pocetak + (brojac - 1) % 26
                sifra_termina = projekcija[0] + chr(prvi_karakter) + chr(drugi_karakter)

                termini_projekcija_matrica.append([sifra_termina, formatiran_datum, dani[datum_termina.weekday()]])
    return


def popuni_sedista(projekcija):
    global sedista_matrica
    sedista_matrica = []
    ascii_pocetak = 65
    brojac = 0
    for termin in termini_projekcija_matrica:
        if projekcija == termin[0]:
            bioskopska_projekcija = termin[0][:4]
            break
    for redovi in bioskopske_projekcije_matrica:
        if bioskopska_projekcija == redovi[0]:
            sifra_sale = redovi[1]
            break
    for redovi in sale_za_projekcije_matrica:
        if sifra_sale == redovi[0]:
            redova = redovi[2]
            kolona = redovi[3]
            break
    red_lista = []
    for i in range(int(redova)):
        for j in range(int(kolona)):
            slovo = chr(ascii_pocetak + i)
            broj_sedista = slovo + str(j)
            red_lista.append(broj_sedista)
        sedista_matrica.append(red_lista)
        red_lista = []
    return ()


def sacuvaj_podatke_u_fajl():
    global korisnici_matrica
    global filmovi_matrica
    global bioskopske_projekcije_matrica
    global sale_za_projekcije_matrica
    global karte_matrica
    # Nakon obrade podataka u matrici, prepisujemo je u fajl
    with open('korisnici.txt', 'w') as korisnici:
        for row in korisnici_matrica:
            korisnici.write(';'.join(row) + '\n')
    with open('filmovi.txt', 'w') as filmovi:
        for row in filmovi_matrica:
            filmovi.write(';'.join(row) + '\n')
    with open('bioskopske_projekcije.txt', 'w') as bioskopske_projekcije:
        for row in bioskopske_projekcije_matrica:
            bioskopske_projekcije.write(';'.join(row) + '\n')
    with open('sale_za_projekcije.txt', 'w') as sale_za_projekcije:
        for row in sale_za_projekcije_matrica:
            sale_za_projekcije.write(';'.join(row) + '\n')
    with open('bioskopske_karte.txt', 'w') as bioskopske_karte:
        for row in karte_matrica:
            bioskopske_karte.write(';'.join(row) + '\n')


def sacuvaj_kartu(termin_projekcije, sediste, uneto_korisnicko, uneto_ime, uneto_prezime, status_karte, obrisana):
    global karte_matrica
    datum = datetime.date.today().strftime('%d.%m.%Y')
    ime = prijavljeni_korisnik[0]
    prezime = prijavljeni_korisnik[1]

    if prijavljeni_korisnik[4] != "prodavac":
        if (prijavljeni_korisnik[4] != "neregistrovan"):
            korisnicko = prijavljeni_korisnik[2]
        else:
            korisnicko = "neregistrovan"
        ime_prodavca = "/"

    else:
        korisnicko = uneto_korisnicko
        ime = uneto_ime
        prezime = uneto_prezime
        ime_prodavca = prijavljeni_korisnik[2]

    for projekcije in bioskopske_projekcije_matrica:
        if termin_projekcije[:4] == projekcije[0]:
            cena = projekcije[6]
            cena = int(cena)
            promena = promena_cene(termin_projekcije)
            if promena == -1:
                cena = cena - 50
            elif promena == 1:
                cena = cena + 50

            lojalnost= kartica_lojalnosti(korisnicko)
            if lojalnost:
                cena=cena*0.9
            cena=round(cena)
            cena = str(cena)
            break
    karte_matrica.append(
        [ime, prezime, korisnicko, datum, termin_projekcije, sediste, status_karte, ime_prodavca, cena, 'False'])


def sacuvaj_korisnika(ime, prezime, korisnicko_ime, password, uloga):
    # Funklcija za sacuvavanje novih korisnika/izmenjenih podataka korisnika u matricu

    # Proveravamo da li je korisnik vec postojeci i samo menja podatke
    for user in korisnici_matrica:
        if user[2] == korisnicko_ime:
            # Menjamo podatke
            user[0] = ime
            user[1] = prezime
            user[3] = password
            user[4] = uloga
            break
    # Ukoliko je korisnik nov dodajemo ga u matricu
    else:
        korisnici_matrica.append([ime, prezime, korisnicko_ime, password, uloga])


#
# Slede funkcije koje se pozivaju iz menija
#
def izlaz_iz_aplikacije():
    # Pre nego što napustimo program, sačuvavamo podatke iz matrice u fajl
    print("Napuštate aplikaciju. Sve promene će sada biti snimljene u bazu.")
    sacuvaj_podatke_u_fajl()
    exit()


def prijava_na_sistem():
    print("Odabrali ste prijavu na sistem.")
    global prijavljeni_korisnik

    korisnicko_ime = input("Unesite svoje korisnicko ime: ")
    # While petlja koja će se vrteti sve dok ne unesemo postojeće korisničko ime ili jednicu za povratak na glavni meni
    while not validacije.korisnicko_postoji(korisnicko_ime, korisnici_matrica):
        korisnicko_ime = input(
            "Ovo korisničko ime ne postoji. Unesite 'q' kako biste se vratili na glavni meni ili unesite svoje korisničko ime: ")
        if korisnicko_ime == 'q':
            return

    # While petlja sve dok ne unesemo lozinku koja odgovara unetom korisnickom imenu
    while True:
        password = input("Unesite ispravnu lozinku: ")
        # For petlja koja prolazi kroz korisnike dok ne nadje red u kom se korisnicko ime i uneta lozinka poklapaju
        for row in korisnici_matrica:
            temp_ki = row[2]
            temp_pass = row[3]
            if temp_ki == korisnicko_ime and password == temp_pass:
                prijavljeni_korisnik = row
                print(f'\nUspešno ste se ulogovali {prijavljeni_korisnik[0]} {prijavljeni_korisnik[1]}.'
                      f'\nVaša uloga je {prijavljeni_korisnik[4]}')
                return


def registracija():
    # Registracija
    global prijavljeni_korisnik
    # While petlje za unos imena i prezimena kako ne bi bio unesen prazan string

    while True:
        temp_ime = input("Unesite ime: ").title()
        if (temp_ime != ''):
            ime = temp_ime
            break

    while True:
        temp_prezime = input("Unesite prezime: ").title()
        if (temp_prezime != ''):
            prezime = temp_prezime
            break

    # Unosi se korisnicko ime, uz proveru njegove jedinstvenosti u sistemu
    korisnicko_ime = input("Unesite jedinstveno korisnicko ime: ")
    while (validacije.korisnicko_postoji(korisnicko_ime, korisnici_matrica)) or (korisnicko_ime == ''):
        korisnicko_ime = input("Uneto korisničko ime je već zauzeto, unesite jedinstveno korisničko ime:")

    # Unosi se lozinka, uz proveru da li je dovoljno dugačka i da li sadži cifru
    password = input("Unesite lozinku koja sadrži više od 6 karaktera i bar jednu cifru:")
    while not validacije.lozinka_valid(password):
        password = input("Unesite lozinku koja sadrži više od 6 karaktera i bar jednu cifru:")

    # Ulogu definišemo posebno
    if (prijavljeni_korisnik[4] == 'menadzer'):
        print("Odaberite ulogu:\n1)Prodavac\n2)Menadzer")
        while True:
            uputstvo = input("Odaberi: ")
            if (uputstvo == '1'):
                uloga = 'prodavac'
                break
            elif (uputstvo == '2'):
                uloga = 'menadzer'
                break
    else:
        # Meni registracija je omogucen samo menadzeru i neregistrovanom pa je prema tome dozvoljena samo uloga kupac u nastavku
        uloga = 'kupac'
        # Ako se registruje neregistrovani korisnik odmah cemo ga i definisati kao prijavljenog. Za menadzera to ne vazi
        prijavljeni_korisnik = [ime, prezime, korisnicko_ime, password, uloga]
        print(f'\nUspešno ste se registrovali {prijavljeni_korisnik[0]} {prijavljeni_korisnik[1]}.'
              f'\nVaša uloga je {prijavljeni_korisnik[4]}')

    sacuvaj_korisnika(ime, prezime, korisnicko_ime, password, uloga)


def odjava():
    global prijavljeni_korisnik
    prijavljeni_korisnik = ['', '', '', '', 'neregistrovan']


def izmena_podataka():
    global prijavljeni_korisnik
    ime = prijavljeni_korisnik[0]
    prezime = prijavljeni_korisnik[1]
    korisnicko_ime = prijavljeni_korisnik[2]
    password = prijavljeni_korisnik[3]
    uloga = prijavljeni_korisnik[4]
    print('Izmenite svoje podatke:')
    novo_ime = input('Ime ({})'.format(ime))
    # U svakom ifu proveravamo da li je unos prazan string-ukoliko jeste neće doći ni do kakve promene
    if (novo_ime != ''):
        ime = novo_ime
    novo_prezime = input('Prezime ({})'.format(prezime))
    if (novo_prezime != ''):
        prezime = novo_prezime
    nov_passw = input('Lozinka ({})'.format(password))
    if (nov_passw != ''):
        # I nova lozinka mora biti validna, vrsimo proveru
        while not validacije.lozinka_valid(nov_passw) & (nov_passw != ''):
            nov_passw = input("Nova lozinka mora da sadrži više od 6 karaktera i bar jednu cifru:")
        password = nov_passw

    # sacuvavamo izmenjene podatke u matricu
    sacuvaj_korisnika(ime, prezime, korisnicko_ime, password, uloga)
    print("Uspešno ste izmenili svoje podatke. Vaši ažurirani podaci su: {}, {}, {}\n".format(ime, prezime, password))
    glavni_meni()


def menadzment_filmova():
    imena_filmova = {film[0].strip() for film in filmovi_matrica}

    # pretrage.ispis_filmova(filmovi_matrica)
    while True:
        print('\nIzaberite jednu od ponuđenih stavki:')
        print('1) Unos filma')
        print('2) Izmena filma')
        print('3) Brisanje filma')
        print('4) Povratak u glavni meni')
        stavka = input()
        if stavka == '1':
            unos_filma()
        elif stavka == '2':
            izmena_filma()
        elif stavka == '3':
            print('\nDostupni filmovi:')
            print(', '.join(imena_filmova))
            while True:
                naziv = input('Unesite naziv filma koji zelite obrisati:')
                if naziv == 'q':
                    break
                elif naziv.strip() not in imena_filmova:
                    print("Naziv filma nije pronadjen. Molimo pokusajte ponovo.")
                else:
                    brisanje_filma(naziv)
                    break
        elif stavka == '4':
            glavni_meni()
        else:
            print('Neispravan unos.')


def unos_filma():
    print('Unesite sve tražene podatke:')
    naziv = input('Naziv filma:').title()
    print('Dostupni zanrovi:')
    print(', '.join(zanrovi))
    while True:
        zanr = input('Zanr filma:').title()
        if (zanr in zanrovi):
            break
        else:
            print('Neispravan unos.')
    trajanje = input('Trajanje filma:')
    reziser = input('Reziser filma:').title()
    uloge = input('Uloge (odvojiti ih zarezom):').title()
    zemlja = input('Zemlja:').title()
    godina = input('Godina:')
    sacuvaj_film(naziv, zanr, trajanje, reziser, uloge, zemlja, godina)


def sacuvaj_film(naziv, zanr, trajanje, reziser, uloge, zemlja, godina):
    global filmovi_matrica
    for film in filmovi_matrica:
        if film[0].strip().lower() == naziv.strip().lower():
            # Menjamo podatke
            film[1] = zanr
            film[2] = trajanje
            film[3] = reziser
            film[4] = uloge
            film[5] = zemlja
            film[6] = godina
            break
    # Ukoliko je film nov dodajemo ga u matricu
    else:
        filmovi_matrica.append([naziv, zanr, trajanje, reziser, uloge, zemlja, godina])
        print('Podaci su uspesno snimljeni.')
    return


def izmena_filma():
    global filmovi_matrica
    imena_filmova = {film[0].strip() for film in filmovi_matrica}

    print('\nDostupni filmovi:')
    print(', '.join(imena_filmova))

    tren_film = input('\nUnesite naslov filma koji zelite da menjate:').title()

    if tren_film.strip() not in imena_filmova:
        print("Naziv filma nije pronadjen. Molimo pokusajte ponovo.")
    else:
        for film in filmovi_matrica:
            if tren_film.strip().lower() == film[0].strip().lower():
                naziv = film[0].title()
                zanr = film[1].title().strip()
                trajanje = film[2].title().strip()
                reziser = film[3].title().strip()
                uloge = film[4].title().strip()
                zemlja = film[5].title().strip()
                godina = film[6].title().strip()

                print('Izmenite podatke filma:')
                print('Dostupni zanrovi:')
                print(', '.join(zanrovi))
                while True:
                    novi_zanr = input('Zanr ({}):'.format(zanr)).title()
                    if (novi_zanr in zanrovi):
                        break
                    elif novi_zanr == '':
                        break
                    else:
                        print('Neispravan unos.')
                # U svakom ifu proveravamo da li je unos prazan string-ukoliko jeste neće doći ni do kakve promene
                if (novi_zanr != ''):
                    zanr = novi_zanr
                novo_traj = input('Trajanje ({}):'.format(trajanje))
                if (novo_traj != ''):
                    trajanje = novo_traj
                n_reziser = input('Reziser ({}):'.format(reziser)).title()
                if (n_reziser != ''):
                    reziser = n_reziser
                n_uloge = input('Uloge ({}):'.format(uloge)).title()
                if (n_uloge != ''):
                    uloge = n_uloge
                novo_zemlja = input('Zemlja ({}):'.format(zemlja)).title()
                if (novo_zemlja != ''):
                    zemlja = novo_zemlja
                novo_god = input('Godina ({}):'.format(godina))
                if (novo_god != ''):
                    godina = novo_god
                # sacuvavamo izmenjene podatke u matricu
                sacuvaj_film(naziv, zanr, trajanje, reziser, uloge, zemlja, godina)
                return


def brisanje_filma(naziv_filma):
    global filmovi_matrica
    # Ukoliko film postoji u bioskopskim projekcijama, necemo dozvoliti brisanje zbog konzistentnosti
    filmovi_u_projekcijama = {film[5].strip().lower() for film in bioskopske_projekcije_matrica}
    if naziv_filma.strip().lower() in filmovi_u_projekcijama:
        print(f'Film {naziv_filma.upper()} se koristi u bioskopskim projekcijama i brisanje nije dozvoljeno!')
        return

    obrisan = False
    i = 0
    for film in filmovi_matrica:
        if (film[0].strip().lower() == naziv_filma.strip().lower()):
            del filmovi_matrica[i]
            obrisan = True
            print(f'Film {naziv_filma.upper()} je uspesno obrisan!')
            return
        i += 1
    if not obrisan:
        print('Film nije pronadjen u bazi. Brisanje neuspesno!')
        return


def menadzment_bp():
    while True:
        print('\nIzaberite jednu od ponuđenih stavki:')
        print('1) Kreiranje bioskopske projekcije')
        print('2) Izmena bioskopske projekcije')
        print('3) Brisanje bioskopske projekcije')
        print('4) Povratak u glavni meni')
        stavka = input()
        if stavka == '1':
            unos_bp()
        elif stavka == '2':
            izmena_bp()
        elif stavka == '3':
            sifra = input('Unesite sifru bioskopske projekcije koju zelite obrisati:')
            brisanje_bp(sifra)
        elif stavka == '4':
            return
        else:
            print('Neispravan unos.')


def unos_bp():
    print('Unesite sve tražene podatke:')
    validna_sifra = False
    while validna_sifra == False:
        sifra = input('Cetvorocifrena sifra:')
        if (len(sifra) == 4 and sifra.isdigit() == True):
            for projekcije in bioskopske_projekcije_matrica:
                if (sifra == projekcije[0]):
                    validna_sifra = False
                    break
                else:
                    validna_sifra = True
        else:
            print('Neispravan unos.')
    while True:
        sala_bp = input('Sala projekcije:').title()
        if validacije.sala_postoji(sala_bp, sale_za_projekcije_matrica) == True:
            break
        else:
            print('Uneta nepostojeca sala. Pokusajte ponovo.')

    while True:
        pocetak = input('Vreme pocetka projekcije (hh:mm):')
        kraj = input('Vreme kraja projekcije (hh:mm):')
        dani = input('Dani prikazivanja projekcije (odvojiti zarezom):').title()

        if validacije.projekcija_zauzeta(pocetak, kraj, dani, sala_bp, bioskopske_projekcije_matrica):
            print('Vreme projekcije je već zauzeto')
        else:
            break
    while True:
        film_bp = input('Film:').title()
        if validacije.film_postoji(film_bp, filmovi_matrica) == True:
            if (validacije.duzina_filma_odgovara(pocetak, kraj, film_bp, filmovi_matrica) == True):
                break
            else:
                print('Film ne odgovara trajanju projekcije.')
                continue
        else:
            print('Unet je nepostojeci film. Pokusajte ponovo.')

    while True:
        cena_karte = input('Cena karte:')
        if (cena_karte.isdigit() == True):
            break
        else:
            print('Neispravan unos.')
    sacuvaj_bp(sifra, sala_bp, pocetak, kraj, dani, film_bp, cena_karte)


def sacuvaj_bp(sifra, sala, pocetak, kraj, dani, film, cena):
    global bioskopske_projekcije_matrica
    for projekcija in bioskopske_projekcije_matrica:
        if projekcija[0] == sifra:
            # Menjamo podatke
            projekcija[1] = sala
            projekcija[2] = pocetak
            projekcija[3] = kraj
            projekcija[4] = dani
            projekcija[5] = film
            projekcija[6] = cena
            break
    # Ukoliko je bp nov dodajemo ga u matricu
    else:
        bioskopske_projekcije_matrica.append([sifra, sala, pocetak, kraj, dani, film, cena])
    return


def izmena_bp():
    tren_projekcija = input('Unesite sifru projekcije koju zelite da menjate:')
    for projekcija in bioskopske_projekcije_matrica:
        if tren_projekcija == projekcija[0]:
            sifra = projekcija[0].title()
            sala = projekcija[1].title().strip()
            pocetak = projekcija[2].title().strip()
            kraj = projekcija[3].title().strip()
            dani = projekcija[4].title().strip()
            film = projekcija[5].title().strip()
            cena = projekcija[6].title().strip()
            while True:
                print('Izmenite podatke projekcije:')
                while True:
                    nova_sala = input('Sala ({}):'.format(sala)).title()
                    if validacije.sala_postoji(nova_sala, sale_za_projekcije_matrica) == True:
                        sala = nova_sala
                        break
                    else:
                        print('Uneta mepostojeca sala. Pokusajte ponovo.')
                # U svakom ifu proveravamo da li je unos prazan string-ukoliko jeste neće doći ni do kakve promene
                nov_pocetak = input('Pocetak projekcije ({}):'.format(pocetak))
                if (nov_pocetak != ''):
                    pocetak = nov_pocetak
                nov_kraj = input('Kraj projekcije ({}):'.format(kraj))
                if (nov_kraj != ''):
                    kraj = nov_kraj
                novi_dani = input('Dani prikazivanja ({}):'.format(dani)).title()
                if (novi_dani != ''):
                    dani = novi_dani
                while True:
                    nov_film = input('Film ({}):'.format(film)).title()
                    if (validacije.film_postoji(nov_film, filmovi_matrica) == True):
                        film = nov_film
                        break
                    elif (nov_film == ''):
                        break
                    else:
                        print('Unet nepostojeci film. Pokusajte ponovo.')
                while True:
                    nova_cena = input('Cena karte ({}):'.format(cena))
                    if (nova_cena.isdigit() == True):
                        cena = nova_cena
                        break
                    elif (nova_cena == ''):
                        break
                    else:
                        print('Neispravan unos.')

                print('Podaci ce biti sacuvani.')
                break

            # sacuvavamo izmenjene podatke u matricu
            sacuvaj_bp(sifra, sala, pocetak, kraj, dani, film, cena)
            break


def brisanje_bp(sifra):
    global bioskopske_projekcije_matrica
    obrisan = False
    i = 0
    for bp in bioskopske_projekcije_matrica:
        if (bp[0].strip() == sifra.strip()):
            del bioskopske_projekcije_matrica[i]
            obrisan = True
            print(f'Projekcija sa sifrom {sifra} je uspesno obrisana!')

            ## Posto se projekcije koriste u terminima projekcija pokrecemo ponovo automatsko
            ## generisanje projekcija za naredne 2 nedelje
            termini_projekcija_matrica.clear()
            popuni_termine_projekcija()
            return
        i += 1
    if not obrisan:
        print('Projekcija nije pronadjena u bazi. Brisanje neuspesno!')
        return
    return


def rezervacija_karte():
    global status_karte
    print("Postojeci termini projekcija:")
    pretrage.prikaz_svih_termina_projekcija(termini_projekcija_matrica, bioskopske_projekcije_matrica)
    zeljena_projekcija = ""
    while validacije.termin_projekcije_postoji(zeljena_projekcija, termini_projekcija_matrica) == False:
        zeljena_projekcija = input("Unesite sifru projekcije za koju zelite da rezervisete kartu:").upper()
    popuni_sedista(zeljena_projekcija)
    prodavac_unos = ""
    uneto_korisnicko = ""
    ime = ''
    prezime = ''
    if prijavljeni_korisnik[4] == "prodavac":
        dalje = False
        while dalje == False:
            print("Unesite odgovarajuci broj. \nRezervisem kartu za: \n1)Registrovanog kupca \n2)Neregistrovanog kupca")
            prodavac_unos = input()
            if prodavac_unos == "1":
                while validacije.korisnicko_postoji(uneto_korisnicko, korisnici_matrica) == False:
                    print("Unesite korisnicko ime kupca:")
                    uneto_korisnicko = input()
                if kartica_lojalnosti(uneto_korisnicko):
                    print('Korisnik ostvaruje pravo na popust za karticu lojalnosti!')
                for rows in korisnici_matrica:
                    if rows[2] == uneto_korisnicko:
                        ime = rows[0]
                        prezime = rows[1]
                dalje = True
            elif prodavac_unos == "2":
                uneto_korisnicko = "neregistrovan"
                print("Unesite ime i prezime kupca:")
                ime = input("Ime:").title()
                prezime = input("Prezime:").title()
                dalje = True
            else:
                dalje = False
    else:
        if kartica_lojalnosti(prijavljeni_korisnik[2]):
            print('Ostvarujete pravo na popust za karticu lojalnosti! (10% popusta na svaku kartu)')
    ispis_karata(zeljena_projekcija)
    jos_karata=True
    while jos_karata==True:
        zeljeno_sediste = ""
        while validacije.sediste_slobodno(zeljena_projekcija, zeljeno_sediste, sedista_matrica, karte_matrica) == False:
            zeljeno_sediste = input("Unesite broj sedista za koje zelite da rezervisete kartu ili q za povratak na meni:").upper()
            if zeljeno_sediste=="Q":
                return
            status_karte = "rezervisana"
        sacuvaj_kartu(zeljena_projekcija, zeljeno_sediste, uneto_korisnicko, ime, prezime, status_karte, 'False')
        print("Karta je uspesno rezervisana")
        jos = input("Da li zelite da nastavite rezervaciju karata? (da/ne)")
        if jos == "da":
            jos_karata = True
        else:
            jos_karata = False

    input("Pritisni bilo koji taster...")
    return

def ponistavanje_prodavac():
    global karte_matrica
    pretrage.ispis_svih_karata(karte_matrica,bioskopske_projekcije_matrica)
    print('Unesite termin i sediste karte koju zelite da otkazete')
    dalje = False
    while dalje == False:
        termin = input("Termin:").upper()
        sediste = input("Sediste:").upper()
        i = 0
        for karta in karte_matrica:
            if (termin == karta[4]) and (sediste == karta[5]):
                dalje = True
                if karta[9]=="False":
                    del karte_matrica[i]
                    print("Karta je uspesno ponistena!")
                    break
            i += 1
    return

def ponistavanje_rezervacije():
    global karte_matrica
    print('Vase karte:')
    rezervisane = pretrage.pregled_rezervisanih_karata(karte_matrica, bioskopske_projekcije_matrica,
                                                       termini_projekcija_matrica, prijavljeni_korisnik)
    if rezervisane == []:
        print("Niste rezervisali ni jednu kartu")
        return
    print('Unesite termin i sediste karte koju zelite da otkazete')
    dalje = False
    while dalje == False:
        termin = input("Termin:").upper()
        sediste = input("Sediste:").upper()
        i = 0
        for karta in rezervisane:
            if (termin == karta[0]) and (sediste == karta[1]):
                dalje = True
                for redovi in karte_matrica:
                    if (karta[0] == redovi[4]) and (karta[1] == redovi[5]):
                        del karte_matrica[i]
                        break
                    i += 1

                print("Rezervacija je uspesno ponistena")
                break
    return


def prikaz_karata():
    print('Unesite termin za koji zelite prikaz karata:')
    pretrage.prikaz_svih_termina_projekcija(termini_projekcija_matrica, bioskopske_projekcije_matrica)
    while True:
        zeljeni_termin = input().upper()
        for termin in termini_projekcija_matrica:
            if zeljeni_termin == termin[0]:
                popuni_sedista(zeljeni_termin)
                ispis_karata(zeljeni_termin)

                input("Pritisni bilo koji taster...")
                return

def izmena_karata():
    global karte_matrica
    pretrage.pregled_karata_prodavac(karte_matrica, bioskopske_projekcije_matrica)
    print('Unesite termin i sediste karte koju zelite da otkazete')
    dalje = False
    while dalje == False:
        zeljeni_termin = input("Termin:").upper()
        sediste = input("Sediste:").upper()
        i = -1

        for karte in karte_matrica:
            i += 1
            if zeljeni_termin == karte[4] and sediste == karte[5] and karte[9] == 'False':
                dalje = True

                pretrage.prikaz_svih_termina_projekcija(termini_projekcija_matrica, bioskopske_projekcije_matrica)
                while True:
                    novi_termin = input("Unesite novi termin projekcije:").upper()
                    if validacije.termin_projekcije_postoji(novi_termin, termini_projekcija_matrica):
                        karte_matrica[i][4] = novi_termin
                        zeljeni_termin = novi_termin
                        break
                    elif novi_termin == "":
                        break
                if karte[2] == "neregistrovan":
                    novo_ime = input("Unesite izmenu imena:").title()
                    if novo_ime != "":
                        karte_matrica[i][0] = novo_ime
                    novo_prezime = input("Unesite izmenu prezimena:").title()
                    if novo_prezime != "":
                        karte_matrica[i][1] = novo_prezime
                while True:
                    popuni_sedista(zeljeni_termin)
                    ispis_karata(zeljeni_termin)
                    novo_sediste = input("Unesite zeljeno sediste:").upper()
                    if validacije.sediste_slobodno(zeljeni_termin, novo_sediste, sedista_matrica, karte_matrica) or novo_sediste == karte[5]:
                        karte_matrica[i][5] = novo_sediste
                        break
    print("Karta je uspesno izmenjena")
    return

def ispis_karata(termin):
    zauzete = []
    for karta in karte_matrica:
        if termin == karta[4] and karta[9] == 'False':
            zauzete.append(karta[5])

    for redova in sedista_matrica:
        for i, sedista in enumerate(redova):
            if redova[i] in zauzete:
                str = len(sedista) - 1

                redova[i] = "X" + (" " * str)
    for redova in sedista_matrica:
        print(" ".join(redova))

    return


def direktna_prodaja():
    pretrage.prikaz_svih_termina_projekcija(termini_projekcija_matrica, bioskopske_projekcije_matrica)
    termin = ""
    while not validacije.termin_projekcije_postoji(termin, termini_projekcija_matrica):
        termin = input("Unesite zeljeni termin projekcije:").upper()
    popuni_sedista(termin)
    dalje = False
    korisnicko = ""
    while not dalje:
        izbor = input("Prodajete kartu: \n1)Registrovanom kupcu \n2)Nergistrovanom kupcu\n")
        if izbor == "1":
            while not validacije.korisnicko_postoji(korisnicko, korisnici_matrica):
                korisnicko = input("Unesite korisnicko ime kupca:")
                for rows in korisnici_matrica:
                    if rows[2] == korisnicko:
                        ime = rows[0]
                        prezime = rows[1]
                        if kartica_lojalnosti(korisnicko):
                            print('Korisnik ostvaruje pravo na popust za karticu lojalnosti!')
                        break
            dalje = True
        elif izbor == "2":
            korisnicko = "neregistrovan"
            print("Unesite ime i prezime kupca:")
            ime = input("Ime:").title()
            prezime = input("Prezime:").title()
            dalje = True
        else:
            dalje = False
    ispis_karata(termin)
    jos_karata=True
    while jos_karata==True:
        zeljeno_sediste=""
        while not validacije.sediste_slobodno(termin, zeljeno_sediste, sedista_matrica, karte_matrica):
            zeljeno_sediste = input("Unesite zeljeno sediste ili q za povratak na meni:").upper()
            if zeljeno_sediste=="Q":
                return
        status = 'prodata'
        sacuvaj_kartu(termin, zeljeno_sediste, korisnicko, ime, prezime, status, 'False')
        print("Karta je uspesno prodata")
        jos=input("Da li zelite da nastavite prodaju  karata za ovog korisnika? (da/ne)")
        if jos=="da":
            jos_karata=True
        else:
            jos_karata=False

    input("Pritisni bilo koji taster...")
    return

def prodaja_rezervisana_karte():
    global karte_matrica
    pretrage.pregled_karata_prodavac(karte_matrica, bioskopske_projekcije_matrica)
    termin = ""
    sediste = ""
    termini = []
    sedista = []
    for red in karte_matrica:
        termini.append(red[4])

    while not termin in termini:
        termin = input("Unesite termin za koji prodajete kartu:").upper()
    for red in karte_matrica:
        if termin == red[4] and red[9] == 'False':
            sedista.append(red[5])
    while not sediste in sedista:
        sediste = input("Unesite zeljeno sediste:").upper()
    i = -1
    for red in karte_matrica:
        i += 1
        if termin == red[4] and sediste == red[5] and  red[9] == 'False':
            karte_matrica[i][6] = "prodata"
            karte_matrica[i][3] = datetime.date.today().strftime('%d.%m.%Y')
            karte_matrica[i][7] = prijavljeni_korisnik[2]
            print("Karta je uspesno prodata")
    return


def promena_cene(termin):
    promena=0
    for bp in termini_projekcija_matrica:
        if termin == bp[0]:
            datum=bp[1]
            datum_dt=datetime.datetime.strptime(datum, '%d.%m.%Y')
            dan_u_nedelji=datum_dt.weekday()
            if dan_u_nedelji==1:
                promena=-1
            elif dan_u_nedelji==5:
                promena=1
            break
    return(promena)

def kartica_lojalnosti(korisnicko):
    potroseno=0
    godinu_dana=datetime.timedelta(days=365)
    danasnji_datum=datetime.date.today().strftime('%d.%m.%Y')
    danasnji_datum=datetime.datetime.strptime(danasnji_datum,"%d.%m.%Y")
    for k in karte_matrica:
        datum_prodaje=k[3]
        datum_prodaje=datetime.datetime.strptime(datum_prodaje,"%d.%m.%Y")
        razlika=abs(danasnji_datum-datum_prodaje)
        if k[2]==korisnicko and k[6]=="prodata"and razlika<=godinu_dana:
            potroseno=potroseno+int(k[8])
    if potroseno>=5000:
        return True
    else:
        return False


def automatsko_ponistavanje_rezervacija():
    global karte_matrica
    for termin in termini_projekcija_matrica:
        termin_datum = datetime.datetime.strptime(termin[1], "%d.%m.%Y").date()
        if termin_datum == datetime.datetime.today().date():
            bp = next((bp_red for bp_red in bioskopske_projekcije_matrica if bp_red[0] == termin[0][:4]), None)
            bp_poc = str(bp[2]).split(':')
            vreme_pocetka = int(bp_poc[0]) * 60 + int(bp_poc[1])
            vreme_sada = datetime.datetime.now().hour * 60 + datetime.datetime.now().minute
            vreme_razlika = vreme_pocetka - vreme_sada   # Racunamo razliku vremena u minutima, sve sto je manje od 30 je za ponistavanje

            brojac = 0
            for karta in karte_matrica:
                if karta[9] == 'False' and vreme_razlika <= 30 and karta[4] == termin[0] and karta[6] == 'rezervisana':
                    # Karta ispunjava uslove za brisanje
                    print(f'Karta za brisanje: {karta[2]}, {karta[3]}, pocetak filma je: {bp[2]}, izracunata razlika je: {vreme_razlika}')
                    del karte_matrica[brojac]
                brojac += 1

    input("Pritisni bilo koji taster...")
    return


def glavni_meni():
    # While petlja koja će se vrteti sve dok ne unesemo validan unos
    while True:
        uloga = prijavljeni_korisnik[4]
        # Prvih 6 funkcionalnosti su zajedničke za sve korisnike
        print('\n')
        print("1) Prijava na sistem")
        print("2) Izlaz iz aplikacije")
        print("3) Pregled dostupnih filmova")
        print("4) Pretraga filmova")
        print("5) Višekriterijumska pretraga filmova")
        print("6) Pretraga termina bioskopskih projekcija")
        # Registracija je omogućena neregistrovanim korisnicima kao i  menadžerima. Ulogovani kupac i prodavac nemaju ovu opciju
        if (uloga == 'neregistrovan' or uloga == 'menadzer'):
            print("7) Registracija")
        # Funkcionalnosti za sve registrovane (ulogovane) korisnike
        if not (uloga == 'neregistrovan'):
            print("8) Odjava sa sistema")
            print("9) Izmena ličnih podataka")
        # Funkcionalnosti koje po nazivu su iste za kupca i prodavca ali će pareametar odrediti drugačiji tok u samoj funkciji
        if (uloga == 'prodavac' or uloga == 'kupac'):
            print("10) Rezervacija karata")
            print("11) Pregled rezervisanih karata")
            print("12) Poništavanje rezervacije karata")
        if (uloga == 'prodavac'):
            print("13) Pretraga karata")
            print("14) Direktna prodaja karata")
            print("15) Prodaja rezervisane karte")
            print("16) Izmena karte")
            print("17) Poništavanje rezervacije pola sata pre počekta projekcije")
        if (uloga == 'menadzer'):
            print("18) Izveštavanje")
            print("19) Popusti za karticu lojalnosti")
            print("20) Prikaz sedišta u obliku matrice")
            print("21) Promena cene karata")
            print('22) Unos, izmena i brisanje filmova')
            print('23) Unos, izmena i brisanje bioskopskih projekcija')
            print('24) Prikaz svih termina projekcija')
        uputstvo = input()

        if uputstvo == '1':
            prijava_na_sistem()
        elif uputstvo == '2':
            izlaz_iz_aplikacije()
        elif uputstvo == '3':
            pretrage.ispis_filmova(filmovi_matrica)
        elif uputstvo == '4':
            pretrage.pretraga_filmova(filmovi_matrica)
        elif uputstvo == '5':
            pretrage.visekriterijumska_pretraga(filmovi_matrica)
        elif uputstvo == '6':
            pretrage.pretraga_termina_bioskopske_projekcije(termini_projekcija_matrica, filmovi_matrica,
                                                            sale_za_projekcije_matrica, bioskopske_projekcije_matrica)
        elif uputstvo == '7':
            registracija()
        elif uputstvo == '8':
            odjava()
        elif uputstvo == '9':
            izmena_podataka()
        elif uputstvo == '10':
            rezervacija_karte()
        elif uputstvo == '11':
            if prijavljeni_korisnik[4] == "kupac":
                pretrage.pregled_rezervisanih_karata(karte_matrica, bioskopske_projekcije_matrica,
                                                     termini_projekcija_matrica, prijavljeni_korisnik)
            else:
                pretrage.pregled_karata_prodavac(karte_matrica, bioskopske_projekcije_matrica)
        elif uputstvo == "12":
            if uloga=='kupac':
                ponistavanje_rezervacije()
            else:
                ponistavanje_prodavac()
        elif uputstvo == '13':
            pretrage.pretraga_karata(karte_matrica)
        elif uputstvo == "14":
            direktna_prodaja()
        elif uputstvo == "15":
            prodaja_rezervisana_karte()
        elif uputstvo == "16":
            izmena_karata()
        elif uputstvo == "17":
            automatsko_ponistavanje_rezervacija()
        elif uputstvo == '18':
            izvestaji.meni_izvestaji(karte_matrica, bioskopske_projekcije_matrica, termini_projekcija_matrica, korisnici_matrica, filmovi_matrica)
        elif uputstvo == '19':
            print("U izradi... Odaberite drugu opciju")
        elif uputstvo == "20":
            prikaz_karata()
        elif uputstvo == '21':
            print("U izradi... Odaberite drugu opciju")
        elif uputstvo == '22':
            menadzment_filmova()
        elif uputstvo == '23':
            menadzment_bp()
        elif uputstvo == '24':
            pretrage.prikaz_svih_termina_projekcija(termini_projekcija_matrica, bioskopske_projekcije_matrica)

        else:
            # Unesen je neispravan podatak, nastavljamo while petlju
            print("Unesite neki od traženih brojeva")


if __name__ == '__main__':
    # U slučaju da fajl ne postoji na disku, kreiramo prazan fajl
    korisnici = 'korisnici.txt'
    with open(korisnici, 'a+'):
        pass
    bioskopske_projekcije = 'bioskopske_projekcije.txt'
    with open(bioskopske_projekcije, 'a+'):
        pass
    sale_za_projekcije = 'sale_za_projekcije.txt'
    with open(sale_za_projekcije, 'a+'):
        pass
    filmovi = 'filmovi.txt'
    with open(filmovi, 'a+'):
        pass
    bioskopske_karte = 'bioskopske_karte.txt'
    with open(bioskopske_karte, 'a+'):
        pass

    # pozivamo glavne funkcije
    ucitaj_podatke()
    popuni_termine_projekcija()
    automatsko_ponistavanje_rezervacija()
    glavni_meni()
