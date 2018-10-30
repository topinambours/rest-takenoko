package takenoko.joueur.strategie.StrategieJardinier;

import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.objectives.GardenObjectiveCard;
import takenoko.objectives.ObjectiveCard;
import takenoko.objectives.PandaObjectiveCard;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;
import takenoko.util.comparators.ComparateurCarteObjectifPanda;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class StratégieJardinierObjectifs implements StrategieJardinier {


    @Override
    public String getStrategieJardinierLabel() {
        return "";
    }

    @Override
    public CoordAxial getJardinierMove(Plateau plateau, Joueur joueur) {

        List<GardenObjectiveCard> jardCard = joueur.getGardenObjectiveCards();

        // On cherche à compléter ses objectifs on cherche donc des tuiles parmis les déplacements légaux correspondants aux critères des cartes

        List<Plot> legalMoves = plateau.getLinePlots(plateau.getPosJardinier());

        List<Plot> interesting = new ArrayList<>();

        for (Plot p : legalMoves){
            for (GardenObjectiveCard card : jardCard){
                if (p.haveWater() && card.getColor().equals(p.getCouleur()) && p.getBambou() < 3){
                    interesting.add(p);
                }
            }
        }

        if (!interesting.isEmpty()) {
            for (Plot p : interesting) {
                if (p.getAmenagement().equals(Amenagement.ENGRAIS)) {
                    return p.getCoord();
                }
            }

            return interesting.get(0).getCoord();
        }
        // Il joue dans l'intéret du panda

        List<PandaObjectiveCard> sorted = joueur.getPandaObjectiveCards().stream()
                .sorted(new ComparateurCarteObjectifPanda(
                        joueur.getBambousJaunes(),
                        joueur.getBambousVerts(),
                        joueur.getBambousRoses()))
                .collect(Collectors.toList());

        List<CoordAxial> legalPos = plateau.getLinePlots(plateau.getPosJardinier()).stream().map(Plot::getCoord).collect(Collectors.toList());
        legalPos.remove(plateau.getPosJardinier());
        legalPos.remove(new CoordAxial(0,0));

        for (PandaObjectiveCard card : sorted){

            EnumMap<Couleur, Integer> needed = card.countRequired();

            for (CoordAxial pos : legalPos){
                Plot current = plateau.getPlot(pos);
                if (current.getAmenagement() != Amenagement.ENCLOS && needed.get(current.getCouleur()) > 0){
                    return pos;
                }
            }

        }

        // Position aléatoire sinon
        return new StrategieJardinierRandom().getJardinierMove(plateau,joueur);

    }
}
