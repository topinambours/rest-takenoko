package Takenoko;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieRandom;
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

    @Test
    public void newputParcel() {
        var plat = new Plateau();
        var parc = new Parcel();
        var bot = new Joueur(1, new StrategieRandom());
        var coo = bot.putParcel(parc, plat);
        assertEquals(coo.getQ(), parc.getq());
        assertEquals(coo.getR(), parc.getr());
    }
}
