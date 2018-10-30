package takenoko.util.comparators;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Plateau;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompPosJardinierTest {
    @Autowired
    @Qualifier("plateauTakenoko")
    Plateau p;

    @Test
    public void compare() {


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