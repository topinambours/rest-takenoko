package Takenoko;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.*;
import java.lang.Math;
import java.util.stream.Collectors;

/**
 * Classe plateau, utilise un HashMap, stocke les parcelles en jeu avec leurs coordonnées axiales en clé
 */
public class Plateau {
    private final CoordAxial _STARTING_COORDINATE_ = new CoordAxial(0,0);
    private final List<CoordAxial> posInit = _STARTING_COORDINATE_.getNeighborCoords();

    private HashMap<CoordAxial, Plot> plots;
    private Plot lastPlop;

    private HashSet<CoordIrrig> irrigations;

    /**
     * Constructeur par défaut, instancie un plateau vide
     */
    public Plateau() {
        plots = new HashMap<>();
        lastPlop = null;
        irrigations = new HashSet<CoordIrrig>();

        List<CoordIrrig> borderCoords = _STARTING_COORDINATE_.getBorderCoords();
        for(CoordIrrig coordIrrig : borderCoords){
            irrigations.add(coordIrrig);
        }

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
     * placeur de parcelle, prend les coordonnées séparément
     * @param plot une parcelle à placer
     * @param q coord en axe q où placer la parcelle
     * @param r coord en axe r où placer la parcelle
     */
    public void putPlot(Plot plot, int q, int r) {
        plots.put(new CoordAxial(q, r), plot);
        setLastPlop(plot);

    }

    public HashSet<CoordIrrig> getIrrigations() {
        return irrigations;
    }

    public void setIrrigations(HashSet<CoordIrrig> irrigations) {
        this.irrigations = irrigations;
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
            var myPlot = getPlot(nbc);
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
    public boolean isPositionLegal(CoordAxial coo) {
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

    public HashMap<CoordAxial, Plot> getPlots() {
        return plots;
    }

    public boolean checkPlotWater(CoordAxial coordAxial){
        return this.getIrrigations().contains(coordAxial.getBorderCoords());
    }
}
