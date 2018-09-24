package Takenoko;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieRandom;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlateauTest {

    @Test
    public void getputPlot() {
        var plat = new Plateau();
        var parc = new Plot(3, 5);
        var coo = new CoordAxial(3, 5);
        plat.putPlot(parc, coo);
        assertEquals(parc, plat.getPlot(3, 5));
    }

    @Test
    public void newputPlot() {
        var plat = new Plateau();
        var parc = new Plot();
        var bot = new Joueur(1, new StrategieRandom());
        var coo = bot.putPlot(parc, plat);
        assertEquals(coo.getQ(), parc.getq());
        assertEquals(coo.getR(), parc.getr());
    }
}
