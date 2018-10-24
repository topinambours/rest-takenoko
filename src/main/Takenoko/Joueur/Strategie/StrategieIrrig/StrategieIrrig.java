package takenoko.joueur.strategie.StrategieIrrig;

import takenoko.Plateau;

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
    public Optional getIrrig(Plateau P);
    public String getStrategieIrrigLabel();
}
