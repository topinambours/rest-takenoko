package client.strategie;

import takenoko.irrigation.CoordIrrig;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.List;
import java.util.Random;

public class RandomStrategie implements Strategie {
    private Random rand = new Random();

    public RandomStrategie() {
    }

    @Override
    public Tuile selectTuile(List<Tuile> tuiles) {
        if(tuiles.size() == 1){
            return  tuiles.get(0);
        }
        return tuiles.get(rand.nextInt(tuiles.size() - 1));
    }

    @Override
    public CoordAxial selectEmplacement(List<CoordAxial> coordAxials) {
        if(coordAxials.size() == 1){
            return  coordAxials.get(0);
        }
        return coordAxials.get(rand.nextInt(coordAxials.size() - 1));

    }

    @Override
    public CoordIrrig selectIrrigationEmplacement(List<CoordIrrig> coordIrrigs) {
        if(coordIrrigs.size() == 1){
            return  coordIrrigs.get(0);
        }
        return coordIrrigs.get(rand.nextInt(coordIrrigs.size() - 1));
    }

    @Override
    public CoordAxial selectPandaEmplacement(List<CoordAxial> coordAxials) {
        if(coordAxials.size() == 1){
            return  coordAxials.get(0);
        }
        return coordAxials.get(rand.nextInt(coordAxials.size() - 1));
    }
}
