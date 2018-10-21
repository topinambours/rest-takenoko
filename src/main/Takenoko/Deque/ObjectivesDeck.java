package Takenoko.Deque;

import Takenoko.Objectives.ObjectiveCard;
import Takenoko.Objectives.PandaObjectiveCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Pioche réservée aux cartes objectifs
 */
@Component
@Configurable
@ImportResource("ObjectivesGarden.xml")
public class ObjectivesDeck {

    /**
     * Pile de pioche
     */
    private Stack<ObjectiveCard> stack;

    /**
     * La taille de la pioche
     */
    private int size;

    @Autowired
    public ObjectivesDeck(List<ObjectiveCard> initDeck){
        Collections.shuffle(initDeck);
        stack = new Stack<>();
        for (ObjectiveCard card : initDeck){
            push(card);
        }
        size = initDeck.size();
    }

    public ObjectivesDeck() {
    }

    /**
     * Une pioche est initialisé à partir d'une liste de cartes
     * Cette liste est fabriqué au sein des classes filles
     * @param initDeck liste de départ
     */
    public void init(List<ObjectiveCard> initDeck){
        Collections.shuffle(initDeck);
        stack = new Stack<>();
        for (ObjectiveCard card : initDeck){
            push(card);
        }
        size = initDeck.size();
    }

    /**
     * Test si la pioche est vide
     * @return vrai si la pioche est vide
     */
    public Boolean isEmpty(){
        return stack.isEmpty();
    }

    /**
     * Insère un élément au sommet de la pioche
     * @param card carte à ajouter
     */
    private void push(ObjectiveCard card){
        stack.push(card);
        size += 1;
    }

    /**
     * Permet de piocher une carte, après appel, la carte est retirée de la pioche
     * @return la carte piochée
     */
    public ObjectiveCard pop(){
        size -= 1;
        return stack.pop();
    }

    /**
     * Permet de récupérer la taille de la pioche
     * @return taille de la pioche
     */
    public int getSize(){
        return size;
    }

    @Bean
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
        Collections.shuffle(out);
        return new ObjectivesDeck(out);
    }

}
