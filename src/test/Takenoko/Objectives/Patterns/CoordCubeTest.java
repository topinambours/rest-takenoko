package Takenoko.Objectives.Patterns;

import Takenoko.Plot.CoordAxial;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CoordCubeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullTest() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        CoordCube coord = new CoordCube(1, 1, 1);
    }

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
