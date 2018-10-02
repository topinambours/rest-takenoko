package Takenoko.Joueur;

import Takenoko.Deque.Deck;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Joueur.Strategie.Strategie;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Plateau;
import Takenoko.Properties.Couleur;
import Takenoko.Util.Exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Le robot, joue au jeu en utilisant une stratégie spécifique
 */
public class Joueur implements Comparable{

    /**
     * Identidiant du joueur
     */
    public int id;

    /**
     * Stratégie adoptée par le joueur
     */
    private Strategie strategie;

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
     * Un joueur est initialisé avec un identifiant
     * @param id identifiant (unique)
     * @param strategie stratégie adopté {@link Strategie}
     */
    public Joueur(int id,Strategie strategie){
        this.id = id;
        this.bambooByColor = new HashMap<>();
        for (Couleur c : Couleur.values()){
            this.bambooByColor.put(c, 0);
        }
        this.strategie = strategie;
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
     * @param deck Deck le deck
     * @return Plot une parcelle
     */
    public Plot draw(Deck deck) throws EmptyDeckException {
        return deck.popLast();
    }

    /**
     * Permet de piocher plusieurs parcelles en même temps
     * @param deck Deck le deck
     * @param n int
     */

    /**
     * Permet de piocher plusieurs parcelles en même temps
     * Si n > h la hauteur de la pioche , le joueur récupère h cartes
     * @param deck deck dans lequel le joueur pioche
     * @param n nombre de cartes à piocher
     * @return list contenant les cartes
     */
    public List<Plot> multiDraw(Deck deck,int n) throws EmptyDeckException {
        List<Plot> out = new ArrayList<>();
        while (!deck.isEmpty() && n > 0){
            out.add(draw(deck));
            n--;
        }
        return out;
    }

    /**
     * Permet de replacer dans la pioche une parcelle
     * @param deck Deck le deck
     * @param plot Plot une parcelle
     * @return Plot la parcelle replacée
     */
    public Plot replaceInDeck(Deck deck, Plot plot){
        deck.addFirst(plot);
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
     * @param plot
     * @param board
     * @return
     */
    public CoordAxial putPlot(Plot plot, Plateau board){
        CoordAxial coor = strategie.getCoord(board, plot);
        plot.setCoord(coor.getQ(),coor.getR());
        //plot.setWater(board.checkPlotWater(plot.getCoord())); //Check if have water
        board.putPlot(plot);
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
            plateau.putIrrigation(coo);
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
     * @param o Joueur
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
     * @param joueur Joueur un joueur
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
    private int getBambooByColor(Couleur c){
        return bambooByColor.get(c);
    }

    /**
     * Modifie le compte en bambous en fonction de la couleur
     * @param c la couleur des bambous à modifier
     * @param amount le nouveau compte en bambou
     */
    private void setBambooByColor(Couleur c, int amount){
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
        setBambooByColor(Couleur.VERT, bambousVerts);
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
        setBambooByColor(Couleur.JAUNE, bambousJaunes);
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
        setBambooByColor(Couleur.ROSE, bambousRoses);
    }


}
