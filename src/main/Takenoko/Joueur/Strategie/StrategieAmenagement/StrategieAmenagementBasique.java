package takenoko.joueur.strategie.StrategieAmenagement;

import takenoko.deck.AmenagementDecks;
import takenoko.joueur.Joueur;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.Plateau;
import takenoko.Plot.Plot;
import takenoko.util.exceptions.EmptyDeckException;

import java.util.HashSet;
import java.util.Optional;

public class StrategieAmenagementBasique implements StrategieAmenagement {

    @Override
    public Amenagement chooseAmenagement(AmenagementDecks deckAmenagement) throws EmptyDeckException {

        if (!deckAmenagement.isEmpty(Amenagement.BASSIN)){
            deckAmenagement.draw(Amenagement.BASSIN);
            return Amenagement.BASSIN;
        }else{
            if (!deckAmenagement.isEmpty(Amenagement.ENGRAIS)){
                deckAmenagement.draw(Amenagement.ENGRAIS);
                return Amenagement.ENGRAIS;
            }else{
                if (!deckAmenagement.isEmpty(Amenagement.ENCLOS)){
                    deckAmenagement.draw(Amenagement.ENCLOS);
                    return Amenagement.ENCLOS;
                }else{
                    return Amenagement.NON;
                }
            }
        }

    }

    @Override
    public Optional<Plot> plotAmenagement(Joueur joueur,Plateau plateau) {
        if (!joueur.getAmenagements().isEmpty()){
            return Optional.of(plateau.getLastPlop());
        }else{
            return Optional.empty();
        }

    }
}
