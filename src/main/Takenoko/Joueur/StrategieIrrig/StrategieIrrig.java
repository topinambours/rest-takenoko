package Takenoko.Joueur.StrategieIrrig;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

/**
 * L'interface de stratégie d'irrigation.
 * Elle compte les classes suivantes :
 * <ul>
 *     <li>getIrrig qui permet au robot de définir les bons emplacements pour jouer une irrigation</li>
 *     <li>getStrategieLabel qui permet d'avoir le label de la strategie</li>
 * </ul>
 */
public interface StrategieIrrig {
    public Optional<CoordIrrig> getIrrig(Plateau P);
    public String getStrategieLabel();
}
