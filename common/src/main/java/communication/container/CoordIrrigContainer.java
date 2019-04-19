package communication.container;

import takenoko.irrigation.CoordIrrig;

import java.util.List;

public class CoordIrrigContainer extends Container<CoordIrrig> {
    public CoordIrrigContainer() {
        super();
    }

    public CoordIrrigContainer(CoordIrrig single) {
        super(single);
    }

    public CoordIrrigContainer(List<CoordIrrig> multipleElement) {
        super(multipleElement);
    }
}
