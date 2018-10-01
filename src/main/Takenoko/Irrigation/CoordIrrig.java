package Takenoko.Irrigation;

import Takenoko.Plot.CoordAxial;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.*;

/**
 * La classe CoorIrrig permet de calculer les coordonnées des irrigations
 */
public class CoordIrrig {
    private int u;
    private int v;
    private Orient o;

    public CoordIrrig(CoordAxial uv, Orient o) {
        u = uv.getQ();
        v = uv.getR();
        this.o = o;
    }

    public CoordIrrig(int u, int v, Orient o) {
        this.u = u;
        this.v = v;
        this.o = o;
    }

    /**
     * prend les coordonnées d'une arête et rend les coordonnées des 2 espaces adjacents
     * @return
     */
    public List<CoordAxial> borders() {
        List<CoordAxial> res = new ArrayList<CoordAxial>();
        switch (o) {
            case N: {
                res.add(new CoordAxial(u, v - 1));
                res.add(new CoordAxial(u, v));
            }
            case W: {
                res.add(new CoordAxial(u - 1, v));
                res.add(new CoordAxial(u, v));
            }
            case S: {
                res.add(new CoordAxial(u, v));
                res.add(new CoordAxial(u - 1, v + 1));
            }
        }
        return res;
    }

    /**
     * prend 2 coordonnées hexagonales et rend les coordonnées de l'arête qu'elles partagent
     * renvoie null si les 2 hexagones ne sont pas adjacents
     * @param a
     * @param b
     * @return
     */
    public static CoordIrrig join(CoordAxial a, CoordAxial b) {
        List<CoordAxial> nbc = a.getNeighborCoords();
        for (int i = 0; i < nbc.size(); i++) {
            if (nbc.get(i).equals(b)) {
                return a.getBorderCoords().get(i);
            }
        }
        return null;
    }

    /**
     * prend les coordonnées d'une arête et rend les coordonnées des 4 arêtes qui y sont connectées bout à bout
     * @return
     */
    public List<CoordIrrig> continues() {
        List<CoordIrrig> res = new ArrayList<CoordIrrig>();
        switch (o) {
            case N: {
                res.add(new CoordIrrig(u, v, Orient.W));
                res.add(new CoordIrrig(u, v - 1, Orient.S));
                res.add(new CoordIrrig(u + 1, v - 1, Orient.W));
                res.add(new CoordIrrig(u + 1, v - 1, Orient.S));
            }
            case W: {
                res.add(new CoordIrrig(u, v, Orient.S));
                res.add(new CoordIrrig(u - 1, v + 1, Orient.N));
                res.add(new CoordIrrig(u, v - 1, Orient.S));
                res.add(new CoordIrrig(u, v, Orient.N));
            }
            case S: {
                res.add(new CoordIrrig(u, v, Orient.W));
                res.add(new CoordIrrig(u - 1, v + 1, Orient.N));
                res.add(new CoordIrrig(u, v + 1, Orient.W));
                res.add(new CoordIrrig(u, v + 1, Orient.N));
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;
        CoordIrrig that = (CoordIrrig) o1;
        return u == that.u &&
                v == that.v &&
                o == that.o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v, o);
    }

    @Override
    public String toString() {
        return "(" +
                "u=" + u +
                ", v=" + v +
                ", o=" + o +
                ')';
    }
}
