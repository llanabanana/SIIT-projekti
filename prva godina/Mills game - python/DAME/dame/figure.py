from dame.konstante import *
import pygame

class Figurica:
    padding = 12

    def __init__(self, red, kolona, boja):
        self.red = red
        self.kolona = kolona
        self.boja = boja
        self.dama = False
        self.x = 0
        self.y = 0
        self.postavi_poziciju()
    
    def postavi_poziciju(self):
        # postavljanje pozicije figure, sredina polja
        self.x = self.kolona * SQUARE_SIZE + SQUARE_SIZE // 2
        self.y = self.red * SQUARE_SIZE + SQUARE_SIZE // 2

    def pretvori_u_damu(self):
        self.dama = True

    def nacrtaj(self, win):
        radius = SQUARE_SIZE // 2 - self.padding
        pygame.draw.circle(win, self.boja, (self.x, self.y), radius)

        if self.dama:
            win.blit(crown, (self.x - crown.get_width() // 2, self.y - crown.get_height() // 2))

    def pomeri(self, red, kolona):
        # pomeranje figure na novu poziciju
        self.red = red
        self.kolona = kolona
        self.postavi_poziciju()


        