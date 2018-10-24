package Takenoko.Objectives.Amenagement;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Permet de créer un deck d'aménagement
 */
public class DeckAmenagement {
    private ArrayList<Amenagement> amenagements;
    private final int NB_AMENAGEMENT = 9;
    private final int NB_PAR_AMENAGEMENT = NB_AMENAGEMENT/3;

    public DeckAmenagement(ArrayList amenagements) {
        this.amenagements = amenagements;
    }

    /**
     * Le constructeur par defaut
     */
    public DeckAmenagement() {
        this.amenagements = new ArrayList<Amenagement>();
    }

    /**
     * Permet d'initialiser le deck
     */
    public void init(){
        for(int i = 0; i<NB_PAR_AMENAGEMENT;i++){
            amenagements.add(Amenagement.BASSIN);
        }

        for(int i = 0; i<NB_PAR_AMENAGEMENT;i++){
            amenagements.add(Amenagement.ENCLOS);
        }

        for(int i = 0; i<NB_PAR_AMENAGEMENT;i++){
            amenagements.add(Amenagement.ENGRAIS);
        }

        shuffle();
    }

    /**
     * Permet de mélanger la pioche
     */
    private void shuffle(){
        Collections.shuffle(amenagements);
    }

    /**
     * Retourne le deck
     * @return ArrayList
     */
    public ArrayList<Amenagement> getAmenagements() {
        return amenagements;
    }

    /**
     * Permet de tirer un aménagement
     * @return Amenagement
     */
    public Amenagement drawAmenagement(){
        return amenagements.remove(0);
    }

    /**
     * Permet de tirer un aménagement en particulier
     * @param amenagement Amenagement
     * @return Boolean true|false
     */
    public boolean drawAmenagement(Amenagement amenagement){
        return this.amenagements.remove(amenagement);
    }

    /**
     * ermet de connaitre la taille de la pioche
     * @return int
     */
    public int size(){
        return amenagements.size();
    }




    @Override
    public String toString() {
        return "DeckAmenagement{" +
                "amenagements=" + amenagements.toString() +
                '}';
    }
}
