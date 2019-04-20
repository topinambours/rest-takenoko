package communication.container;

import takenoko.irrigation.CoordIrrig;

import java.util.ArrayList;
import java.util.HashSet;
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

    public CoordIrrigContainer(HashSet<CoordIrrig> multipleElement){
        super(new ArrayList<>(multipleElement));
    }
}
