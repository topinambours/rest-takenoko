package takenoko;

import takenoko.joueur.strategie.StrategiePanda.StrategiePandaObjectifs;
import takenoko.objectives.ObjectiveCard;
import takenoko.deck.PlotsDeck;
import takenoko.deck.ObjectivesDeck;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieAction.Action;
import takenoko.joueur.strategie.StrategieAction.StrategieActionBasique;
import takenoko.joueur.strategie.StrategieAmenagement.StrategieAmenagementBasique;
import takenoko.joueur.strategie.StrategieConcrete;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordAdjacent;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordRandom;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigComparator;
import takenoko.joueur.strategie.StrategieJardinier.StrategieJardinierBasique;
import takenoko.joueur.strategie.StrategieJardinier.StrategieJardinierRandom;
import takenoko.joueur.strategie.StrategiePanda.StrategiePandaBasique;
import takenoko.joueur.strategie.StrategiePanda.StrategiePandaRandom;
import takenoko.objectives.GardenObjectiveCard;
import takenoko.objectives.PandaObjectiveCard;
import takenoko.objectives.PatternObjectiveCard;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import takenoko.properties.Couleur;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * La classe Game permet de créer une partie
 */
@Configuration
@Configurable
@Component
@Scope(value = "prototype")
@Import(value = {ObjectivesDeck.class, Plot.class})
public class Game {

    private PlotsDeck plotsDeck;

    private ObjectivesDeck pandObjDeck;

    private ObjectivesDeck patternObjDeck;

    private ObjectivesDeck gardenObjDeck;

    private ArrayList<Joueur> joueurs;
    private Plateau plateau;
    private Joueur empereur;
    private int objneedtobecomplete;

    @Autowired()
    public Game(ObjectivesDeck pandObjDeck, ObjectivesDeck gardenObjDeck, ObjectivesDeck patternObjDeck, PlotsDeck plotsDeck) throws EmptyDeckException {
        this.plotsDeck = plotsDeck;
        this.joueurs = new ArrayList<>();
        this.plateau = new Plateau();
        this.plateau.addStartingPlot(new Plot(Couleur.BLEU));

        this.patternObjDeck = patternObjDeck;
        this.gardenObjDeck = gardenObjDeck;
        this.pandObjDeck = pandObjDeck;

        this.empereur = null;

        StrategieConcrete strategieJ1 = new StrategieConcrete(new StrategieCoordAdjacent(),new StrategieIrrigComparator(plateau));
        strategieJ1.setStrategieAction(new StrategieActionBasique());

        Joueur j1 = new Joueur(1, new StrategieConcrete(new StrategieCoordAdjacent(), new StrategieIrrigComparator(plateau), new StrategiePandaObjectifs(), new StrategieJardinierBasique(), new StrategieActionBasique(),new StrategieAmenagementBasique()));

        Joueur j2 = new Joueur(2, new StrategieConcrete(new StrategieCoordRandom(), new StrategieIrrigComparator(plateau), new StrategiePandaRandom(), new StrategieJardinierRandom(), new StrategieActionBasique(),new StrategieAmenagementBasique()));
        //Joueur j3 = new Joueur(3, new StrategieConcrete(new StrategieCoordRandom(), new StrategieIrrigComparator(plateau), new StrategiePandaRandom(), new StrategieJardinierRandom(), new StrategieActionBasique(),new StrategieAmenagementBasique()));

        //Joueur j4 = new Joueur(4, new StrategieConcrete(new StrategieCoordAdjacent(), new StrategieIrrigComparator(plateau), new StrategiePandaBasique(), new StrategieJardinierBasique(), new StrategieActionBasique(),new StrategieAmenagementBasique()));


        joueurs.add(j1);
        joueurs.add(j2);
        //joueurs.add(j3);
        //joueurs.add(j4);

        this.objneedtobecomplete = setObjNeedToBeComplete();

        firstDrawObjectifPanda(joueurs);
        firstDrawPattern(joueurs);
        firstDrawGarden(joueurs);

    }



    /**
     * Permet de faire piocher un pattern au joueur
     * @param joueur joueur le joueur
     * @return Boolean true|false
     */
    public boolean drawPattern(Joueur joueur) throws EmptyDeckException {
        if(!patternObjDeck.isEmpty()){
            ((ObjectiveCard) patternObjDeck.draw()).instanciate(plateau,joueur);
            return true;
        }
        return false;

    }

    /**
     * Permet de faire piocher un pattern à chaque joueur. Utile pour l'initialisation de la partie
     * @param joueurs ArrayList liste des joueurs
     */
    private void firstDrawPattern(ArrayList<Joueur> joueurs) throws EmptyDeckException {
        Iterator<Joueur> iterator = joueurs.iterator();
        while (iterator.hasNext()){
            drawPattern(iterator.next());
        }
    }

    /**
     * Permet de faire piocher un objectif au joueur
     * @param joueur joueur le joueur
     * @return Boolean true|false
     */
    public boolean drawObjectifPanda(Joueur joueur) throws EmptyDeckException {
        boolean bool = !pandObjDeck.isEmpty();
        if (bool){
            ((ObjectiveCard) pandObjDeck.draw()).instanciate(plateau,joueur);
        }
        return bool;

    }

    /**
     * Permet de faire piocher un objectif à chaque joueur. Utile pour l'initialisation de la partie
     * @param joueurs ArrayList liste des joueurs
     */
    private void firstDrawObjectifPanda(ArrayList<Joueur> joueurs) throws EmptyDeckException {
        Iterator<Joueur> iterator = joueurs.iterator();
        while (iterator.hasNext()){
            drawObjectifPanda(iterator.next());
        }
    }

    public boolean drawGarden(Joueur joueur) throws EmptyDeckException {
        Boolean bool = !gardenObjDeck.isEmpty();
        if (bool){
            ((ObjectiveCard) gardenObjDeck.draw()).instanciate(plateau,joueur);
        }
        return bool;

    }

    private void firstDrawGarden(ArrayList<Joueur> joueurs) throws EmptyDeckException {
        Iterator<Joueur> iterator = joueurs.iterator();
        while (iterator.hasNext()){
            drawGarden(iterator.next());
        }
    }



    public ObjectivesDeck getPandObjDeck(){
        return pandObjDeck;
    }
    public ObjectivesDeck getPatternObjDeck(){
        return patternObjDeck;
    }
    public ObjectivesDeck getGardenObjDeck(){
        return gardenObjDeck;
    }


    /**
     * Permet d'avoir la liste des joueurs
     * @return ArrayList Joueurs
     */
    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public PlotsDeck getPlotsDeck(){
        return plotsDeck;
    }

    public int setObjNeedToBeComplete(){
        int nbrJoueur = joueurs.size();
        switch (nbrJoueur){
            case 2 :
                return 5;
                //return 9;
            case 3 :
                return 4;
                //return 8;
            case 4 :
                return 3;
                //return 7;
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

            j.turn(this,Action.ObjCard);
            j.turn(this,Action.Gardener);
            j.turn(this,Action.Panda);
            j.turn(this, Action.ObjCard);

            evaluate(j, j.getPlot().getCoord());
            if(j.getObjectifComplete()== objneedtobecomplete && empereur == null){
                j.addScore(2);
                empereur = j;
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

            if(!empereur.equals(j)) {

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
        return empereur !=null;
    }

    /**
     * La fonction principale qui permet de lancer et faire la game
     */
    public void play() throws EmptyDeckException, NoActionSelectedException {
        while(!end()){ //Tant que la partie n'est pas terminée
            gameturn();
        }
        lastTurn();
        Console.Log.println("----\nLa partie est terminée");
        for (Joueur j : joueurs){
            Console.Log.println(String.format("Robot_%d a marqué %d points avec une %s", j.getId(), j.getScore(), j.getStrategieLabel()));
        }
    }

    /**
     * evaluate permet d'évaluer les points à chaque tour
     */
    protected void evaluate(Joueur j, CoordAxial coord) throws EmptyDeckException {

        int evaluatedPandaObjective = evaluatePandaObjective(j);
        j.addScore(evaluatedPandaObjective);
        while (evaluatedPandaObjective > 0){
            j.addObjectifComplete();
            Console.Log.println(String.format("Robot_%d gagne %d points grace à la réalisation d'une carte panda",j.getId(),evaluatedPandaObjective));
            evaluatedPandaObjective = evaluatePandaObjective(j);
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
     * @param joueur joueur le joueur
     * @return int Le score resultant
     */
    protected int evaluatePandaObjective(Joueur joueur){
        int score  = 0;

        List<PandaObjectiveCard> completedObjPanda = joueur.getPandaObjectiveCards().stream().filter(PandaObjectiveCard::isComplete).collect(Collectors.toList());

        if (!completedObjPanda.isEmpty()) {
            PandaObjectiveCard maxpoints = completedObjPanda.get(0);
            for (PandaObjectiveCard card : completedObjPanda) {
                if (card.getPointValue() > maxpoints.getPointValue()) {
                    maxpoints = card;
                }
            }
            maxpoints.validate();
            score = score + maxpoints.getPointValue();
            joueur.removePandaObjectiveCard(maxpoints);
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
    protected int evaluatePatternObjective(Joueur joueur) throws EmptyDeckException {
        int score = 0;
        ArrayList<PatternObjectiveCard> patternObjectiveCard1 = new ArrayList<>();
        List<PatternObjectiveCard> patternCards = joueur.getPatternObjectiveCards();
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
        List<GardenObjectiveCard> gardenObjectiveCards = new ArrayList<>(joueur.getGardenObjectiveCards());
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
