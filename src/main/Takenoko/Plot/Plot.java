package Takenoko.Plot;

public class Plot {

    private CoordAxial coord;
    private int bambou;

    public Plot(int q, int r){
        this.coord = new CoordAxial(q,r);
        this.bambou = 0;
    }

    public Plot(CoordAxial coordAxial){
        this.coord = coordAxial;
        this.bambou = 0;
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

    public void removeBamboo(){
        this.bambou = 0;
    }

    public void pousserBambou(){
        bambou++;
    }

    @Override
    public String toString(){
        return "Parcelle("+getq()+","+getr()+")";
    }

}
