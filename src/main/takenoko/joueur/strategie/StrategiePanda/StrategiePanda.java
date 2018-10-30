package takenoko.joueur.strategie.StrategiePanda;

import takenoko.joueur.Joueur;
import takenoko.Plateau;
import takenoko.plot.CoordAxial;


public interface StrategiePanda {
    public String getStrategiePandaLabel();
    public CoordAxial getPandaMove(Plateau plateau, Joueur joueur);
}
