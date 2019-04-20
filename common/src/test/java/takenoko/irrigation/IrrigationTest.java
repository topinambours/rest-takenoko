package takenoko.irrigation;

import org.junit.Test;
import takenoko.tuile.CoordAxial;


import java.util.List;

import static org.junit.Assert.*;

public class IrrigationTest {

    @Test
    public void bordersTest() {
        List<CoordAxial> borders = new CoordIrrig(0, 0, Orient.N).borders();
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
        List<CoordIrrig> continues = new CoordIrrig(3, 3, Orient.N).continues();
        assertEquals(new CoordIrrig(3, 3, Orient.W), continues.get(0));
        assertEquals(new CoordIrrig(3, 2, Orient.S), continues.get(1));
        assertEquals(new CoordIrrig(4, 2, Orient.W), continues.get(2));
        assertEquals(new CoordIrrig(4, 2, Orient.S), continues.get(3));


        continues = new CoordIrrig(3, 3, Orient.W).continues();
        assertEquals(new CoordIrrig(3, 3, Orient.S), continues.get(0));
        assertEquals(new CoordIrrig(2, 4, Orient.N), continues.get(1));
        assertEquals(new CoordIrrig(3, 2, Orient.S), continues.get(2));
        assertEquals(new CoordIrrig(3, 3, Orient.N), continues.get(3));

        continues = new CoordIrrig(3, 3, Orient.S).continues();
        assertEquals(new CoordIrrig(3, 3, Orient.W), continues.get(0));
        assertEquals(new CoordIrrig(2, 4, Orient.N), continues.get(1));
        assertEquals(new CoordIrrig(3, 4, Orient.W), continues.get(2));
        assertEquals(new CoordIrrig(3, 4, Orient.N), continues.get(3));
    }

    @Test
    public void joinTest() {
        CoordIrrig join = CoordIrrig.join(new CoordAxial(0, 3), new CoordAxial(1, 3));
        assertEquals(join, new CoordIrrig(1, 3, Orient.W));
    }

    @Test
    public void noJoinTest() {
        CoordIrrig join = CoordIrrig.join(new CoordAxial(0, 0), new CoordAxial(1, 3));
        assertNull(join);
    }

    @Test
    public void hashCodeTest(){
        assertNotEquals(new CoordIrrig(1,2, Orient.N).hashCode(), new CoordIrrig(1,2, Orient.W).hashCode());
        assertNotEquals(null, new CoordIrrig(0,0, Orient.W).hashCode());
        assertNotEquals(new CoordIrrig(0,0, Orient.N).hashCode(), new CoordIrrig(0,0, Orient.W).hashCode());
        assertNotEquals(new CoordIrrig(1,2, Orient.N).hashCode(), new CoordIrrig(0,2, Orient.N).hashCode());
    }

    @Test
    public void toStringTest(){
        assertEquals("((1,2), W)", new CoordIrrig(1,2,Orient.W).toString());
    }
}
