package takenoko.joueur.strategie.StrategieAction;

import takenoko.Game;

import java.util.Random;

public class StrategieActionBasique implements StrategieAction{

    public StrategieActionBasique() {
    }

    @Override
    public Action firstActionType(Game game) {
        if(!game.getPlotsDeck().isEmpty()){
            return Action.Card;
        }else{
            Random rd = new Random();
            int nb = rd.nextInt(2);
            if (nb == 0){
                return Action.Irrig;
            }else{
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
