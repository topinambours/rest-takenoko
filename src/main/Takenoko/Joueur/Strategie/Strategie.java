package Takenoko.Joueur.Strategie;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

public interface Strategie {
    public CoordAxial getCoord(Plateau P, Plot plot);
}
