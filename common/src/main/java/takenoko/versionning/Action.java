package takenoko.versionning;

import lombok.Data;

import java.util.Objects;

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
