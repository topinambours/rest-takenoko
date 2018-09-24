package Takenoko;

import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.HashMap;
import java.util.List;

/**
 * Classe plateau, utilise un HashMap, stocke les parcelles en jeu avec leurs coordonnées axiales en clé
 */
public class Plateau {
    private final CoordAxial _STARTING_COORDINATE_ = new CoordAxial(0,0);

    private HashMap<CoordAxial, Plot> plots;

    /**
     * Constructeur par défaut, instancie un plateau vide
     */
    public Plateau() {
        plots = new HashMap<>();
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

    /**
     * placeur de parcelle, prend les coordonnées séparément
     * @param plot une parcelle à placer
     * @param q coord en axe q où placer la parcelle
     * @param r coord en axe r où placer la parcelle
     */
    public void putPlot(Plot plot, int q, int r) {
        plots.put(new CoordAxial(q, r), plot);
    }

    /**
     * placeur de parcelle, prend les coordonnées mises ensemble
     * @param plot une parcelle à placer
     * @param coord le couple de coordonnées où la placer
     */
    public void putPlot(Plot plot, CoordAxial coord) {
        plots.put(coord, plot);
    }

    public void putPlot(Plot plot){
        putPlot(plot, plot.getCoord());
    }

    public void addStartingPlot(Plot plot){
        putPlot(plot, _STARTING_COORDINATE_);
    }

    public List<CoordAxial> legalPositions() {
        return null;
    }

    public boolean isPositionLegal(CoordAxial coo) {
        return true;
    }
}
