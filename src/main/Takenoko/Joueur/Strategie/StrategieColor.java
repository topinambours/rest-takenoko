package Takenoko.Joueur.Strategie;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;


public class StrategieColor implements Strategie{

    public CoordAxial getCoord(Plateau plateau, Plot plot) {

        List<CoordAxial> listOfPos = plateau.legalPositions();

        int i = 0;
        while(!listOfPos.isEmpty()){
            for(int j =0; j<listOfPos.get(i).getNeighborCoords().size(); j++){
                if(plateau.getPlot(listOfPos.get(i).getNeighborCoords().get(j)).getCouleur() == plot.getCouleur()){
                    return listOfPos.get(i).getNeighborCoords().get(j);
                }
            }
            i++;
        }
        return listOfPos.get(0);

    }


    public StrategieColor() {
    }

}
