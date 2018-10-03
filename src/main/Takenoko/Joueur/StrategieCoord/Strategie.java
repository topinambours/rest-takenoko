package Takenoko.Joueur.StrategieCoord;

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
public interface Strategie {
    public List<CoordAxial> getCoords(Plateau p, Plot plot);
    public List<CoordAxial> getCoords(Plateau p);

    public Optional<CoordIrrig> getIrrig(Plateau P);

    public CoordAxial getCoord(Plateau p, Plot plot);
    public CoordAxial getCoord(Plateau p);

    public String getStrategieLabel();
}
