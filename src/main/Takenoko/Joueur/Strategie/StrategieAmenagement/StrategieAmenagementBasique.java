package Takenoko.Joueur.Strategie.StrategieAmenagement;

import Takenoko.Joueur.Joueur;
import Takenoko.Objectives.Amenagement.Amenagement;
import Takenoko.Objectives.Amenagement.DeckAmenagement;
import Takenoko.Plateau;
import Takenoko.Plot.Plot;

import java.util.HashSet;
import java.util.Optional;

public class StrategieAmenagementBasique implements StrategieAmenagement {

    @Override
    public Amenagement chooseAmenagement(DeckAmenagement deckAmenagement) {
        HashSet<Amenagement> hashSet = deckAmenagement.getAmenagementSet();

        if (hashSet.contains(Amenagement.BASSIN)){
            deckAmenagement.drawAmenagement(Amenagement.BASSIN);
            return Amenagement.BASSIN;
        }else{
            if (hashSet.contains(Amenagement.ENGRAIS)){
                deckAmenagement.drawAmenagement(Amenagement.ENGRAIS);
                return Amenagement.ENGRAIS;
            }else{
                if (hashSet.contains(Amenagement.ENCLOS)){
                    deckAmenagement.drawAmenagement(Amenagement.ENCLOS);
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