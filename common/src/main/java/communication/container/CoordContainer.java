package communication.container;

import takenoko.tuile.CoordAxial;

import java.util.List;

public class CoordContainer extends Container<CoordAxial> {

    public CoordContainer(){
        super();
    }

    public CoordContainer(CoordAxial single) {
        super(single);
    }

    public CoordContainer(List<CoordAxial> multiple) {
        super(multiple);
    }
}
