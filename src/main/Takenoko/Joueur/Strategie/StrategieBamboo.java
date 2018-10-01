package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Le robot prend le parti de placer sa parcelle au placement maximisant le nombre de bambous adjacents
 */
public class StrategieBamboo implements Strategie {

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        List<CoordAxial> legPos = p.legalPositions();

        return legPos.stream().collect(Collectors.groupingBy
                (pos -> p.getNeighbors(pos).stream().mapToInt(Plot::getBambou).sum(),
                        TreeMap::new, toList())).lastEntry().getValue();
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        return getCoords(p);
    }

    public CoordAxial getCoord(Plateau p, Plot plot) {
        return getCoord(p);
    }

    @Override
    public CoordAxial getCoord(Plateau p) {
        List<CoordAxial> posMaxBamboo = getCoords(p);

        Random rand = new Random();
        return posMaxBamboo.get(rand.nextInt(posMaxBamboo.size()));
    }

    public Optional<CoordIrrig> getIrrig(Plateau plateau) {
        List<CoordIrrig> res = plateau.legalIrrigPositions();
        if (res.size() >= 1) {
            return Optional.of(res.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String getStrategieLabel() {
        return "max adj bamboo";
    }
}
