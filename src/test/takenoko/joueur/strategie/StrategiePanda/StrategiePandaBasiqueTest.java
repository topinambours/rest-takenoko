package takenoko.joueur.strategie.StrategiePanda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieConcrete;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategiePandaBasiqueTest {
    @Autowired
    @Qualifier("plateauTakenoko")
    Plateau p;

    @Test
    public void getPandaMove() {
        Joueur j = new Joueur(0, new StrategieConcrete());
        p.putPlot(new Plot(1,0));
        p.putPlot(new Plot(2,0));
        StrategiePanda strat = new StrategiePandaBasique();

        p.getPlot(1,0).pousserBambou();
        assertEquals(new CoordAxial(1,0), strat.getPandaMove(p, j));
        p.getPlot(1,0).removeAllBambou();

        p.getPlot(2,0).setWater(true);
        p.getPlot(2,0).pousserBambou();

        assertEquals(new CoordAxial(2,0), strat.getPandaMove(p, j));
    }
}