package Takenoko.Deque;

import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
import Takenoko.Util.Exceptions.EmptyDeckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Class Deck, it can make a deck of objects.
 * It can get the first and the last element
 * of the deck. It can remove or add an element
 * at the top or at the end.
 * It contains a deque and the size of the deck.
 */
@Component
public class Deck {

    /**
     * File à double queue, permet d'insérer au début et à la fin de la file
     */
    private Deque<Plot> deck;

    /**
     * Nombre total de parcelles dans le jeu (parcelle de départ exclue)
     */
    private static final int NB_PARCELLE = 27;

    /**
     * Nombre de couleurs associées aux parcelles voir {@link Couleur}
     */
    private static final int NB_COLOR = 3;

    /**
     * Nombre de parcelles vertes
     */
    private static final int NB_VERTE = 11;

    /**
     * Nombre de parcelles roses
     */
    private static final int NB_ROSE = 7;

    /**
     * Nombre de parcelles jaunes
     */
    private static final int NB_JAUNE = 9;

    /**
     * Dictionnaire associant à une couleur, le nombre de parcelles correspondantes
     */
    private HashMap<Couleur, Integer> nbPlotByColor;

    /**
     * Constructeur se chargeant de construire les stuctures de données
     */
    @Autowired
    public Deck(List<Plot> plots){
        List<Plot> cpy = new ArrayList<>(plots);
        Collections.shuffle(cpy);
        deck = new ArrayDeque<>(cpy);
    }

    /**
     * Constructeur se chargeant de construire les stuctures de données
     */
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
        List<Plot> preDeck = new ArrayList<>();
        for (int i = 0;i < NB_COLOR; i++){
            for (int j = 0; j < nbPlotByColor.get(Couleur.getById(i)); j++){
                preDeck.add(new Plot(Couleur.getById(i)));
            }
        }
        Collections.shuffle(preDeck);

        deck.addAll(preDeck);

        return NB_PARCELLE == this.getSize();

    }

    /**
     * Get the first element of the deck.
     * @return first element in deck
     */
    public Plot getFirst() throws EmptyDeckException {
        if (deck.isEmpty()){
            throw new EmptyDeckException();
        }
        return deck.peekFirst();
    }

    /**
     * Get the last element of the deck.
     * @return last element in deck
     */
    public Plot getLast() throws EmptyDeckException {
        if (deck.isEmpty()){
            throw new EmptyDeckException();
        }
        return deck.peekLast();
    }

    /**
     * Add an element at the top of the deck.
     * @param plot {@link}
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
    public Plot popFirst() throws EmptyDeckException {
        if (deck.isEmpty()){
            throw new EmptyDeckException();
        }
        return deck.pollFirst();
    }

    /**
     * Delete the last element of the deck.
     * @return
     */
    public Plot popLast() throws EmptyDeckException {
        if (deck.isEmpty()){
            throw new EmptyDeckException();
        }
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
