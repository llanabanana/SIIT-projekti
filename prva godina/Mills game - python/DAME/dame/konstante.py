import pygame
from krunica import *

SIRINA = 800
VISINA = 800
REDOVI = 8
KOLONE = 8
SQUARE_SIZE = SIRINA // KOLONE

# Boje
bela = (232, 223, 229)
crna = (1, 1, 1)
crvena = (255, 0, 0)
plava = (91, 143, 227)
siva = (128, 128, 128)
zuta = (255, 255, 0)
zelena = (0, 255, 0)
ljubicasta = (128, 0, 128)
roze = (176, 23, 125)

#loadujem ikonicu krune
crown = pygame.transform.scale(pygame.image.load('krunica/crown.png'), (34, 25))
