package Takenoko.Parcel;

public class Parcel {

    private CoordAxial coord;

    public Parcel(int q, int r){
        this.coord = new CoordAxial(q,r);
    }

    public Parcel(){
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

}
