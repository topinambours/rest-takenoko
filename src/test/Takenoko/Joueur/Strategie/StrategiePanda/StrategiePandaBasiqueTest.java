package Takenoko.Joueur.Strategie.StrategiePanda;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieConcrete;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
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