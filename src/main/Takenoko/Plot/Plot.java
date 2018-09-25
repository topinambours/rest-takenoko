package Takenoko.Plot;

public class Plot {

    private CoordAxial coord;
    private int bambou;
    private boolean water;

    public Plot(int q, int r){
        this.coord = new CoordAxial(q,r);
        this.bambou = 0;
        this.water = false;
    }

    public Plot(CoordAxial coordAxial){
        this.coord = coordAxial;
        this.bambou = 0;
        this.water=false;
    }

    public Plot(){
        this(0,0);
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
     * Permet de savoir si le plot est le plot Lac.
     * @return
     */
    public boolean isWater() {
        return water;
    }

    /**
     * Permet de modifier un plot pour le transformer
     * en lac.
     * @param water
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
     */
    public void removeBamboo(){
        this.bambou = 0;
    }

    /**
     * Fait pousser de 1 le bambou sur le plot
     * si le plot est irrigué.
     */
    public void pousserBambou(){
        if(isWater()){
            bambou++;
        }
        //pour l'instant, tant que nous n'avons pas encore l'irrigation
        bambou++;
    }

    @Override
    public String toString(){
        return "Parcelle("+getq()+","+getr()+")";
    }

}
