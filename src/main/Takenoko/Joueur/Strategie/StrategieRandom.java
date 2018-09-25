package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Irrigation.Orient;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

import java.util.List;
import java.util.Random;

public class StrategieRandom implements Strategie{

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        return p.legalPositions();
    }

    public CoordAxial getCoord(Plateau p) {

        List<CoordAxial> listOfPos = p.legalPositions();
        Random random = new Random();
        int a = random.nextInt(listOfPos.size());
        return listOfPos.get(a);

    }

    public CoordIrrig getIrrig(Plateau plateau) {
        return plateau.legalIrrigPositions().get(0);
    }

    public StrategieRandom() {
    }

    @Override
    public String getStrategieLabel() {
        return "Stratégie aléatoire";
    }
}
