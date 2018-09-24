package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieRandom;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Util.Console;

/**
 * La classe Game permet de créer une partie
 */
public class Game {

    private Plateau gameBoard;
    private Deck deck;
    private Joueur joueur;
    private Plateau plateau;

    public Game() {
        this.deck = new Deck();
        Plot parc = new Plot();
        for (int i = 0; i < 28; i++) {
            deck.addFirst(parc);
        }

        this.plateau = new Plateau();
        this.plateau.addStartingPlot(new Plot());

        this.joueur = new Joueur(1, new StrategieRandom());
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
            Plot current = deck.popFirst();
            CoordAxial coord = joueur.putPlot(current,plateau);

            Console.Log.println("Le joueur pose une parcelle ici : "+coord);

            //Todo : faire piocher -> faire poser
        }
        Console.Log.println("La partie est terminée");
        Console.Log.debugPrintln("Ceci est une ligne de debug");
    }

    public Deck getDeck(){
        return deck;
    }


}
