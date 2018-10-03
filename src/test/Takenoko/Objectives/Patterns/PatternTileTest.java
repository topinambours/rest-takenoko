package Takenoko.Objectives.Patterns;

import Takenoko.Plot.CoordAxial;
import Takenoko.Properties.Couleur;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PatternTileTest {

    @Test
    public void rotate60Test() {
        PatternTile myTile = new PatternTile(new CoordCube(1, 1), Couleur.JAUNE);
        PatternTile rotTile = new PatternTile(new CoordCube(2, -1), Couleur.JAUNE);

        assertEquals(rotTile, myTile.rotate60());
    }

    @Test
    public void toConcretePositionTest() {
        CoordAxial myCoord = new CoordAxial(5, -3);
        CoordAxial finalCoord = new CoordAxial(6, -2);
        PatternTile myTile = new PatternTile(new CoordCube(1, 1), Couleur.ROSE);

        assertEquals(finalCoord, myCoord.add(new CoordAxial(1, 1)));
        CoordCube cubeTest = new CoordCube(1, 1);
        assertEquals(new CoordAxial(1, 1), cubeTest.toAxial());
        assertEquals(finalCoord, myCoord.add(cubeTest.toAxial()));
        assertEquals(finalCoord, myTile.toConcretePosition(myCoord));
    }
}
