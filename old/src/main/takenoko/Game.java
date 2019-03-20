package takenoko;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import takenoko.deck.ObjectivesDeck;
import takenoko.deck.PlotsDeck;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieAction.Action;
import takenoko.joueur.strategie.StrategieAction.StrategieActionBasique;
import takenoko.joueur.strategie.StrategieAmenagement.StrategieAmenagementBasique;
import takenoko.joueur.strategie.StrategieConcrete;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordAdjacent;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordRandom;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigComparator;
import takenoko.joueur.strategie.StrategieJardinier.StrategieJardinierRandom;
import takenoko.joueur.strategie.StrategieJardinier.StratégieJardinierObjectifs;
import takenoko.joueur.strategie.StrategiePanda.StrategiePandaObjectifs;
import takenoko.joueur.strategie.StrategiePanda.StrategiePandaRandom;
import takenoko.objectives.GardenObjectiveCard;
import takenoko.objectives.ObjectiveCard;
import takenoko.objectives.PandaObjectiveCard;
import takenoko.objectives.PatternObjectiveCard;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe Game permet de créer une partie
 */
@Configuration
@Configurable
@Component
@Scope(value = "prototype")
@Import(value = {ObjectivesDeck.class, Plot.class, Plateau.class, Joueur.class})
public class Game {

    private PlotsDeck plotsDeck;

    private ObjectivesDeck pandObjDeck;

    private ObjectivesDeck patternObjDeck;

    private ObjectivesDeck gardenObjDeck;

    private List<Joueur> joueurs;
    private Plateau plateau;
    private Joueur empereur;
    private int objneedtobecomplete;
    private int nbTour = 0;

    @Autowired()
    public Game( Plateau plateau, ObjectivesDeck pandObjDeck, ObjectivesDeck gardenObjDeck, ObjectivesDeck patternObjDeck, PlotsDeck plotsDeck, List<Joueur> joueurs) throws EmptyDeckException {
        this.plotsDeck = plotsDeck;
        this.joueurs = new ArrayList<>();
        this.plateau = plateau;

        this.patternObjDeck = patternObjDeck;
        this.gardenObjDeck = gardenObjDeck;
        this.pandObjDeck = pandObjDeck;

        this.empereur = null;

        this.joueurs = joueurs;

        for (Joueur j : joueurs){
            j.assignPlateau(this.plateau);
        }

        this.objneedtobecomplete = setObjNeedToBeComplete();


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

    public boolean drawGarden(Joueur joueur) throws EmptyDeckException {
        Boolean bool = !gardenObjDeck.isEmpty();
        if (bool){
            ((ObjectiveCard) gardenObjDeck.draw()).instanciate(plateau,joueur);
        }
        return bool;

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
    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public PlotsDeck getPlotsDeck(){
        return plotsDeck;
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

    public Joueur getEmpereur(){
        return empereur;
    }

    /**
     * Effectue un tour de jeu pour chaque joueur
     * @throws EmptyDeckException
     * @throws NoActionSelectedException
     */
    public void gameturn() throws EmptyDeckException, NoActionSelectedException{
        for (Joueur j : joueurs){
            Console.Log.println("----");

            if (nbTour > 1) {
                j.throwDice();
            }
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

            evaluate(j);
            if(j.getObjectifComplete() >= objneedtobecomplete && empereur == null){
                j.addScore(2);
                empereur = j;
                Console.Log.println(String.format("Robot_%d a marqué 2 points grâce à l'Empereur, le dernier tour est engagé.", j.getId()));
                break;
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

                j.throwDice();
                j.turn(this);

                if (plateau.getCanalIrrigation() > 0) {
                    j.addCanalIrrigation();
                    plateau.removeCanalIrrigation();
                    j.turn(this, Action.Irrig);
                }

                j.turn(this, Action.Gardener);
                j.turn(this, Action.Panda);

                evaluate(j);
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


    public void firstDrawObjectives() throws EmptyDeckException {
        for (Joueur j : joueurs){
            drawPattern(j);
            drawGarden(j);
            drawObjectifPanda(j);
        }
    }

    /**
     * La fonction principale qui permet de lancer et faire la game
     */
    public void play() throws EmptyDeckException, NoActionSelectedException {
        firstDrawObjectives();
        while(!end()){ //Tant que la partie n'est pas terminée
            gameturn();
            nbTour += 1;
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
    protected void evaluate(Joueur j) {

        int evaluatedPandaObjective = evaluatePandaObjective(j);
        j.addScore(evaluatedPandaObjective);
        while (evaluatedPandaObjective > 0){
            j.addObjectifComplete();
            j.addObjPanda();
            Console.Log.println(String.format("Robot_%d gagne %d points grace à la réalisation d'une carte panda",j.getId(),evaluatedPandaObjective));
            evaluatedPandaObjective = evaluatePandaObjective(j);
        }

        int evaluatePatternObjective = evaluatePatternObjective(j);
        j.addScore(evaluatePatternObjective);
        while (evaluatePatternObjective > 0){
            j.addObjectifComplete();
            j.addObjPattern();
            Console.Log.println(String.format("Robot_%d gagne %d points grace à la réalisation d'une carte Pattern",j.getId(),evaluatePatternObjective));
            evaluatePatternObjective = evaluatePatternObjective(j);
        }

        int evaluateGardenObjective = evaluateGardenObjective(j);
        j.addScore(evaluateGardenObjective);
        while (evaluateGardenObjective > 0){
            j.addObjectifComplete();
            j.addObjGarden();
            Console.Log.println(String.format("Robot_%d gagne %d points grace à la réalisation d'une carte Jardinier",j.getId(),evaluateGardenObjective));
            evaluateGardenObjective = evaluateGardenObjective(j);
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
    protected int evaluatePatternObjective(Joueur joueur) {
        int score = 0;
        List<PatternObjectiveCard> patternCards = joueur.getPatternObjectiveCards();

        List<PatternObjectiveCard> completed = patternCards.stream().filter(PatternObjectiveCard::isComplete).collect(Collectors.toList());

        if (!completed.isEmpty()){
            PatternObjectiveCard maxPoint = completed.get(0);
            for (PatternObjectiveCard card : completed){
                if (card.getPointValue() > maxPoint.getPointValue()){
                    maxPoint = card;
                }
            }
            maxPoint.validate();
            score = maxPoint.getPointValue();
            joueur.removeObjectiveCard(maxPoint);
        }

        return score;

    }

    protected int evaluateGardenObjective(Joueur joueur){
        int score = 0;
        List<GardenObjectiveCard> gardenObjectiveCards = new ArrayList<>(joueur.getGardenObjectiveCards());

        List<GardenObjectiveCard> completed = gardenObjectiveCards.stream().filter(GardenObjectiveCard::isComplete).collect(Collectors.toList());

        if (!completed.isEmpty()){
            GardenObjectiveCard maxPoint = completed.get(0);
            for (GardenObjectiveCard card : completed){
                if (card.getPointValue() > maxPoint.getPointValue()){
                    maxPoint = card;
                }
            }
            maxPoint.validate();
            score = maxPoint.getPointValue();
            joueur.removeGardenObjectiveCard(maxPoint);
        }

        return score;
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
