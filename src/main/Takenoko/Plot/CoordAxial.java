package Takenoko.Plot;

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
