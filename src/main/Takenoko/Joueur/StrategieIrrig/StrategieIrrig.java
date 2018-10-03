package Takenoko.Joueur.StrategieIrrig;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

/**
 * L'interface de stratégie Général. Permet d'implémenter les stratégies.
 * Elle compte les classes suivantes :
 * <ul>
 *     <li>getCoords qui permet au robot de définir les bonnes coordonnés en fonction de la strategie adoptée</li>
 *     <li>getIrrig qui permet au robot de placer des irrigations en fonction de la strategie adoptée</li>
 *     <li>getStrategieLabel qui permet de savoir sur quelle stratégie on travaille actuellement</li>
 * </ul>
 */
public interface StrategieIrrig {
    public Optional<CoordIrrig> getIrrig(Plateau P);
    public String getStrategieLabel();
}
