package takenoko.joueur.strategie.StrategieJardinier;

import takenoko.joueur.Joueur;
import takenoko.Plateau;
import takenoko.plot.CoordAxial;

public interface StrategieJardinier {
    public String getStrategieJardinierLabel();
    public CoordAxial getJardinierMove(Plateau plateau, Joueur joueur);
}
