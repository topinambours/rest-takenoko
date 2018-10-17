package Takenoko.Joueur.Strategie;

import Takenoko.Plateau;

public interface StrategieActions {

    Action firstActionType(Plateau plat);
    Action secondActionType(Plateau plat);
    Action thirdActionType(Plateau plat);
}
