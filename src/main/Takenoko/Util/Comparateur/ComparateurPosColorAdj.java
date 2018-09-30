package Takenoko.Util.Comparateur;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Couleur;
import Takenoko.Plot.Plot;

import java.util.Comparator;
import java.util.List;

/**
 * Permet de comparer les couleurs
 */
public class ComparateurPosColorAdj implements Comparator<CoordAxial> {

    private Plateau p;
    private Couleur c;

    public ComparateurPosColorAdj(Plateau p, Couleur c){
        this.p = p;
        this.c = c;
    }

    /**
     * renvoie si 1 si le plot est de même couleur que la
     * couleur de base
     * @param plot
     * @return
     */
    private int oneIfSameColor(Plot plot){
        if (plot.getCouleur().equals(c)){
            return 1;
        }
        return 0;
    }

    /**
     * Compare 2 coordonnées selon le nombre d'adajcence au plot
     * contenant la même couleur que lui.
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(CoordAxial o1, CoordAxial o2) {
        List<Plot> n1 = p.getNeighbors(o1);
        List<Plot> n2 = p.getNeighbors(o2);

        int sumAdjColor1 = n1.stream().mapToInt(this::oneIfSameColor).sum();
        int sumAdjColor2 = n2.stream().mapToInt(this::oneIfSameColor).sum();

        return sumAdjColor1 - sumAdjColor2;
    }
}
