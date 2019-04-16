package communication.container;

import lombok.Data;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

@Data
public class PoseTuileContainer {

    private CoordAxial pos;

    private Tuile tuile;

    public PoseTuileContainer(CoordAxial pos, Tuile tuile){
        this.pos = pos;
        this.tuile = tuile;
    }

    public CoordAxial getPos() {
        return pos;
    }

    public void setPos(CoordAxial pos) {
        this.pos = pos;
    }

    public Tuile getTuile() {
        return tuile;
    }

    public void setTuile(Tuile tuile) {
        this.tuile = tuile;
    }

}
