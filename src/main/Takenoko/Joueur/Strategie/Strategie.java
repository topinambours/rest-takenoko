package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

public interface Strategie {
    public CoordAxial getCoord(Plateau P);
    public CoordIrrig getIrrig(Plateau P);

    public String getStrategieLabel();
}
