package Takenoko.Joueur.Strategie.StrategieJardinier;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Util.Comparators.CompPosJardinier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Dans cette stratégie, le placement maximise le nombre de bambous qui peuvent pousser en un seul tour
 */
public class StrategieJardinierBasique implements StrategieJardinier {

    @Override
    public String getStrategieJardinierLabel() {
        return "";
    }

    @Override
    public CoordAxial getJardinierMove(Plateau plateau, Joueur joueur) {
        List<CoordAxial> coords = plateau.getLinePlots(plateau.getPosJardinier()).stream().map(Plot::getCoord).collect(Collectors.toList());
        coords.remove(plateau.getPosJardinier());

        // on cherche à maximiser le nombre de bambous qui peuvent pousser pour une position


        Optional<CoordAxial> coordAxial = coords.stream().max(new CompPosJardinier(plateau));
        return coordAxial.orElseGet(plateau::getPosJardinier);

    }
}
