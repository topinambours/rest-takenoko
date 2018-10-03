package Takenoko.Joueur.StrategieIrrig;

import Takenoko.Util.Comparators.ComparateurIrig;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

/**
 * La stratégie irrigation de base. Pose où elle le peut
 */
public class StrategieIrigBase implements StrategieIrrig {

    Plateau p;

    public StrategieIrigBase(Plateau p){
        this.p = p;
    }

    /**
     * Permet d'avoir l'irrigation a poser
     * @param P Plateau le plateau
     * @return Optional une irrigation si une est possible
     */
    @Override
    public Optional<CoordIrrig> getIrrig(Plateau plateau) {
        List<CoordIrrig> res = plateau.legalIrrigPositions();
        if (res.size() >= 1) {
            return Optional.of(res.get(0));
        } else {
            return Optional.empty();
        }
    }


    /**
     * Permet d'avoir le label de la strategie
     * @return String label
     */
    @Override
    public String getStrategieLabel() {
        return "Stratégie irrigation de base";
    }
}
