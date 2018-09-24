package Takenoko.Plot;

import org.junit.Test;

import static org.junit.Assert.*;


public class CoordAxialTest {
    @Test
    public void TestEqual(){
        CoordAxial coord1 = new CoordAxial(1,1);
        CoordAxial coord2 = new CoordAxial(1,1);

        assertEquals(coord1 , coord2);
    }
}
