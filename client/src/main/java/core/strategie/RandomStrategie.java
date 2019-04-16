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
        if(tuiles.size() == 1){
            return  tuiles.get(0);
        }
        Random rand = new Random();
        return tuiles.get(rand.nextInt(tuiles.size() - 1));
    }

    @Override
    public CoordAxial selectEmplacement(List<CoordAxial> coordAxials) {
        if(coordAxials.size() == 1){
            return  coordAxials.get(0);
        }
        Random rand = new Random();
        return coordAxials.get(rand.nextInt(coordAxials.size() - 1));

    }

}
