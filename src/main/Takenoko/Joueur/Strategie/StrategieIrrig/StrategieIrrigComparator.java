package Takenoko.Joueur.Strategie.StrategieIrrig;

import Takenoko.Util.Comparators.ComparateurIrig;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

/**
 * La stratégie irrigation plus complexe utilisant notamment un comparateur {@link ComparateurIrig}
 */
public class StrategieIrrigComparator implements StrategieIrrig {

    Plateau p;

    public StrategieIrrigComparator(Plateau p){
        this.p = p;
    }

    /**
     * Permet d'avoir l'irrigation a poser
     * @param P Plateau le plateau
     * @return Optional une irrigation si une est possible
     */
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


    /**
     * Permet d'avoir le label de la strategie
     * @return String label
     */
    @Override
    public String getStrategieLabel() {
        return "Stratégie irrigation optimal";
    }
}
