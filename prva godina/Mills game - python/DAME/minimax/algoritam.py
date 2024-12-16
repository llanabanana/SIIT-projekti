from copy import deepcopy
import pygame
from dame.konstante import crna, roze
from dame.figure import Figurica
from dame.tabla import Tabla
def minimax (tabla, dubina, alpha, beta, maximizer, igra):
    if dubina == 0 or tabla.pobednik() != None: # ako smo na root node - u
        return tabla.score_table(), None
    
    if maximizer:
        maxScore = float('-inf')
        najbolji_potez = None
        for potez in get_sve_poteze(tabla, roze, igra):
            score = minimax(potez, dubina - 1, alpha, beta, False, igra) [0]

            if score>maxScore:
                maxScore = score
                najbolji_potez = potez

            alpha = max(alpha, score)
            if beta <= alpha:
                break
            
        return maxScore, najbolji_potez
    
    else:
        minScore = float('inf')
        najbolji_potez = None
        for potez in get_sve_poteze(tabla, crna, igra):
            score = minimax(potez, dubina - 1, alpha, beta, True, igra) [0]
            if score < minScore:
                minScore = score
                najbolji_potez =potez
            beta = min(beta, score)
            if beta <= alpha:
                break

            
        return minScore, najbolji_potez


def isprobaj_pomeranje(figura, potez, tabla, igra, preskocen):
    tabla.pomeri_figuru(figura, potez[0], potez[1]) # stavljamo [0] jer je potez tuple (red, kolona) [1] je kolona
    if preskocen:
        tabla.ukloni_figuru(preskocen)
    return tabla

def get_sve_poteze(tabla, boja, igra):
    potezi = []

    for figura in tabla.get_sve_figurice(boja):
        validni_potezi = tabla.get_validne_poteze(figura)
        for potez, preskocen in validni_potezi.items():
            temp_tabla = deepcopy(tabla)
            temp_figura = temp_tabla.get_figura(figura.red, figura.kolona)
            nova_tabla = isprobaj_pomeranje(temp_figura, potez, temp_tabla, igra, preskocen)
            potezi.append(nova_tabla)
    return potezi