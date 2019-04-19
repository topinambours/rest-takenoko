package takenoko.irrigation;


import lombok.Data;
import takenoko.tuile.CoordAxial;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
/**
 * La classe CoorIrrig permet de calculer les coordonnées des irrigations
 */
public class CoordIrrig {
    /**
     * Une coordonnée d'irigation est placé sur une {@link CoordAxial}
     */
    private CoordAxial coordAxial;
    /**
     * Une coordonné d'irrigation dispose d'une orientation parmis {@link Orient}
     */
    private final Orient o;

    public CoordIrrig(CoordAxial coordAxial, Orient o) {
        this.coordAxial = coordAxial;
        this.o = o;
    }

    public CoordIrrig(int q, int r, Orient o) {
        this(new CoordAxial(q,r), o);
    }

    /**
     * prend les coordonnées d'une arête et rend les coordonnées des 2 espaces adjacents
     * @return les coordonées partageant la coordonée d'irrigation
     */
    public List<CoordAxial> borders() {
        List<CoordAxial> res = new ArrayList<>();

        int q = coordAxial.getQ();
        int r = coordAxial.getR();

        switch (o) {
            case N: {
                res.add(new CoordAxial(q, r - 1));
                break;
            }
            case W: {
                res.add(new CoordAxial(q - 1, r));
                break;
            }
            case S: {
                res.add(new CoordAxial(q - 1, r + 1));
                break;
            }
        }
        res.add(new CoordAxial(q, r));

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
        List<CoordAxial> nbc = a.computeNeighborCoords();
        for (int i = 0; i < nbc.size(); i++) {
            if (nbc.get(i).equals(b)) {
                return a.computeBorderCoords().get(i);
            }
        }
        return null;
    }

    /**
     * prend les coordonnées d'une arête et rend les coordonnées des 4 arêtes qui y sont connectées bout à bout
     * @return les quatres arretes composants la continuité d'une coordIrrig
     */
    public List<CoordIrrig> continues() {
        List<CoordIrrig> res = new ArrayList<>();
        int q = coordAxial.getQ();
        int r = coordAxial.getR();
        switch (o) {
            case N: {
                res.add(new CoordIrrig(q, r, Orient.W));
                res.add(new CoordIrrig(q, r - 1, Orient.S));
                res.add(new CoordIrrig(q + 1, r - 1, Orient.W));
                res.add(new CoordIrrig(q + 1, r - 1, Orient.S));
                break;
            }
            case W: {
                res.add(new CoordIrrig(q, r, Orient.S));
                res.add(new CoordIrrig(q - 1, r + 1, Orient.N));
                res.add(new CoordIrrig(q, r - 1, Orient.S));
                res.add(new CoordIrrig(q, r, Orient.N));
                break;
            }
            case S: {
                res.add(new CoordIrrig(q, r, Orient.W));
                res.add(new CoordIrrig(q - 1, r + 1, Orient.N));
                res.add(new CoordIrrig(q, r + 1, Orient.W));
                res.add(new CoordIrrig(q, r + 1, Orient.N));
                break;
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;
        CoordIrrig that = (CoordIrrig) o1;
        return Objects.equals(coordAxial, that.coordAxial) &&
                o == that.o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordAxial, o);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", coordAxial.toString(), o.toString());
    }
}
