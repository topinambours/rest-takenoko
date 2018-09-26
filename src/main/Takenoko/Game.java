package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieAdjacent;
import Takenoko.Joueur.Strategie.StrategieBamboo;
import Takenoko.Joueur.Strategie.StrategieRandom;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Couleur;
import Takenoko.Plot.Plot;
import Takenoko.Util.Console;

import java.util.*;
import java.util.function.Function;

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
        for (int i = 0; i < 500; i++) {
            deck.addFirst(new Plot());
        }

        this.plateau = new Plateau();
        this.plateau.addStartingPlot(new Plot());

        Joueur j1 = new Joueur(1, new StrategieAdjacent());
        Joueur j2 = new Joueur(2, new StrategieBamboo());
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
                Plot current = turn(j);
                CoordAxial coord = current.getCoord();
                Console.Log.println(String.format("Le joueur %d pose une parcelle ici : %s", j.number, coord));
                Console.Log.debugPrint("La parcelle "+current.toString()+"a water a : "+getPlateau().checkPlotWater(coord));
                //Console.Log.println(String.format("Le joueur %d pose un bambou ici : %s", j.number, coord));

                evaluate(j, coord);
            }//Todo : faire piocher -> faire poser

            grow();
        }
        Console.Log.println("La partie est terminée");
        for (Joueur j : joueurs){
            Console.Log.println(String.format("Le joueur %d a marqué %d points avec une %s", j.number, j.getScore(), j.getStrategieLabel()));
        }
    }

    public Deck getDeck(){
        return deck;
    }

    public Plot turn(Joueur joueur){
        Plot current = deck.popFirst();
        CoordAxial coord = joueur.putPlot(current,plateau);
        current.setWater(getPlateau().checkPlotWater(coord)); //dans le joueur maintenant

        return current;
    }


    //GRADUATE

    /**
     * Graduate permet d'évaluer les points à chaque tour
     */
    protected void evaluate(Joueur j, CoordAxial coord){
        //CHECK NeighborColor
        //int n = plateau.getNeighbors(coord).size();
        int n = plateau.getNeighbors(coord).stream().mapToInt(parcel -> parcel.getBambou()).sum();
        j.addScore(n);
        for (Plot nei : plateau.getNeighbors(coord)){
            nei.removeBamboo();
        }
        Console.Log.println(String.format("Le joueur %d gagne %d point car il a posé une parcelle", j.number ,n));
    /*
        HashSet<Couleur> couleurs = getNeighborColor(plateau.getLastPlop(),plateau);
        if(couleurs.contains(plateau.getLastPlop().getCouleur())){
            j.addScore1();
            Console.Log.println("Le joueur gagne 1 point la parcelle posée à la même couleur que la parcelle adjacente");
        }
        */
    }


/*
    private HashSet<Couleur> getNeighborColor(Plot plot,Plateau plateau){
        HashSet<Couleur> couleurs = new HashSet<>();

        List<Plot> neighbors = plateau.getNeighbors(plot.getCoord());

        for (Plot current : neighbors){
                couleurs.add(current.getCouleur());

        }
        return couleurs;

    }
    */

   private void grow(Plateau plateau){
       HashMap<CoordAxial, Plot> hashMap = plateau.getPlots();
       Iterator iterator = hashMap.entrySet().iterator();
       while (iterator.hasNext()){
           Map.Entry<CoordAxial, Plot> pair = (Map.Entry<CoordAxial, Plot>) iterator.next();
           Console.Log.debugPrint(pair.getKey() +"=" +pair.getValue()+"\n");
           Plot current = pair.getValue();

           if (!current.getCoord().equals(new CoordAxial(0, 0))) {
               current.pousserBambou();
           }
       }

   }

   protected void grow(){
       grow(this.plateau);
   }

    public Plateau getPlateau() {
        return plateau;
    }
}
