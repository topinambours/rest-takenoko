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

    public Deck getHand(){
        return hand;
    }

    public Parcel draw(Deck deck){
        Parcel parcel = deck.popLast();
        hand.addLast(parcel);
        return parcel;
    }

    public void multiDraw(Deck deck, int n){
        int i = n;
        while(!deck.isEmpty() && i > 0){
            draw(deck);
            i--;
        }
    }

    public Parcel replaceInDeck(Deck deck, Parcel parcel){
        hand.remove(parcel);
        deck.addFirst(parcel);
        return parcel;
    }

    public CoordAxial putParcel(Parcel parcel, Plateau board){
        hand.remove(parcel);
        CoordAxial coor = strategie.getCoord();
        parcel.setCoord(coor.getQ(),coor.getR());
        board.putParcel(parcel);
        return coor;
    }

}
