package takenoko.util.comparators;

import takenoko.Plateau;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

import java.util.Comparator;

/**
 * Compare deux positions pour le placement du panda
 */
public class CompPosPanda implements Comparator<CoordAxial> {

    private Plateau p;

    public CompPosPanda(Plateau p){
        this.p = p;
    }

    @Override
    public int compare(CoordAxial o1, CoordAxial o2) {
        Plot p1 = p.getPlot(o1);
        Plot p2 = p.getPlot(o2);

        return p2.getBambou() - p1.getBambou();
    }
}
