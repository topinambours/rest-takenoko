package Takenoko;

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
        return positionsToTest().stream().filter(c -> isPositionLegal(c)).collect(Collectors.toList());
    }

    public boolean isPositionLegal(CoordAxial coo) {
        if (getPlot(coo) != null) {
            return false;
        }
        if (coo.getQ() == 0 && coo.getR() == 0) {
            return false;
        }
        if (Math.max(Math.abs(coo.getQ()), Math.abs(coo.getR())) == 1) {
            return true;
        }
        int v = 0;
        for (CoordAxial nbc : coo.getNeighborCoords()) {
            if (getPlot(nbc) != null) {
                v++;
            }
        }
        return (v >= 2);
    }

    private List<CoordAxial> positionsToTest() {
        CoordAxial origin = new CoordAxial(0, 0);
        Set<CoordAxial> res = new HashSet<>();
        Iterator it = plots.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            CoordAxial coo = (CoordAxial)pair.getValue();
            for (CoordAxial nbc : coo.getNeighborCoords()) {
                if (nbc != origin && getPlot(nbc) == null) {
                    res.add(nbc);
                }
            }
        }
        return new ArrayList<>(res);
    }
}
