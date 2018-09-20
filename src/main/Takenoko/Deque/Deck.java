package Takenoko.Deque;

import Takenoko.Parcel.Parcel;

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

    private Deque<Parcel> deck;

    public Deck(){
        deck = new ArrayDeque<>();
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
     * @param parcel
     * @return
     */
    public boolean addFirst(Parcel parcel){
        boolean res = deck.offerFirst(parcel);
        return res;
    }

    /**
     * Add an element at the end of the deck.
     * @param parcel
     * @return
     */
    public boolean addLast(Parcel parcel){
        boolean res = deck.offerLast(parcel);
        return res;
    }

    /**
     * Delete the first element of the deck.
     * @return
     */
    public Parcel popFirst(){
        return deck.pollFirst();
    }

    /**
     * Delete the last element of the deck.
     * @return
     */
    public Parcel popLast(){
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

    public boolean isEmpty(){
        if(getSize()==0){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
