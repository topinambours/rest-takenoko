package Takenoko.Joueur.Strategie.StrategieAction;

import Takenoko.Plateau;

public interface StrategieAction {

    Action firstActionType(Plateau plat);
    Action secondActionType(Plateau plat);
    Action thirdActionType(Plateau plat);
}
