package Takenoko.Util.Comparators;

import Takenoko.Plot.CoordAxial;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComparateurCoordAxialTest {

    @Test
    public void compare() {
        ComparateurCoordAxial cp = new ComparateurCoordAxial();
        CoordAxial c1 = new CoordAxial(0,0);
        CoordAxial c2 = new CoordAxial(1,0);

        assertTrue(cp.compare(c1,c2) < 0);
        assertFalse(cp.compare(c2,c1) < 0);

        c2 = new CoordAxial(0,1);
        assertTrue(cp.compare(c1,c2) < 0);
        assertFalse(cp.compare(c2,c1) < 0);

        c2 = new CoordAxial(5,1);
        assertTrue(cp.compare(c1,c2) < 0);
        assertFalse(cp.compare(c2,c1) < 0);

        c2 = new CoordAxial(0,0);
        assertEquals(0,cp.compare(c1,c2));
    }
}