package takenoko.deck;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.util.exceptions.EmptyDeckException;

import java.util.EnumMap;

@Component
public class AmenagementDecks {

    private EnumMap<Amenagement, Integer> decks;

    private AmenagementDecks(int countEnclos, int countEngrais, int countBassin){
        decks = new EnumMap<>(Amenagement.class);
        decks.put(Amenagement.ENCLOS, countEnclos);
        decks.put(Amenagement.ENGRAIS, countEngrais);
        decks.put(Amenagement.BASSIN, countBassin);
    }
    private AmenagementDecks(){};

    @Bean
    @Scope("prototype")
    public AmenagementDecks defaultTakenokoAmDeck(){
        return new AmenagementDecks(3,3,3);
    }

    public Amenagement draw(Amenagement amenagement) throws EmptyDeckException {
        if (decks.containsKey(amenagement)){
            int tmp = decks.get(amenagement);
            decks.remove(amenagement);
            if (tmp > 1){
                decks.put(amenagement, tmp -1);
            }
            return amenagement;
        }else{
            throw new EmptyDeckException();
        }
    }

    public int size(Amenagement amenagement){
        if (isEmpty(amenagement)){
            return 0;
        }
        return decks.get(amenagement);
    }

    public boolean isEmpty(Amenagement amenagement){
        return !decks.containsKey(amenagement);
    }

    private String getSizeLabel(Amenagement amenagement){
        return  isEmpty(amenagement)? "Vide" : "" + size(amenagement);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Pioches aménagements :\n");
        sb.append(String.format("\t Pioche Enclos : %s\n", getSizeLabel(Amenagement.ENCLOS)));
        sb.append(String.format("\t Pioche Engrais : %s\n", getSizeLabel(Amenagement.ENGRAIS)));
        sb.append(String.format("\t Pioche Bassin : %s", getSizeLabel(Amenagement.BASSIN)));
        return sb.toString();
    }




}
