package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import Takenoko.Properties.Couleur;
import org.springframework.beans.factory.annotation.Autowired;

public class GardenObjectiveCard extends ObjectiveCard{

    //private AmenagementType amenagement;
    private Couleur color;
    private int nbTower;

    private int pointValue;

    private Plateau plateau;

    @Autowired
    public GardenObjectiveCard(Couleur color, int nbTower, int pointValue){
        this.color = color;
        this.nbTower = nbTower;
        this.pointValue = pointValue;
    }

    public Couleur getColor(){
        return color;
    }

    public int getTower(){
        return nbTower;
    }

    @Override
    public int getPointValue(){
        return pointValue;
    }

    @Override
    public boolean isComplete(){
        return plateau.isMotifInAll(color, nbTower);
    }

    @Override
    public int validate() {
        return pointValue;
    }

    @Override
    public void instanciate(Plateau plateau, Joueur joueur) {
        this.plateau = plateau;
        joueur.addGardenObjectiveCard(this);
    }

    @Override
    public String toString() {
        return "GardenObjectiveCard{" +
                "color=" + color +
                ", nbTower=" + nbTower +
                ", pointValue=" + pointValue +
                ", plateau=" + plateau +
                '}';
    }
}
