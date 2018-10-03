package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.Strategie;
import Takenoko.Joueur.Strategie.StrategieAdjacent;
import Takenoko.Joueur.Strategie.StrategieBamboo;
import Takenoko.Joueur.Strategie.StrategieColor;
import Takenoko.Objectives.PandaObjectiveCard;
import Takenoko.Plot.CoordAxial;
import Takenoko.Properties.Couleur;
import Takenoko.Plot.Plot;
import Takenoko.Util.Console;
import Takenoko.Util.Exceptions.EmptyDeckException;

import java.util.*;

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
        this.plateau = new Plateau();
        this.plateau.addStartingPlot(new Plot(Couleur.BLEU));


        Boolean deckBool = deck.init();
        Console.Log.debugPrint("Deck init : "+ deckBool+"\n");

        Joueur j1 = new Joueur(1, new StrategieAdjacent());

        StrategieBamboo stratJ2 = new StrategieBamboo(true);
        Joueur j2 = new Joueur(2, stratJ2);
        stratJ2.setJoueur(j2);

        Joueur j3 = new Joueur(3, new StrategieColor());
        joueurs.add(j1);
        joueurs.add(j2);
        joueurs.add(j3);

        //Todo: Création d'un ou plusieurs robot


        //Instanciation des cartes panda.
        List<PandaObjectiveCard> cartesPanda = new ArrayList<>();
        cartesPanda.add(new PandaObjectiveCard(1, 0, 0, 1));
        cartesPanda.add(new PandaObjectiveCard(1, 1, 0, 1));
        cartesPanda.add(new PandaObjectiveCard(1, 0, 1, 1));
        cartesPanda.add(new PandaObjectiveCard(2, 0, 0, 2));
        cartesPanda.add(new PandaObjectiveCard(2, 2, 0, 2));
        cartesPanda.add(new PandaObjectiveCard(2, 0, 2, 2));
        cartesPanda.add(new PandaObjectiveCard(0, 2, 2, 2));
        cartesPanda.add(new PandaObjectiveCard(0, 1, 2, 2));
        cartesPanda.add(new PandaObjectiveCard(1, 1, 2, 2));

        for(int i = 0; i < 3; i++){
            cartesPanda.remove(0).instanciate(plateau, j1);
            cartesPanda.remove(0).instanciate(plateau, j2);
            cartesPanda.remove(0).instanciate(plateau, j3);
        }

        stratJ2.setGoal(j2.getPandaObjectiveCards());
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
    public void play() throws EmptyDeckException {
        while(!end()){ //Tant que la partie n'est pas terminée
            for (Joueur j : joueurs){
                if (end()){
                    break;
                }
                Plot current = turn(j);
                Optional<CoordIrrig> newIrrig = irrigTurn(j);
                CoordAxial coord = current.getCoord();
                Console.Log.println(String.format("Le joueur %d pose une parcelle ici : %s", j.getId(), coord));
                if (newIrrig.isPresent()) {
                    Console.Log.println(String.format("Le joueur %d pose une section d'irrigation ici : %s",j.getId(), newIrrig.get()));
                }
                Console.Log.debugPrint("La parcelle "+current.toString()+"a water a : "+getPlateau().checkPlotWater(coord));
                //Console.Log.println(String.format("Le joueur %d pose un bambou ici : %s", j.getId(), coord));

                evaluate(j, coord);
            }//Todo : faire piocher -> faire poser

            grow();
        }
        Console.Log.println("La partie est terminée");
        for (Joueur j : joueurs){
            Console.Log.println(String.format("Le joueur %d a marqué %d points avec une %s", j.getId(), j.getScore(), j.getStrategieLabel()));
        }
    }

    public Deck getDeck(){
        return deck;
    }

    /**
     * Effectue le tour d'un joueur
     * @param joueur Joueur un joueur
     * @return Plot la parcelle que le joueur a joué
     */
    public Plot turn(Joueur joueur) throws EmptyDeckException {
        Plot current;
        current = joueur.draw(deck);
        CoordAxial coord = joueur.putPlot(current,plateau);
        current.setWater(getPlateau().checkPlotWater(coord)); //dans le joueur maintenant

        return current;
    }

    /**
     * Effectue le tour de pose d'irrigation d'un joueur
     * @param joueur Joueur un joueur
     * @return Optional une irrigation si une irrigation a été posée
     */
    public Optional<CoordIrrig> irrigTurn(Joueur joueur) {
        Optional<CoordIrrig> coo = joueur.putIrrig(plateau);
        return coo;
    }

    //GRADUATE

    /**
     * Graduate permet d'évaluer les points à chaque tour
     */
    protected void evaluate(Joueur j, CoordAxial coord){
        //CHECK NeighborColor
        //int n = plateau.getNeighbors(coord).size();
        /*int n = plateau.getNeighbors(coord).stream().mapToInt(parcel -> parcel.getBambou()).sum();
        j.addScore(n);*/

        int n = evaluateBambou(j,coord);

        j.addScore(n);


        for (Plot nei : plateau.getNeighbors(coord)){
            nei.removeBamboo();
        }
        Console.Log.println(String.format("Le joueur %d gagne %d point car il a posé une parcelle", j.getId(),n));

        HashSet<Couleur> couleurs = getNeighborColor(coord,plateau);
        if(couleurs.contains(plateau.getLastPlop().getCouleur())){
            j.addScore1();
            Console.Log.println("Le joueur gagne 1 point car la parcelle posée à la même couleur que la parcelle adjacente");
        }


        int evaluatedPandaObjective = evaluatePandaObjective(j);
        j.addScore(evaluatedPandaObjective);
        if (evaluatedPandaObjective > 0){
            Console.Log.println(String.format("Le joueur %d gagne %d point grace à la réalisation d'une carte panda",j.getId(),evaluatedPandaObjective));
        }


    }

    /**
     * permet d'evaluer les bambous
     * @param j Joueur le joueur
     * @param coord CoordAxial la coordonnée
     * @return int l'évaluation
     */
    protected int evaluateBambou(Joueur j,CoordAxial coord){

        int vert = plateau.getNeighbors(coord)
                .stream()
                .filter(p -> p.getCouleur() == Couleur.VERT)
                .mapToInt(p -> p.getBambou())
                .sum();
        int jaune = plateau.getNeighbors(coord)
                .stream()
                .filter(p -> p.getCouleur() == Couleur.JAUNE)
                .mapToInt(p -> p.getBambou())
                .sum();
        int rose = plateau.getNeighbors(coord)
                .stream()
                .filter(p -> p.getCouleur() == Couleur.ROSE)
                .mapToInt(p -> p.getBambou())
                .sum();
        int n = vert + jaune + rose;


        j.setBambousVerts(j.getBambousVerts() + vert);
        j.setBambousJaunes(j.getBambousJaunes() + jaune);
        j.setBambousRoses(j.getBambousRoses() + rose);

        return n;
    }

    /**
     * Permet d'evaluer
     * @param joueur Joueur le joueur
     * @return int Le score resultant
     */
    protected int evaluatePandaObjective(Joueur joueur){
        int score  = 0;
        HashSet<PandaObjectiveCard> pandas = (HashSet<PandaObjectiveCard>) joueur.getPandaObjectiveCards().clone();
        for (PandaObjectiveCard pandaObjectiveCard : pandas){
            if (pandaObjectiveCard.isComplete()){
                score = score + pandaObjectiveCard.getPointValue();
                joueur.removePandaObjectiveCard(pandaObjectiveCard);
                Console.Log.debugPrint(String.format("Le joueur %d stock 1 point pour la réalisation d'une carte panda",joueur.getId()));
            }
        }
        return score;
    }


    /**
     * Permet d'avoir la couleur de ses voisins
     * @param coordAxial CoordAxial une coordonnée
     * @param plateau Plateau le plateau
     * @return HashSet ensemble de couleurs
     */
    private HashSet<Couleur> getNeighborColor(CoordAxial coordAxial,Plateau plateau){
        HashSet<Couleur> couleurs = new HashSet<>();

        List<Plot> neighbors = plateau.getNeighbors(coordAxial);

        for (Plot current : neighbors){
                couleurs.add(plateau.getPlot(current.getCoord()).getCouleur());

        }
        return couleurs;

    }

    /**
     * Permet de faire pousser les bambous
     * @param plateau Plateau le plateau
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
