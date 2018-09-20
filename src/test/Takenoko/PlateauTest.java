package Takenoko;

import Takenoko.Parcel.CoordAxial;
import Takenoko.Parcel.Parcel;
import Takenoko.Plateau;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlateauTest {

    @Test
    public void getputParcel() {
        var plat = new Plateau();
        var parc = new Parcel(3, 5);
        var coo = new CoordAxial(3, 5);
        plat.putParcel(parc, coo);
        assertEquals(parc, plat.getParcel(3, 5));
    }
}
