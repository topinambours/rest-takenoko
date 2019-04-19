package takenoko;

import org.junit.Test;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

public class PlateauTest {

    @Test
    public void toString1() {
        Plateau p = new Plateau();
    }

    @Test
    public void testInitIrrigation(){
        Plateau p = new Plateau();
        p = p.plateau_depart();

        System.out.println(p.irrigationsList().size());
    }
   
}