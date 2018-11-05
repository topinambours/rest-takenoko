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
        List<CoordAxial> legPos = plateau.legalPositions();
        List<CoordAxial> posMaxBamboo = legPos.stream().collect(Collectors.groupingBy
                (ppos -> plateau.getNeighbors(ppos).size(),
                        TreeMap::new, toList())).lastEntry().getValue();
        Random rand = new Random();
        CoordAxial coo = posMaxBamboo.get(rand.nextInt(posMaxBamboo.size()));
        return new StrategieCoordResult(pos, plot, coo);
    }

    @Override
    public String getStrategieCoordLabel() {
        return "Stratégie qui maximise les gains par action";
    }

}
