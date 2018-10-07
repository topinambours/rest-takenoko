package Takenoko.Irrigation;

import Takenoko.Plot.CoordAxial;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoordIrrigTest {

    @Test
    public void bordersTest() {
        var borders = new CoordIrrig(0, 0, Orient.N).borders();
        assertTrue(borders.contains(new CoordAxial(0, 0)));
        assertTrue(borders.contains(new CoordAxial(0, -1)));

        borders = new CoordIrrig(0, 0, Orient.W).borders();
        assertTrue(borders.contains(new CoordAxial(0, 0)));
        assertTrue(borders.contains(new CoordAxial(-1, 0)));

        borders = new CoordIrrig(0, 0, Orient.S).borders();
        assertTrue(borders.contains(new CoordAxial(0, 0)));
        assertTrue(borders.contains(new CoordAxial(-1, 1)));

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
