package Takenoko.Joueur.StrategieIrrig;

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
public class StrategieIrigBase implements StrategieIrrig {

    Plateau p;

    public StrategieIrigBase(Plateau p){
        this.p = p;
    }


    @Override
    public Optional<CoordIrrig> getIrrig(Plateau P) {
        List<CoordIrrig> legalPos = p.legalIrrigPositions();
        if(!P.getLastPlop().getCoord().equals(new CoordAxial(0,0))){
            Optional res = legalPos.stream().max(new ComparateurIrig(p));

            if (res.isPresent()){
                return (Optional<CoordIrrig>) res.get();
            }
        }


        return Optional.empty();
    }



    @Override
    public String getStrategieLabel() {
        return "Stratégie irrigation optimal";
    }
}
