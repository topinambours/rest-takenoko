package takenoko.objectives;

import takenoko.joueur.Joueur;
import takenoko.objectives.patterns.Pattern;
import takenoko.Plateau;

public class PatternObjectiveCard extends ObjectiveCard {
    private int pointValue;



    private Pattern pattern;

    private Plateau plateau;



    /**
     * Constructeur d'une carte objectif pattern
     * @param pattern
     * @param pointValue
     */
    public PatternObjectiveCard(Pattern pattern, int pointValue) {
        this.pointValue = pointValue;
        this.pattern = pattern;
    }

    /**
     * Constructeur d'une carte objectif pattern vide
     * @param
     * @param
     */
    public PatternObjectiveCard() {
        this.pointValue = 0;
        this.pattern = null;
    }

    /**
     * Renvoie la valeur de point de la carte
     * @return
     */
    @Override
    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Permet de détécter si le pattern est présent sur la plateau
     * @return true si le pattern existe sur la plateau, sinon false
     */
    @Override
    public boolean isComplete() {
        return pattern.checkAllRotate(this.plateau);
    }

    @Override
    public int validate() {
        return pointValue;
    }

    /**
     * Permet d'instancier la carte dans le plateau et au joueur
     * @param plateau le plateau
     * @param joueur le propriétaire
     */
    @Override
    public void instanciate(Plateau plateau, Joueur joueur) {
        this.plateau = plateau;
        joueur.addPatternObjectiveCard(this);
    }

    @Override
    public String toString() {
        return "PatternObjectiveCard{" +
                "pointValue=" + pointValue +
                ", pattern=" + pattern +
                ", plateau=" + plateau +
                '}';
    }

    @Override
    public String getTypeName() {
        return "Parcelles";
    }
}
