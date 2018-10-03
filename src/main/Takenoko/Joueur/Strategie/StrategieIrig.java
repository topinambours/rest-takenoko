package Takenoko.Joueur.Strategie;

import Takenoko.Util.Comparators.ComparateurIrig;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

/**
 * La stratégie irrigation est elle faite pour la pose des irrigations
 */
public class StrategieIrig implements Strategie{

    Plateau p;

    public StrategieIrig(Plateau p){
        this.p = p;
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        return null;
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        return null;
    }

    @Override
    public Optional<CoordIrrig> getIrrig(Plateau P) {
        List<CoordIrrig> legalPos = p.legalIrrigPositions();

        Optional res = legalPos.stream().max(new ComparateurIrig(p));

        if (res.isPresent()){
            return (Optional<CoordIrrig>) res.get();
        }

        return Optional.empty();
    }

    @Override
    public CoordAxial getCoord(Plateau p, Plot plot) {
        return null;
    }

    @Override
    public CoordAxial getCoord(Plateau p) {
        return null;
    }

    @Override
    public String getStrategieLabel() {
        return "Stratégie irrigation optimal";
    }
}
