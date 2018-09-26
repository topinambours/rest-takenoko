package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class StrategieRandom implements Strategie{

    @Override
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        return getCoords(p);
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        return p.legalPositions();
    }

    public CoordAxial getCoord(Plateau p, Plot plot) {
        return getCoord(p);

    }

    public CoordAxial getCoord(Plateau p) {
        List<CoordAxial> listOfPos = p.legalPositions();
        Random random = new Random();
        int a = random.nextInt(listOfPos.size());
        return listOfPos.get(a);

    }

    public Optional<CoordIrrig> getIrrig(Plateau plateau) {
        var res = plateau.legalIrrigPositions();
        if (res.size() >= 1) {
            return Optional.of(res.get(0));
        } else {
            return Optional.empty();
        }
    }
    public StrategieRandom() {
    }

    @Override
    public String getStrategieLabel() {
        return "Stratégie aléatoire";
    }
}
