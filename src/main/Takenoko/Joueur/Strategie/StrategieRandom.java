package Takenoko.Joueur.Strategie;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

import java.util.List;
import java.util.Random;

public class StrategieRandom implements Strategie{

    public CoordAxial getCoord(Plateau plateau) {

        List<CoordAxial> listOfPos = plateau.legalPositions();
        Random random = new Random();
        int a = random.nextInt(listOfPos.size());
        return listOfPos.get(a);

    }

    public StrategieRandom() {

    }

    @Override
    public String getStrategieLabel() {
        return "Stratégie aléatoire";
    }
}
