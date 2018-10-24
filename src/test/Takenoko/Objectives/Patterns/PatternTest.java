package takenoko.objectives.patterns;

import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import takenoko.properties.Couleur;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PatternTest {

    @Test
    public void check() {
        Plateau plateau = new Plateau();
        PatternTile tile1 = new PatternTile(new CoordCube(0, 0), Couleur.VERT);
        PatternTile tile2 = new PatternTile(new CoordCube(0, 1), Couleur.JAUNE);
        List<PatternTile> list = new ArrayList<>();
        list.add(tile1);
        list.add(tile2);
        Pattern myPattern = new Pattern(list);

        plateau.putPlot(new Plot(0, 0, Couleur.VERT), 0, 0);
        plateau.putPlot(new Plot(0, 1, Couleur.JAUNE), 0, 1);

        assertEquals(true, myPattern.check(plateau, new CoordAxial(0, 0)));
    }

    @Test
    public void checkAllRotateTest() {
        Plateau plateau = new Plateau();
        PatternTile tile1 = new PatternTile(new CoordCube(0, 0), Couleur.VERT);
        PatternTile tile2 = new PatternTile(new CoordCube(0, 1), Couleur.JAUNE);
        List<PatternTile> list = new ArrayList<>();
        list.add(tile1);
        list.add(tile2);
        Pattern myPattern = new Pattern(list);

        plateau.putPlot(new Plot(0, 0, Couleur.ROSE), 0, 0);
        plateau.putPlot(new Plot(0, 1, Couleur.ROSE), 0, 1);
        plateau.putPlot(new Plot(1, 1, Couleur.VERT), 1, 1);
        plateau.putPlot(new Plot(1, 0, Couleur.JAUNE), 1, 0);

        assertEquals(false, myPattern.checkAllRotate(plateau));

        plateau.putIrrigation(new CoordIrrig(1,0, Orient.S));
        plateau.putIrrigation(new CoordIrrig(1,1, Orient.S));

        assertEquals(true, myPattern.checkAllRotate(plateau));
    }
}
