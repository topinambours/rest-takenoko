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
    private int score;


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

    public CoordAxial putPlot(Plot plot, Plateau board){
        hand.remove(plot);
        CoordAxial coor = strategie.getCoord();
        plot.setCoord(coor.getQ(),coor.getR());
        board.putPlot(plot);
        return coor;
    }

    /**
     * Getter score
     * @return int score
     */
    public int getScore() {
        return score;
    }

    /**
     * fonction privée Setter score
     * @param score int le score
     */
    private void setScore(int score) {
        this.score = score;
    }

    /**
     * addScore permet d'ajouter n points
     * @param number int nombre de points
     */
    public void addScore(int number){
        setScore(getScore()+number);
    }

    /**
     * addScore1 ajoute 1 point
     */
    public void addScore1(){
        addScore(1);
    }


}
