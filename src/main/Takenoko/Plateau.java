package Takenoko;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe plateau, utilise un HashMap, stocke les parcelles en jeu avec leurs coordonnées axiales en clé
 */
public class Plateau {
    private final CoordAxial _STARTING_COORDINATE_ = new CoordAxial(0,0);
    private final List<CoordAxial> posInit = _STARTING_COORDINATE_.getNeighborCoords();

    private HashMap<CoordAxial, Plot> plots;
    private Plot lastPlop;

    /**
     * Position du Panda sur le plateau
     */
    private CoordAxial posPanda;

    /**
     * Position du Jardinier sur le plateau
     */
    private CoordAxial posJardinier;

    private HashSet<CoordIrrig> irrigations;

    /**
     * Constructeur par défaut, instancie un plateau vide
     */
    public Plateau() {
        plots = new HashMap<>();
        lastPlop = new Plot(_STARTING_COORDINATE_, Couleur.BLEU);
        plots.put(_STARTING_COORDINATE_, lastPlop);
        irrigations = new HashSet<CoordIrrig>();

        List<CoordIrrig> borderCoords = _STARTING_COORDINATE_.getBorderCoords();
        irrigations.addAll(borderCoords);

        posPanda = _STARTING_COORDINATE_;
        posJardinier = _STARTING_COORDINATE_;
    }

    /**
     * getter de parcelle, prend les coordonnées séparément
     * @param q coord en axe q de la parcelle
     * @param r coord en axe r de la parcelle
     * @return la parcelle placée en (q, r)
     */
    public Plot getPlot(int q, int r) {
        return plots.get(new CoordAxial(q, r));
    }

    /**
     * getter de parcelle, prend les coordonnées mises ensemble
     * @param coord coordonnées axiales de la parcelle
     * @return la parcelle placée en (coord)
     */
    public Plot getPlot(CoordAxial coord) {
        return plots.get(coord);
    }

    public Plot getLastPlop() {
        return lastPlop;
    }

    private void setLastPlop(Plot lastPlop) {
        this.lastPlop = lastPlop;
    }

    /**
     * placeur de parcelle, prend les coordonnées mises ensemble
     * @param plot une parcelle à placer
     * @param coord le couple de coordonnées où la placer
     */
    public void putPlot(Plot plot, CoordAxial coord) {
        plot.setCoord(coord.getQ(), coord.getR());
        plots.put(coord, plot);
        setLastPlop(plot);
        plot.setWater(checkPlotWater(plot.getCoord()));
    }

    /**
     * placeur de parcelle, prend les coordonnées séparément
     * @param plot une parcelle à placer
     * @param q coord en axe q où placer la parcelle
     * @param r coord en axe r où placer la parcelle
     */
    public void putPlot(Plot plot, int q, int r) {
        putPlot(plot, new CoordAxial(q, r));
    }

    public void putPlot(Plot plot){
        putPlot(plot, plot.getCoord());
    }

    public HashSet<CoordIrrig> getIrrigations() {
        return irrigations;
    }

    public void setIrrigations(HashSet<CoordIrrig> irrigations) {
        this.irrigations = irrigations;
    }

    /**
     * ajoute une section d'irrigation au plateau
     * Modifie la propriété "irriguée" aux parcelles adjacentes à l'irrigation
     * @param coo
     */
    public void putIrrigation(CoordIrrig coo) {
        irrigations.add(coo);
        for (Plot p : getPlotsFromIrig(coo)){
            p.setWater(true);
        }
    }

    /**
     * ajoute la tuile étang au plateau
     * @param plot
     */
    public void addStartingPlot(Plot plot){
        plot.setWater(true);
        putPlot(plot, _STARTING_COORDINATE_);
    }

    /**
     * rend la liste des parcelles adjacentes
     * @param coo
     * @return
     */
    public List<Plot> getNeighbors(CoordAxial coo) {
        ArrayList<Plot> res = new ArrayList<>();
        for (CoordAxial nbc : coo.getNeighborCoords()) {
            Plot myPlot = getPlot(nbc);
            if (myPlot != null) {
                res.add(myPlot);
            }
        }
        return res;
    }

    /**
     * rend la liste des positions légales pour jouer
     * @return
     */
    public List<CoordAxial> legalPositions() {
        //return positionsToTest().stream().filter(c -> isPositionLegal(c)).collect(Collectors.toList());
        List<CoordAxial> res = new ArrayList<>();
        for (CoordAxial c : positionsToTest()) {
            if (isPositionLegal(c)) {
                res.add(c);
            }
        }
        return res;
    }

    /**
     * vérifie si une position est jouable
     * @param coo
     * @return
     */
    private boolean isPositionLegal(CoordAxial coo) {
        if (getPlot(coo) != null) {
            return false;
        }
        if (coo.getQ() == 0 && coo.getR() == 0) {
            return false;
        }
        for (CoordAxial oc : posInit) {
            if (coo.equals(oc)) {
                return true;
            }
        }
        int v = 0;
        for (CoordAxial nbc : coo.getNeighborCoords()) {
            if (getPlot(nbc) != null) {
                v++;
            }
        }
        return (v >= 2);
    }

    /**
     * rend le nombre de parcelles adjacentes
     * @param coo
     * @return
     */
    public int nbAdajcent(CoordAxial coo){
        return getNeighbors(coo).size();
    }

    /**
     * méthode utilitaire qui donne une liste de coordonnées à tester pour legalPositions
     * @return
     */
    private List<CoordAxial> positionsToTest() {
        CoordAxial origin = new CoordAxial(0, 0);
        Set<CoordAxial> res = new HashSet<>();
        for (CoordAxial c : origin.getNeighborCoords()) {
            res.add(c);
        }
        Iterator it = plots.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Plot plot = (Plot) pair.getValue();
            CoordAxial coo = plot.getCoord();
            for (CoordAxial nbc : coo.getNeighborCoords()) {
                if (nbc != origin && getPlot(nbc) == null) {
                    res.add(nbc);
                }
            }
        }
        return new ArrayList<>(res);
    }

    /**
     * rend la liste des endroits où on peut placer des irrigations
     * @return
     */
    public List<CoordIrrig> legalIrrigPositions() {
        return irrigPositionsToTest().stream().filter(this::isIrrigPositionLegal).collect(Collectors.toList());
    }

    /**
     * Renvoie la liste des tuiles adjacentes à une coordonnée d'irrigation
     * Les tuiles renvoyées sont posées sur le plateau
     * @param coordIrrig coordonnée d'irrigation
     * @return tuiles présentent sur le plateau étant adjacente à coordIrrig passée en paramètre
     */
    public List<Plot> getPlotsFromIrig(CoordIrrig coordIrrig){
        List<Plot> out = new ArrayList<>();
        List<CoordAxial> coordAxials = coordIrrig.borders();

        for (CoordAxial c : coordAxials){
            if (plots.containsKey(c)){
                out.add(plots.get(c));
            }
        }
        return out;
    }

    /**
     * renvoie si on peut placer
     * @param coo
     * @return
     */
    public boolean isIrrigPositionLegal(CoordIrrig coo) {
        // Déjà placé
        if (irrigations.contains(coo)){
            return false;
        }

        // Si la position est la continuité d'un autre canal d'irrigation la pos est valide
        for (CoordIrrig nbc : coo.continues()) {
            if (irrigations.contains(nbc) && getPlotsFromIrig(coo).size() == 2) {
                // Un canaux ne peut être placé qu'entre deux parcelles
                    return true;
            }
        }
        return false;
    }

    /**
     * fonction utilitaire pour legalIrrigPositions
     * @return
     */
    private List<CoordIrrig> irrigPositionsToTest() {
        HashSet<CoordIrrig> mySet = new HashSet<CoordIrrig>();
        for (CoordIrrig irrig : irrigations) {
            mySet.addAll(irrig.continues());
        }
        return new ArrayList<>(mySet);
    }

    public HashMap<CoordAxial, Plot> getPlots() {
        return plots;
    }

    /**
     * Permet de définir si une parcelle est irriguée
     * Une parcelle est automatique irrigué si elle est adjacente à la parcelle de départ
     * @param coordAxial CoordAxial les coordonnées de la parcelle
     * @return boolean true|false
     */
    public boolean checkPlotWater(CoordAxial coordAxial){
        if (coordAxial.getNeighborCoords().contains(_STARTING_COORDINATE_)){
            return true;
        }

        for (CoordIrrig c : coordAxial.getBorderCoords()){
            if (this.getIrrigations().contains(c)){
                return true;
            }
        }
        return false;
    }

    /**
     * RELATIF AU PANDA
     */

    /**
     * Modifie la position du panda uniquement si pour la nouvelle position, il existe une parcelle sur le plateau
     * @param coord nouvelle coordonnée de la figurine panda
     * @return vrai si le panda a changé de position
     */
    public boolean movePanda(CoordAxial coord){
        if (plots.containsKey(coord)){
            posPanda = coord;
            return true;
        }
        return false;
    }


    /**
     * RELATIF AU JARDINIER
     */

    /**
     * Fonction permettant de bouger le jardinier.
     * @param coord une coordonnée
     * @return true si le jardinier est bien bougé sinon false
     */
    public boolean moveJardinier(CoordAxial coord){
        if(coord.isInLine(posJardinier)){
            posJardinier = coord;
            return true;
        }
        return false;
    }



}
