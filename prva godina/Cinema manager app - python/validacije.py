
def korisnicko_postoji(korisnicko_ime, korisnici_matrica):
    # Prolazimo kroz svaki red matrice i uporedjujemo uneseno korisnicko ime sa svakim postojecim
    for row in korisnici_matrica:
        temp_ki = row[2]
        if temp_ki == korisnicko_ime:
            # Ukoliko korisnicko ime vec postoji vracamo true
            return True
    # Ukoliko nismo naisli na isto korisnicko ime
    return False


def film_postoji(film_bp, filmovi_matrica):
    for film in filmovi_matrica:
        naslov_filma = film[0].strip().lower()
        if (film_bp.strip().lower() == naslov_filma.strip().lower()):
            return True
    return False



def sediste_slobodno(termin, zeljeno_sediste, sedista_matrica,karte_matrica):
    if zeljeno_sediste=="":
        return False

    for row in karte_matrica:
        if termin == row[3] and zeljeno_sediste == row[4] and row[9] == 'False':
                print('Uneto sediste je vec zauzeto!')
                return False

    for redova in sedista_matrica:
        trenutno_list=redova
        trenutno=trenutno_list[0]
        if str(zeljeno_sediste)[:1]==trenutno[:1]:
            if zeljeno_sediste in trenutno_list:
                return True

    print("Uneto sediste je nepostojece")
    return False
def termin_projekcije_postoji(zeljena_projekcija, termini_projekcija_matrica):
    if zeljena_projekcija=="":
        return False
    for sifra in termini_projekcija_matrica:
        if zeljena_projekcija == sifra[0]:
            return True
    return False
def projekcija_zauzeta(pocetak, kraj, dani, sala_bp, bioskopske_projekcije_matrica):
    pocetak = pocetak.split(':')
    kraj = kraj.split(':')
    pocetak_min = int(pocetak[0]) * 60 + int(pocetak[1])
    kraj_min = int(kraj[0]) * 60 + int(kraj[1])
    dani_set = set(dani.split(', '))  # Pretvaramo dane (niz) u set radi poredjenja

    for projekcija in bioskopske_projekcije_matrica:
        if(projekcija[1] == sala_bp):    # Preklapanje proveravamo samo ako se radi o istoj bioskopskoj sali
            projekcija_dani = set(projekcija[4].split(', '))
            if dani_set.intersection(projekcija_dani):
                proj_poc = projekcija[2].split(':')
                proj_kraj = projekcija[3].split(':')
                proj_poc_min = int(proj_poc[0]) * 60 + int(proj_poc[1])
                proj_kraj_min = int(proj_kraj[0]) * 60 + int(proj_kraj[1])
                if(pocetak_min >= proj_poc_min and pocetak_min < proj_kraj_min) or (kraj_min >= proj_poc_min and kraj_min < proj_kraj_min):
                    return True
    return False
def sala_postoji(sala_bp, sale_za_projekcije_matrica):
    for sala in sale_za_projekcije_matrica:
        sifra_sale = sala[0]
        if (sala_bp == sifra_sale):
            return True
    return False

def duzina_filma_odgovara(pocetak, kraj, film, filmovi_matrica):
    # Validacija da li duzina filma odgovara vremenskom prozoru projekcije
    kraj = kraj.split(':')
    pocetak = pocetak.split(':')
    duzina_u_minutima = (int(kraj[0]) * 60 + int(kraj[1])) - (int(pocetak[0]) * 60 + int(pocetak[1]))
    for row in filmovi_matrica:
        if row[0].strip().title() == film.strip().title():
            if duzina_u_minutima < int(row[2]):
                return False
            else:
                return True


def lozinka_valid(password):
    # Proveravamo duzinu lozinke i da li je bar jedan karakter cifra *digit*
    return len(password) > 6 and any(c.isdigit() for c in password)