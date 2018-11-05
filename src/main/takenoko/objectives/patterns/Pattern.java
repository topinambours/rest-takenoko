package takenoko.objectives.patterns;

import takenoko.Plateau;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;
import takenoko.util.Pair;

import java.util.ArrayList;
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
    private CoordAxial bestAnchor;
    private double bestCompletion;

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

    /**
     * renvoie le taux de complétion et la coordonnée du meilleur endroit où le pattern existe partiellement
     * @param plateau le plateau de jeu à examiner
     * @return une paire contenant le taux de complétion et la coordonnée. taux négatif si le pattern est impossible (ne
     * devrait que rarement se produire)
     */
    public Pair<Double, CoordAxial> bestPartialCompletion(Plateau plateau) {
        CoordAxial coo = new CoordAxial(0, 0);
        int score = 0;
        int oldscore = 0;
        Pattern pat = new Pattern(this);
        for (int i = 0; i < 6; i++) {
            pat = pat.rotate60();
            for (CoordAxial c : plateau.getPlots().keySet()) {
                score = Math.max(score, partialCompletionAt(plateau, c));
                if (score > oldscore) coo = c;
                oldscore = score;
            }
        }
        double res = (double)score/tiles.size();
        bestCompletion = res;
        Pair<Double, CoordAxial> pair = new Pair<Double, CoordAxial>(res, coo);

        return pair;
    }

    /**
     * retourne le nombre d'éléments du pattern partiellement complété. renvoie -1 si pattern invalide à cette position
     * @param plateau le plateau de jeu à examiner
     * @param coordAxial la coordonnée du plateau à examiner
     * @return le nombre de parcelles déjà existantes pour ce pattern à cette position. -1 si invalide
     */
    public int partialCompletionAt(Plateau plateau, CoordAxial coordAxial) {
        int points = 0;
        for (PatternTile tile : tiles) {
            if (tile.matchPlot(plateau, coordAxial)) points++;
            if (plateau.getPlot(coordAxial) != null && !tile.matchPlot(plateau, coordAxial)) return -1;
        }
        return points;
    }

    public List<Pair<Couleur, CoordAxial>> nextParcelsToProgress(Plateau plateau) {
        List <CoordAxial> legalPos = plateau.legalPositions();
        Pair<Double, CoordAxial> best = bestPartialCompletion(plateau);
        CoordAxial anchor = best.getRight();

        List<Pair<Couleur, CoordAxial>> res = new ArrayList<>();

        for (PatternTile tile : tiles) {
            if (legalPos.contains(tile.toConcretePosition(anchor))) {
                res.add(new Pair<Couleur, CoordAxial>(tile.getCouleur(), tile.toConcretePosition(anchor)));
            }
        }
        return res;
    }
}
