package Takenoko.Joueur.Strategie.StrategieJardinier;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieConcrete;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrategieJardinierBasiqueTest {

    @Test
    public void getStrategieJardinierLabel() {
    }

    @Test
    public void getJardinierMove() {
        Plateau p = new Plateau();
        StrategieJardinier strat = new StrategieJardinierBasique();
        Joueur j = new Joueur(0, new StrategieConcrete());

        p.putPlot(new Plot(new CoordAxial(1,-1)));
        p.putPlot(new Plot(new CoordAxial(1,0)));

        p.putPlot(new Plot(new CoordAxial(-1,0)));

        assertEquals(new CoordAxial(0,0), p.getPosJardinier());

        assertEquals(new CoordAxial(1,-1), strat.getJardinierMove(p, j));


        p.putPlot(new Plot(2,-1));
        p.putPlot(new Plot(2,0));
        p.getPlot(2,-1).setWater(true);
        p.getPlot(2,0).setWater(true);

        assertEquals(new CoordAxial(1,0), strat.getJardinierMove(p, j));
    }
}