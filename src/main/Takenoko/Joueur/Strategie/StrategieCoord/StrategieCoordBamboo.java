package Takenoko.Joueur.Strategie.StrategieCoord;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Joueur.Joueur;
import Takenoko.Objectives.PandaObjectiveCard;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Le robot prend le parti de placer sa parcelle au placement maximisant le nombre de bambous adjacents
 */
public class StrategieCoordBamboo implements StrategieCoord {

    private Boolean mustCompleteGoals;

    private Joueur joueur;

    private HashSet<PandaObjectiveCard> goals;


    public Joueur getJoueur() {
        return joueur;
    }
    public HashSet<PandaObjectiveCard> getGoal() {
        return goals;
    }
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setGoal(HashSet<PandaObjectiveCard> goals) {
        this.goals = goals;
    }


    public StrategieCoordBamboo(Boolean mustCompleteGoals){
        this.mustCompleteGoals = mustCompleteGoals;
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        List<CoordAxial> legPos = p.legalPositions();

        List<CoordAxial> legPosFilteredByMaxBambooAdj =  legPos.stream().collect(Collectors.groupingBy
                (pos -> p.getNeighbors(pos).stream().mapToInt(Plot::getBambou).sum(),
                        TreeMap::new, toList())).lastEntry().getValue();



        return legPosFilteredByMaxBambooAdj;

    }

    @Override
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        return getCoords(p);
    }

    public CoordAxial getCoord(Plateau p, Plot plot) {
        return getCoord(p);
    }

    @Override
    public CoordAxial getCoord(Plateau p) {
        List<CoordAxial> posMaxBamboo = getCoords(p);

        if (mustCompleteGoals) {

            int countAdjVert = 0;
            int countAdjRose = 0;
            int countAdjJaune = 0;
            for (CoordAxial pos : posMaxBamboo) {
                countAdjVert = p.getNeighbors(pos).stream().filter(plot -> plot.getCouleur() == Couleur.VERT).mapToInt(Plot::getBambou).sum();
                countAdjRose = p.getNeighbors(pos).stream().filter(plot -> plot.getCouleur() == Couleur.VERT).mapToInt(Plot::getBambou).sum();
                countAdjJaune = p.getNeighbors(pos).stream().filter(plot -> plot.getCouleur() == Couleur.VERT).mapToInt(Plot::getBambou).sum();

                for (PandaObjectiveCard card : goals ){
                    EnumMap<Couleur, Integer> needed = card.countRequired();
                    // Le joueur à plus de bambous jaune, il peut compléter la carte sans se préocuper des jaunes
                    if ((countAdjJaune - joueur.getBambousJaunes()) > needed.get(Couleur.JAUNE) &&
                            (countAdjRose - joueur.getBambousRoses()) > needed.get(Couleur.ROSE) &&
                            (countAdjVert - joueur.getBambousVerts()) > needed.get(Couleur.VERT)){
                        return pos;
                    }
                }

            }
        }

        return posMaxBamboo.get(0);
    }



    @Override
    public String getStrategieLabel() {
        return "max adj bamboo";
    }
}
