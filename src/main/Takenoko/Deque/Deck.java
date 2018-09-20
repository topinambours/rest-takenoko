package Takenoko.Deque;

import Takenoko.Parcel;

import java.util.Deque;
import java.util.LinkedList;


/**
 * Class Deck, it can make a deck of objects.
 * It can get the first and the last element
 * of the deck. It can remove or add an element
 * at the top or at the end.
 * It contains a deque and the size of the deck.
 */
public class Deck {

    private Deque<Parcel> deck;
    private int size;

    public Deck(){

        deck = new LinkedList();
        size = 0;
    }

    /**
     * Get the first element of the deck.
     * @return
     */
    public Parcel getFirst(){
        return deck.peekFirst();
    }

    /**
     * Get the last element of the deck.
     * @return
     */
    public Parcel getLast(){
        return deck.peekLast();
    }

    /**
     * Add an element at the top of the deck.
     * @param parcels
     * @return
     */
    public boolean addFirst(Parcel parcels){
        boolean res = deck.offerFirst(parcels);
        size = size + 1;
        return res;
    }

    /**
     * Add an element at the end of the deck.
     * @param parcels
     * @return
     */
    public boolean addLast(Parcel parcels){
        boolean res = deck.offerLast(parcels);
        size = size + 1;
        return res;
    }

    /**
     * Delete the first element of the deck.
     * @param parcels
     * @return
     */
    public Parcel popFirst(Parcel parcels){
        Parcel parcels1 = deck.pollFirst();
        if(parcels1==null){
            return null;
        }
        else{
            size = size - 1;
            return parcels1;
        }
    }

    /**
     * Delete the last element of the deck.
     * @param parcels
     * @return
     */
    public Parcel popLast(Parcel parcels){
        Parcel parcels1 = deck.pollLast();
        if(parcels1==null){
            return null;
        }
        else{
            size = size - 1;
            return parcels1;
        }
    }

    /**
     * Get the size of the deck.
     * @return
     */
    public int getSize(){
        return size;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
