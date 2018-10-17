package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Deque.ObjectivesGardenDeck;
import Takenoko.Deque.ObjectivesPandaDeck;
import Takenoko.Deque.ObjectivesPatternDeck;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieAction.Action;
import Takenoko.Joueur.Strategie.StrategieAction.StrategieActionBasique;
import Takenoko.Joueur.Strategie.StrategieConcrete;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordAdjacent;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordBamboo;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordColor;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigComparator;
import Takenoko.Joueur.Strategie.StrategieSansPions;
import Takenoko.Objectives.PandaObjectiveCard;
import Takenoko.Objectives.PatternObjectiveCard;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
import Takenoko.Util.Console;
import Takenoko.Util.Exceptions.EmptyDeckException;
import Takenoko.Util.Exceptions.NoActionSelectedException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * La classe Game permet de créer une partie
 */
public class Game {

    private Deck deck;
    private ObjectivesPandaDeck pandObjDeck;
    private ObjectivesPatternDeck patternObjDeck;
    private ObjectivesGardenDeck gardenObjDeck;
    private ArrayList<Joueur> joueurs;
    private Plateau plateau;

    public Game() {
        this.deck = new Deck();
        this.pandObjDeck = new ObjectivesPandaDeck();
        this.joueurs = new ArrayList<>();
        this.plateau = new Plateau();
        this.plateau.addStartingPlot(new Plot(Couleur.BLEU));
        this.patternObjDeck = new ObjectivesPatternDeck();
        this.gardenObjDeck = new ObjectivesGardenDeck();


        Boolean deckBool = deck.init();
        Console.Log.debugPrint("Deck init : "+ deckBool+"\n");

        StrategieConcrete strategieJ1 = new StrategieConcrete(new StrategieCoordAdjacent(),new StrategieIrrigComparator(plateau));
        strategieJ1.setStrategieAction(new StrategieActionBasique());
        Joueur j1 = new Joueur(1, strategieJ1);

        StrategieConcrete strategieJ2 = new StrategieConcrete();
        strategieJ2.setStrategieAction(new StrategieActionBasique());
        Joueur j2 = new Joueur(2, strategieJ2);
        StrategieCoordBamboo stratCoordJ2 = new StrategieCoordBamboo(true);
        stratCoordJ2.setJoueur(j2);
        strategieJ2.initialize(stratCoordJ2, new StrategieIrrigBase(plateau));

        StrategieConcrete strategieJ3 = new StrategieConcrete(new StrategieCoordColor(),new StrategieIrrigComparator(plateau));
        strategieJ3.setStrategieAction(new StrategieActionBasique());
        Joueur j3 = new Joueur(3, strategieJ3);
        joueurs.add(j1);
        joueurs.add(j2);
        joueurs.add(j3);

        firstDrawObjectif(joueurs);

        firstDrawPattern(joueurs);
        
        firstDrawGarden(joueurs);


    }

    /**
     * Permet de faire piocher un pattern au joueur
     * @param joueur Joueur le joueur
     * @return Boolean true|false
     */
    public boolean drawPattern(Joueur joueur){
        if(!patternObjDeck.isEmpty()){
            patternObjDeck.pop().instanciate(plateau,joueur);
            return true;
        }
        return false;

    }

    /**
     * Permet de faire piocher un pattern à chaque joueur. Utile pour l'initialisation de la partie
     * @param joueurs ArrayList liste des joueurs
     */
    private void firstDrawPattern(ArrayList<Joueur> joueurs){
        Iterator<Joueur> iterator = joueurs.iterator();
        while (iterator.hasNext()){
            drawPattern(iterator.next());
        }
    }

    /**
     * Permet de faire piocher un objectif au joueur
     * @param joueur Joueur le joueur
     * @return Boolean true|false
     */
    public boolean drawObjectif(Joueur joueur){
        Boolean bool = !pandObjDeck.isEmpty();
        if (bool){
            pandObjDeck.pop().instanciate(plateau,joueur);
        }
        return bool;

    }

    /**
     * Permet de faire piocher un objectif à chaque joueur. Utile pour l'initialisation de la partie
     * @param joueurs ArrayList liste des joueurs
     */
    private void firstDrawObjectif(ArrayList<Joueur> joueurs){
        Iterator<Joueur> iterator = joueurs.iterator();
        while (iterator.hasNext()){
            drawObjectif(iterator.next());
        }
    }

    public boolean drawGarden(Joueur joueur){
        Boolean bool = !gardenObjDeck.isEmpty();
        if (bool){
            gardenObjDeck.pop().instanciate(plateau,joueur);
        }
        return bool;

    }

    private void firstDrawGarden(ArrayList<Joueur> joueurs){
        Iterator<Joueur> iterator = joueurs.iterator();
        while (iterator.hasNext()){
            drawGarden(iterator.next());
        }
    }

    /**
     * Permet d'avoir la liste des joueurs
     * @return ArrayList Joueurs
     */
    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public Deck getDeck(){
        return deck;
    }

    /**
     * La fonction end permet de savoir si la partie est terminée
     * @return boolean true|false
     */
    public boolean end(){
        return deck.isEmpty();
    }


    /**
     * La fonction principale qui permet de lancer et faire la game
     */
    public void play() throws EmptyDeckException, NoActionSelectedException {
        while(!end()){ //Tant que la partie n'est pas terminée
            for (Joueur j : joueurs){
                Console.Log.println("----");
                if (end()){
                    break;
                }

                j.turn(this);


                evaluate(j, j.getPlot().getCoord());
            }//Todo : faire piocher -> faire poser


        }
        Console.Log.println("----\nLa partie est terminée");
        for (Joueur j : joueurs){
            Console.Log.println(String.format("Robot_%d a marqué %d points avec une %s", j.getId(), j.getScore(), j.getStrategieLabel()));
        }
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

        if (n > 0) {
            j.addScore(n);

            for (Plot nei : plateau.getNeighbors(coord)) {
                nei.removeAllBambou();
            }
            if (n > 1) {
                Console.Log.println(String.format("Robot_%d gagne 1 point, une unique section de bambou était présente sur les parcelles adjacentes", j.getId()));
            }else{
                Console.Log.println(String.format("Robot_%d gagne %d points car %d sections de bambou étaient présentes sur les parcelles adjacentes", j.getId(), n, n));
            }
        }

        HashSet<Couleur> couleurs = getNeighborColor(coord,plateau);
        if(couleurs.contains(plateau.getPlot(coord).getCouleur())){
            j.addScore1();
            Console.Log.println(String.format("Robot_%d gagne 1 point car une des parcelles adjacentes est de la même couleur", j.getId()));
        }

        int evaluatedPandaObjective = evaluatePandaObjective(j);
        j.addScore(evaluatedPandaObjective);
        if (evaluatedPandaObjective > 0){
            Console.Log.println(String.format("Robot_%d gagne %d points grace à la réalisation d'une carte panda",j.getId(),evaluatedPandaObjective));
        }
        j.addScore(evaluatePatternObjective(j));

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
                Console.Log.debugPrint(String.format("Le joueur %d stock %d point pour la réalisation d'une carte panda",joueur.getId(), pandaObjectiveCard.getPointValue()));
            }
        }
        return score;
    }

    /**
     * Evaluation de l'objectif de pattern en fonction des cartes
     * objectifs que possède le joueur et le motif créer en posant
     * une parcelle à une coordonnée coo
     * @param joueur Le joueur jouant le tour
     * @return le score sous forme d'entier int
     */
    protected int evaluatePatternObjective(Joueur joueur){
        int score = 0;
        ArrayList<PatternObjectiveCard> patternObjectiveCard1 = new ArrayList<>();
        ArrayList<PatternObjectiveCard> patternCards = joueur.getPatternObjectiveCards();
        int i = 0;
        for(PatternObjectiveCard patternObjectiveCard : patternCards){
            if(patternObjectiveCard.isComplete()){
                patternObjectiveCard1.add(patternObjectiveCard);
                score = score + patternObjectiveCard.getPointValue();
                Console.Log.debugPrint(String.format("Le joueur %d stock %d point pour la réalisation d'une carte pattern",joueur.getId(), patternObjectiveCard.getPointValue()));
                i+= 1;
            }
        }
        for (int j = 0;j<patternObjectiveCard1.size(); j++) {
            joueur.removeObjectiveCard(patternObjectiveCard1.get(j));
        }
        for(int k = 0; k<i; k++){
            drawPattern(joueur);
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
