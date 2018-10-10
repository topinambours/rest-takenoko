package Takenoko.Util.Comparators;

import Takenoko.Plot.CoordAxial;

import java.util.Comparator;
/**
 * Le comparateur de coordonnées axiales
 */
public class ComparateurCoordAxial implements Comparator<CoordAxial> {

    public ComparateurCoordAxial(){

    }

    /**
     * Compare 2 objet avec les coordonnées axial
     * @param o1
     * @param o2
     * @return 1 si o1 a la coordonnée Q la plus grande et -1 sinon, si il y a égalité, on compare la coordonnée R.
     */
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