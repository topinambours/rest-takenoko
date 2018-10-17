package Takenoko.Util.Comparators;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompPosJardinierTest {

    @Test
    public void compare() {
        Plateau p = new Plateau();

        p.putPlot(new Plot(new CoordAxial(1,-1)));
        p.putPlot(new Plot(new CoordAxial(1,0)));

        p.putPlot(new Plot(new CoordAxial(-1,0)));

        assertEquals(new CoordAxial(0,0), p.getPosJardinier());

        CompPosJardinier cp = new CompPosJardinier(p);

        assertTrue(cp.compare(new CoordAxial(1,0), new CoordAxial(-1,0)) > 0);
        p.putPlot(new Plot(2,-1));
        p.putPlot(new Plot(2,0));
        p.getPlot(2,-1).setWater(true);

        assertTrue(cp.compare(new CoordAxial(1,0), new CoordAxial(1,-1)) == 0);
        assertTrue(cp.compare(new CoordAxial(1,0), new CoordAxial(2,-1)) == 0);
        assertTrue(cp.compare(new CoordAxial(1,-1), new CoordAxial(2,-1)) == 0);
        p.getPlot(2,0).setWater(true);

        assertTrue(cp.compare(new CoordAxial(1,0), new CoordAxial(2,0)) > 0);
        assertTrue(cp.compare(new CoordAxial(2,0), new CoordAxial(1,0)) < 0);
    }
}