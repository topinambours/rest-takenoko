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
        int size = tuiles.size();
        System.out.println("=>size " + size);
        if(size >= 0){
            return tuiles.get(rand.nextInt(size - 1));
        }else{
            return null;
        }



    }

    @Override
    public CoordAxial selectEmplacement(List<CoordAxial> coordAxials) {
        Random rand = new Random();
        int size = coordAxials.size();
        if(size >= 0){
            return coordAxials.get(rand.nextInt());
        }else{
            return null;
        }

    }

}
