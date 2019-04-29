package takenoko.versionning;

/**
 * Une evolution est une action effectué sur la plateau
 * @param <T>
 */
public class Action<T> {
    private ActionType action;
    private T argument;

    public Action(ActionType action, T argument) {
        this.action = action;
        this.argument = argument;
    }

    public ActionType getAction() {
        return action;
    }

    public T getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return "Evolution{" +
                "action=" + action +
                ", argument=" + argument +
                '}';
    }
}
