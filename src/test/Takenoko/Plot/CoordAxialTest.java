package takenoko.Plot;

import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CoordAxialTest {
    @Test
    public void testEqual(){
        CoordAxial coord1 = new CoordAxial(1,1);
        CoordAxial coord2 = new CoordAxial(1,1);

        assertEquals(coord1 , coord2);
    }

    @Test
    public void testNeighborCoords() {
        CoordAxial myCoo = new CoordAxial(0, 0);
        List<CoordAxial> toTest = myCoo.getNeighborCoords();

        assertEquals(toTest.get(0), new CoordAxial(1, -1));
        assertEquals(toTest.get(1), new CoordAxial(1, 0));
        assertEquals(toTest.get(2), new CoordAxial(0, -1));
        assertEquals(toTest.get(3), new CoordAxial(0, 1));
        assertEquals(toTest.get(4), new CoordAxial(-1, 0));
        assertEquals(toTest.get(5), new CoordAxial(-1, 1));
    }

    @Test
    public void testBorderCoords() {
        CoordAxial myCoo = new CoordAxial(0, 0);
        List<CoordIrrig> toTest = myCoo.getBorderCoords();

        assertEquals(toTest.get(0), new CoordIrrig(1, -1, Orient.S));
        assertEquals(toTest.get(1), new CoordIrrig(1, 0, Orient.W));
        assertEquals(toTest.get(2), new CoordIrrig(0, 0, Orient.N));
        assertEquals(toTest.get(3), new CoordIrrig(0, 1, Orient.N));
        assertEquals(toTest.get(4), new CoordIrrig(0, 0, Orient.W));
        assertEquals(toTest.get(5), new CoordIrrig(0, 0, Orient.S));
    }

    @Test
    public void testAdd() {
        CoordAxial myCoo1 = new CoordAxial(2, 2);
        CoordAxial myCoo2 = new CoordAxial(3, -1);
        CoordAxial finalCoo = new CoordAxial(5, 1);

        assertEquals(finalCoo, myCoo1.add(myCoo2));
        assertEquals(finalCoo, myCoo2.add(myCoo1));
    }

    @Test
    public void isInLine() {

        CoordAxial orig = new CoordAxial(0,0);

        for (CoordAxial c : orig.getNeighborCoords()){
            assertTrue(c.isInLine(orig));
        }

        assertFalse(orig.isInLine(new CoordAxial(1,-2)));
        assertFalse(orig.isInLine(new CoordAxial(2,-1)));
        assertFalse(orig.isInLine(new CoordAxial(-1,-1)));
        assertFalse(orig.isInLine(new CoordAxial(-2,1)));
        assertFalse(orig.isInLine(new CoordAxial(-1,2)));
        assertFalse(orig.isInLine(new CoordAxial(1,1)));

        assertFalse(orig.isInLine(orig));
    }
}
