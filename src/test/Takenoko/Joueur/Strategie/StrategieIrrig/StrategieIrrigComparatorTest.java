package takenoko.joueur.strategie.StrategieIrrig;

import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StrategieIrrigComparatorTest {

    private Plateau p;
    private StrategieIrrigComparator cp;

    @Before
    public void setUp() throws Exception {
        p = new Plateau();
        cp = new StrategieIrrigComparator(p);
    }

    @Test
    public void getIrrig() {
        assertFalse(cp.getIrrig(p).isPresent());
        // Cette parcelle est iriguée par construction
        p.putPlot(new Plot(new CoordAxial(0,-1)));
        assertFalse(cp.getIrrig(p).isPresent());
        p.putIrrigation(new CoordIrrig(1,-1, Orient.W));

        // Une seule position d'irrigation possible
        p.putPlot(new Plot(new CoordAxial(1,-2)));
        assertEquals(new CoordIrrig(1,-2,Orient.S), cp.getIrrig(p).get());
        p.putIrrigation(new CoordIrrig(1,-2, Orient.S));
        assertFalse(cp.getIrrig(p).isPresent());

        p.putPlot(new Plot(new CoordAxial(1,-1)));

        assertEquals(new CoordIrrig(1,-1,Orient.N), cp.getIrrig(p).get());


    }

    @Test
    public void getStrategieLabel() {
        assertEquals("Stratégie irrigation optimal", cp.getStrategieIrrigLabel());
    }
}