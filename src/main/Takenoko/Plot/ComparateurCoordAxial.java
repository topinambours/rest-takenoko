package Takenoko.Plot;

import java.util.Comparator;

public class ComparateurCoordAxial implements Comparator<CoordAxial> {

    public ComparateurCoordAxial(){

    }

    @Override
    public int compare(CoordAxial o1, CoordAxial o2) {
        if (o1.getQ() > o2.getQ()){
            return 1;
        }
        if (o1.getQ() < o2.getQ()){
            return -1;
        }
        return o1.getR() - o2.getR();
    }
}