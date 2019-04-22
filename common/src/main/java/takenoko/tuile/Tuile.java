package takenoko.tuile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import communication.container.BambouContainer;
import lombok.Data;
import takenoko.Couleur;

/**
 * Une tuile takenoko dispose d'une couleur, d'un aménagement ainsi qu'un nombre de bambou compris en 0 et 4
 * Une tuile peut être irrigée ou non (haveWater)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Tuile {

    /**
     * Identifiant unique de la tuile (pour un plateau de jeu)
     */
    private final int unique_id;

    /**
     * Couleur de la tuile
     */
    private final Couleur couleur;

    /**
     * Aménagement effectif sur la tuile {@link Amenagement}
     */
    private Amenagement amenagement;

    /**
     * Marqueur d'irrigation
     */
    private Boolean haveWater;

    /**
     * Nombre de bambous sur la tuile
     * De la même couleur que la tuile
     */
    private int nbBambous;

    public Tuile() {
        this(-1, Couleur.BLEU, Amenagement.NONE);
    }

    public Tuile(int id, Couleur couleur) {
        this(id, couleur, Amenagement.NONE);
    }

    public Tuile(int id, Couleur couleur, Amenagement amenagement){
        this.unique_id = id;
        this.couleur = couleur;
        this.amenagement = amenagement;
        this.haveWater = couleur == Couleur.BLEU || amenagement == Amenagement.BASSIN;
        this.nbBambous = 0;
    }

    /**
     * Permet de savoir si la pousse de bambou est possible sur la tuile
     * @return vrai si la tuile dispose de moins de 4 bambous et si elle est irriguée
     */
    public boolean poussePossible(){
        return haveWater && nbBambous <= 3 && couleur != Couleur.BLEU;
    }

    /**
     * Permet de faire pousser le ou les bambous sur la tuile
     * Une tuile gagne 2 bambous si elle dispose de l'aménagement engrais
     * Peut être appelé sur une tuile non irrigée
     */
    public void pousserBambou(){
        nbBambous += poussePossible() ? 1 : 0;
        if (amenagement == Amenagement.ENGRAIS){
            nbBambous += poussePossible() ? 1 : 0;
        }
    }

    /**
     * Enlève un bambou à la tuile si celle si en dispose
     * @return le nombre et la couleur du bambou enlevé valeur comprise entre 0 et 1
     */
    public BambouContainer enleverBambou(){
        int nb_to_remove = (nbBambous > 0) ? 1 : 0;
        nbBambous -= nb_to_remove;
        return new BambouContainer(couleur, nb_to_remove);
    }


    /**
     * Irrigue sans verification la tuile
     */
    public void irrigate(){
        this.setHaveWater(true);
    }

    public int getUnique_id() {
        return unique_id;
    }

    public Couleur getCouleur() {
        return couleur;
    }


    public Amenagement getAmenagement() {
        return amenagement;
    }

    public void setAmenagement(Amenagement amenagement) {
        this.amenagement = amenagement;
    }

    public Boolean getHaveWater() {
        return haveWater;
    }

    public void setHaveWater(Boolean haveWater) {
        this.haveWater = haveWater;
    }

    public int getNbBambous() {
        return nbBambous;
    }

    public void setNbBambous(int nbBambous) {
        this.nbBambous = nbBambous;
    }


    public String generateDrawCode(CoordAxial pos){

        String color = "black";
        switch (couleur){
            case ROSE: color = "pink"; break;
            case JAUNE: color = "yellow"; break;
            case VERT: color = "green"; break;
            case BLEU: color = "blue"; break;
        }

        double x = (500 - 43.5) + 87.25 * pos.getQ() + pos.getR() * 43.5;
        double y = (500 - 50) + 75 * pos.getR();

        return "draw.polygon('43.5,0 87,25 87,75 43.5,100 0,75 0,25 43.5,0').fill('" + color + "').move("+x+", "+y+").stroke({ width: 1, color: 'black' });" +
                "\ndraw.plain('"+pos.toString()+"').move("+(x + 25)+", "+(y+ 10)+");" +
                "\ndraw.plain('Bambou :"+nbBambous+"').move("+(x )+", "+(y+ 43.5)+");" +
                "\ndraw.plain('Irrig :"+ haveWater +"').move("+(x +10 )+", "+(y+ 65)+");";
    }


}
