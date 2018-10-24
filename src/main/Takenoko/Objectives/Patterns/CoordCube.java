package takenoko.objectives.patterns;

import takenoko.Plot.CoordAxial;

import java.util.Objects;

/**
 * Représente un emplacement hexagonal dans un système de coordonnées cubiques
 */
public class CoordCube {
    private int q;
    private int r;
    private int s;

    /**
     * constructeur prenant les 3 coordonnées séparément
     * @param q la coordonnée q
     * @param r la coordonnée r
     * @param s la coordonnée s
     */
    public CoordCube(int q, int r, int s) {
        if (q + r + s != 0) throw new IllegalArgumentException("q + r + s must equal 0");
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public CoordCube() {
        this.q = 0;
        this.r = 0;
        this.s = 0;
    }

    /**
     * constructeur prenant 2 coordonnées et calculant la 3e
     * @param q la coordonnée q
     * @param r la coordonnée r
     */
    public CoordCube(int q, int r) {
        this.q = q;
        this.r = r;
        this.s = -q - r;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public int getS() {
        return s;
    }

    /**
     * convertit la coordonnée en coordonnée axiale
     * @return la coordonnée axiale correspondante
     */
    public CoordAxial toAxial() {
        return new CoordAxial(q, r);
    }

    /**
     * calcule la coordonnée tournée de 60° autour de l'origine
     * @return une nouvelle coordonnée tournée de 60°
     */
    public CoordCube rotate60() {
        return new CoordCube(-s, -q, -r);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordCube coordCube = (CoordCube) o;
        return getQ() == coordCube.getQ() &&
                getR() == coordCube.getR() &&
                getS() == coordCube.getS();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQ(), getR(), getS());
    }

    @Override
    public String toString() {
        return "CoordCube{" +
                "q=" + q +
                ", r=" + r +
                ", s=" + s +
                '}';
    }
}
