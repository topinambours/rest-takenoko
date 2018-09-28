package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;

public class MotifObjectiveCard extends ObjectiveCard {
    private int pointValue;
    private Joueur owner;

    private boolean vert;
    private boolean jaune;
    private boolean rose;

    //4 motif different numérotés
    private int motifNumber;

    public int getPointValue(){
        return pointValue;
    }

    public boolean isComplete(){
        //@TODO wait for implement motif
        return false;
    }

    public int validate(){
        return pointValue;
    }

    public MotifObjectiveCard(int value, int motif, boolean vert, boolean jaune, boolean rose){
        pointValue = value;
        motifNumber = motif;
        this.vert = vert;
        this.rose = rose;
        this.jaune = jaune;
    }

    public void instanciate(Plateau plateau, Joueur joueur){
        owner = joueur;
    }
}
