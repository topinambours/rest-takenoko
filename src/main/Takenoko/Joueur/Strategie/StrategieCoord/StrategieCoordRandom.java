package takenoko.joueur.strategie.StrategieCoord;

import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;

import java.util.List;
import java.util.Random;

/**
 * La stratégie random est la stratégie de base. Elle effectue les actions de façon aléatoire
 */
public class StrategieCoordRandom implements StrategieCoord {

    @Override
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        return getCoords(p);
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        return p.legalPositions();
    }

    public CoordAxial getCoord(Plateau p, Plot plot) {
        return getCoord(p);

    }

    public CoordAxial getCoord(Plateau p) {
        List<CoordAxial> listOfPos = p.legalPositions();
        Random random = new Random();
        int a = random.nextInt(listOfPos.size());
        return listOfPos.get(a);

    }


    @Override
    public String getStrategieCoordLabel() {
        return "Stratégie aléatoire";
    }
}
