package takenoko.util.comparators;

import takenoko.Plateau;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

import java.util.Comparator;
import java.util.List;

/**
 * Ce comparateur trie deux {@link CoordAxial} selon le nombre de bambous que peuvent pousser autour et sur cette coordonnée.
 */
public class CompPosJardinier implements Comparator<CoordAxial> {

    private Plateau p;

    public CompPosJardinier(Plateau p){
        this.p = p;
    }

    @Override
    public int compare(CoordAxial o1, CoordAxial o2) {
        List<Plot> neighbour1 = p.getNeighbors(o1);
        List<Plot> neighbour2 = p.getNeighbors(o2);

        neighbour1.add(p.getPlot(o1));
        neighbour2.add(p.getPlot(o2));

        if (neighbour1.contains(p.getPlot(new CoordAxial(0,0)))){
            neighbour1.remove(p.getPlot(new CoordAxial(0,0)));
        }

        if (neighbour2.contains(p.getPlot(new CoordAxial(0,0)))){
            neighbour2.remove(p.getPlot(new CoordAxial(0,0)));
        }


        int value = neighbour1.stream().mapToInt(plot -> plot.haveWater()? 4 - plot.getBambou() : 0).sum();
        int value2 = neighbour2.stream().mapToInt(plot -> plot.haveWater()? 4 - plot.getBambou() : 0).sum();

        return value - value2;

    }
}
