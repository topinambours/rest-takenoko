package communication.container;

import takenoko.objectives.patterns.CoordCube;

import java.util.List;

public class CoordCudeContainer extends Container<CoordCube> {
    public CoordCudeContainer() {
        super();
    }

    public CoordCudeContainer(CoordCube simple) {
        super(simple);
    }

    public CoordCudeContainer(List<CoordCube> multipleElement) {
        super(multipleElement);
    }
}
