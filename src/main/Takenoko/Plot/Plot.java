package takenoko.Plot;

import takenoko.objectives.amenagement.Amenagement;
import takenoko.properties.Couleur;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Cette classe represente les parcelles
 */
@Component
public class Plot {

    private CoordAxial coord;
    private int bambou;
    private boolean water;
    private Couleur couleur;
    private Amenagement amenagement;

    public Plot(Couleur couleur, Amenagement amenagement){
        this.couleur = couleur;
        this.amenagement = amenagement;
        water = false;
        if (amenagement.equals(Amenagement.BASSIN)){
            water = true;
        }
        this.coord = new CoordAxial(0,0);
        this.bambou = 0;
    }

    public Plot(int q, int r,Couleur couleur){
        this.coord = new CoordAxial(q,r);
        this.bambou = 0;
        this.water = false;
        this.couleur = couleur;
        this.amenagement = Amenagement.NON;
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
     * @return la coordonnée q du plot.
     */
    public int getq(){
        return coord.getQ();
    }

    /**
     * Renvoie la coordonnée r du plot.
     * @return la coordonnée r du plot.
     */
    public int getr(){
        return coord.getR();
    }

    /**
     * Renvoie les coordonnées du plot.
     * @return les coordonnées du plot.
     */
    public CoordAxial getCoord() {
        return coord;
    }

    /**
     * Permet d'assigner une coordonnée à un plot.
     * @param q la coordonnée q
     * @param r la coordonnée r
     */
    public void setCoord(int q, int r){
        coord.setQ(q);
        coord.setR(r);
    }

    /**
     * Permet de définir les coordonnées de la plot
     * @param coord CoordAxial coord
     */
    public void setCoord(CoordAxial coord){
        this.coord.setQ(coord.getQ());
        this.coord.setR(coord.getR());
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
    public boolean haveWater() { //Le bambous à le bassin
        return amenagement.equals(Amenagement.BASSIN)||water;
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
    public int removeAllBambou(){
        return removeBambou(this.bambou);
    }

    /**
     * Permet de supprimer un certain nombre de bambou du plot
     * @param n nombre de bambous à enlever
     * @return le nombre de bambous enlevés de la parcelle
     */
    public int removeBambou(int n){
        int out = this.bambou;
        if (n > this.bambou){
            this.bambou = 0;
        }else{
            this.bambou -= n;
        }
        return out;
    }

    /**
     * Permet de retourner l'amenagement courant
     * @return amenagement
     */
    public Amenagement getAmenagement() {
        return amenagement;
    }

    /**
     * Permet de definir l'amenagement
     * @param amenagement amenagement
     */
    public void setAmenagement(Amenagement amenagement) {
        this.amenagement = amenagement;
    }

    /**
     * Fait pousser de 1 le bambou sur le plot
     * si le plot est irrigué.
     * @return true si un nouveau bambou a été ajouté false sinon.
     */
    public boolean pousserBambou(){
        if(haveWater() && bambou < 4){
            bambou++;
            if(amenagement.equals(Amenagement.ENGRAIS) && bambou < 4){ //Ajout Bambou
                bambou++;
            }
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
    public int hashCode() {
        return Objects.hash(coord, bambou, water, couleur);
    }

    @Override
    public String toString(){
        return "Parcelle("+getq()+","+getr()+")";
    }

}
