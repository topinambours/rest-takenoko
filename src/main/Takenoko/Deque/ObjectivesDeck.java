package Takenoko.Deque;

import Takenoko.Objectives.ObjectiveCard;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Pioche réservée aux cartes objectifs
 */
public class ObjectivesDeck {

    /**
     * Pile de pioche
     */
    private Stack<ObjectiveCard> stack;

    /**
     * La taille de la pioche
     */
    private int size;

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

}
