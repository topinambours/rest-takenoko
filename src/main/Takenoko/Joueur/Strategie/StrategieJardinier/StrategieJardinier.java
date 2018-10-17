package Takenoko.Joueur.Strategie.StrategieJardinier;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

public interface StrategieJardinier {
    public String getStrategieJardinierLabel();
    public CoordAxial getJardinierMove(Plateau plateau, Joueur joueur);
}
