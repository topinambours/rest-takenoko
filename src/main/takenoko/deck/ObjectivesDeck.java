package takenoko.deck;

import takenoko.objectives.ObjectiveCard;
import takenoko.objectives.PandaObjectiveCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Pioche réservée aux cartes objectifs
 */
@Component
@Configurable
@ImportResource(locations = {"ObjectivesGarden.xml", "ObjectivesPattern.xml"})
public class ObjectivesDeck extends Deck {

    /**
     * Type de pioche parmis {Panda, parcelles, jardinier}
     */
    private String type;

    /**
     * Création d'une pioche de cartes objectifs à partir d'une liste
     * Il est préférable d'utiliser une pioche pour chacun des types.
     * @param initDeck liste de cartes Objectifs
     */
    @Autowired
    public ObjectivesDeck(List<ObjectiveCard> initDeck){
        super(initDeck);
        type = "?";
        if (!initDeck.isEmpty()) {
            type = initDeck.get(0).getTypeName();
        }
    }

    /**
     * Création d'une pioche vide, le type est inconnu.
     */
    public ObjectivesDeck() {
        super();
        type = "?";
    }

    /**
     * Fonction de formatage
     * @return Type de pioche ainsi que sa hauteur.
     */
    @Override
    public String toString() {
        return String.format("Pioche d'objectifs %s : Hauteur %d",type, size());
    }



    /**
     * Bean Spring permettant de créer la pioche d'objectifs Panda en conformité avec les règles.
     * @return Pioche d'objectifs panda
     */
    @Bean
    @Scope("prototype")
    public ObjectivesDeck pandObjDeck(){
        ArrayList<ObjectiveCard> out = new ArrayList<>();
        for (int i = 0; i <= 14; i++){
            if (i >=  12){
                out.add(new PandaObjectiveCard(1,1,1,6));
                continue;
            }
            if (i >=  9){
                out.add(new PandaObjectiveCard(0,0,2,5));
                continue;
            }
            if (i >=  5){
                out.add(new PandaObjectiveCard(0,2,0,4));

            }
            else {
                out.add(new PandaObjectiveCard(1,1,1,6));
            }
        }
        return new ObjectivesDeck(out);
    }

}
