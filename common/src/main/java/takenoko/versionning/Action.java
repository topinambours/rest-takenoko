package takenoko.versionning;

import lombok.Data;
import takenoko.Plateau;
import takenoko.irrigation.CoordIrrig;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.*;

/**
 * Une evolution est une action effectué sur la plateau
 * @param <T>
 */
@Data
public class Action<T> {
    private ActionType action;
    private List<T> argument;

    public Action() {
    }

    public Action(ActionType action, T... arguments) {
        this.action = action;
        this.argument = new ArrayList<T>(Arrays.asList(arguments));
    }

    public ActionType getAction() {
        return action;
    }

    public List<T> getArgument() {
        return argument;
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
                    if (action.getArgument().get(0).getClass().equals(CoordAxial.class) && action.getArgument().get(1).getClass().equals(Tuile.class)){
                        CoordAxial coordAxial = CoordAxial.class.cast(action.getArgument().get(0));
                        Tuile tuile = Tuile.class.cast(action.getArgument().get(1));

                        plateau.poserTuile(coordAxial,tuile);
                        return true;
                    }else return false;
                }else return false;
            case ADDIRRIG:
                if (action.getArgument().size() == 1){
                    if(action.getArgument().get(0).getClass().equals(CoordIrrig.class)){
                        CoordIrrig coordIrrig = CoordIrrig.class.cast(action.getArgument().get(0));

                        return plateau.addIrrigation(coordIrrig);
                    }else return false;
                }else return false;
            case MOOVEPANDA:
                if (action.getArgument().size() == 1){
                    if(action.getArgument().get(0).getClass().equals(CoordAxial.class)){
                        CoordAxial coordAxial = CoordAxial.class.cast(action.getArgument().get(0));

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
                "action=" + action +
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
