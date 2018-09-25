package Takenoko.Plot;

import Takenoko.Util.Console;

public class Bambou {
    private Plot plot;
    private int hauteur;

    /**
     * Creation d'un bambou
     * @param plot Plot
     * @param hauteur int
     */
    public Bambou(Plot plot, int hauteur) {
        this.plot = plot;
        this.hauteur = hauteur;
    }

    /**
     * Creation d'un bambou. Hauteur 1
     * @param plot Plot
     */
    public Bambou(Plot plot){
        this(plot,1);
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    /**
     * Augmente la taille d'un bambou de n
     * @param hauteur int
     */
    public void addHauteur(int hauteur){
        this.hauteur = this.hauteur + hauteur;
        Console.Log.debugPrint("Le bambou de la parcelle "+this.plot.toString()+" pousse de "+hauteur+"\n");
        Console.Log.debugPrint("Le bambou de la parcelle "+this.plot.toString()+" a maintenant une hauteur de "+this.hauteur+"\n");
    }

    /**
     * Augmente la taille d'un bambou de 1
     */
    public void addHauteur1(){
        addHauteur(1);
    }
}
