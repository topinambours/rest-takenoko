package takenoko;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tuile {

    private final int id;

    private final Couleur couleur;

    public Tuile(){
        this.id = -1;
        this.couleur = Couleur.BLEU;
    }

    public Tuile(int id, Couleur couleur){
        this.id = id;
        this.couleur = couleur;
    }

    public int getId() {
        return id;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    @Override
    public String toString() {
        return String.format("Tuile n°%d : %s", this.id, this.couleur.toString());
    }

}
