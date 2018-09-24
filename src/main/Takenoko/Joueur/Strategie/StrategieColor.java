package Takenoko.Joueur.Strategie;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;

/**
 * Classe en prévision de la suite !
 */
public class StrategieColor {
    /*
    public CoordAxial getCoord(Plateau plateau, Plot plot) {

        List<CoordAxial> listOfPos = plateau.legalPositions();

        int i = 0;
        while(!listOfPos.isEmpty()){
            for(int j =0; j<listOfPos.get(i).getNeighborCoords().size(); j++){
                Plot plot2 = plateau.getPlot(listOfPos.get(i).getNeighborCoords().get(j));
                if(plot2.getCouleur() == plot.getCouleur()){
                    return listOfPos.get(i).getNeighborCoords().get(j);
                }
            }
            i++;
        }
        return listOfPos.get(0);

    }
    */

    public StrategieColor() {
    }
}