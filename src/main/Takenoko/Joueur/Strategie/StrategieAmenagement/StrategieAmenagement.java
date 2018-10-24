package takenoko.joueur.strategie.StrategieAmenagement;

import takenoko.joueur.Joueur;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.objectives.amenagement.DeckAmenagement;
import takenoko.Plateau;
import takenoko.Plot.Plot;

import java.util.Optional;

/**
 * Permet de définir la stratégie a adopter pour les amenagements.
 * Ce qui comprend :
 * <ul>
 *     <li>chooseAmenagement permet de choisir un aménagement</li>
 *     <li>plotAmenagement permet de choisir un plot a aménager</li>
 * </ul>
 */
public interface StrategieAmenagement {

    Amenagement chooseAmenagement(DeckAmenagement deckAmenagement);
    Optional<Plot> plotAmenagement(Joueur joueur, Plateau plateau);

}
