package takenoko.joueur.strategie.StrategieCoord;

import takenoko.Game;
import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

import java.util.ArrayList;
import java.util.List;

/**
 * L'interface de stratégie Général. Permet d'implémenter les stratégies.
 * Elle compte les classes suivantes :
 * <ul>
 *     <li>getCoords qui permet au robot de définir les bonnes coordonnés en fonction de la strategie adoptée</li>
 *     <li>getIrrig qui permet au robot de placer des irrigations en fonction de la strategie adoptée</li>
 *     <li>getStrategieLabel qui permet de savoir sur quelle stratégie on travaille actuellement</li>
 * </ul>
 */
public interface StrategieCoord {
    public StrategieCoordResult getDecision(Joueur joueur, Plateau plateau, List<Plot> plots);

    public String getStrategieCoordLabel();
}
