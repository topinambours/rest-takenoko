package Takenoko.Joueur;

import Takenoko.Deque.Deck;
import Takenoko.Joueur.Strategie.Strategie;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
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

    public Plot draw(Deck deck){
        Plot plot = deck.popLast();
        hand.addLast(plot);
        return plot;
    }

    public void multiDraw(Deck deck, int n){
        int i = n;
        while(!deck.isEmpty() && i > 0){
            draw(deck);
            i--;
        }
    }

    public Plot replaceInDeck(Deck deck, Plot plot){
        hand.remove(plot);
        deck.addFirst(plot);
        return plot;
    }

    /**
     * Fonction qui permet au joueur de poser un plot sur le board.
     * @param plot
     * @param board
     * @return
     */
    public CoordAxial putPlot(Plot plot, Plateau board){
        hand.remove(plot);
        CoordAxial coor = strategie.getCoord(board, plot);
        plot.setCoord(coor.getQ(),coor.getR());
        board.putPlot(plot);
        return coor;
    }

}
