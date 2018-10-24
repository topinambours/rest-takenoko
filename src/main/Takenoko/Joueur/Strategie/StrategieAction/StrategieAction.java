package takenoko.joueur.strategie.StrategieAction;

import takenoko.Game;

public interface StrategieAction {

    Action firstActionType(Game game);
    Action secondActionType(Game game);
    Action thirdActionType(Game game);

    default Action getLogicalNextAction(Action action){
        switch (action){
            case Gardener:
                return Action.Plot;
            case Panda:
                return Action.Gardener;
            case Irrig:
                return Action.Gardener;
            case Plot:
                return Action.Panda;
            case Card:
                return Action.Plot;
            case ObjCard:
                return Action.ObjCard;
            default:
                return Action.Plot;

        }

    }
}
