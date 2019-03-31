package takenoko.tuile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import takenoko.Couleur;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tuile {

    private final int unique_id;

    private final Couleur couleur;

    public Tuile() {
        this.unique_id = -1;
        this.couleur = Couleur.BLEU;
    }

    public Tuile(int id, Couleur couleur) {
        this.unique_id = id;
        this.couleur = couleur;
    }

    public int getUnique_id() {
        return unique_id;
    }

    public Couleur getCouleur() {
        return couleur;
    }


    @Override
    public String toString() {
        return String.format("Tuile id=%d : %s", this.unique_id, this.couleur.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuile tuile = (Tuile) o;
        return Objects.equals(unique_id, tuile.unique_id) &&
                couleur == tuile.couleur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unique_id, couleur);
    }
}
