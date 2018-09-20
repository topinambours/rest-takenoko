package Takenoko.Joueur.Strategie;

import Takenoko.Plot.CoordAxial;

import java.util.Random;

public class StrategieRandom implements Strategie{

    public CoordAxial getCoord() {
        Random random = new Random();
        return new CoordAxial(random.nextInt(100),random.nextInt(100));
    }

    public StrategieRandom() {

    }
}
