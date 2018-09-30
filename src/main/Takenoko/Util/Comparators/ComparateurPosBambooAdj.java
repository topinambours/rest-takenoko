package Takenoko.Util.Comparators;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.Comparator;
import java.util.List;

public class ComparateurPosBambooAdj implements Comparator<CoordAxial> {

    private Plateau p;

    public ComparateurPosBambooAdj(Plateau p){
        this.p = p;
    }

    @Override
    public int compare(CoordAxial o1, CoordAxial o2) {
        List<Plot> n1 = p.getNeighbors(o1);
        List<Plot> n2 = p.getNeighbors(o2);

        int sumAdjBamb1 = n1.stream().mapToInt(Plot::getBambou).sum();
        int sumAdjBamb2 = n2.stream().mapToInt(Plot::getBambou).sum();

        return sumAdjBamb1 - sumAdjBamb2;
    }
}
