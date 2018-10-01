package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;

public class PandaObjectiveCard extends ObjectiveCard {
    private int vertRequis;
    private int jauneRequis;
    private int roseRequis;

    private int pointValue;
    private Joueur owner;

    @Override
    public int getPointValue() {
        return pointValue;
    }

    @Override
    public boolean isComplete() {
        return (owner.getBambousVerts() >= vertRequis && owner.getBambousJaunes() >= jauneRequis && owner.getBambousRoses() >= roseRequis);
    }

    @Override
    public int validate() {
        owner.setBambousVerts(owner.getBambousVerts() - vertRequis);
        owner.setBambousJaunes(owner.getBambousJaunes() - jauneRequis);
        owner.setBambousRoses(owner.getBambousRoses() - roseRequis);
        return pointValue;
    }

    public PandaObjectiveCard(int vert, int jaune, int rose, int value) {
        vertRequis = vert;
        jauneRequis = jaune;
        roseRequis = rose;
        pointValue = value;
    }

    @Override
    public void instanciate(Plateau plateau, Joueur joueur) {
        owner = joueur;
    }
}
