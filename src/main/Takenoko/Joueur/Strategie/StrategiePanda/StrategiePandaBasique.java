package Takenoko.Joueur.Strategie.StrategiePanda;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Util.Comparators.CompPosPanda;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Le joueur déplace le panda sur une position disposant dans la mesure du possible des bambous
 */
public class StrategiePandaBasique implements StrategiePanda {
    @Override
    public String getStrategiePandaLabel() {
        return null;
    }

    @Override
    public CoordAxial getPandaMove(Plateau plateau, Joueur joueur) {
        List<CoordAxial> legalPos = plateau.getLinePlots(plateau.getPosPanda()).stream().map(Plot::getCoord).collect(Collectors.toList());
        legalPos.remove(plateau.getPosPanda());

        legalPos.sort(new CompPosPanda(plateau));
        return legalPos.get(0);
    }
}
