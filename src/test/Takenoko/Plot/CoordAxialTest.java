package Takenoko.Plot;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Irrigation.Orient;
import org.junit.Test;

import static org.junit.Assert.*;


public class CoordAxialTest {
    @Test
    public void testEqual(){
        CoordAxial coord1 = new CoordAxial(1,1);
        CoordAxial coord2 = new CoordAxial(1,1);

        assertEquals(coord1 , coord2);
    }

    @Test
    public void testNeighborCoords() {
        var myCoo = new CoordAxial(0, 0);
        var toTest = myCoo.getNeighborCoords();

        assertEquals(toTest.get(0), new CoordAxial(1, -1));
        assertEquals(toTest.get(1), new CoordAxial(1, 0));
        assertEquals(toTest.get(2), new CoordAxial(0, -1));
        assertEquals(toTest.get(3), new CoordAxial(0, 1));
        assertEquals(toTest.get(4), new CoordAxial(-1, 0));
        assertEquals(toTest.get(5), new CoordAxial(-1, 1));
    }

    @Test
    public void testBorderCoords() {
        var myCoo = new CoordAxial(0, 0);
        var toTest = myCoo.getBorderCoords();

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
}
