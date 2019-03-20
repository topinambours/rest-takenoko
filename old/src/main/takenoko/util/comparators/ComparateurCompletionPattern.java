package takenoko.util.comparators;

import takenoko.plot.CoordAxial;
import takenoko.util.Pair;

import java.util.Comparator;

public class ComparateurCompletionPattern implements Comparator<Pair<Double, CoordAxial>> {
    public ComparateurCompletionPattern() {

    }

    @Override
    public int compare(Pair<Double, CoordAxial> o1, Pair<Double, CoordAxial> o2) {
        if (o1.getLeft() == o2.getLeft()) return 0;
        else if (o1.getLeft() > o2.getLeft()) return 1;
        else return -1;
    }
}
