package Takenoko.Objectives.Patterns;

import Takenoko.Objectives.Patterns.CoordCube;
import Takenoko.Objectives.Patterns.Pattern;
import Takenoko.Objectives.Patterns.PatternTile;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Couleur;
import Takenoko.Plot.Plot;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

        assertEquals(true, myPattern.checkAllRotate(plateau));
    }
}
