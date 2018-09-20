package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Joueur.Joueur;
import Takenoko.Parcel.Parcel;

/**
 * La classe Game permet de créer une partie
 */
public class Game {


    private Plateau gameBoard;
    private Deck deck;
    private Joueur bot;

    public Game() {
        this.deck = new Deck();
        this.gameBoard = new Plateau();
        //Todo: Création d'un ou plusieurs robot

    }

    /**
     * La fonction end permet de savoir si la partie est terminée
     * @return boolean true|false
     */
    public boolean end(){
        return deck.getSize()==0;
    }


    /**
     * La fonction principale qui permet de lancer et faire la game
     */
    public void play(){
        while(!end()){ //Tant que la partie n'est pas terminée
            Parcel current = deck.getFirst();
            bot.putParcel(current, gameBoard);
        }
    }

    public Deck getDeck(){
        return deck;
    }


}
