package Takenoko.Util.Comparators;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Le commparateur des irrigations
 */
public class ComparateurIrig implements Comparator<CoordIrrig> {

    Plateau plateau;

    public ComparateurIrig(Plateau p){
        this.plateau = p;
    }

    /**
     *
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(CoordIrrig o1, CoordIrrig o2) {
        int o1NotirigatedAdjCount = o1.borders().stream().filter(coordAxial -> plateau.getPlot(coordAxial).haveWater()).collect(Collectors.toList()).size();
        int o2NotirigatedAdjCount = o2.borders().stream().filter(coordAxial -> plateau.getPlot(coordAxial).haveWater()).collect(Collectors.toList()).size();
        return o1NotirigatedAdjCount - o2NotirigatedAdjCount;
    }
}
