package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Irrigation.Orient;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;
import java.util.zip.CheckedInputStream;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class StrategieAdjacent implements Strategie{

    @Override
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        List<CoordAxial> legPos = p.legalPositions();

        return legPos.stream().collect(Collectors.groupingBy
                (pos -> p.getNeighbors(pos).size(),
                        TreeMap::new, toList())).lastEntry().getValue();
    }

    public Optional<CoordIrrig> getIrrig(Plateau plateau) {
        var res = plateau.legalIrrigPositions();
        if (res.size() >= 1) {
            return Optional.of(res.get(0));
        } else {
            return Optional.empty();
        }
    }

    public CoordAxial getCoord(Plateau p, Plot plot) {
        List<CoordAxial> posMaxBamboo = getCoords(p, plot);


        Random rand = new Random();
        return posMaxBamboo.get(rand.nextInt(posMaxBamboo.size()));
    }

    @Override
    public String getStrategieLabel() {
        return "Stratégie des adjacents";
    }

    public StrategieAdjacent() {

    }
}
