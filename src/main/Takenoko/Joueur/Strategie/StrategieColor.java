package Takenoko.Joueur.Strategie;
import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Irrigation.Orient;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

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

    public Optional<CoordIrrig> getIrrig(Plateau plateau) {
        var res = plateau.legalIrrigPositions();
        if (res.size() >= 1) {
            return Optional.of(res.get(0));
        } else {
            return Optional.empty();
        }
    }
    public StrategieColor() {
    }
}