package Takenoko.Joueur.Strategie.StrategieJardinier;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Random;

/**
 * Une stratégie aléatoire, renvoi une coordonnée aléatoire pour déplacer le jardinier
 */
public class StrategieJardinierRandom implements StrategieJardinier {

    @Override
    public String getStrategieJardinierLabel() {
        return "Jardinier Random";
    }

    @Override
    public CoordAxial getJardinierMove(Plateau plateau, Joueur joueur) {
        List<Plot> plots = plateau.getLinePlots(plateau.getPosJardinier());
        return plots
                .get(new Random().nextInt(plots.size()))
                .getCoord();
    }
}
