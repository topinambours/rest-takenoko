package takenoko.objectives.patterns;

import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import takenoko.properties.Couleur;

import java.util.Objects;


/**
 * Une PatternTile est un élément d'un pattern, disposant d'une coordonnée cubique.
 *
 */
public class PatternTile {
    /**
     * Coordonnée cubique relative à l'origine (0,0,0)
     */
    private CoordCube offset;

    /**
     * Couleur associée à la tuile
     */
    private Couleur couleur;

    /**
     * Un patternTile est innitialisé à l'aide d'une coordonnée relative et une couleur.
     * @param offset coordonnée relative à (0,0,0)
     * @param couleur couleur de la tuile {@link Couleur}
     */
    public PatternTile(CoordCube offset, Couleur couleur) {
        this.offset = offset;
        this.couleur = couleur;
    }

    /**
     * Applique une rotation de 60 degrés à la coordonnée de la tuile
     * @return renvoi une nouvelle PatternTile après rotation
     */
    public PatternTile rotate60() {
        return new PatternTile(offset.rotate60(), couleur);
    }

    /**
     * Getter
     * @return coordonnée de la tuile
     */
    public CoordCube getOffset() {
        return offset;
    }

    /**
     * Getter
     * @return couleur de la tuile
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Permet de convertir la position cubique d'une PatternTile.
     * Nécessite la position axiale du point d'ancrage du pattern
     * @param coo point ancrage
     * @return coordAxial correspondante sur le plateau
     */
    public CoordAxial toConcretePosition(CoordAxial coo) {
        return coo.add(offset.toAxial());
    }

    /**
     * Permet de déterminer si la PatternTile est complétée sur le plateau
     * @param plateau plateau de jeu
     * @param coo coordonnée à tester
     * @return vrai si une parcelle complète le patternTile sur plateau
     */
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
