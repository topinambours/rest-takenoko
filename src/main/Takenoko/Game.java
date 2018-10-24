package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Deque.ObjectivesDeck;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieAction.Action;
import Takenoko.Joueur.Strategie.StrategieAction.StrategieActionBasique;
import Takenoko.Joueur.Strategie.StrategieConcrete;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordAdjacent;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigComparator;
import Takenoko.Joueur.Strategie.StrategieJardinier.StrategieJardinierBasique;
import Takenoko.Joueur.Strategie.StrategieJardinier.StrategieJardinierRandom;
import Takenoko.Joueur.Strategie.StrategiePanda.StrategiePandaBasique;
import Takenoko.Joueur.Strategie.StrategiePanda.StrategiePandaRandom;
import Takenoko.Objectives.GardenObjectiveCard;
import Takenoko.Objectives.PandaObjectiveCard;
import Takenoko.Objectives.PatternObjectiveCard;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
import Takenoko.Util.Console;
import Takenoko.Util.Exceptions.EmptyDeckException;
import Takenoko.Util.Exceptions.NoActionSelectedException;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * La classe Game permet de créer une partie
 */
@Configuration
@Configurable
@Component
@Scope(value = "prototype")
@Import(ObjectivesDeck.class)
public class Game {

    private Deck deck;

    private ObjectivesDeck pandObjDeck;

    private ObjectivesDeck patternObjDeck;

    private ObjectivesDeck gardenObjDeck;

    private ArrayList<Joueur> joueurs;
    private Plateau plateau;
    private Pair<Boolean, Joueur> empereur;
    private int objneedtobecomplete;

    @Autowired()
    public Game(ObjectivesDeck pandObjDeck, ObjectivesDeck gardenObjDeck, ObjectivesDeck patternObjDeck) {
        this.deck = new Deck();
        this.joueurs = new ArrayList<>();
        this.plateau = new Plateau();
        this.plateau.addStartingPlot(new Plot(Couleur.BLEU));

        this.patternObjDeck = patternObjDeck;
        this.gardenObjDeck = gardenObjDeck;
        this.pandObjDeck = pandObjDeck;

        this.empereur = new Pair<>(false, null);


        boolean deckBool = deck.init();
        Console.Log.debugPrint("Deck init : "+ deckBool+"\n");

        StrategieConcrete strategieJ1 = new StrategieConcrete(new StrategieCoordAdjacent(),new StrategieIrrigComparator(plateau));
        strategieJ1.setStrategieAction(new StrategieActionBasique());

        Joueur j1 = new Joueur(1, new StrategieConcrete(new StrategieCoordAdjacent(), new StrategieIrrigComparator(plateau), new StrategiePandaBasique(), new StrategieJardinierBasique(), new StrategieActionBasique()));

        Joueur j2 = new Joueur(2, new StrategieConcrete(new StrategieCoordRandom(), new StrategieIrrigComparator(plateau), new StrategiePandaRandom(), new StrategieJardinierRandom(), new StrategieActionBasique()));


        joueurs.add(j1);
        joueurs.add(j2);

        this.objneedtobecomplete = setObjNeedToBeComplete();

        firstDrawObjectifPanda(joueurs);

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
    public boolean drawObjectifPanda(Joueur joueur){
        boolean bool = !pandObjDeck.isEmpty();
        if (bool){
            pandObjDeck.pop().instanciate(plateau,joueur);
        }
        return bool;

    }

    /**
     * Permet de faire piocher un objectif à chaque joueur. Utile pour l'initialisation de la partie
     * @param joueurs ArrayList liste des joueurs
     */
    private void firstDrawObjectifPanda(ArrayList<Joueur> joueurs){
        Iterator<Joueur> iterator = joueurs.iterator();
        while (iterator.hasNext()){
            drawObjectifPanda(iterator.next());
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

    public int setObjNeedToBeComplete(){
        int nbrJoueur = joueurs.size();
        switch (nbrJoueur){
            case 2 :
                return 9;
            case 3 :
                return 8;
            case 4 :
                return 7;
            default:
                return 0;
        }
    }

    /**
     * Effectue un tour de jeu pour chaque joueur
     * @throws EmptyDeckException
     * @throws NoActionSelectedException
     */
    public void gameturn() throws EmptyDeckException, NoActionSelectedException{
        for (Joueur j : joueurs){
            Console.Log.println("----");

            j.trowDice();
            j.turn(this);

            if(plateau.getCanalIrrigation() > 0){
                j.addCanalIrrigation();
                plateau.removeCanalIrrigation();
                j.turn(this,Action.Irrig);
            }

            j.turn(this,Action.Gardener);
            j.turn(this,Action.Panda);

            evaluate(j, j.getPlot().getCoord());
            Pair<Boolean, Joueur> tmp = new Pair<>(false, null);
            if(j.getObjectifComplete()== objneedtobecomplete && empereur.equals(tmp)){
                j.addScore(2);
                empereur = new Pair(true, j);
                Console.Log.println(String.format("Robot_%d a marqué 2 points grâce à l'Empereur, le dernier tour est engagé.", j.getId()));
            }
        }
    }

    /**
     * Effectue le dernier tour de jeu
     * @throws EmptyDeckException
     * @throws NoActionSelectedException
     */
    public void lastTurn() throws EmptyDeckException, NoActionSelectedException{
        for (Joueur j : joueurs){
            Console.Log.println("----");

            if(!empereur.getValue().equals(j)) {

                j.trowDice();
                j.turn(this);

                if (plateau.getCanalIrrigation() > 0) {
                    j.addCanalIrrigation();
                    plateau.removeCanalIrrigation();
                    j.turn(this, Action.Irrig);
                }

                j.turn(this, Action.Gardener);
                j.turn(this, Action.Panda);

                evaluate(j, j.getPlot().getCoord());
            }
        }
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
        while(true){ //Tant que la partie n'est pas terminée
            gameturn();
            //Todo : faire piocher -> faire poser
            if(empereur.getKey()){
                lastTurn();
                break;
            }
        }
        Console.Log.println("----\nLa partie est terminée");
        for (Joueur j : joueurs){
            Console.Log.println(String.format("Robot_%d a marqué %d points avec une %s", j.getId(), j.getScore(), j.getStrategieLabel()));
        }
    }
    
    /**
     * evaluate permet d'évaluer les points à chaque tour
     */
    protected void evaluate(Joueur j, CoordAxial coord){

        int evaluatedPandaObjective = evaluatePandaObjective(j);
        j.addScore(evaluatedPandaObjective);
        if (evaluatedPandaObjective > 0){
            j.addObjectifComplete();
            Console.Log.println(String.format("Robot_%d gagne %d points grace à la réalisation d'une carte panda",j.getId(),evaluatedPandaObjective));
        }

        int evaluatePatternObjective = evaluatePatternObjective(j);
        j.addScore(evaluatePatternObjective);
        if (evaluatePatternObjective > 0){
            j.addObjectifComplete();
            Console.Log.println(String.format("Robot_%d gagne %d points grace à la réalisation d'une carte Pattern",j.getId(),evaluatePatternObjective));
        }

        int evaluateGardenObjective = evaluateGardenObjective(j);
        j.addScore(evaluateGardenObjective);
        if (evaluateGardenObjective > 0){
            j.addObjectifComplete();
            Console.Log.println(String.format("Robot_%d gagne %d points grace à la réalisation d'une carte Jardinier",j.getId(),evaluateGardenObjective));
        }

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

    protected int evaluateGardenObjective(Joueur joueur){
        int score = 0;
        HashSet<GardenObjectiveCard> gardenObjectiveCards = (HashSet<GardenObjectiveCard>) joueur.getGardenObjectiveCards().clone();
        for(GardenObjectiveCard gardenObjectiveCard : gardenObjectiveCards){
            if(gardenObjectiveCard.isComplete()){
                score = score + gardenObjectiveCard.getPointValue();
                joueur.removeGardenObjectiveCard(gardenObjectiveCard);
            }
        }
        return score;
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
