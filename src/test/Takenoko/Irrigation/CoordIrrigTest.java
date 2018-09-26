package Takenoko.Irrigation;

import Takenoko.Plot.CoordAxial;
import org.junit.Test;
import static org.junit.Assert.*;

import Takenoko.Irrigation.CoordIrrig;

public class CoordIrrigTest {

    @Test
    public void bordersTest() {
        var borders = new CoordIrrig(3, 3, Orient.N).borders();
        assertEquals(borders.get(0), new CoordAxial(3, 2));
        assertEquals(borders.get(1), new CoordAxial(3, 3));
    }

    @Test
    public void continuesTest() {
        var continues = new CoordIrrig(3, 3, Orient.N).continues();
        assertEquals(continues.get(0), new CoordIrrig(3, 3, Orient.W));
    }

    @Test
    public void joinTest() {
        var join = CoordIrrig.join(new CoordAxial(0, 3), new CoordAxial(1, 3));
        assertEquals(join, new CoordIrrig(1, 3, Orient.W));
    }
}
