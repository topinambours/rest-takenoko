package Takenoko.Joueur.Strategie;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

import java.util.List;

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

    public StrategieAdjacent() {

    }
}
