package takenoko;

import org.junit.Test;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import static org.junit.Assert.*;

public class PlateauTest {

    @Test
    public void toString1() {
        Plateau p = new Plateau();
    }

    @Test
    public void testInitIrrigation(){
        Plateau p = new Plateau();
        p = p.plateau_depart();

        assertEquals(p.irrigationsList().size(),6);

        p.poserTuile(new CoordAxial(0,1),new Tuile(1,Couleur.ROSE));
        assertEquals(p.getTuileAtCoord(new CoordAxial(0,1)).getHaveWater(),true);
    }

    @Test
    public void testAddIrrigation(){
        Plateau p = new Plateau();
        p.poserTuile(new CoordAxial(0,1),new Tuile(1,Couleur.ROSE));

        assertEquals(p.getTuileAtCoord(new CoordAxial(0,1)).getHaveWater(),true);

        p.poserTuile(new CoordAxial(-1,2),new Tuile(2,Couleur.ROSE));

        assertEquals(p.getTuileAtCoord(new CoordAxial(-1,2)).getHaveWater(),false);

        p.addIrrigation(new CoordIrrig(-1,2, Orient.S));

        assertTrue(p.checkPlotWater(new CoordAxial(-1,2)));
        assertEquals(p.getTuileAtCoord(new CoordAxial(-1,2)).getHaveWater(),true);
    }

}