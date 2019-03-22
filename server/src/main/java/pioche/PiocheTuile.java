package pioche;

import org.springframework.context.annotation.Bean;
import communication.Container.TuileContainer;
import takenoko.Couleur;
import takenoko.Tuile;

import java.util.ArrayList;
import java.util.List;

/**
 * Pioche de parcelles
 */
public class PiocheTuile extends Pioche<Tuile> {

    /**
     * Initialisé à partir d'une liste de parcelles
     * @param tuiles
     */
    public PiocheTuile(List<Tuile> tuiles){
        super(tuiles);
    }

    /**
     * Création d'une Container vide
     */
    public PiocheTuile(){
        super();
    }

    /**
     * Fonction de formatage toString
     * @return Description de la Container et sa taille
     */
    @Override
    public String toString() {
        return String.format("Pioche de parcelles : Hauteur %d", size());
    }

    public TuileContainer toContainer(){
        return new TuileContainer(List.copyOf(getDeque()));
    }

    @Bean(name="pioche_depart")
    public PiocheTuile pioche_depart(){
        ArrayList<Tuile> tuiles = new ArrayList<>();
        for (int i = 1; i <= 27 ; i++){
            if (i <= 11 ) tuiles.add(new Tuile(i, Couleur.VERT));
            if (i > 11 && i <= 18) tuiles.add(new Tuile(i, Couleur.ROSE));
            if (i > 18) tuiles.add(new Tuile(i, Couleur.JAUNE));
        }
        return new PiocheTuile(tuiles);
    }
}
