package takenoko.objectives;

import takenoko.joueur.Joueur;
import takenoko.Plateau;
import takenoko.properties.Couleur;

public class GardenObjectiveCard extends ObjectiveCard{

    //private AmenagementType amenagement;
    private Couleur color;
    private int nbTower;

    private int pointValue;

    private Plateau plateau;

    public GardenObjectiveCard(Couleur color, int nbTower, int pointValue){
        this.color = color;
        this.nbTower = nbTower;
        this.pointValue = pointValue;
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

    @Override
    public String getTypeName() {
        return "Jardinier";
    }
}
