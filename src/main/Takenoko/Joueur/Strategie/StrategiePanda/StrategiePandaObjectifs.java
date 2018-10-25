package takenoko.joueur.strategie.StrategiePanda;

import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import takenoko.joueur.Joueur;
import takenoko.objectives.PandaObjectiveCard;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.properties.Couleur;
import takenoko.util.comparators.ComparateurCarteObjectifPanda;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Le joueur déplace le panda afin de compléter ses objectifs
 */
public class StrategiePandaObjectifs implements StrategiePanda {
    @Override
    public String getStrategiePandaLabel() {
        return "";
    }

    @Override
    public CoordAxial getPandaMove(Plateau plateau, Joueur joueur) {
        List<PandaObjectiveCard> sorted = joueur.getPandaObjectiveCards().stream()
                .sorted(new ComparateurCarteObjectifPanda(
                        joueur.getBambousJaunes(),
                        joueur.getBambousVerts(),
                        joueur.getBambousRoses()))
                .collect(Collectors.toList());

        List<CoordAxial> legalPos = plateau.getLinePlots(plateau.getPosPanda()).stream().map(Plot::getCoord).collect(Collectors.toList());
        legalPos.remove(plateau.getPosPanda());
        legalPos.remove(new CoordAxial(0,0));

        for (PandaObjectiveCard card : sorted){

            EnumMap<Couleur, Integer> needed = card.countRequired();

            for (CoordAxial pos : legalPos){
                Plot current = plateau.getPlot(pos);
                if (current.haveBambou() &&  current.getAmenagement() != Amenagement.ENCLOS && needed.get(current.getCouleur()) > 0){
                    return pos;
                }
            }

        }

        return new StrategiePandaRandom().getPandaMove(plateau,joueur);
    }
}
