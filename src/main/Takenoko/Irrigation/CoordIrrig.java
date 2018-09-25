package Takenoko.Irrigation;

import Takenoko.Plot.CoordAxial;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

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
        var res = new ArrayList<CoordAxial>();
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
        int du = abs(a.getQ() - b.getQ());
        int dv = abs(a.getR() - b.getR());

        switch (du) {
            case 0: {
                if (dv == 1) {
                    return new CoordIrrig(a.getQ(), min(a.getR(), b.getR()), Orient.W);
                } else {
                    return null;
                }
            }
            case 1: {
                if (dv == 0) {
                    return new CoordIrrig(min(a.getQ(), b.getQ()), a.getR(), Orient.N);
                } else if (dv == 1) {
                    return new CoordIrrig(max(a.getQ(), b.getQ()), min(a.getR(), b.getR()), Orient.S);
                } else {
                    return null;
                }
            }
            default: {
                return null;
            }
        }
    }

    /**
     * prend les coordonnées d'une arête et rend les coordonnées des 4 arêtes qui y sont connectées bout à bout
     * @return
     */
    public List<CoordIrrig> continues() {
        var res = new ArrayList<CoordIrrig>();
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
}
