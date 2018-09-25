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
       this(coordAxial.getQ(),coordAxial.getR());
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

    public boolean isWater() {
        return water;
    }

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
        if(isIrriguee()){
            bambou++;
        }
        //pour l'instant, tant que nous n'avons pas encore l'irrigation
        bambou++;
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
