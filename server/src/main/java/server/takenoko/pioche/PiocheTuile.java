package server.takenoko.pioche;

import communication.container.TuileContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import takenoko.Couleur;
import takenoko.tuile.Amenagement;
import takenoko.tuile.Tuile;

import java.util.ArrayList;
import java.util.List;

/**
 * Pioche de parcelles
 */
@Component
public class PiocheTuile extends Pioche<Tuile> {

    /**
     * Initialisé à partir d'une liste de parcelles
     *
     * @param tuiles
     */
    public PiocheTuile(List<Tuile> tuiles) {
        super(tuiles);
    }

    /**
     * Création d'une Container vide
     */
    @Autowired
    public PiocheTuile() {
        super();
    }

    /**
     * Fonction de formatage toString
     *
     * @return Description de la Container et sa taille
     */
    @Override
    public String toString() {
        return String.format("Pioche de parcelles : Hauteur %d", size());
    }

    public TuileContainer toContainer() {
        return new TuileContainer(List.copyOf(getDeque()));
    }

    @Primary
    @Bean(name = "piocheDepart")
    @Scope("prototype")
    public PiocheTuile pioche_depart() {
        int i = 1;
        ArrayList<Tuile> tuiles = new ArrayList<>();

        tuiles.add(new Tuile(i++, Couleur.VERT));
        tuiles.add(new Tuile(i++, Couleur.VERT));
        tuiles.add(new Tuile(i++, Couleur.VERT));
        tuiles.add(new Tuile(i++, Couleur.VERT));
        tuiles.add(new Tuile(i++, Couleur.VERT));
        tuiles.add(new Tuile(i++, Couleur.VERT));
        tuiles.add(new Tuile(i++, Couleur.VERT, Amenagement.BASSIN));
        tuiles.add(new Tuile(i++, Couleur.VERT, Amenagement.BASSIN));
        tuiles.add(new Tuile(i++, Couleur.VERT, Amenagement.ENCLOS));
        tuiles.add(new Tuile(i++, Couleur.VERT, Amenagement.ENCLOS));
        tuiles.add(new Tuile(i++, Couleur.VERT, Amenagement.ENGRAIS));



        tuiles.add(new Tuile(i++, Couleur.ROSE));
        tuiles.add(new Tuile(i++, Couleur.ROSE));
        tuiles.add(new Tuile(i++, Couleur.ROSE));
        tuiles.add(new Tuile(i++, Couleur.ROSE));
        tuiles.add(new Tuile(i++, Couleur.ROSE, Amenagement.BASSIN));
        tuiles.add(new Tuile(i++, Couleur.ROSE, Amenagement.ENCLOS));
        tuiles.add(new Tuile(i++, Couleur.ROSE, Amenagement.ENGRAIS));

        tuiles.add(new Tuile(i++, Couleur.JAUNE));
        tuiles.add(new Tuile(i++, Couleur.JAUNE));
        tuiles.add(new Tuile(i++, Couleur.JAUNE));
        tuiles.add(new Tuile(i++, Couleur.JAUNE));
        tuiles.add(new Tuile(i++, Couleur.JAUNE));
        tuiles.add(new Tuile(i++, Couleur.JAUNE));
        tuiles.add(new Tuile(i++, Couleur.JAUNE, Amenagement.BASSIN));
        tuiles.add(new Tuile(i++, Couleur.JAUNE, Amenagement.ENCLOS));
        tuiles.add(new Tuile(i, Couleur.JAUNE, Amenagement.ENGRAIS));

        return new PiocheTuile(tuiles);
    }

}
