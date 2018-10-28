package takenoko.joueur.strategie.StrategieJardinier;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieConcrete;
import takenoko.Plateau;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategieJardinierBasiqueTest {

    @Autowired
    @Qualifier("plateauTakenoko")
    Plateau p;

    @Test
    public void getStrategieJardinierLabel() {
    }

    @Test
    public void getJardinierMove() {
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