package core.strategie;

import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.List;
import java.util.Random;

public class RandomStrategie implements Strategie {
    public RandomStrategie() {
    }

    @Override
    public Tuile selectTuile(List<Tuile> tuiles) {
        Random rand = new Random();
        return tuiles.get(rand.nextInt(tuiles.size()));
    }

    @Override
    public CoordAxial selectEmplacement(List<CoordAxial> coordAxials) {
        Random rand = new Random();
        return coordAxials.get(rand.nextInt(coordAxials.size()));
    }

}
