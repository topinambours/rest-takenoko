package takenoko.deck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import takenoko.plot.Plot;

import java.util.List;

/**
 * Pioche de parcelles
 */
@Component
public class PlotsDeck extends Deck<Plot> {

    /**
     * Initialisé à partir d'une liste de parcelles
     * @param plots
     */
    @Autowired
    public PlotsDeck(List<Plot> plots){
        super(plots);
    }

    /**
     * Création d'une pioche vide
     */
    public PlotsDeck(){
        super();
    }

    /**
     * Fonction de formatage toString
     * @return Description de la pioche et sa taille
     */
    @Override
    public String toString() {
        return String.format("Pioche de parcelles : Hauteur %d", size());
    }
}
