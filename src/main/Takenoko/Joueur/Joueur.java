package Takenoko.Joueur;

import Takenoko.Deque.Deck;
import Takenoko.Joueur.Strategie.Strategie;
import Takenoko.Parcel.CoordAxial;
import Takenoko.Parcel.Parcel;
import Takenoko.Plateau;

public class Joueur {

    private Deck hand;
    private int number;
    private Strategie strategie;


    public Joueur(int n,Strategie strategie){
        hand = new Deck();
        number = n;
        this.strategie = strategie;
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
        CoordAxial coor = strategie.getCoord();
        board.putParcel(parcel, coor.getQ(), coor.getR());
    }

}
