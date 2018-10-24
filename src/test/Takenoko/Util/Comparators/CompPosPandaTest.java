package takenoko.util.comparators;

import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CompPosPandaTest {

    @Test
    public void compare() {
        Plateau p = new Plateau();

        p.putPlot(new Plot(1,0));

        CompPosPanda cp = new CompPosPanda(p);

        assertTrue(cp.compare(new CoordAxial(0,0), new CoordAxial(1,0)) == 0);

        p.getPlot(1,0).setWater(true);
        p.getPlot(1,0).pousserBambou();

        assertTrue(cp.compare(new CoordAxial(0,0), new CoordAxial(1,0)) > 0);
    }
}