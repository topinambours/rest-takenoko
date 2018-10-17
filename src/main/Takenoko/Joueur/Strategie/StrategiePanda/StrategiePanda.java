package Takenoko.Joueur.Strategie.StrategiePanda;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;


public interface StrategiePanda {
    public String getStrategiePandaLabel();
    public CoordAxial getMove(Plateau plateau, Joueur joueur);
}
