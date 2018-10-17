package Takenoko.Joueur.Strategie;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoord;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrig;
import Takenoko.Joueur.Strategie.StrategieJardinier.StrategieJardinier;
import Takenoko.Joueur.Strategie.StrategiePanda.StrategiePanda;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

public abstract class AbstractStrategie implements StrategieCoord, StrategieIrrig, StrategieJardinier, StrategiePanda {

    public abstract List<CoordAxial> getCoords(Plateau p, Plot plot);

    public abstract List<CoordAxial> getCoords(Plateau p);

    public abstract CoordAxial getCoord(Plateau p, Plot plot);

    public abstract CoordAxial getCoord(Plateau p);

    public abstract String getStrategieLabel();

    public abstract Optional getIrrig(Plateau P);

    public abstract CoordAxial getJardinierMove(Plateau plateau, Joueur joueur);

    public abstract CoordAxial getPandaMove(Plateau plateau, Joueur joueur);
}
