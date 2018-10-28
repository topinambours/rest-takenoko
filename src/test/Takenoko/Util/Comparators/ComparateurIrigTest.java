package takenoko.util.comparators;

import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.Plateau;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComparateurIrigTest {

    @Test
    public void compare() {
        Plateau p = new Plateau();

        // au voisinage de la tuile de départ (irriguée)
        Plot p1 = new Plot(new CoordAxial(0,-1));

        // placement non légal : pas d'irrigation
        Plot p2 = new Plot(new CoordAxial(5,0));

        p.putPlot(p1);
        p.putPlot(p2);

        ComparateurIrig cp = new ComparateurIrig(p);

        // pour toute les orientations, les arretes de la parcelle (0,-1) possède au moins une parcelle irriguée (0,-1)
        for (Orient orientation : Orient.values()) {
            assertEquals(1, cp.compare(new CoordIrrig(0, -1, orientation), new CoordIrrig(5, 0, Orient.N)));
            assertEquals(-1, cp.compare(new CoordIrrig(5, 0, orientation), new CoordIrrig(0, -1, Orient.N)));
        }

        // On ajoute l'irrigation à la tuile (5,0)
        p.addIrrigation( new CoordIrrig(5,0,Orient.W));

        // Egalité entre (5,0) et (0,-1)
        assertEquals(0, cp.compare(new CoordIrrig(5,0,Orient.N), new CoordIrrig(0,-1,Orient.N)));

        p.putPlot(new Plot(), new CoordAxial(0,-2));

        assertEquals(0, cp.compare(new CoordIrrig(5,0,Orient.N), new CoordIrrig(0,-1,Orient.N)));

        assertEquals(0, cp.compare(new CoordIrrig(0,-1,Orient.N), new CoordIrrig(5,0,Orient.N)));

        // Configuration inverse, la coordonnée (5,0,W) est adjacente à deux tuiles irriguées
        p.putPlot(new Plot(new CoordAxial(4,0)));

        assertEquals(-1, cp.compare(new CoordIrrig(0,-1,Orient.N), new CoordIrrig(5,0,Orient.W)));

    }
}