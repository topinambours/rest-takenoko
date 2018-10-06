package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Objectives.Patterns.Pattern;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

public class PatternObjectiveCard extends ObjectiveCard {
    private int pointValue;
    private Pattern pattern;

    private Plateau plateau;

    public PatternObjectiveCard(Pattern pattern, int value) {
        this.pointValue = value;
        this.pattern = pattern;
    }

    @Override
    public int getPointValue() {
        return pointValue;
    }

    @Override
    public boolean isComplete() {
        return pattern.checkAllRotate(this.plateau);
    }


    public boolean isCompleteCoord(CoordAxial coo){
        return pattern.checkRotate(this.plateau, coo);
    }

    @Override
    public int validate() {
        return pointValue;
    }

    @Override
    public void instanciate(Plateau plateau, Joueur joueur) {
        this.plateau = plateau;
    }
}
