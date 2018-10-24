package takenoko.joueur.strategie.StrategieIrrig;

import takenoko.irrigation.CoordIrrig;
import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.util.comparators.ComparateurIrig;

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
     * Renvoie la liste triée des positions irrigables en fonction du nombre de parcelles non irriguées
     * Voir {@link ComparateurIrig}
     * @param P Plateau le plateau
     * @return Optional une irrigation si une est possible
     */
    @Override
    public Optional<CoordIrrig> getIrrig(Plateau P) {
        List<CoordIrrig> legalPos = p.legalIrrigPositions();
        if(!P.getLastPlop().getCoord().equals(new CoordAxial(0,0))) {
            // le robot cherche à maximiser le nombre de nouvelles parcelles irriguées
            // On cherche donc la position maximisant le nombre de nouvelles parcelles

            Optional<CoordIrrig> res = legalPos.stream().max((o1, o2) -> {

                int nbIrrigatedPloto1 = p.getPlotsFromIrig(o1).stream().mapToInt(pos -> pos.haveWater() ? 1 : 0).sum();
                int nbIrrigatedPloto2 = p.getPlotsFromIrig(o2).stream().mapToInt(pos -> pos.haveWater() ? 1 : 0).sum();

                return nbIrrigatedPloto1 - nbIrrigatedPloto2;
            });

            if (res.isPresent()) {
                return res;
            }
        }

        return Optional.empty();
    }


    /**
     * Permet d'avoir le label de la strategie
     * @return String label
     */
    @Override
    public String getStrategieIrrigLabel() {
        return "Stratégie irrigation optimal";
    }
}
