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
        Plateau plat = new Plateau();
        Plot parc = new Plot(3, 5);
        CoordAxial coo = new CoordAxial(3, 5);
        plat.putPlot(parc, coo);
        assertEquals(parc, plat.getPlot(3, 5));
    }

    @Test
    public void newputPlot() {
        Plateau plat = new Plateau();
        Plot parc = new Plot();
        Joueur bot = new Joueur(1, new StrategieRandom());
        CoordAxial coo = bot.putPlot(parc, plat);
        assertEquals(coo.getQ(), parc.getq());
        assertEquals(coo.getR(), parc.getr());
    }

    @Test public void irrigationInit(){
        Plateau plateau = new Plateau();
        assertEquals(6,plateau.getIrrigations().size());
    }

}
