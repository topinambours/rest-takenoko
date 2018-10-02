package Takenoko.Plot;

import Takenoko.Properties.Couleur;

import java.util.Objects;

/**
 * Cette classe represente les parcelles
 */
public class Plot {

    private CoordAxial coord;
    private int bambou;
    private boolean water;
    private Couleur couleur;

    public Plot(int q, int r,Couleur couleur){
        this.coord = new CoordAxial(q,r);
        this.bambou = 0;
        this.water = false;
        this.couleur = couleur;
    }

    public Plot(CoordAxial coordAxial,Couleur couleur){
        this(coordAxial.getQ(),coordAxial.getR(),couleur);
    }

    public Plot(int q, int r){
        this(q,r,Couleur.JAUNE);
    }

    public Plot(CoordAxial coordAxial){
        this(coordAxial.getQ(),coordAxial.getR(),Couleur.JAUNE);
    }

    public Plot(Couleur couleur){
        this(0,0,couleur);
    }

    public Plot(){
        this(0,0,Couleur.JAUNE);
    }


    /**
     * Renvoie la coordonnée q du plot.
     * @return
     */
    public int getq(){
        return coord.getQ();
    }

    /**
     * Renvoie la coordonné r du plot.
     * @return
     */
    public int getr(){
        return coord.getR();
    }

    /**
     * Renvoie les coordonnées du plot.
     * @return
     */
    public CoordAxial getCoord() {
        return coord;
    }

    /**
     * Permet d'assigner une coordonnée à un plot.
     * @param q
     * @param r
     */
    public void setCoord(int q, int r){
        coord.setQ(q);
        coord.setR(r);
    }

    /**
     * Permet de connaitre la couleur d'une parcelle
     * @return Couleur la couleur
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Permet de definir la couleur d'une parcelle
     * @param couleur Couleur la couleur
     */
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Permet de savoir si la parcelle est irriguée, utile pour le bambou
     * @return boolean true|false
     */
    public boolean haveWater() {
        return water;
    }

    /**
     * Permet de mettre à jour la parcelle pour pouvoir faire pousser un bambou
     * @param water boolean true|false
     */
    public void setWater(boolean water) {
        this.water = water;
    }

    /**
     * Permet de savoir si le plot a un bambou
     * @return boolean true|false
     */
    public boolean haveBambou(){
        return bambou != 0;
    }

    /**
     * Permet d'avoir le bambou du plot
     * @return int
     */
    public int getBambou(){
        return bambou;
    }

    /**
     * Permet de supprimer le bambou du plot
     * @return le nombre de bambous enlevés de la parcelle
     */
    public int removeBamboo(){
        int out = this.bambou;
        this.bambou = 0;
        return out;
    }

    /**
     * Fait pousser de 1 le bambou sur le plot
     * si le plot est irrigué.
     * @return true si un nouveau bambou a été ajouté false sinon.
     */
    public boolean pousserBambou(){
        if(haveWater() && bambou < 4){
            bambou++;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plot plot = (Plot) o;
        return bambou == plot.bambou &&
                water == plot.water &&
                Objects.equals(coord, plot.coord) &&
                couleur == plot.couleur;
    }

    @Override
    public String toString(){
        return "Parcelle("+getq()+","+getr()+")";
    }

}
