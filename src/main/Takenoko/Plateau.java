package Takenoko;

import Takenoko.Parcel.CoordAxial;
import Takenoko.Parcel.Parcel;

import java.util.HashMap;

public class Plateau {
    private HashMap<CoordAxial, Parcel> parcels;

    public Plateau() {
        parcels = new HashMap<>();
    }

    public Parcel getParcel(int q, int r) {
        return parcels.get(new CoordAxial(q, r));
    }

    public Parcel getParcel(CoordAxial coord) {
        return parcels.get(coord);
    }

    public void putParcel(Parcel parcel, int q, int r) {
        parcels.put(new CoordAxial(q, r), parcel);
    }

    public void putParcel(Parcel parcel, CoordAxial coord) {
        parcels.put(coord, parcel);
    }
}
