package Takenoko;

import Takenoko.Parcel.CoordAxial;
import Takenoko.Parcel.Parcel;

import java.util.HashMap;

/**
 * Classe plateau, utilise un HashMap, stocke les parcelles en jeu avec leurs coordonnées axiales en clé
 */
public class Plateau {
    private HashMap<CoordAxial, Parcel> parcels;

    /**
     * Constructeur par défaut, instancie un plateau vide
     */
    public Plateau() {
        parcels = new HashMap<>();
    }

    /**
     * getter de parcelle, prend les coordonnées séparément
     * @param q coord en axe q de la parcelle
     * @param r coord en axe r de la parcelle
     * @return la parcelle placée en (q, r)
     */
    public Parcel getParcel(int q, int r) {
        return parcels.get(new CoordAxial(q, r));
    }

    /**
     * getter de parcelle, prend les coordonnées mises ensemble
     * @param coord coordonnées axiales de la parcelle
     * @return la parcelle placée en (coord)
     */
    public Parcel getParcel(CoordAxial coord) {
        return parcels.get(coord);
    }

    /**
     * placeur de parcelle, prend les coordonnées séparément
     * @param parcel une parcelle à placer
     * @param q coord en axe q où placer la parcelle
     * @param r coord en axe r où placer la parcelle
     */
    public void putParcel(Parcel parcel, int q, int r) {
        parcels.put(new CoordAxial(q, r), parcel);
    }

    /**
     * placeur de parcelle, prend les coordonnées mises ensemble
     * @param parcel une parcelle à placer
     * @param coord le couple de coordonnées où la placer
     */
    public void putParcel(Parcel parcel, CoordAxial coord) {
        parcels.put(coord, parcel);
    }

    public void putParcel(Parcel parcel){
        parcels.put(parcel.getCoord(), parcel);
    }
}
