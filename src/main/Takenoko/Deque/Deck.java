package Takenoko.Deque;

import Takenoko.Plot.Couleur;
import Takenoko.Plot.Plot;

import java.awt.*;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashMap;


/**
 * Class Deck, it can make a deck of objects.
 * It can get the first and the last element
 * of the deck. It can remove or add an element
 * at the top or at the end.
 * It contains a deque and the size of the deck.
 */
public class Deck {

    private Deque<Plot> deck;
    private final int NB_PARCELLE = 27;
    private final int NB_COLOR = 3;

    private final int NB_VERTE = 11;
    private final int NB_ROSE = 7;
    private final int NB_JAUNE = 9;
    private HashMap<Couleur, Integer> nbPlotByColor;




    public Deck(){
        deck = new ArrayDeque<>();
        nbPlotByColor = new HashMap<>();
        nbPlotByColor.put(Couleur.VERT, NB_VERTE);
        nbPlotByColor.put(Couleur.ROSE,NB_ROSE);
        nbPlotByColor.put(Couleur.JAUNE,NB_JAUNE);
    }


    /**
     * Permet d'initialiser le deck
     * @return boolean true|false
     */
    public boolean init() {

        for (int i = 0;i < NB_COLOR; i++){
            for (int j = 0; j < nbPlotByColor.get(Couleur.getById(i)); j++){
                deck.addFirst(new Plot(Couleur.getById(i)));
            }
        }

        return NB_PARCELLE == this.getSize();

    }



    /*
    public boolean init(){

        for(int i = 0; i < NB_COULEUR; i++){
            for (int j = 0; j < Couleur.getnb(i); j++){
                deck.addFirst(new Plot(Couleur.getById(i)));
                System.out.println(Couleur.getById(i));
            }
        }

        return NB_PARCELLE == this.getSize();
     }
     */

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
