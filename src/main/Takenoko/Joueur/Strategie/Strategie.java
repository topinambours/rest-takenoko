package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

import java.util.List;

public interface Strategie {
    public CoordIrrig getIrrig(Plateau P);
    public List<CoordAxial> getCoords(Plateau p);

    public CoordAxial getCoord(Plateau p);

    public String getStrategieLabel();
}
