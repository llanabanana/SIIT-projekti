from prettytable import PrettyTable
import datetime
import os


def meni_izvestaji(karte_matrica, bp_matrica, termini_matrica, korisnici_matrica, filmovi_matrica):
    while True:
        print('\n')
        print("a) Lista prodatih karata za odabran datum prodaje")
        print("b) Lista prodatih karata za odabran datum termina bioskopske projekcije")
        print("c) Lista prodatih karata za odabran datum prodaje i odabranog prodavca")
        print("d) Ukupan broj i ukupna cena prodatih karata za izabran dan (u nedelji) prodaje")
        print("e) Ukupan broj i ukupna cena prodatih karata za izabran dan (u nedelji) održavanja projekcije")
        print("f) Ukupna cena prodatih karata za zadati film u svim projekcijama")
        print("g) Ukupan broj i ukupna cena prodatih karata za izabran dan prodaje i odabranog prodavca")
        print("h) Ukupan broj i ukupna cena prodatih karata po prodavcima (za svakog prodavca) u poslednjih 30 dana")
        print("i) Ukupan broj i ukupna cena prodatih karata po svim danima u nedelji")

        izbor = input("Izaberite opciju (a-h): ")
        if izbor.lower() not in ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i']:
            print("Nevalidan unos. Molimo izaberite opciju od a do h.")

### Izbor je a) Lista prodatih karata za odabran datum prodaje
        if izbor.lower() == 'a':
            datum = input("Unesite datum prodaje/rezervacije (dd.mm.yyyy): ")
            try:
                datetime.datetime.strptime(datum, "%d.%m.%Y")
            except ValueError:
                print("Neispravan datum.")
                input("Pritisni bilo koji taster...")
                return

            tabela = PrettyTable()
            tabela.field_names = ["Datum prodaje", "Sifra projekcije", "Sediste", "Naziv filma", "Pocetak projekcije",
                                  "Prodavac", "Korisnicko", "Ime i Prezime", "Status", "Cena"]

            for karta in karte_matrica:
                if karta[3] == datum and karta[6] == 'prodata':
                    sifra_projekcije = karta[4][:4]
                    # Uzimamo odgovarajucu projekciju kako bi popunili i naziv filma i pocetak projekcije
                    red_projekcije = next((bp_row for bp_row in bp_matrica if bp_row[0] == sifra_projekcije), None)

                    red = [karta[3], karta[4], karta[5], red_projekcije[5], red_projekcije[2], karta[7], karta[2],
                           karta[0] + ' ' + karta[1], karta[6], karta[8]]
                    tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_prodate_karte_po_datumu'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return

###  Izbor je b) Lista prodatih karata za odabran datum termina bioskopske projekcije
        elif izbor.lower() == 'b':
            datum = input("Unesite datum termina bioskopske projekcije (dd.mm.yyyy): ")
            try:
                datetime.datetime.strptime(datum, "%d.%m.%Y")
            except ValueError:
                print("Neispravan datum.")
                input("Pritisni bilo koji taster...")
                return

            tabela = PrettyTable()
            tabela.field_names = ["Datum prodaje", "Sifra projekcije", "Sediste", "Naziv filma", "Pocetak projekcije",
                                  "Prodavac", "Korisnicko", "Ime i Prezime", "Status", "Cena"]

            niz_sifra_termina = set()  # Kreiramo niz (set) koji ce storovati samo jedinstvene vrednosti, bez duplikata
            for termin in termini_matrica:
                if termin[1] == datum:
                    niz_sifra_termina.add(termin[0])

            for karta in karte_matrica:
                if karta[4] in niz_sifra_termina and karta[6] == 'prodata':
                    sifra_projekcije = karta[4][:4]
                    # Uzimamo odgovarajucu projekciju kako bi popunili i naziv filma i pocetak projekcije
                    red_projekcije = next((bp_row for bp_row in bp_matrica if bp_row[0] == sifra_projekcije), None)

                    red = [karta[3], karta[4], karta[5], red_projekcije[5], red_projekcije[2], karta[7], karta[2],
                           karta[0] + ' ' + karta[1], karta[6], karta[8]]

                    tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_prodate_karte_pod_terminu'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return

###  Izbor je c) Lista prodatih karata za odabran datum prodaje i odabranog prodavca
        elif izbor.lower() == 'c':

            prodavci = set()
            for k in karte_matrica:
                if k[7] != '/' and k[6] == 'prodata':  # Ako je slash na sedmom polju u kartama onda nije prodavac prodao kartu
                    prodavci.add(k[7])

            print("\nDostupni prodavci:")
            for p in prodavci:
                print(p)
            prodavac = input('Odaber prodavca iz liste: ')

            datumi = set()
            for d in karte_matrica:
                if d[7] == prodavac.lower().strip() and k[6] == 'prodata':
                    datumi.add(d[3])

            print('\nDostupni datumi:')
            for d in datumi:
                print(d)
            datum = input('Odaber datum iz liste (dd.mm.yyyy): ')
            try:
                datetime.datetime.strptime(datum, "%d.%m.%Y")
            except ValueError:
                print("Neispravan datum.")
                input("Pritisni bilo koji taster...")
                return

            tabela = PrettyTable()
            tabela.field_names = ["Datum prodaje", "Sifra projekcije", "Sediste", "Naziv filma", "Pocetak projekcije",
                                  "Prodavac", "Korisnicko", "Ime i Prezime", "Status", "Cena"]

            for karta in karte_matrica:
                if karta[3] == datum and karta[7] == prodavac and karta[6] == 'prodata':
                    sifra_projekcije = karta[4][:4]
                    # Uzimamo odgovarajucu projekciju kako bi popunili i naziv filma i pocetak projekcije
                    red_projekcije = next((bp_row for bp_row in bp_matrica if bp_row[0] == sifra_projekcije), None)

                    red = [karta[3], karta[4], karta[5], red_projekcije[5], red_projekcije[2], karta[7], karta[2],
                           karta[0] + ' ' + karta[1], karta[6], karta[8]]

                    tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_prodate_karte_prodavac_datum'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return

###  Izbor je d) Ukupan broj i ukupna cena prodatih karata za izabran dan (u nedelji) prodaje
        elif izbor == 'd':
            dani = ['Ponedeljak', 'Utorak', 'Sreda', 'Cetvrtak', 'Petak', 'Subota',
                    'Nedelja']  # Koristimo listu dana u nedelji i poredicemo njene indexe sa weekday

            while True:
                dan_izbor = input('\nUnesite dan u nedelji za koji se trazi izvestaj:')
                if dan_izbor.capitalize() in dani:
                    index_dana = dani.index(dan_izbor.capitalize())
                    break
                else:
                    print('Neispravan unos. Pokusajte ponovo')

            tabela = PrettyTable()
            tabela.field_names = ["Broj prodatih karata", "Ukupan Iznos"]

            br_karata = 0
            vrednost_karata = 0

            for k in karte_matrica:
                if k[6] == 'prodata':
                    dan_u_nedelji_index = datetime.datetime.strptime(k[3], '%d.%m.%Y').weekday()
                    if dan_u_nedelji_index == index_dana:
                        br_karata += 1
                        vrednost_karata += float(k[8])

            red = [str(br_karata), str(vrednost_karata)]
            tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_ukupno_po_danu_u_nedelji'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return

### Izbor je e) Ukupan broj i ukupna cena prodatih karata za izabran dan (u nedelji) održavanja projekcije
        elif izbor == 'e':
            dani = ['Ponedeljak', 'Utorak', 'Sreda', 'Cetvrtak', 'Petak', 'Subota', 'Nedelja']

            tabela_projekcije = PrettyTable()
            tabela_projekcije.field_names = ["Sifra projekcije", "Film", "Dani"]

            tabela = PrettyTable()
            tabela.field_names = ["Broj prodatih karata", "Ukupan Iznos"]

            print('\nDostupne bioskopske projekcije')
            for bp in bp_matrica:
                sifra = bp[0]
                film = bp[5]
                dani_projekcije = bp[4]

                red = [sifra, film, dani_projekcije]
                tabela_projekcije.add_row(red)
            print(tabela_projekcije)

            sifra_bp = input("Unesite sifru projekcije: ")

            while True:
                dan_izbor = input('\nUnesite dan u nedelji za koji se trazi izvestaj:')
                if dan_izbor.capitalize() in dani:
                    index_dana = dani.index(dan_izbor.capitalize())
                    break
                else:
                    print('Neispravan unos. Pokusajte ponovo')

            br_karata = 0
            vrednost_karata = 0

            for k in karte_matrica:
                if k[6] == 'prodata':
                    dan_u_nedelji_index = datetime.datetime.strptime(k[3], '%d.%m.%Y').weekday()
                    if dan_u_nedelji_index == index_dana and sifra_bp == k[4][:4]:   # uzimamo samo prve cetiri cifre
                        br_karata += 1
                        vrednost_karata += float(k[8])

            red = [str(br_karata), str(vrednost_karata)]
            tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_ukupno_po_danu_i_projekciji'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return

###  Izbor je f) Ukupna cena prodatih karata za zadati film u svim projekcijama
        elif izbor == 'f':

            tabela = PrettyTable()
            tabela.field_names = ["Broj prodatih karata", "Ukupan Iznos"]

            fimovi_niz = set()
            sifre_bp = set()
            br_karata = 0
            vrednost_karata = 0

            print('\nDostupni filmovi:')
            for f in filmovi_matrica:
                print(f[0])
                fimovi_niz.add(str(f[0]).lower().strip())

            while True:
                film_izbor = input('\nUnesite naziv filma iz gornje liste: ')
                if film_izbor.lower().strip() in fimovi_niz:
                    break
                else:
                    print('Neispravan unos. Pokusajte ponovo.')

            for bp in bp_matrica:
                if str(bp[5]).lower().strip() == film_izbor.lower().strip():
                    sifre_bp.add(bp[0])

            for k in karte_matrica:
                if k[4][:4] in sifre_bp and k[6] == 'prodata':
                    br_karata += 1
                    vrednost_karata += float(k[8])

            red = [str(br_karata), str(vrednost_karata)]
            tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_ukupno_po_filmu'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return

###  Izbor je g) Ukupan broj i ukupna cena prodatih karata za izabran dan prodaje i odabranog prodavca
        elif izbor =='g':
            dani = ['Ponedeljak', 'Utorak', 'Sreda', 'Cetvrtak', 'Petak', 'Subota', 'Nedelja']

            tabela = PrettyTable()
            tabela.field_names = ["Broj prodatih karata", "Ukupan Iznos"]
            br_karata = 0
            vrednost_karata = 0

            prodavci = set()  # Koristimo set da bi napravili niz od jedistvenih vrednosti (bez ponavaljanja)
            for k in karte_matrica:
                if k[7] != '/' and k[6] == 'prodata':  # Ako je slash na sedmom polju u kartama onda nije prodavac prodao kartu
                    prodavci.add(str(k[7]).lower().strip())

            print('\nProdavci koji su prodavali karte:')
            for p in prodavci:
                print(p)

            while True:
                prodavac_izbor = input('\nUnesite prodavca iz liste:')
                if prodavac_izbor.lower().strip() in prodavci:
                    break
                else:
                    print('Neispravan unos. Pokusajte ponovo')

            while True:
                dan_izbor = input('\nUnesite dan u nedelji za koji se trazi izvestaj:')
                if dan_izbor.capitalize() in dani:
                    index_dana = dani.index(dan_izbor.capitalize())
                    break
                else:
                    print('Neispravan unos. Pokusajte ponovo')

            for k in karte_matrica:
                if k[6] == 'prodata':
                    dan_u_nedelji_index = datetime.datetime.strptime(k[3], '%d.%m.%Y').weekday()
                    if str(k[7]).lower().strip() == prodavac_izbor.lower().strip() and dan_u_nedelji_index  == index_dana:
                        br_karata += 1
                        vrednost_karata += float(k[8])

            red = [str(br_karata), str(vrednost_karata)]
            tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_ukupno_po_danu_u_nedelji_i_prodavcu'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return


###  Izbor je  h) Ukupan broj i ukupna cena prodatih karata po prodavcima (za svakog prodavca) u poslednjih 30 dana
        elif izbor == 'h':

            tabela = PrettyTable()
            tabela.field_names = ["Ime i Prezime Prodavca", "Broj prodatih karata", "Ukupan Iznos"]

            prodavci = set()  # Koristimo set da bi napravili niz od jedistvenih vrednosti (bez ponavaljanja)
            for k in karte_matrica:
                if k[7] != '/' and k[6] == 'prodata':  # Ako je slash na sedmom polju u kartama onda nije prodavac prodao kartu
                    prodavci.add(k[7])

            for prodavac in prodavci:
                br_karata = 0
                vrednost_karata = 0
                ime_prezime = ''
                for k in karte_matrica:
                    if k[6] == 'prodata':
                        konvertovan_datum = datetime.datetime.strptime(k[3], "%d.%m.%Y")  # Zahtev za poslednjih 30 dana
                        if k[7] == prodavac and konvertovan_datum >= (
                                datetime.datetime.today() - datetime.timedelta(days=30)):
                            br_karata += 1
                            vrednost_karata += float(k[8])

                for k in korisnici_matrica:
                    if k[2] == prodavac:
                        ime_prezime = k[0] + ' ' + k[1]

                red = [ime_prezime, str(br_karata), str(vrednost_karata)]
                tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_ukupno_po_prodavcu_30_dana'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return

### Izbor je  i) Ukupan broj i ukupna cena prodatih karata po svim danima u nedelji
        elif izbor == 'i':
            dani = ['Ponedeljak', 'Utorak', 'Sreda', 'Cetvrtak', 'Petak', 'Subota', 'Nedelja']

            tabela = PrettyTable()
            tabela.field_names = ["Dan u nedelji", "Broj prodatih karata", "Ukupan Iznos"]

            for i in range(7):
                br_karata = 0
                vrednost_karata = 0
                for k in karte_matrica:
                    if k[6] == 'prodata':
                        dan_u_nedelji_index = datetime.datetime.strptime(k[3], '%d.%m.%Y').weekday()
                        if dan_u_nedelji_index == i:
                            br_karata += 1
                            vrednost_karata += float(k[8])

                dan = dani[i]
                red = [dan, str(br_karata), str(vrednost_karata)]
                tabela.add_row(red)

            print(tabela)

            # Opciono ponuditi snimanje u fajl
            filename = 'izv_ukupno_po_svi_dani_u_nedelji'
            snimianje_izvestaja(tabela, filename)

            input("Pritisni bilo koji taster...")
            return


def snimianje_izvestaja(tabela, filename):
    snimi = input("\nDa li zelite snimiti izvestaj u fajl? (da/ne): ").lower()
    if snimi == 'da':
        if not os.path.exists('izvestaji'):
            os.makedirs('izvestaji')

        timestamp = datetime.datetime.today().strftime('%d.%m.%Y-%H-%M-%S')
        filename = f'izvestaji/{filename}_{timestamp}.txt'
        with open(filename, 'w') as file:
            file.write(tabela.get_string())
        print(f"\nIzvestaj je uspesno snimljen pod imenom:  {filename}")

    return
