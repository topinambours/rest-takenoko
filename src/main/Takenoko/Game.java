package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieRandom;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Couleur;
import Takenoko.Plot.Plot;

import java.util.HashSet;

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
        for (int i = 0; i < 28; i++) {
            deck.addFirst(new Plot());
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
            System.out.println("Le joueur pose une parcelle ici : "+coord);

            //Todo : faire piocher -> faire poser
        }

        System.out.println("La partie est terminée");
    }

    public Deck getDeck(){
        return deck;
    }

    //GRADUATE

    public void graduate(){
        //CHECK NeighborColor
        HashSet<Couleur> couleurs = getNeighborColor(plateau.getLastPlop(),plateau);
        if(couleurs.contains(plateau.getLastPlop().getCouleur())){
            //Todo : ajouter un point
        }
    }


    private HashSet<Couleur> getNeighborColor(Plot plot,Plateau plateau){
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

    }



}
