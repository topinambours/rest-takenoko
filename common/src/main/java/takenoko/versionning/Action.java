package takenoko.versionning;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import takenoko.Plateau;
import takenoko.irrigation.CoordIrrig;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Une evolution est une actionType effectué sur la plateau
 * @param <T>
 */
@Data
public class Action<T> {
    private int id;
    private ActionType actionType;
    private List<T> argument;

    private static ObjectMapper mapper = new ObjectMapper();

    public Action() {
    }

    public Action(int id, ActionType actionType, T... arguments) {
        this.id = id;
        this.actionType = actionType;
        this.argument = new ArrayList<T>(Arrays.asList(arguments));
    }

    public Action(ActionType actionType, T... arguments) {
        this.id = -1;
        this.actionType = actionType;
        this.argument = new ArrayList<T>(Arrays.asList(arguments));
    }


    public ActionType getActionType() {
        return actionType;
    }

    public List<T> getArgument() {
        return argument;
    }

    public int getId() {
        return id;
    }



    public boolean haveValidArguments(){
        switch (actionType){
            case PUTPLOT: return argument.size() == 2 && argument.get(0) instanceof CoordAxial && argument.get(1) instanceof Tuile;

            case ADDIRRIG: return argument.size() == 1 && argument.get(0) instanceof CoordIrrig;

            case MOOVEPANDA: return argument.size() == 1 && argument.get(0) instanceof CoordAxial;

            default: return false;
        }
    }

    /**
     * Static function to apply a actionType into a board
     * @param action Action
     * @param plateau Plateau
     * @return boolean
     */
    static public boolean applyAction(Action action, Plateau plateau){

        if (!action.haveValidArguments()) {
            return false;
        }

        switch (action.getActionType()){
            case PUTPLOT:
                if(action.getArgument().size() == 2){
                        CoordAxial coordAxial = mapper.convertValue(action.getArgument().get(0), CoordAxial.class);
                        Tuile tuile = mapper.convertValue(action.getArgument().get(1), Tuile.class);
                    if ((coordAxial != null) && (tuile != null)){
                        plateau.poserTuile(coordAxial,tuile);
                        return true;
                    }else return false;
                }else return false;
            case ADDIRRIG:
                if (action.getArgument().size() == 1){
                    CoordIrrig coordIrrig;
                        coordIrrig = (CoordIrrig) action.getArgument().get(0);
                    if(coordIrrig != null){
                        return plateau.addIrrigation(coordIrrig);
                    }else return false;
                }else return false;
            case MOOVEPANDA:
                if (action.getArgument().size() == 1){
                    CoordAxial coordAxial = mapper.convertValue(action.getArgument().get(0),CoordAxial.class);
                    if(coordAxial != null){
                        plateau.movePanda(coordAxial);
                        return true;
                    }else return false;
                }else return false;

            default: return false;
        }
    }

    /**
     * Function to apply all actions
     * @param actions List<Action>
     * @param plateau Plateau
     * @return boolean
     */
    static public boolean applyAllAction(List<Action> actions,Plateau plateau){
        if (actions.size() == 0) return true;
        for (Action action : actions){
            boolean current = applyAction(action,plateau);
            if (!current) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", actionType=" + actionType +
                ", argument=" + argument +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action<?> action1 = (Action<?>) o;
        return actionType == action1.actionType &&
                Objects.equals(argument, action1.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionType, argument);
    }
}
