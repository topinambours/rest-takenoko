package takenoko.util.comparators;

import takenoko.irrigation.CoordIrrig;
import takenoko.Plateau;
import takenoko.Plot.Plot;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Le commparateur des irrigations
 * Une liste de coordonnées d'irrigation est ordonnée en fonction du nombre de parcelles irrigués au voisinage d'une coordonné d'irrigation
 * Ainsi une {@link CoordIrrig} est plus "grande" si celle si dispose de plus de parcelles irrigués à son voisinage.
 */
public class ComparateurIrig implements Comparator<CoordIrrig> {

    private Plateau plateau;

    public ComparateurIrig(Plateau p){
        this.plateau = p;
    }

    /**
     * Ordonne en fonction du nombre de parcelle irrigués au voisinage d'une coordonnée d'irrigation
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(CoordIrrig o1, CoordIrrig o2) {
        int o1NotirigatedAdjCount = plateau.getPlotsFromIrig(o1).stream().filter(Plot::haveWater).collect(Collectors.toList()).size();
        int o2NotirigatedAdjCount = plateau.getPlotsFromIrig(o2).stream().filter(Plot::haveWater).collect(Collectors.toList()).size();

        if (o1NotirigatedAdjCount == o2NotirigatedAdjCount){
            return 0;
        }
        return o1NotirigatedAdjCount - o2NotirigatedAdjCount;
    }
}
