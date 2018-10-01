package Takenoko.Plot;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Irrigation.Orient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** La classe CoordAxial permet de nous créer une coordonnée
 */
public class CoordAxial {



    private int q;
    private int r;

    public CoordAxial(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public CoordAxial add(CoordAxial coo) {
        return new CoordAxial(q + coo.getQ(), r + coo.getR());
    }

    /**
     * rend les 6 coordonnées des hexagones voisins
     * @return
     */
    public List<CoordAxial> getNeighborCoords() {
        ArrayList<CoordAxial> res = new ArrayList<>();
        res.add(new CoordAxial(q + 1, r - 1));
        res.add(new CoordAxial(q + 1, r));
        res.add(new CoordAxial(q, r - 1));
        res.add(new CoordAxial(q, r + 1));
        res.add(new CoordAxial(q - 1, r));
        res.add(new CoordAxial(q - 1, r + 1));
        return res;
    }

    /**
     * rend les coordonnées des 6 arêtes du pourtour de l'hexagone
     * @return
     */
    public List<CoordIrrig> getBorderCoords() {
        ArrayList<CoordIrrig> res = new ArrayList<CoordIrrig>();
        res.add(new CoordIrrig(q + 1, r - 1, Orient.S));
        res.add(new CoordIrrig(q + 1, r, Orient.W));
        res.add(new CoordIrrig(q, r, Orient.N));
        res.add(new CoordIrrig(q, r + 1, Orient.N));
        res.add(new CoordIrrig(q, r, Orient.W));
        res.add(new CoordIrrig(q, r, Orient.S));
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoordAxial that = (CoordAxial) o;

        if (q != that.q) return false;
        return r == that.r;
    }

    @Override
    public int hashCode() {
        int result = q;
        result = 31 * result + r;
        return result;
    }

    @Override
    public String toString() {
        return "(" + q +
                "," + r +
                ')';
    }
}
