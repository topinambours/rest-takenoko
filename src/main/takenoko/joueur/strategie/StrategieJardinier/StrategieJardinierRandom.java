package takenoko.joueur.strategie.StrategieJardinier;

import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;

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
        plots.remove(new Plot(plateau.getPosPanda(), Couleur.BLEU));
        return plots
                .get(new Random().nextInt(plots.size()))
                .getCoord();
    }
}
