package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import org.springframework.stereotype.Component;

/**
 * Classe abstraite pour représenter les cartes objectif
 */
@Component
public abstract class ObjectiveCard {
    /**
     * rend la valeur en points de la carte
     * @return la valeur en points de la carte
     */
    public abstract int getPointValue();

    /**
     * vérifie si les conditions de l'objectif sont remplies
     * @return vrai si l'objectif est rempli, faux sinon
     */
    public abstract boolean isComplete();

    /**
     * valide l'objectif et change l'état du jeu en accordance
     * @return la veleur en points de l'objectif
     */
    public abstract int validate();

    /**
     * met à jour les cartes aved des références au plateau et à leur propriétaire à la pioche
     * @param plateau le plateau
     * @param joueur le propriétaire
     */
    public abstract void instanciate(Plateau plateau, Joueur joueur);
}
