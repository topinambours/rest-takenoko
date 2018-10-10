package Takenoko.Joueur.Strategie.StrategieIrrig;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;

import java.util.List;
import java.util.Optional;

/**
 * La stratégie irrigation de base. Pose où elle le peut
 */
public class StrategieIrrigBase implements StrategieIrrig {

    Plateau p;

    public StrategieIrrigBase(Plateau p){
        this.p = p;
    }

    /**
     * Permet d'avoir l'irrigation a poser
     * @param plateau Plateau le plateau
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
    public String getStrategieIrrigLabel() {
        return "Stratégie irrigation de base";
    }
}
