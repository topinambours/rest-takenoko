package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Objectives.Patterns.Pattern;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

public class PatternObjectiveCard extends ObjectiveCard {
    private int pointValue;
    private Pattern pattern;

    private Plateau plateau;

    /**
     * Constructeur d'une carte objectif pattern
     * @param pattern
     * @param value
     */
    public PatternObjectiveCard(Pattern pattern, int value) {
        this.pointValue = value;
        this.pattern = pattern;
    }

    /**
     * Renvoie la valeur de point de la carte
     * @return
     */
    @Override
    public int getPointValue() {
        return pointValue;
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
}
