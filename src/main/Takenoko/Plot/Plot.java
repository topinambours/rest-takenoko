package Takenoko.Plot;

public class Plot {

    private CoordAxial coord;
    private Couleur couleur;

    public Plot(int q, int r,Couleur couleur){
        this.coord = new CoordAxial(q,r);
        this.couleur = couleur;
    }

    public Plot(Couleur couleur){
        this(0,0,couleur);
    }

    public Plot(int q,int r){
        this(q,r,Couleur.ROUGE);
    }

    public Plot(){
        this(0,0,Couleur.ROUGE);
    }

    public int getq(){
        return coord.getQ();
    }

    public int getr(){
        return coord.getR();
    }

    public CoordAxial getCoord() {
        return coord;
    }

    public void setCoord(int q, int r){
        coord.setQ(q);
        coord.setR(r);
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    @Override
    public String toString(){
        return "Parcelle";
    }

}
