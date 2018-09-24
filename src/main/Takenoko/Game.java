package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieAdjacent;
import Takenoko.Joueur.Strategie.StrategieRandom;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Couleur;
import Takenoko.Plot.Plot;
import Takenoko.Util.Console;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * La classe Game permet de créer une partie
 */
public class Game {

    private Deck deck;
    private ArrayList<Joueur> joueurs;
    private Plateau plateau;

    public Game() {
        this.deck = new Deck();
        this.joueurs = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            deck.addFirst(new Plot());
        }

        this.plateau = new Plateau();
        this.plateau.addStartingPlot(new Plot());

        Joueur j1 = new Joueur(1, new StrategieAdjacent());
        Joueur j2 = new Joueur(2, new StrategieRandom());

        joueurs.add(j1);
        joueurs.add(j2);

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
            for (Joueur j : joueurs){
                if (end()){
                    break;
                }
                Plot current = deck.popFirst();
                CoordAxial coord = j.putPlot(current,plateau);
                Console.Log.println(String.format("Le joueur %d pose une parcelle ici : %s", j.number, coord));

                graduate(j, coord);
            }//Todo : faire piocher -> faire poser
        }
        Console.Log.println("La partie est terminée");
        for (Joueur j : joueurs){
            Console.Log.println(String.format("Le joueur %d a marqué %d points avec une %s", j.number, j.getScore(), j.getStrategieLabel()));
        }
    }

    public Deck getDeck(){
        return deck;
    }



    //GRADUATE

    /**
     * Graduate permet d'évaluer les points à chaque tour
     */
    protected void graduate(Joueur j, CoordAxial coord){
        //CHECK NeighborColor
        int n = plateau.getNeighbors(coord).size();
        j.addScore(n);
        Console.Log.println(String.format("Le joueur %d gagne %d point car il a posé une parcelle", j.number ,n));

        /*HashSet<Couleur> couleurs = getNeighborColor(plateau.getLastPlop(),plateau);
        if(couleurs.contains(plateau.getLastPlop().getCouleur())){
            joueur.addScore1();
            Console.Log.println("Le joueur gagne 1 point la parcelle posée à la même couleur que la parcelle adjacente");
        }*/
    }



   /* private HashSet<Couleur> getNeighborColor(Plot plot,Plateau plateau){
        HashSet<Couleur> couleurs = new HashSet<>();

        int q = plot.getq();
        int r = plot.getr();

        couleurs.add(plateau.getPlot(new CoordAxial(q-1,r)).getCouleur());
        couleurs.add(plateau.getPlot(new CoordAxial(q,r-1)).getCouleur());
        couleurs.add(plateau.getPlot(new CoordAxial(q+1,r-1)).getCouleur());
        couleurs.add(plateau.getPlot(new CoordAxial(q+1,r)).getCouleur());
        couleurs.add(plateau.getPlot(new CoordAxial(q,r+1)).getCouleur());
        couleurs.add(plateau.getPlot(new CoordAxial(q-1,r+1)).getCouleur());

        return couleurs;

    }*/

    public Plateau getPlateau() {
        return plateau;
    }
}
