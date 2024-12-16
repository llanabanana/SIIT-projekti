import pygame
from dame.konstante import *
from dame.figure import Figurica
from dame.tabla import Tabla

class Igra:
    def __init__(self, win):
        self.odabrana_figura = None
        self.tabla = Tabla()
        self.na_potezu = crna
        self.validni_potezi = {}
        self.win = win

    def update(self):
        self.tabla.nacrtaj(self.win)
        self.nacrtaj_validne_poteze(self.validni_potezi)
        pygame.display.update()

    def resetuj(self):
        self.odabrana_figura = None
        self.tabla = Tabla()
        self.na_potezu = crna
        self.validni_potezi = {}

    def odaberi(self, red, kolona):
        if self.odabrana_figura:
            rez = self.pomeri_figuru(red, kolona)
            if not rez:
                self.odabrana_figura = None
                self.validni_potezi = {}
                self.odaberi(red, kolona)
        
        figura = self.tabla.get_figura(red, kolona)
        if figura != 0 and figura.boja == self.na_potezu:
            self.odabrana_figura = figura
            self.validni_potezi = self.tabla.get_validne_poteze(figura)
            return True

        return False
    
    def pobednik(self):
        return self.tabla.pobednik()
    
    def pomeri_figuru(self, red, kolona):
        
        figura = self.tabla.get_figura(red, kolona)

        if self.odabrana_figura and figura == 0 and (red, kolona) in self.validni_potezi:
            self.tabla.pomeri_figuru(self.odabrana_figura, red, kolona)
            preskocen = self.validni_potezi[(red, kolona)]

            if preskocen:
                self.tabla.ukloni_figuru(preskocen)

            self.promeni_na_potezu()
        else:
            return False
        return True
    

    def nacrtaj_validne_poteze(self, potezi):
        for p in potezi:
            red, kolona = p
            pygame.draw.circle(self.win, plava, (kolona * SQUARE_SIZE + SQUARE_SIZE // 2, red * SQUARE_SIZE + SQUARE_SIZE // 2), 15)
    
    
    def promeni_na_potezu(self):
        self.validni_potezi = {}
        self.na_potezu = crna if self.na_potezu == roze else roze

    def get_tabla(self):
        return self.tabla
    
    def ai_potez(self, tabla): #prosledjujemo trenutnu tablu nako sto je AI odigrao svoj potez
        self.tabla = tabla
        self.promeni_na_potezu()
        

   