package Takenoko.Joueur.Strategie;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Irrigation.Orient;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

import java.util.List;
import java.util.zip.CheckedInputStream;

public class StrategieAdjacent implements Strategie{

    public CoordAxial getCoord(Plateau plateau) {

        List<CoordAxial> listOfPos = plateau.legalPositions();
        int max=0;
        CoordAxial c = new CoordAxial(0, 0);
        for(int i = 0; i<listOfPos.size(); i++){
            int res = plateau.nbAdajcent(listOfPos.get(i));
            if(res > max){
                max = res;
                c = listOfPos.get(i);
            }
        }
        return c;
    }

    public CoordIrrig getIrrig(Plateau plateau) {
        return new CoordIrrig(0, 0, Orient.N);
    }

    @Override
    public String getStrategieLabel() {
        return "Stratégie des adjacents";
    }

    public StrategieAdjacent() {

    }
}
