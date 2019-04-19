package takenoko;

import org.junit.Test;
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
    }


}