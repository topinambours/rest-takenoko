package Takenoko.Joueur;

import Takenoko.Deque.Deck;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Joueur.Strategie.Strategie;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Plateau;

import java.util.Optional;


public class Joueur implements Comparable{

    public int number;
    private Strategie strategie;
    private int score;

    private int bambousVerts;
    private int bambousJaunes;
    private int bambousRoses;


    public Joueur(int n,Strategie strategie){
        number = n;
        this.strategie = strategie;
    }

    public int getNumber(){
        return number;
    }

    public Plot draw(Deck deck){
        Plot plot = deck.popLast();
        return plot;
    }

    public void multiDraw(Deck deck, int n){
        int i = n;
        while(!deck.isEmpty() && i > 0){
            draw(deck);
            i--;
        }
    }

    public Plot replaceInDeck(Deck deck, Plot plot){
        deck.addFirst(plot);
        return plot;
    }

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

    public Optional<CoordIrrig> putIrrig(Plateau plateau) {
        Optional<CoordIrrig> strat = strategie.getIrrig(plateau);
        if (strat.isPresent()) {
            var coo = strat.get();
            var borders = coo.borders();
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

    public int getBambousVerts() {
        return bambousVerts;
    }

    public void setBambousVerts(int bambousVerts) {
        this.bambousVerts = bambousVerts;
    }

    public int getBambousJaunes() {
        return bambousJaunes;
    }

    public void setBambousJaunes(int bambousJaunes) {
        this.bambousJaunes = bambousJaunes;
    }

    public int getBambousRoses() {
        return bambousRoses;
    }

    public void setBambousRoses(int bambousRoses) {
        this.bambousRoses = bambousRoses;
    }


}
