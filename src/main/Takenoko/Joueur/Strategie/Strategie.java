package Takenoko.Joueur.Strategie;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

public interface Strategie {
    public CoordAxial getCoord(Plateau P);

    public String getStrategieLabel();
}
