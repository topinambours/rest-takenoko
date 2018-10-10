package Takenoko.Objectives.Patterns;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;

import java.util.Objects;


public class PatternTile {
    private CoordCube offset;
    private Couleur couleur;

    public PatternTile(CoordCube offset, Couleur couleur) {
        this.offset = offset;
        this.couleur = couleur;
    }

    public PatternTile rotate60() {
        return new PatternTile(offset.rotate60(), couleur);
    }

    public CoordCube getOffset() {
        return offset;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public CoordAxial toConcretePosition(CoordAxial coo) {
        return coo.add(offset.toAxial());
    }

    boolean matchPlot(Plateau plateau, CoordAxial coo) {
        Plot myPlot = plateau.getPlot(toConcretePosition(coo));
        return (myPlot != null && myPlot.getCouleur() == couleur && myPlot.haveWater());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatternTile that = (PatternTile) o;
        return Objects.equals(getOffset(), that.getOffset()) &&
                getCouleur() == that.getCouleur();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOffset(), getCouleur());
    }
}
