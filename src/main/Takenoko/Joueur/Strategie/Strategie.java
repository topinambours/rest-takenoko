package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

public interface Strategie {
    public List<CoordAxial> getCoords(Plateau p, Plot plot);
    public List<CoordAxial> getCoords(Plateau p);

    public Optional<CoordIrrig> getIrrig(Plateau P);

    public CoordAxial getCoord(Plateau p, Plot plot);
    public CoordAxial getCoord(Plateau p);

    public String getStrategieLabel();
}
