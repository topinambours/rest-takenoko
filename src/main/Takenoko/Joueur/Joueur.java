package Takenoko.Joueur;

import Takenoko.Deque.Deck;
import Takenoko.Parcel.Parcel;
import Takenoko.Plateau;

public class Joueur {

    private Deck hand;
    private int number;


    public Joueur(int n){
        hand = new Deck();
        number = n;
    }

    public int getNumber(){
        return number;
    }

    public void draw(Deck deck){
        Parcel parcel = deck.popFirst();
        hand.addLast(parcel);
    }

    public void replaceInDeck(Deck deck, Parcel parcel){
        hand.remove(parcel);
        deck.addLast(parcel);
    }

    public void putParcel(Parcel parcel, int q, int r, Plateau board){
        hand.remove(parcel);
        board.putParcel(parcel, q, r);
    }

}
