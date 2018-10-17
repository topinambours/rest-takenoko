package Takenoko.Joueur.Strategie.StrategiePanda;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StrategiePandaRandom implements StrategiePanda {
    @Override
    public String getStrategiePandaLabel() {
        return null;
    }

    @Override
    public CoordAxial getPandaMove(Plateau plateau, Joueur joueur) {
        List<CoordAxial> legalPos = plateau.getLinePlots(plateau.getPosPanda()).stream().map(Plot::getCoord).collect(Collectors.toList());
        legalPos.remove(plateau.getPosPanda());

        return legalPos.get(new Random().nextInt(legalPos.size()));
    }
}
