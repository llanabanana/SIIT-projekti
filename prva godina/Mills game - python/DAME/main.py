import pygame
from dame.konstante import *
from dame.igra import Igra
from dame.tabla import Tabla
from minimax.algoritam import minimax, get_sve_poteze

pygame.font.init()


FPS = 60
WIN = pygame.display.set_mode((SIRINA, VISINA))
font = pygame.font.Font(None, 74)



pygame.display.set_caption('Igra dame')

def get_red_kolona_od_misa(pos):
    x, y = pos
    red = y // SQUARE_SIZE
    kolona = x // SQUARE_SIZE
    return red, kolona



def main():
    clock = pygame.time.Clock()
    igra = Igra(WIN)

    run = True
    while run:
        clock.tick(FPS)



        if igra.na_potezu == roze:
            broj_poteza = len(get_sve_poteze(igra.get_tabla(), roze, igra))
            if broj_poteza < 7:
                value, nova_tabla = minimax(igra.get_tabla(), 5, float('-inf'), float('inf'), roze, igra)
            else:
                value, nova_tabla = minimax(igra.get_tabla(), 3, float('-inf'), float('inf'), roze, igra)
            igra.ai_potez(nova_tabla)

        igra.get_tabla()
        if igra.pobednik() != None:
            if igra.pobednik() == crna:
                print('Crni pobedili')
                pobednik = "crni igrac"
            elif igra.pobednik() == roze:
                print('Rozi pobedili')
                pobednik = "roze igrac"
            elif igra.pobednik() == 0:
                print('Nerešeno')
                pobednik = "Rezultat je neresen"
            pygame.time.delay(500)
            if igra.pobednik() == crna or igra.pobednik == roze:
                WIN.fill(igra.pobednik())
                win_text = font.render("Pobednik je " + pobednik, True, bela)
            else:
                WIN.fill(plava)
                win_text = font.render(pobednik, True, bela)

            win_text_rect = win_text.get_rect(center=(SIRINA // 2, VISINA // 2))
            WIN.blit(win_text, win_text_rect)

            pygame.display.update()

            pygame.time.delay(2000)

            igra.resetuj()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False
            if event.type == pygame.MOUSEBUTTONDOWN:
                pos = pygame.mouse.get_pos()
                red, kolona = get_red_kolona_od_misa(pos)
                igra.odaberi(red, kolona)


        igra.update()


    pygame.quit()

if __name__ == '__main__':
    main()