package Takenoko.Parcel;

import java.util.Vector;

public class Parcel {

    /**
     * coord must be a class
     */
    private Vector<Integer> coord;

    public Parcel(int q, int r){
        this.coord = new Vector<>();
        this.coord.add(0,q);
        this.coord.add(1,r);
    }

    public Parcel(){
        this(0,0);
    }

    public int getq(){
        return coord.get(0);
    }

    public int getr(){
        return coord.get(1);
    }

    public void setCoord(int q, int r){
        this.coord.set(0,q);
        this.coord.set(1,r);
    }

}
