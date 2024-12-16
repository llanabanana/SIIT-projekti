import pygame
from dame.konstante import *
from dame.figure import Figurica


class Tabla:    
    def __init__(self):
        self.tabla = []
        self.preostale_crne = self.preostale_roze = 12
        self.crnih_dama = self.rozih_dama = 0
        self.napravi_tablu()

    def nacrtaj_polja(self, win):
        win.fill(bela)
        for i in range(REDOVI):
            for j in range(i % 2, KOLONE, 2): #svako drugo polje je crno
                pygame.draw.rect(win, crna, (i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE))

    

    def pomeri_figuru(self, figura, red, kolona):
        # zamenimo mesta figurama na tabli
        self.tabla[figura.red][figura.kolona], self.tabla[red][kolona] = self.tabla[red][kolona], self.tabla[figura.red][figura.kolona]
        figura.pomeri(red, kolona)

        # provera da li je figura postala dama
        if red == REDOVI - 1 or red == 0:
            figura.pretvori_u_damu()
            if not figura.dama:
                if figura.boja == roze:
                    self.rozih_dama += 1
                else:
                    self.crnih_dama += 1

    def get_figura(self, red, kolona):
        return self.tabla[red][kolona]
    
    def get_sve_figurice(self, boja):
        figurice = []
        for red in self.tabla:
            for figura in red:
                if figura != 0 and figura.boja == boja:
                    figurice.append(figura)
        return figurice

            
    def napravi_tablu(self):
        for redova in range(REDOVI):
            self.tabla.append([]) # dodajemo praznu listu (reprezentacija za svaki red) 
            for kolona in range(KOLONE):
                #preskacemo prvo gornje levo polje, pa crtamo figurice
                if kolona % 2 == ((redova + 1) % 2):
                    if redova < 3:
                        self.tabla[redova].append(Figurica(redova, kolona, roze))
                    elif redova > 4:
                        self.tabla[redova].append(Figurica(redova, kolona, crna))
                    else:
                        self.tabla[redova].append(0)
                    
                else:
                    self.tabla[redova].append(0)

    def nacrtaj(self, win):
        self.nacrtaj_polja(win)
        for redova in range(REDOVI):
            for kolona in range(KOLONE):
                figura = self.tabla[redova][kolona]
                if figura != 0:
                    figura.nacrtaj(win)

    def ukloni_figuru(self, fig):
        for figura in fig:
            self.tabla[figura.red][figura.kolona] = 0
        
            if figura != 0:
                if figura.boja == crna:
                    self.preostale_crne -= 1
                elif figura.boja == roze:
                    self.preostale_roze -= 1

    def pobednik(self):
        if self.preostale_crne <= 0:
            return roze
        elif self.preostale_roze <= 0:
            return crna
        elif self.preostale_crne == 1 and self.preostale_roze == 1:
            return 0
        return None
        


    def get_validne_poteze(self, figura):
        validni_potezi = {}

        kolona_ulevo = figura.kolona - 1
        kolona_udesno = figura.kolona + 1
        red = figura.red

        if figura.boja == crna or figura.dama:
            validni_potezi.update(self.proveri_levu_dijagonalu(red - 1, max(red - 3, -1), -1, figura.boja, kolona_ulevo))
            validni_potezi.update(self.proveri_desnu_dijagonalu(red - 1, max(red - 3, -1), -1, figura.boja, kolona_udesno))
        
        if figura.boja == roze or figura.dama:
            validni_potezi.update(self.proveri_levu_dijagonalu(red + 1, min(red + 3, REDOVI), 1, figura.boja, kolona_ulevo))
            validni_potezi.update(self.proveri_desnu_dijagonalu(red + 1, min(red + 3, REDOVI), 1, figura.boja, kolona_udesno))

        return validni_potezi
    
    def proveri_levu_dijagonalu(self, pocetak, kraj, korak, boja, kolona_ulevo, preskocen=[]):
        validni_potezi = {}
        prethodni_skok = [] #sacuvamo preskocenu figuru

        for red in range(pocetak, kraj, korak):
            if kolona_ulevo < 0: #ako smo izasli iz table
                break
            trenutno_polje = self.tabla [red][kolona_ulevo] #trenutno polje koje proveravamo

            if trenutno_polje == 0: #ako je prazno polje

                if preskocen and not prethodni_skok:
                    break   
                elif preskocen:
                    validni_potezi[(red, kolona_ulevo)] = preskocen + prethodni_skok #sacuvamo preskocenu figuru
                else:
                    validni_potezi[(red, kolona_ulevo)] = prethodni_skok	#sacuvamo poziciju

                if prethodni_skok:
                    if korak == -1:
                        redova = max(red - 3, -1)
                    else:
                        redova = min(red + 3, REDOVI)
                    
                    validni_potezi.update(self.proveri_levu_dijagonalu(red + korak, redova, korak, boja, kolona_ulevo - 1, preskocen=prethodni_skok))
                    validni_potezi.update(self.proveri_desnu_dijagonalu(red + korak, redova, korak, boja, kolona_ulevo + 1, preskocen=prethodni_skok))
                break
            elif trenutno_polje.boja == boja: #ako je figura iste boje
                break
            else: #ako je figura druge boje, preskacemo je (proveravamo sledece polje)
                prethodni_skok = [trenutno_polje]
            
            kolona_ulevo -= 1 #proveravamo sledece polje
        return validni_potezi
        
    def proveri_desnu_dijagonalu(self, pocetak, kraj, korak, boja, kolona_udesno, preskocen=[]):
        validni_potezi = {}
        prethodni_skok = [] #sacuvamo preskocenu figuru

        for red in range(pocetak, kraj, korak):
            if kolona_udesno >= KOLONE: #ako smo izasli iz table
                break
            trenutno_polje = self.tabla [red][kolona_udesno] #trenutno polje koje proveravamo

            if trenutno_polje == 0: #ako je prazno polje

                if preskocen and not prethodni_skok:
                    break   
                elif preskocen:
                    validni_potezi[(red, kolona_udesno)] = preskocen + prethodni_skok #sacuvamo preskocenu figuru
                else:
                    validni_potezi[(red, kolona_udesno)] = prethodni_skok	#sacuvamo poziciju

                if prethodni_skok:
                    if korak == -1:
                        redova = max(red - 3, -1)
                    else:
                        redova = min(red + 3, REDOVI)
                    
                    validni_potezi.update(self.proveri_levu_dijagonalu(red + korak, redova, korak, boja, kolona_udesno - 1, preskocen=prethodni_skok))
                    validni_potezi.update(self.proveri_desnu_dijagonalu(red + korak, redova, korak, boja, kolona_udesno + 1, preskocen=prethodni_skok))
                break
            elif trenutno_polje.boja == boja: #ako je figura iste boje
                break
            else: #ako je figura druge boje, preskacemo je (proveravamo sledece polje)
                prethodni_skok = [trenutno_polje]
            
            kolona_udesno += 1 #proveravamo sledece polje

        return validni_potezi
    

    def score_table(self):
        return self.preostale_roze - self.preostale_crne + (self.rozih_dama * 0.5 - self.crnih_dama * 0.5) #dodajemo 0.5 jer je dama vrednija od obicne figure
    
        

        