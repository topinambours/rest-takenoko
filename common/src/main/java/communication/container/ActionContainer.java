package communication.container;


import takenoko.versionning.Action;

import java.util.List;

public class ActionContainer extends Container<Action> {
    public ActionContainer() {
    }

    public ActionContainer(Action single) {
        super(single);
    }

    public ActionContainer(List<Action> multipleElement) {
        super(multipleElement);
    }
}
