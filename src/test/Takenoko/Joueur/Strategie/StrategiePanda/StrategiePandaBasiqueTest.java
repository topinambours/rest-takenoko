package takenoko.joueur.strategie.StrategiePanda;

import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieConcrete;
import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StrategiePandaBasiqueTest {

    @Test
    public void getPandaMove() {
        Plateau p = new Plateau();
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