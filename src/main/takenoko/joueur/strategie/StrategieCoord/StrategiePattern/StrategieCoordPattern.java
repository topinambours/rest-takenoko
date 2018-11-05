package takenoko.joueur.strategie.StrategieCoord.StrategiePattern;

import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoord;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordResult;
import takenoko.objectives.PatternObjectiveCard;
import takenoko.objectives.patterns.Pattern;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;
import takenoko.util.Pair;
import takenoko.util.comparators.ComparateurCompletionPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategieCoordPattern implements StrategieCoord {

    /**
     * tente de placer une parcelle pour compléter son pattern le plus avancé. Si aucun coup qui avance un pattern n'est
     * trouvé, on joue le premier coup légal
     * @param joueur le joueur actuel
     * @param plateau le plateau de jeu
     * @param plots la liste des parcelles piochées par le joueur
     * @return
     */
    @Override
    public StrategieCoordResult getDecision(Joueur joueur, Plateau plateau, List<Plot> plots) {
        List<Pair<Double, CoordAxial>> scores = new ArrayList<>();
        Map<Pair<Double, CoordAxial>, Pattern> relations = new HashMap<>();
        for (PatternObjectiveCard card : joueur.getPatternObjectiveCards()) {
            Pair<Double, CoordAxial> score = card.getPattern().bestPartialCompletion(plateau);
            if (score.getLeft() > 0) scores.add(score);
        }
        scores.sort(new ComparateurCompletionPattern());

        for (int i = scores.size() - 1; i >= 0; i--) {
            List<Pair<Couleur, CoordAxial>> positions = relations.get(scores.get(i)).nextParcelsToProgress(plateau);
            for (int j = 0; j < plots.size(); j++) {
                for (Pair<Couleur, CoordAxial> coup : positions) {
                    if (plots.get(j).getCouleur() == coup.getLeft())
                        return new StrategieCoordResult(j, plots.get(j), coup.getRight());
                }
            }
        }
        return new StrategieCoordResult(0, plots.get(0), plateau.legalPositions().get(0));
    }

    @Override
    public String getStrategieCoordLabel() {
        return "Stratégie avec patterns";
    }
}
