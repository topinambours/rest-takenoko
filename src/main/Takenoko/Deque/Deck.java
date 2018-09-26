package Takenoko.Deque;

import Takenoko.Plot.Couleur;
import Takenoko.Plot.Plot;

import java.util.Deque;
import java.util.ArrayDeque;


/**
 * Class Deck, it can make a deck of objects.
 * It can get the first and the last element
 * of the deck. It can remove or add an element
 * at the top or at the end.
 * It contains a deque and the size of the deck.
 */
public class Deck {

    private Deque<Plot> deck;
    private final int NB_PARCELLE = 24;
    private final int NB_COULEUR = 4;
    private final int NB_PARCELLE_PAR_COULEUR = 6;



    public Deck(){
        deck = new ArrayDeque<>();
    }

    public boolean init(){

        for(int i = 0; i < NB_COULEUR; i++){
            for (int j = 0; j < NB_PARCELLE_PAR_COULEUR; j++){
                deck.addFirst(new Plot(Couleur.getById(i)));
            }
        }

        return NB_PARCELLE == this.getSize();
     }

    /**
     * Get the first element of the deck.
     * @return
     */
    public Plot getFirst(){
        return deck.peekFirst();
    }

    /**
     * Get the last element of the deck.
     * @return
     */
    public Plot getLast(){
        return deck.peekLast();
    }

    /**
     * Add an element at the top of the deck.
     * @param plot
     * @return
     */
    public boolean addFirst(Plot plot){
        boolean res = deck.offerFirst(plot);
        return res;
    }

    /**
     * Add an element at the end of the deck.
     * @param plot
     * @return
     */
    public boolean addLast(Plot plot){
        boolean res = deck.offerLast(plot);
        return res;
    }

    /**
     * Delete the first element of the deck.
     * @return
     */
    public Plot popFirst(){
        return deck.pollFirst();
    }

    /**
     * Delete the last element of the deck.
     * @return
     */
    public Plot popLast(){
        return deck.pollLast();
    }

    /**
     * Delete an object o in the deck.
     * @param o
     * @return
     */
    public boolean remove(Object o){
        return deck.remove(o);
    }

    /**
     * Get the size of the deck.
     * @return
     */
    public int getSize(){
        return deck.size();
    }

    /**
     * Permet de tester si le deck est vide.
     * @return
     */
    public boolean isEmpty(){
        return getSize() == 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
