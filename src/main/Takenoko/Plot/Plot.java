package Takenoko.Plot;

public class Plot {

    private CoordAxial coord;

    public Plot(int q, int r){
        this.coord = new CoordAxial(q,r);
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

    @Override
    public String toString(){
        return "Parcelle";
    }

}
