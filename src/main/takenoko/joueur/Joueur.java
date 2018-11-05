package takenoko.joueur;

import takenoko.Game;
import takenoko.Plateau;
import takenoko.WeatherDice;
import takenoko.deck.PlotsDeck;
import takenoko.irrigation.CoordIrrig;
import takenoko.joueur.strategie.AbstractStrategie;
import takenoko.joueur.strategie.StrategieAction.Action;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoord;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordResult;
import takenoko.objectives.GardenObjectiveCard;
import takenoko.objectives.PandaObjectiveCard;
import takenoko.objectives.PatternObjectiveCard;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Le robot, joue au jeu en utilisant une stratégie spécifique
 */
public class Joueur implements Comparable{

    private Plot plot;

    /**
     * Identidiant du joueur
     */
    private int id;

    public AbstractStrategie getStrategie() {
        return strategie;
    }

    /**
     * Stratégie globale du joueur englobant tous les types d'action possibles
     */
    private AbstractStrategie strategie;

    /**
     * Score générale du joueur
     */
    private int score;

    /**
     * Registre des portions de bambous dont dispose le joueur.
     * Classées par couleur.
     */
    private HashMap<Couleur, Integer> bambooByColor;

    /**
     * Registre des cartes objectifs dont dispose le joueur.
     */
    private List<PandaObjectiveCard> pandaObjectiveCards;

    /**
     * Déclaration du paquet de carte objectif pattern du joueur sous forme d'un HashSet
     */
    private List<PatternObjectiveCard> patternObjectiveCards;

    private List<GardenObjectiveCard> gardenObjectiveCards;

    private int canalIrrigation;
    private int objectifComplete;

    private int objPanda;
    private int objPattern;
    private int objGarden;

    private WeatherDice meteo;

    private ArrayList<Amenagement> amenagements;



    /**
     * Un joueur est initialisé avec un identifiant
     * @param id identifiant (unique)
     * @param strategie stratégie adopté {@link StrategieCoord}
     */
    public Joueur(int id, AbstractStrategie strategie){
        this.id = id;
        this.bambooByColor = new HashMap<>();
        for (Couleur c : Couleur.values()){
            this.bambooByColor.put(c, 0);
        }
        this.strategie = strategie;
        this.pandaObjectiveCards = new ArrayList<>();
        this.patternObjectiveCards = new ArrayList<>();
        this.gardenObjectiveCards = new ArrayList<>();
        this.canalIrrigation = 0;
        this.objectifComplete = 0;
    }

    /**
     * Permet d'avoir la liste des cartes en main
     * @return HashSet les cartes
     */
    public List<PandaObjectiveCard> getPandaObjectiveCards() {
        return pandaObjectiveCards;
    }

    public List<GardenObjectiveCard> getGardenObjectiveCards() {
        return gardenObjectiveCards;
    }

    /**
     * Permet d'ajouter une carte panda au joueur
     * @param pandaObjectiveCard PandaObjectiveCard une carte panda
     */
    public void addPandaObjectiveCard(PandaObjectiveCard pandaObjectiveCard){
        this.pandaObjectiveCards.add(pandaObjectiveCard);
    }

    /**
     * Permet de retirer une carte panda au joueur
     * Voir la fonction Equal !
     * @param pandaObjectiveCard PandaObjectiveCard une carte panda
     */
    public void removePandaObjectiveCard(PandaObjectiveCard pandaObjectiveCard){
        this.pandaObjectiveCards.remove(pandaObjectiveCard);
    }

    /**
     * Renvoie les cartes pattern objectifs du joueur sous forme d'un HashSet
     * @return le SashSet
     */
    public List<PatternObjectiveCard> getPatternObjectiveCards(){
        return patternObjectiveCards;
    }

    /**
     * Permet d'ajouter une carte pattern au joueur
     * @param patternObjectiveCard
     */
    public void addPatternObjectiveCard(PatternObjectiveCard patternObjectiveCard){
        this.patternObjectiveCards.add(patternObjectiveCard);
    }

    /**
     * Permet de supprimer une carte pattern du joueur
     * @param patternObjectiveCard
     */
    public void removeObjectiveCard(PatternObjectiveCard patternObjectiveCard){
        this.patternObjectiveCards.remove(patternObjectiveCard);
    }

    /**
     * Permet d'ajouter une carte objectif Jardinier du joueur
     * @param gardenObjectiveCard
     */
    public void addGardenObjectiveCard(GardenObjectiveCard gardenObjectiveCard){
        this.gardenObjectiveCards.add(gardenObjectiveCard);
    }

    /**
     * Permet de supprimer une carte objectif Jardinier du joueur
     * @param gardenObjectiveCard
     */
    public void removeGardenObjectiveCard(GardenObjectiveCard gardenObjectiveCard){
        this.gardenObjectiveCards.remove(gardenObjectiveCard);
    }

    public int countObjectiveCards() {
        return pandaObjectiveCards.size() + gardenObjectiveCards.size() + patternObjectiveCards.size();
    }

    /**
     * Getter pour l'identifiant du joueur
     * @return identifiant du joueur
     */
    public int getId(){
        return id;
    }

    /**
     * Permet de piocher
     * @param plotsDeck Deck le deck
     * @return plot une parcelle
     */
    public Plot draw(PlotsDeck plotsDeck) throws EmptyDeckException {
        Plot plot = plotsDeck.draw();
        this.setPlot(plot);
        return plot;
    }

    /**
     * Permet de piocher plusieurs parcelles en même temps
     * Si n > h la hauteur de la pioche , le joueur récupère h cartes
     * @param plotsDeck deck dans lequel le joueur pioche
     * @param n nombre de cartes à piocher
     * @return list contenant les cartes
     */
    public List<Plot> multiDraw(PlotsDeck plotsDeck, int n) throws EmptyDeckException {
        List<Plot> out = new ArrayList<>();
        while (!plotsDeck.isEmpty() && n > 0){
            out.add(draw(plotsDeck));
            n--;
        }
        return out;
    }

    /**
     * Permet de replacer dans la pioche une parcelle
     * @param plotsDeck Deck le deck
     * @param plot plot une parcelle
     * @return plot la parcelle replacée
     */
    public Plot replaceInDeck(PlotsDeck plotsDeck, Plot plot){
        plotsDeck.insertBottom(plot);
        return plot;
    }

    /**
     * Permet de savoir la strategie courante du joueur sous forme de string
     * @return String strategie
     */
    public String getStrategieLabel(){
        return strategie.getStrategieLabel();
    }

    /**
     * Fonction qui permet au joueur de poser un plot sur le board.
     * @param plots
     * @param board
     * @return
     */
    public CoordAxial putPlot(List<Plot> plots, Plateau board, PlotsDeck plotsDeck) {
        StrategieCoordResult decision = strategie.getDecision(this, board, plots);
        Plot plot = decision.getPlot();
        CoordAxial coor = decision.getCoordAxial();

        plots.remove(decision.getPos());
        for (Plot aPlot : plots) {
            replaceInDeck(plotsDeck, aPlot);
        }
        plot.setCoord(coor.getQ(),coor.getR());
        board.putPlot(plot);
        this.plot.setCoord(coor);
        return coor;
    }

    /**
     * Permet de placer une irrigation
     * @param plateau Plateau le plateau
     * @return Optional l'irrigation posee
     */
    public Optional<CoordIrrig> putIrrig(Plateau plateau) {
        Optional<CoordIrrig> strat = strategie.getIrrig(plateau);
        if (strat.isPresent()) {
            CoordIrrig coo = strat.get();
            List<CoordAxial> borders = coo.borders();
            Plot plot = plateau.getPlot(borders.get(0));
            if (plot != null) plot.setWater(true);
            Plot plot2 = plateau.getPlot(borders.get(1));
            if (plot2 != null ) plot2.setWater(true);
            plateau.addIrrigation(coo);
            return Optional.of(coo);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Getter score
     * @return int score
     */
    public int getScore() {
        return score;
    }

    /**
     * Permet d'avoir la liste des Amenagements stockés
     * @return ArrayList
     */
    public ArrayList<Amenagement> getAmenagements() {
        return amenagements;
    }

    /**
     * fonction privée Setter score
     * @param score int le score
     */
    private void setScore(int score) {
        this.score = score;
    }

    /**
     * addScore permet d'ajouter n points
     * @param number int nombre de points
     */
    public void addScore(int number){
        setScore(getScore()+number);
    }

    /**
     * addScore1 ajoute 1 point
     */
    public void addScore1(){
        addScore(1);
    }

    /**
     * Le comparateur permet de comparer le nombre de point de differeence entre 2 bots
     * @param o joueur
     * @return int resultat
     */
    @Override
    public int compareTo(Object o) {
        if(o.getClass().equals(Joueur.class)){
            //Nous allons trier sur le nom d'artiste
            Joueur joueur = (Joueur) o;
            return ((Integer) this.score).compareTo(joueur.getScore());

        }
        return -1;
    }

    /**
     * Permet de savoir si un joueur a plus de points qu'un autre
     * @param joueur joueur un joueur
     * @return boolean true|false
     */
    public boolean isUpper(Joueur joueur){
        return this.compareTo(joueur) > 0;
    }

    /**
     * Méthode private se chargeant de récupérer le nombre de bambous dont dispose le joueur en fonction d'une couleur.
     * @param c la couleur des bambous
     * @return nombre de bambous correspondants
     */
    public int getBambooByColor(Couleur c){
        return bambooByColor.get(c);
    }

    /**
     * Modifie le compte en bambous en fonction de la couleur
     * @param c la couleur des bambous à modifier
     * @param amount le nouveau compte en bambou
     */
    public void setBambooByColor(Couleur c, int amount){
        bambooByColor.remove(c);
        bambooByColor.put(c, amount);
    }

    /**
     * getter pour les bambous verts
     * @return le nombre de bambous verts dont dispose le joueur
     */
    public int getBambousVerts() {
        return getBambooByColor(Couleur.VERT);
    }

    /**
     * setter pour les bambous verts
     * @param bambousVerts le nouveau compte en bambous verts du joueur
     */
    public void setBambousVerts(int bambousVerts) {
        if(bambousVerts > 0) {
            setBambooByColor(Couleur.VERT, bambousVerts);
        }
        else{
            setBambooByColor(Couleur.VERT, 0);
        }
    }

    /**
     * getter pour les bambous jaunes
     * @return le nombre de bambous jaunes dont dispose le joueur
     */
    public int getBambousJaunes() {
        return getBambooByColor(Couleur.JAUNE);
    }

    /**
     * setter pour les bambous jaunes
     * @param bambousJaunes le nouveau compte en bambous jaunes du joueur
     */
    public void setBambousJaunes(int bambousJaunes) {
        if(bambousJaunes > 0) {
            setBambooByColor(Couleur.JAUNE, bambousJaunes);
        }
        else{
            setBambooByColor(Couleur.JAUNE, 0);
        }
    }

    /**
     * getter pour les bambous roses
     * @return le nombre de bambous roses dont dispose le joueur
     */
    public int getBambousRoses() {
        return getBambooByColor(Couleur.ROSE);
    }

    /**
     * setter pour les bambous roses
     * @param bambousRoses le nouveau compte en bambous roses du joueur
     */
    public void setBambousRoses(int bambousRoses) {
        if(bambousRoses > 0) {
            setBambooByColor(Couleur.ROSE, bambousRoses);
        }
        else{
            setBambooByColor(Couleur.ROSE, 0);
        }
    }

    public int getCanalIrrigation(){
        return canalIrrigation;
    }
    public void addCanalIrrigation(){
        canalIrrigation = canalIrrigation + 1;
    }
    public void removeCanalIrrigation(){
        if(canalIrrigation > 0) {
            canalIrrigation = canalIrrigation - 1;
        }
    }

    public int getObjectifComplete(){
        return objectifComplete;
    }
    public void addObjectifComplete(){
        objectifComplete = objectifComplete + 1;
    }

    public int getObjPanda(){
        return objPanda;
    }
    public int getObjPattern(){
        return objPattern;
    }
    public int getObjGarden(){
        return objGarden;
    }
    public void addObjPanda(){
        objPanda = objPanda + 1;
    }
    public void addObjPattern(){
        objPattern = objPattern + 1;
    }
    public void addObjGarden(){
        objGarden = objGarden + 1;
    }


    public Plot getPlot() {
        return this.plot;
    }

    public void setPlot(Plot nextPlot) {
        this.plot = nextPlot;
    }

    /**
     * Permet de faire jouer le tour jardinier
     * @param game Game
     */
    public void jardinierTurn(Game game){
        Plateau plateau = game.getPlateau();
        CoordAxial newPosJardinier = strategie.getJardinierMove(plateau, this);

        Boolean mooveJard = plateau.moveJardinier(newPosJardinier);
        if(mooveJard){
            Console.Log.println(String.format("Robot_%d déplace le jardinier en %s", this.getId(), plateau.getPosJardinier()));
        }
    }

    /**
     * Permet de faire jouer le tour au panda
     * @param game Game le jeu
     */
    public void pandaTurn(Game game){
        Plateau plateau = game.getPlateau();

        CoordAxial newPosPanda = strategie.getPandaMove(plateau, this);

        Couleur eatedColor = plateau.movePanda(newPosPanda);

        if (eatedColor != Couleur.BLEU){
            this.setBambooByColor(eatedColor, this.getBambooByColor(eatedColor) + 1);
            Console.Log.println(String.format("Robot_%d déplace le panda en %s, il gagne une section de bambou %s", this.getId(), newPosPanda, eatedColor ));
        }else{
            Console.Log.println(String.format("Robot_%d déplace le panda en %s, il ne récolte aucun bambou",this.getId(), newPosPanda));
        }
    }

    public void plotTurn(Game game) throws EmptyDeckException {
        List<Plot> plots = multiDraw(game.getPlotsDeck(), 3);
        logPlots(this, plots);
        putPlot(plots, game.getPlateau(), game.getPlotsDeck());
        Console.Log.println("Robot_"+getId()+" pose la parcelle en "+getPlot().getCoord());
        Console.Log.debugPrintln("plateau : " + game.getPlateau().getPlots().toString());
    }

    public void logPlots(Joueur joueur, List<Plot> plots) {
        StringBuilder plog = new StringBuilder();
        for (int i = 0; i < plots.size(); i++) {
            plog.append(plots.get(i).getCouleur());
            plog.append(" ");
        }
        Console.Log.println("Robot_"+getId()+" pioche les(s) parcelle(s) : " + plog.toString());
    }

    /**
     * Le joueur effectue un tour
     * @param game Game la game
     * @param action Action une action
     */
    public void turn(Game game, Action action) throws EmptyDeckException, NoActionSelectedException {
        Joueur joueur = this;
        PlotsDeck plotsDeck = game.getPlotsDeck();
        Plateau plateau = game.getPlateau();
        if(action == null){
            throw new NoActionSelectedException();
        }
        Console.Log.debugPrintln("Robot_"+joueur.getId()+" choisit l'action "+action.toString());
        switch (action){
            case Plot:
                plotTurn(game);
                break;
            case Irrig:
                Console.Log.print("Robot_"+joueur.getId()+" essaie de poser une irrigation... ");
                irrigTurn(game.getPlateau());
                break;
            case Panda:
                pandaTurn(game);
                break;
            case Gardener:
                jardinierTurn(game);
                break;
            case ObjCard:
                ObjCardTurn(game);
                break;
            default:
                throw new NoActionSelectedException();
        }

    }

    /**
     * Le joueur effectue un tour
     * @param game Game la game
     */
    public void turn(Game game) throws EmptyDeckException, NoActionSelectedException {
        turn(game,strategie.firstActionType(game));
        turn(game,strategie.secondActionType(game));
    }

    /**
     * Effectue le tour de pose d'irrigation d'un joueur
     * @param plateau Plateau le plateau
     * @return Optional une irrigation si une irrigation a été posée
     */
    public Optional<CoordIrrig> irrigTurn(Plateau plateau) {
        Optional<CoordIrrig> coo = this.putIrrig(plateau);
        if (coo.isPresent() && getCanalIrrigation() > 0) {
            Console.Log.println(String.format("Il pose une section d'irrigation en : %s", coo.get()));
            removeCanalIrrigation();
            List<CoordAxial> newIrrigated = coo.get().borders();
            Console.Log.println(String.format("Les parcelles %s et %s sont irriguées", newIrrigated.get(0), newIrrigated.get(1)));
        }else{
            Console.Log.println(String.format("Il ne peut pas poser de section supplémentaire"));
        }
        return coo;
    }

    public WeatherDice throwDice(){
        WeatherDice dice = WeatherDice.throwDice();
        Console.Log.print(String.format("Robot_%d obtient %s en lançant le dé météo. ", id, dice.toString()));
        if (dice == WeatherDice.PLAYER_DECIDE){
            Console.Log.print("Il peut choisir la condition climatique.");
        }
        Console.Log.println("");
        meteo = dice;
        return dice;
    }

    public void ObjCardTurn(Game game) throws EmptyDeckException {
        // Le robot choisi selon un ordre de priorité

        if (countObjectiveCards() < 5 || true ) { //TODO : enlever le true qui court-circuite
            if (!game.getPandObjDeck().isEmpty()) {
                game.drawObjectifPanda(this);
            }
            else if (!game.getGardenObjDeck().isEmpty()) {
                game.drawGarden(this);
            }
            else if (!game.getPatternObjDeck().isEmpty()){
                game.drawGarden(this);
            } else {
                throw new EmptyDeckException();
            }
        } else {
            Console.Log.println(String.format("Robot_%d essaie de piocher une carte objectif mais sa main est pleine.", id));
        }
    }

}