package Takenoko.Plot;

public class Plot {

    private CoordAxial coord;
    private int bambou;
    private boolean irriguee;
    private boolean water;

    public Plot(int q, int r){
        this.coord = new CoordAxial(q,r);
        this.bambou = 0;
        this.irriguee = false;
        this.water = false;
    }

    public Plot(CoordAxial coordAxial){
        this.coord = coordAxial;
        this.bambou = 0;
        this.irriguee = false;
        this.water=false;
    }

    public Plot(){
        this(0,0);
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
     */
    public void removeBamboo(){
        this.bambou = 0;
    }

    /**
     * Fait pousser de 1 le bambou sur le plot
     * si le plot est irrigué.
     */
    public void pousserBambou(){
<<<<<<< Updated upstream
        if(isIrriguee()){
            bambou++;
        }
        //pour l'instant, tant que nous n'avons pas encore l'irrigation
        bambou++;
=======
        if(haveWater()){
            bambou++;
        }

>>>>>>> Stashed changes
    }

    /**
     * Permet de savoir si le plot contient une irrigation sur au moins 1
     * de ses adjacences.
     * @return
     */
    public boolean isIrriguee(){
        return irriguee;
    }

    @Override
    public String toString(){
        return "Parcelle("+getq()+","+getr()+")";
    }

}
