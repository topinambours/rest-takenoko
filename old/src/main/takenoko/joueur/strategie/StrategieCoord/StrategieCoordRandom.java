package takenoko.joueur.strategie.StrategieCoord;

import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

import java.util.List;
import java.util.Random;

/**
 * La stratégie random est la stratégie de base. Elle effectue les actions de façon aléatoire
 */
public class StrategieCoordRandom implements StrategieCoord {

    @Override
    public StrategieCoordResult getDecision(Joueur joueur, Plateau plateau, List<Plot> plots) {
        int pos = 0;
        Plot plot = plots.get(0);
        List<CoordAxial> listOsPos = plateau.legalPositions();
        Random random = new Random();
        int a = random.nextInt(listOsPos.size());
        CoordAxial coordAxial = listOsPos.get(a);
        return new StrategieCoordResult(pos, plot, coordAxial);
    }

    @Override
    public String getStrategieCoordLabel() {
        return "Stratégie aléatoire";
    }
}
