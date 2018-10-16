package Takenoko.Objectives.Patterns;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Classe qui représente un motif de parcelles de couleur sur le plateau
 * Utilisée pour les cartes objectif parcelle
 */
public class Pattern {
    public void setTiles(List<PatternTile> tiles) {
        this.tiles = tiles;
    }

    private List<PatternTile> tiles;

    /**
     * Constructeur de pattern
     * @param tiles une liste de PatternTile qui représente les points individuels du motif
     */
    public Pattern(List<PatternTile> tiles) {
        this.tiles = tiles;
    }

    public Pattern(){}

    /**
     * Copy Constructor utilisé en interne
     * @param pattern le Pattern à copier
     */
    Pattern(Pattern pattern) {
        this.tiles = pattern.tiles;
    }

    /**
     * getter pour les PatternTile du motif
     * @param i index de la PatternTile à récupérer
     * @return la PatternTile à l'indice i
     */
    public PatternTile get(int i) {
        return tiles.get(i);
    }

    /**
     * retourne un nouveau pattern représentant le motif tourné de 60°
     * @return nouveau Pattern tourné de 60°
     */
    public Pattern rotate60() {
        return new Pattern(tiles.stream()
                .map(t -> t.rotate60())
                .collect(Collectors.toList()));
    }

    /**
     * Vérifie si le motif représenté par le Pattern existe à la position donnée dans le plateau
     * @param plateau le plateau dans lequel on regarde
     * @param coo la coordonnée du plateau à laquelle on regarde
     * @return vrai si le motif est trouvé, faux sinon
     */
    public boolean check(Plateau plateau, CoordAxial coo) {
        return tiles.stream()
                .allMatch(t -> t.matchPlot(plateau, coo));
    }

    /**
     * Vérifie si le motif représenté par le Pattern existe à n'importe quelle coordonnée du plateau
     * @param plateau le plateau dans lequel on regarde
     * @return vrai si le motif est trouvé, faux sinon
     */
    boolean checkAll(Plateau plateau) {
        return plateau.getPlots()
                .entrySet()
                .stream()
                .anyMatch(c -> this.check(plateau, c.getKey()));
    }

    /**
     * Vérifie si le motif existe à n'importe quelle coordonnée du plateau, dans n'importe quelle orientation
     * @param plateau le plateau dans lequel on regarde
     * @return vrai si le motif est trouvé, faux sinon
     */
    public boolean checkAllRotate(Plateau plateau) {
        Pattern pat = new Pattern(this);
        for (int i = 0; i < 6; i++) {
            pat = pat.rotate60();
            if (pat.checkAll(plateau)) return true;
        }
        return false;
    }

}
