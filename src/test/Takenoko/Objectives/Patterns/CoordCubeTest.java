package Takenoko.Objectives.Patterns;

import Takenoko.Plot.CoordAxial;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CoordCubeTest {
    @Test
    public void toAxialTest() {
        CoordCube myCube = new CoordCube(1, 1, -2);
        assertEquals(new CoordAxial(1, 1), myCube.toAxial());
        CoordCube cubeTest = new CoordCube(1, 1);
        assertEquals(new CoordAxial(1, 1), cubeTest.toAxial());
    }

    @Test
    public void rotate60Test() {
        CoordCube myCube = new CoordCube(1, 1, -2);
        CoordCube rotCube = new CoordCube(2, -1, -1);
        assertEquals(rotCube, myCube.rotate60());
    }
}
