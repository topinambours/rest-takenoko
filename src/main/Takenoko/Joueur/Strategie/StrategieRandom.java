package Takenoko.Joueur.Strategie;

import Takenoko.Parcel.CoordAxial;

import java.util.Random;

public class StrategieRandom implements Strategie{

    public CoordAxial getCoord() {
        Random random = new Random();
        return new CoordAxial(random.nextInt(),random.nextInt());
    }

    public StrategieRandom() {

    }
}
