package takenoko.versionning;

import lombok.Data;
import takenoko.Plateau;
import takenoko.irrigation.CoordIrrig;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * Une evolution est une action effectué sur la plateau
 * @param <T>
 */
@Data
public class Action<T> {
    private int id;
    private ActionType action;
    private List<T> argument;

    private static ObjectMapper mapper = new ObjectMapper();

    public Action() {
    }

    public Action(int id, ActionType action,  T... arguments) {
        this.id = id;
        this.action = action;
        this.argument = new ArrayList<T>(Arrays.asList(arguments));
    }

    public Action(ActionType action, T... arguments) {
        this.id = -1;
        this.action = action;
        this.argument = new ArrayList<T>(Arrays.asList(arguments));
    }


    public ActionType getAction() {
        return action;
    }

    public List<T> getArgument() {
        return argument;
    }

    public int getId() {
        return id;
    }

    /**
     * Static function to apply a action into a board
     * @param action Action
     * @param plateau Plateau
     * @return boolean
     */
    static public boolean applyAction(Action action, Plateau plateau){
        switch (action.getAction()){
            case PUTPLOT:
                if(action.getArgument().size() == 2){
                    CoordAxial coordAxial = mapper.convertValue(action.getArgument().get(0), CoordAxial.class);
                    Tuile tuile = mapper.convertValue(action.getArgument().get(1),Tuile.class);
                    if ((coordAxial != null) && (tuile != null)){
                        plateau.poserTuile(coordAxial,tuile);
                        return true;
                    }else return false;
                }else return false;
            case ADDIRRIG:
                if (action.getArgument().size() == 1){
                    CoordIrrig coordIrrig = mapper.convertValue(action.getArgument().get(0),CoordIrrig.class);
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
        Iterator<Action> iterator = actions.iterator();
        while (iterator.hasNext()){
            boolean current = applyAction(iterator.next(),plateau);
            if (!current) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", action=" + action +
                ", argument=" + argument +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action<?> action1 = (Action<?>) o;
        return action == action1.action &&
                Objects.equals(argument, action1.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, argument);
    }
}
