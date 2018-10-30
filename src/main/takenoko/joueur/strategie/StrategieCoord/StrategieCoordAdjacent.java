package takenoko.joueur.strategie.StrategieCoord;

import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * La stratégie Adjacente consiste à poser des parcelles en maximisant le nombre de voisins
 */
public class StrategieCoordAdjacent implements StrategieCoord {

    @Override
    public StrategieCoordResult getDecision(Joueur joueur, Plateau plateau, List<Plot> plots) {
        int pos = 0;
        Plot plot = plots.get(0);
        CoordAxial coo = getCoord(plateau);
        return new StrategieCoordResult(pos, plot, coo);
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        return getCoords(p);
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        List<CoordAxial> legPos = p.legalPositions();

        return legPos.stream().collect(Collectors.groupingBy
                (pos -> p.getNeighbors(pos).size(),
                        TreeMap::new, toList())).lastEntry().getValue();
    }



    public CoordAxial getCoord(Plateau p, Plot plot) {
        return getCoord(p);
    }

    public CoordAxial getCoord(Plateau p) {
        List<CoordAxial> posMaxBamboo = getCoords(p);
        Random rand = new Random();
        return posMaxBamboo.get(rand.nextInt(posMaxBamboo.size()));
    }

    @Override
    public String getStrategieCoordLabel() {
        return "Stratégie qui maximise les gains par action";
    }

}
