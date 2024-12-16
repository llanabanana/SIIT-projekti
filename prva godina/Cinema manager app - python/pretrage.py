import validacije
rezervisana=[]
from prettytable import PrettyTable

def  ispis_termina_projekcija(termini_projekcija_matrica):
    for termin in termini_projekcija_matrica:
        print('Termin: ' + termin[0] + ' ' + termin[1] + ' ' + '(' + termin[2] + ')')
    return


def ispis_filmova(filmovi_matrica):
    x=PrettyTable()
    x.field_names = ["Naslov", "Žanr", "Trajanje (min)", "Režiser", "Uloge", "Zemlja", "Godina"]
    #prvi_red = " | ".join(kolone) #Prvi red su nazivi kolona
    #print(prvi_red)
    #print("-" * len(prvi_red))

    for film in filmovi_matrica:
        naslov=film[0]
        zanr=film[1]
        trajanje=film[2]
        reziser=film[3]
        uloge=film [4]
        zemlja = film[5]
        godina=film [6]

        redovi = [naslov, zanr, trajanje, reziser, uloge, zemlja, godina]
        #print(" | ".join(redovi))
        x.add_row(redovi)
    print(x)
    return


def pretraga_filmova(filmovi_matrica):
    while True:
        print('Odaberi način pretrage:')
        print("1) Pretraga po nazivu")
        print("2) Pretraga po žanru")
        print("3) Pretraga po trajanju")
        print("4) Pretraga po režiseru")
        print("5) Pretraga po ulogama")
        print("6) Pretraga po zemlji")
        print("7) Pretraga po godini")
        print('8) Povratak u glavni meni')

        kriterijum = input("Unesite broj za izabrani kriterijum: ").strip()
        if kriterijum =='8':
            return
        if kriterijum in ['1', '2', '4', '5', '6', '7']:
            vrednost_krit = input("Unesite vrednost kriterijuma: ").lower().strip()
            break
        elif kriterijum == '3':
            min_krit = input("Minimalno trajanje: ").strip()
            maks_krit = input("Maksimalno trajanje: ").strip()
            try:
                min_krit = int(min_krit)
                maks_krit = int(maks_krit)
            except ValueError:
                print("Neispravan unos, pokušajte ponovo.")
                continue
            break
        else:
            print('Unesite neki od traženih brojeva!')
            continue

    rezultatif = []
    rezultatiz = []
    rezultatitr = []
    rezultatirez = []
    rezultatiu = []
    rezultatizemlja = []
    rezultatig = []

    for film in filmovi_matrica:
        naslov = film[0].lower().strip()
        zanr = film[1].lower().strip()
        trajanje = int(film[2])
        reziser = (film[3]).lower().strip()
        uloge=film [4].lower().strip()
        zemlja = film[5].lower().strip()
        godina = film[6].lower().strip()

        if kriterijum == '1' and naslov == vrednost_krit:
            rezultatif.append(naslov.title())
            rezultatiz.append(zanr.title())
            rezultatitr.append(trajanje)
            rezultatirez.append(reziser.title())
            rezultatiu.append(uloge.title())
            rezultatizemlja.append(zemlja.title())
            rezultatig.append(godina.title())
        elif kriterijum == '2' and zanr == vrednost_krit:
            rezultatif.append(naslov.title())
            rezultatiz.append(zanr.title())
            rezultatitr.append(trajanje)
            rezultatirez.append(reziser.title())
            rezultatiu.append(uloge.title())
            rezultatizemlja.append(zemlja.title())
            rezultatig.append(godina.title())
        elif kriterijum == '3' and min_krit <= trajanje <= maks_krit:
            rezultatif.append(naslov.title())
            rezultatiz.append(zanr.title())
            rezultatitr.append(trajanje)
            rezultatirez.append(reziser.title())
            rezultatiu.append(uloge.title())
            rezultatizemlja.append(zemlja.title())
            rezultatig.append(godina.title())
        elif kriterijum == '4' and reziser == vrednost_krit:
            rezultatif.append(naslov.title())
            rezultatiz.append(zanr.title())
            rezultatitr.append(trajanje)
            rezultatirez.append(reziser.title())
            rezultatiu.append(uloge.title())
            rezultatizemlja.append(zemlja.title())
            rezultatig.append(godina.title())
        elif kriterijum == '5' and vrednost_krit in uloge:
            rezultatif.append(naslov.title())
            rezultatiz.append(zanr.title())
            rezultatitr.append(trajanje)
            rezultatirez.append(reziser.title())
            rezultatiu.append(uloge.title())
            rezultatizemlja.append(zemlja.title())
            rezultatig.append(godina.title())
        elif kriterijum == '6' and zemlja == vrednost_krit:
            rezultatif.append(naslov.title())
            rezultatiz.append(zanr.title())
            rezultatitr.append(trajanje)
            rezultatirez.append(reziser.title())
            rezultatiu.append(uloge.title())
            rezultatizemlja.append(zemlja.title())
            rezultatig.append(godina.title())
        elif kriterijum == '7' and godina == vrednost_krit:
            rezultatif.append(naslov.title())
            rezultatiz.append(zanr.title())
            rezultatitr.append(trajanje)
            rezultatirez.append(reziser.title())
            rezultatiu.append(uloge.title())
            rezultatizemlja.append(zemlja.title())
            rezultatig.append(godina.title())
    if rezultatif:
        x = PrettyTable()
        x.add_column("Naslov", rezultatif)
        x.add_column("Zanr", rezultatiz)
        x.add_column("Trajanje (min)", rezultatitr)
        x.add_column("Režiser", rezultatirez)
        x.add_column("Uloge", rezultatiu)
        x.add_column("Zemlja", rezultatizemlja)
        x.add_column("Godina", rezultatig)

        print(x)
    if rezultatif==[]:
        print('Ne postoji film koji odgovara traženim kriterijumima.')


def visekriterijumska_pretraga(filmovi_matrica):
    print('\nUnesite željene kriterijume:')
    print('Naziv:')
    naziv_krit=input().lower().strip()
    print('Žanr:')
    zanr_krit=input().lower().strip()
    print('Minimalno trajanje:')
    min_krit=input().lower().strip()
    try:
        min_krit=int(min_krit)
    except:
        min_krit=int(0)
    print('Maksimalno trajanje: ')
    maks_krit=input().lower().strip()
    try:
        maks_krit=int(maks_krit)
    except:
        maks_krit=int(1000)
    print('Reziser:')
    reziser_krit=input().lower().strip()
    print('Uloga: ')
    uloge_krit=input().lower().strip()
    print('Zemlja: ')
    zemlja_krit=input().lower().strip()
    print('Godina: ')
    godina_krit=input().lower().strip()

    rezultatif = []
    rezultatiz = []
    rezultatitr = []
    rezultatirez = []
    rezultatiu = []
    rezultatizemlja = []
    rezultatig = []

    for film in filmovi_matrica:
        naslov=film[0].lower().strip()
        zanr=film[1].lower().strip()
        trajanje=film[2].lower().strip()
        trajanje=int(trajanje)
        reziser=film[3].lower().strip()
        uloge=film [4].lower().strip()
        postoji_uloga=uloge_krit in uloge
        zemlja = film[5].lower().strip()
        godiina=film [6].lower().strip()

        if(naziv_krit==naslov or naziv_krit=='') and (zanr_krit == zanr or zanr_krit == '') and (min_krit <= trajanje or min_krit == '') and (maks_krit >= trajanje or maks_krit == '') and (reziser_krit == reziser or reziser_krit == '') and (postoji_uloga==True or uloge_krit == '') and (zemlja_krit == zemlja or zemlja_krit == '') and (godina_krit == godiina or godina_krit == ''):
            rezultatif.append(naslov.title())
            rezultatiz.append(zanr.title())
            rezultatitr.append(trajanje)
            rezultatirez.append(reziser.title())
            rezultatiu.append(uloge.title())
            rezultatizemlja.append(zemlja.title())
            rezultatig.append(godiina.title())
    if rezultatif:
        x = PrettyTable()
        x.add_column("Naslov", rezultatif)
        x.add_column("Zanr", rezultatiz)
        x.add_column("Trajanje (min)", rezultatitr)
        x.add_column("Režiser", rezultatirez)
        x.add_column("Uloge", rezultatiu)
        x.add_column("Zemlja", rezultatizemlja)
        x.add_column("Godina", rezultatig)

        print(x)
    else:
        print('Ne postoji film koji odgovara traženim kriterijumima.')




def pretraga_termina_bioskopske_projekcije(termini_projekcija_matrica, filmovi_matrica, sale_za_projekcije_matrica, bioskopske_projekcije_matrica):
    imena_filmova = {film[0].strip() for film in filmovi_matrica}
    imena_sala = {sala[1].strip().title() for sala in sale_za_projekcije_matrica}
    datumi = {red[1] for red in termini_projekcija_matrica}

    while True:
        print('\nOdaberi način pretrage:')
        print("1) Pretraga po filmu")
        print("2) Pretraga po sali")
        print("3) Pretraga po datumu odrzavanja")
        print('4) Pretraga po vremenu pocetka i kraja projekcije')
        print('5) Povratak u glavni meni')

        kriterijum = input("\nUnesite broj za izabrani kriterijum: ").strip()
        if kriterijum == '1':
            while True:
                print('\nDostupni filmovi:')
                print (', '.join(imena_filmova))
                naziv_filma = input("Unesite zeljeni film: ").title().strip()
                if naziv_filma == 'q':
                    return
                elif validacije.film_postoji(naziv_filma, filmovi_matrica):
                    pretraga_projekcije_film(naziv_filma, bioskopske_projekcije_matrica, termini_projekcija_matrica)
                    break
                else:
                    print('Nepostojeci film.')
        elif kriterijum == '2':
            while True:
                print('\nDostupne sale:')
                print(', '.join(imena_sala))
                naziv_sale = input("Unesite naziv sale: ").strip().title()
                if naziv_sale == 'q':
                    return
                elif naziv_sale not in imena_sala:
                    print('Nepostojeca sala.')
                else:
                    pretraga_projekcija_sala(naziv_sale, sale_za_projekcije_matrica, bioskopske_projekcije_matrica, termini_projekcija_matrica)
                    break
        elif kriterijum == '3':
            while True:
                print('\nDostupni datumi:')
                print(', '.join(datumi))
                datum_za_pretragu = input("Unesite datum: ").strip()
                if datum_za_pretragu == 'q':
                    return
                elif datum_za_pretragu not in datumi:
                    print('Datum nije dobar.')
                else:
                    pretraga_projekcija_datum(datum_za_pretragu, termini_projekcija_matrica, bioskopske_projekcije_matrica)
                    break
        elif kriterijum == '4':
            while True:
                pocetak = input("Pocetak projekcije: ").strip()
                kraj = input("Kraj projekcije: ").strip()
                try:
                    if(':' in kraj) and (':' in pocetak):
                        kraj_proba = kraj.split(':')
                        pocetak_proba = pocetak.split(':')
                        duzina_u_minutima = (int(kraj_proba[0]) * 60 + int(kraj_proba[1])) - (int(pocetak_proba[0]) * 60 + int(pocetak_proba[1]))
                    else:
                        print("Neispravan unos, pokušajte ponovo.")
                        continue
                except ValueError:
                    print("Neispravan unos, pokušajte ponovo.")
                    continue
                pretraga_projekcije_pik(pocetak, kraj, bioskopske_projekcije_matrica, termini_projekcija_matrica)
                break
        elif kriterijum == '5':
            return
        else:
            print('Unesite neki od traženih brojeva!')
            continue


def pretraga_projekcije_pik(pocetak, kraj, bioskopske_projekcije_matrica, termini_projekcija_matrica):
    rezultati = []
    kraj = kraj.split(':')
    pocetak = pocetak.split(':')
    for projekcija in bioskopske_projekcije_matrica:
        pocetak_bp=projekcija[2]
        kraj_bp=projekcija[3]
        kraj_bp = kraj_bp.split(':')
        pocetak_bp = pocetak_bp.split(':')
        kraj_min_bp = (int(kraj_bp[0]) * 60 + int(kraj_bp[1]))
        pocetak_min_bp=(int(pocetak_bp[0]) * 60 + int(pocetak_bp[1]))

        kraj_min = int(kraj[0]) * 60 + int(kraj[1])
        pocetak_min=(int(pocetak[0]) * 60 + int(pocetak[1]))

        if (pocetak_min <= pocetak_min_bp) and (kraj_min >= kraj_min_bp):
            rezultati.append(projekcija)

    if(len(rezultati)>0):
        ispis_rezultata_pretrage_projekcija(rezultati, termini_projekcija_matrica)
    else:
        print('Nisu pronadjeni rezultati koji odgovaraju kriterijumu')
    return

def pretraga_projekcije_film(film, bioskopske_projekcije_matrica, termini_projekcija_matrica):
    rezultati = []
    for projekcija in bioskopske_projekcije_matrica:
        if film.strip().lower() == projekcija[5].strip().lower():
            rezultati.append(projekcija)

    if(len(rezultati)>0):
        ispis_rezultata_pretrage_projekcija(rezultati, termini_projekcija_matrica)
    else:
        print('Nisu pronadjeni rezultati koji odgovaraju kriterijumu')
    return

def pretraga_projekcija_sala(sala, sale_za_projekcije_matrica, bioskopske_projekcije_matrica, termini_projekcija_matrica):
    rezultati = []
    sifra_sale = ''
    for s in sale_za_projekcije_matrica:
        if sala.strip().lower() == s[1].strip().lower():
            sifra_sale = s[0].strip()
            break
    for projekcija in bioskopske_projekcije_matrica:
        if sifra_sale == projekcija[1].strip():
            rezultati.append(projekcija)

    if(len(rezultati)>0):
        ispis_rezultata_pretrage_projekcija(rezultati, termini_projekcija_matrica)
    else:
        print('Nisu pronadjeni rezultati koji odgovaraju kriterijumu')
    return

def pretraga_projekcija_datum(datum, termini_projekcija_matrica, bioskopske_projekcije_matrica):
    rezultati = []
    for termin in termini_projekcija_matrica:
        if datum == termin[1]:
            for proj in bioskopske_projekcije_matrica:
                if termin[0][:4] == proj[0]:
                    rezultati.append(proj)

    if (len(rezultati) > 0):
        ispis_rezultata_pretrage_projekcija(rezultati, termini_projekcija_matrica)
    else:
        print('Nisu pronadjeni rezultati koji odgovaraju kriterijumu')
    return



def ispis_rezultata_pretrage_projekcija(rezultati, termini_projekcija_matrica):
    datumi_projekcija = []
    for rez in rezultati:
        for termin in termini_projekcija_matrica:
            if rez[0].strip() == termin[0][:4].strip():
                datumi_projekcija.append(termin[1])

        print('\nRezultati pretrage:')
        print(f'Film: {rez[5].upper()}, Sala: {rez[1]}, Pocetak: {rez[2]}, Kraj: {rez[3]}')
        print('Datumi prikazivanja: ' + ', '.join(datumi_projekcija))
    return


def prikaz_svih_termina_projekcija(termini_matrica, projekcije_matrica):
    # Prazna lista za cuvanje podataka
    data = []

    # Prikupljanje podataka iz potrebnih matrica
    for termin in termini_matrica:
        for projekcija in projekcije_matrica:
            if termin[0][:4] == projekcija[0]:
                cena=projekcija[6]
                data.append((termin[0], termin[1], projekcija[5], cena))

    # Sortiranje po datumu
    data.sort(key=lambda x: x[1])

    # Kreiranje tabele
    tabela = PrettyTable()
    tabela.field_names = ["Sifra termina", "Datum Odrzavanja", "Film", "Cena karte"]
    for item in data:
        tabela.add_row(item)

    # Print the table
    print(tabela)
    return

def pregled_rezervisanih_karata(karte_matrica,bioskopske_projekcije_matrica,termini_projekcija_matrica,prijavljeni_korisnik):
    global rezervisana
    rezervisana=[]
    for red in karte_matrica:
        if red[2] == prijavljeni_korisnik[2] and red[6] == 'rezervisana' and red[9] == 'False':
            datum = red[3]
            termin=red[4]
            sediste=red[5]
            cena =  red[8]
            for projekcije in termini_projekcija_matrica:
                if termin == projekcije[0]:
                    bioskopska_projekcija = projekcije[0][:4]
                    break
            for redovi in bioskopske_projekcije_matrica:
                if bioskopska_projekcija == redovi[0]:
                    film = redovi[5]
                    pocetak=redovi[2]
                    kraj=redovi[3]
                    break

            rezervisana.append([termin,sediste,film,pocetak,kraj,cena, datum])

    x=PrettyTable()
    x.field_names = ["Sifra termina", "Sediste", "Naziv filma", "Vreme pocetka", "Vreme kraja", "Cena", "Datum rezervacije"]
    x.add_rows(rezervisana)
    if rezervisana==[]:
        print("Nemate rezervisanih karata")
    else:
        print(x)

    input("Pritisni bilo koji taster...")
    return(rezervisana)

def pregled_karata_prodavac(karte_matrica,bioskopske_projekcije_matrica):
    rezervisana=[]
    for red in karte_matrica:
        if red[6]=="rezervisana" and red[9] == 'False':
            bp = red[4]
            ime = red[0]
            prezime = red[1]
            sediste = red[5]
            datum = red[3]
            prodavac = red[7]
            cena = red[8]

            for projekcija in bioskopske_projekcije_matrica:
                if bp[:4]==projekcija[0]:
                    film=projekcija[5]
                    pocetak=projekcija[2]
                    kraj=projekcija[3]
            rezervisana.append([bp, sediste, ime, prezime, film, pocetak, kraj, prodavac, cena, datum])
    x=PrettyTable()
    x.field_names=["Sifra termina","Oznaka sedista","Ime kupca","Prezime kupca","Naziv filma","Vreme pocetka","Vreme kraja", "Prodavac",  "Cena", "Datum rezervacije"]
    x.add_rows(rezervisana)
    print(x)

    input("Pritisni bilo koji taster...")
    return
def ispis_svih_karata(karte_matrica,bioskopske_projekcije_matrica):
    for red in karte_matrica:
        bp = red[4]
        ime = red[0]
        prezime = red[1]
        sediste = red[5]
        datum = red[3]
        prodavac = red[7]
        cena = red[8]

        for projekcija in bioskopske_projekcije_matrica:
            if bp[:4] == projekcija[0]:
                film = projekcija[5]
                pocetak = projekcija[2]
                kraj = projekcija[3]
        rezervisana.append([bp, sediste, ime, prezime, film, pocetak, kraj, prodavac, cena, datum])


    x = PrettyTable()
    x.field_names = ["Sifra termina", "Oznaka sedista", "Ime kupca", "Prezime kupca", "Naziv filma", "Vreme pocetka",
                     "Vreme kraja", "Prodavac", "Cena", "Datum rezervacije"]
    x.add_rows(rezervisana)
    print(x)

    input("Pritisni bilo koji taster...")
    return

def pretraga_karata(karte_matrica):

    tabela = PrettyTable()

    print('\n1) Izlistaj samo rezervisane')
    print('2) Izlistaj samo prodate')
    print('3) Pretraga po kljucnoj reci')

    izbor = input('Unesite 1 - 3:')

    if izbor == '1':
        tabela.field_names = ["Ime i Prezime", "Datum rezervacije", "Sifra Projekcije", "Sediste", "Prodavac", "Cena"]
        for k in karte_matrica:
            if k[6] == 'rezervisana' and k[9] == 'False':
                ime = k[0] + ' ' + k[1]
                red = [ime, k[3], k[4], k[5], k[7], k[8]]
                tabela.add_row(red)

    elif izbor == '2':
        tabela.field_names = ["Ime i Prezime", "Datum prodaje", "Sifra Projekcije", "Sediste", "Prodavac", "Cena"]
        for k in karte_matrica:
            if k[6] == 'prodata' and k[9] == 'False':
                ime = k[0] + ' ' + k[1]
                red = [ime, k[3], k[4], k[5], k[7], k[8]]
                tabela.add_row(red)

    elif izbor == '3':
        tabela.field_names = ["Ime i Prezime", "Datum prodaje", "Sifra Projekcije", "Sediste", "Status", "Prodavac", "Cena"]
        termin = input('\nUnesite trazeni termin (kljucnu rec):').strip()
        for k in karte_matrica:
            if k[9] == 'False':
                if any(termin in str(column).lower() for column in k):
                    ime = k[0] + ' ' + k[1]
                    red = [ime, k[3], k[4], k[5], k[6], k[7], k[8]]
                    tabela.add_row(red)

    else:
        print('Pogresan unos!')
        return

    print(tabela)
    input("Pritisni bilo koji taster...")
    return
