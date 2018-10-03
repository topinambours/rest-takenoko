package Takenoko.Objectives.Patterns;

import Takenoko.Plot.CoordAxial;

import java.util.Objects;

public class CoordCube {
    private int q;
    private int r;
    private int s;

    public CoordCube(int q, int r, int s) {
        if (q + r + s != 0) throw new IllegalArgumentException("q + r + s must equal 0");
        this.q = q;
        this.r = r;
        this.s = s;
    }

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

    public CoordAxial toAxial() {
        return new CoordAxial(q, r);
    }

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
