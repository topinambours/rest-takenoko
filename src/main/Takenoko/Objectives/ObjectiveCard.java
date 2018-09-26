package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;

public abstract class ObjectiveCard {
    public abstract int getPointValue();
    public abstract boolean isComplete();
    public abstract int validate();
    public abstract void instanciate(Plateau plateau, Joueur joueur);
}
