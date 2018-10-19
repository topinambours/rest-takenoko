package Takenoko.Joueur.Strategie.StrategieAction;

import Takenoko.Game;

public class StrategieActionBasique implements StrategieAction{

    public StrategieActionBasique() {
    }

    @Override
    public Action firstActionType(Game game) {
        if(!game.getDeck().isEmpty()){
            return Action.Card;
        }else{
            int nb = (int) (Math.random() * 1 );
            switch (nb){
                case 0:
                    return Action.Irrig;
                case 1:
                    return Action.Panda;
                default:
                    return Action.Panda;
            }
        }

    }

    @Override
    public Action secondActionType(Game game) {
        return getLogicalNextAction(firstActionType(game));
    }

    @Override
    public Action thirdActionType(Game game) {
        return null;
    }
}
