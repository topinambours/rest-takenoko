package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Irrigation.Orient;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.Comparator;
import java.util.List;

/**
 * Le robot prend le parti de placer sa parcelle au placement maximisant le nombre de bambous adjacents
 */
public class StrategieBamboo implements Strategie {

    public class ComparateurPosBambooAdj implements Comparator<CoordAxial>{

        private Plateau p;

        public ComparateurPosBambooAdj(Plateau p){
            this.p = p;
        }

        @Override
        public int compare(CoordAxial o1, CoordAxial o2) {
            List<Plot> n1 = p.getNeighbors(o1);
            List<Plot> n2 = p.getNeighbors(o2);

            int sumAdjBamb1 = n1.stream().mapToInt(parcel -> parcel.getBambou()).sum();
            int sumAdjBamb2 = n2.stream().mapToInt(parcel -> parcel.getBambou()).sum();

            return sumAdjBamb1 - sumAdjBamb2;
        }
    }

    @Override
    public CoordAxial getCoord(Plateau P) {
        List<CoordAxial> legPos = P.legalPositions();

        return legPos.stream().max(new ComparateurPosBambooAdj(P)).get();

    }

    public CoordIrrig getIrrig(Plateau plateau) {
        return new CoordIrrig(0, 0, Orient.W);
    }

    @Override
    public String getStrategieLabel() {
        return "max adj bamboo";
    }
}
