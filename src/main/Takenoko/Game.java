package Takenoko;

import Takenoko.Deque.Deck;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordAdjacent;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordBamboo;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordColor;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigComparator;
import Takenoko.Objectives.PandaObjectiveCard;
import Takenoko.Objectives.PatternObjectiveCard;
import Takenoko.Objectives.Patterns.CoordCube;
import Takenoko.Objectives.Patterns.Pattern;
import Takenoko.Objectives.Patterns.PatternTile;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
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
    private List<PandaObjectiveCard> cartesPanda;
    private List<PatternObjectiveCard> cartesPattern;

    public Game() {
        this.deck = new Deck();
        this.joueurs = new ArrayList<>();
        this.plateau = new Plateau();
        this.plateau.addStartingPlot(new Plot(Couleur.BLEU));
        this.cartesPattern = new ArrayList<>();


        Boolean deckBool = deck.init();
        Console.Log.debugPrint("Deck init : "+ deckBool+"\n");

        Joueur j1 = new Joueur(1, new StrategieCoordAdjacent(),new StrategieIrrigComparator(plateau));

        StrategieCoordBamboo stratJ2 = new StrategieCoordBamboo(true);
        Joueur j2 = new Joueur(2, stratJ2,new StrategieIrrigBase(plateau));
        stratJ2.setJoueur(j2);

        Joueur j3 = new Joueur(3, new StrategieCoordColor(),new StrategieIrrigComparator(plateau));
        joueurs.add(j1);
        joueurs.add(j2);
        joueurs.add(j3);

        //Todo: Création d'un ou plusieurs robot


        //Instanciation des cartes panda.
        cartesPanda = new ArrayList<>();
        cartesPanda.add(new PandaObjectiveCard(1, 0, 0, 1));
        cartesPanda.add(new PandaObjectiveCard(1, 1, 0, 1));
        cartesPanda.add(new PandaObjectiveCard(1, 0, 1, 1));
        cartesPanda.add(new PandaObjectiveCard(2, 0, 0, 2));
        cartesPanda.add(new PandaObjectiveCard(2, 2, 0, 2));
        cartesPanda.add(new PandaObjectiveCard(2, 0, 2, 2));
        cartesPanda.add(new PandaObjectiveCard(0, 2, 2, 2));
        cartesPanda.add(new PandaObjectiveCard(0, 1, 2, 2));
        cartesPanda.add(new PandaObjectiveCard(1, 1, 2, 2));

        Collections.shuffle(cartesPanda);

        firstDrawObjectif(joueurs);

        initPatternsCards();
        firstDrawPattern(joueurs);

        stratJ2.setGoal(j2.getPandaObjectiveCards());




    }

    /**
     * Permet la création des patterns
     * nous avons les patterns suivant :
     * <ul>
     *     <li>(0,0,Rose),(0,1,Rose),(-1,1,Jaune),(-1,2,Jaune)</li>
     *     <li>(0,0,Vert),(0,1,Vert),(0,2,Vert)</li>
     *     <li>(0,0,Jaune),(-1,1,Jaune),(-1,2,Jaune)</li>
     *     <li>(0,0,Rose),(-1,1,Rose),(0,1,Rose)</li>
     *     <li>(0,0,Vert),(0,1,Vert),(-1,1,Rose),(-1,2,Rose)</li>
     * </ul>
     */
    private void initPatternsCards(){
        int q;
        int r;
        Couleur couleur;

        //CARTE 1 :
        List<PatternTile> tilesPattern1 = new ArrayList<>();
        q = 0;
        r = 0;
        couleur = Couleur.ROSE;
        tilesPattern1.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = 0;
        r = 1;
        couleur = Couleur.ROSE;
        tilesPattern1.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = -1;
        r = 1;
        couleur = Couleur.JAUNE;
        tilesPattern1.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = -1;
        r = 2;
        couleur = Couleur.JAUNE;
        tilesPattern1.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        cartesPattern.add(new PatternObjectiveCard(new Pattern(tilesPattern1),5));


        //CARTE 2 :
        List<PatternTile> tilesPattern2 = new ArrayList<>();
        q = 0;
        r = 0;
        couleur = Couleur.VERT;
        tilesPattern2.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = 0;
        r = 1;
        couleur = Couleur.VERT;
        tilesPattern2.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = 0;
        r = 2;
        couleur = Couleur.VERT;
        tilesPattern2.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        cartesPattern.add(new PatternObjectiveCard(new Pattern(tilesPattern2),2));

        //CARTE 3 :
        List<PatternTile> tilesPattern3 = new ArrayList<>();
        q = 0;
        r = 0;
        couleur = Couleur.JAUNE;
        tilesPattern3.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = -1;
        r = 1;
        couleur = Couleur.JAUNE;
        tilesPattern3.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = -1;
        r = 2;
        couleur = Couleur.JAUNE;
        tilesPattern3.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        cartesPattern.add(new PatternObjectiveCard(new Pattern(tilesPattern3),3));

        //CARTE 4 :
        List<PatternTile> tilesPattern4 = new ArrayList<>();
        q = 0;
        r = 0;
        couleur = Couleur.ROSE;
        tilesPattern4.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = -1;
        r = 1;
        couleur = Couleur.ROSE;
        tilesPattern4.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = 0;
        r = 1;
        couleur = Couleur.ROSE;
        tilesPattern4.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        cartesPattern.add(new PatternObjectiveCard(new Pattern(tilesPattern4),4));

        //CARTE 5 :
        List<PatternTile> tilesPattern5 = new ArrayList<>();
        q = 0;
        r = 0;
        couleur = Couleur.VERT;
        tilesPattern5.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = 0;
        r = 1;
        couleur = Couleur.VERT;
        tilesPattern5.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = -1;
        r = 1;
        couleur = Couleur.ROSE;
        tilesPattern5.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        q = -1;
        r = 2;
        couleur = Couleur.ROSE;
        tilesPattern5.add(new PatternTile(new CoordCube(q,r,-q-r),couleur));

        cartesPattern.add(new PatternObjectiveCard(new Pattern(tilesPattern5),4));

        Collections.shuffle(cartesPattern);
    }


    /**
     * Permet de faire piocher un pattern au joueur
     * @param joueur Joueur le joueur
     * @return Boolean true|false
     */
    private boolean drawPattern(Joueur joueur){
        Boolean bool = !cartesPattern.isEmpty();
        if(bool){
            cartesPattern.remove(0).instanciate(plateau,joueur);
        }
        return bool;

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
    private boolean drawObjectif(Joueur joueur){
        Boolean bool = !cartesPanda.isEmpty();
        if (bool){
            cartesPanda.remove(0).instanciate(plateau,joueur);
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
    public void play() throws EmptyDeckException {
        while(!end()){ //Tant que la partie n'est pas terminée
            for (Joueur j : joueurs){
                Console.Log.println("----");
                if (end()){
                    break;
                }
                // Le joueur pioche une parcelle
                Plot pose = turn(j);

                irrigTurn(j);

                evaluate(j, pose.getCoord());
            }//Todo : faire piocher -> faire poser

            grow();
        }
        Console.Log.println("----\nLa partie est terminée");
        for (Joueur j : joueurs){
            Console.Log.println(String.format("Robot_%d a marqué %d points avec une %s", j.getId(), j.getScore(), j.getStrategieLabel()));
        }
    }

    /**
     * Effectue le tour d'un joueur
     * @param joueur Joueur un joueur
     * @return Plot la parcelle que le joueur a joué
     */
    public Plot turn(Joueur joueur) throws EmptyDeckException {
        Plot pioche = joueur.draw(deck);
        joueur.putPlot(pioche,plateau);
        Console.Log.println(String.format("Robot_%d pose une parcelle %s en : %s", joueur.getId(),pioche.getCouleur().toString(), pioche.getCoord()));
        return pioche;
    }

    /**
     * Effectue le tour de pose d'irrigation d'un joueur
     * @param joueur Joueur un joueur
     * @return Optional une irrigation si une irrigation a été posée
     */
    public Optional<CoordIrrig> irrigTurn(Joueur joueur) {
        Optional<CoordIrrig> coo = joueur.putIrrig(plateau);
        if (coo.isPresent()) {
            Console.Log.println(String.format("Robot_%d pose une section d'irrigation en : %s",joueur.getId(), coo.get()));
            List<CoordAxial> newIrrigated = coo.get().borders();
            Console.Log.println(String.format("Les parcelles %s et %s sont irriguées", newIrrigated.get(0), newIrrigated.get(1)));
        }
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

        if (n > 0) {
            j.addScore(n);

            for (Plot nei : plateau.getNeighbors(coord)) {
                nei.removeBamboo();
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
        HashSet<PatternObjectiveCard> patternCards = joueur.getPatternObjectiveCards();
        for(PatternObjectiveCard patternObjectiveCard : patternCards){
            if(patternObjectiveCard.isComplete()){
                score = score + patternObjectiveCard.getPointValue();
                joueur.removeObjectiveCard(patternObjectiveCard);
                Console.Log.debugPrint(String.format("Le joueur %d stock %d point pour la réalisation d'une carte pattern",joueur.getId(), patternObjectiveCard.getPointValue()));
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
