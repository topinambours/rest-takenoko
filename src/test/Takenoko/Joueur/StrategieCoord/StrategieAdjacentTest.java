package Takenoko.Joueur.StrategieCoord;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.StrategieIrrig.StrategieIrigBase;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrategieAdjacentTest{
    private Game game;
    private Joueur joueur;
    private Strategie strategie;

    @Before public void StrategieAdjacentTest(){
         game = new Game();
         strategie = new StrategieAdjacent();
         joueur = new Joueur(1,strategie,new StrategieIrigBase(game.getPlateau()));
    }

    @Test public void test(){
        Plot plot1 = new Plot(-1,0);
        Plot plot2 = new Plot(0,1);

        game.getPlateau().putPlot(plot1,plot1.getCoord());
        game.getPlateau().putPlot(plot2,plot2.getCoord());

        CoordAxial plotFinal = strategie.getCoord(game.getPlateau());

        assertEquals(new Plot(-1,1).getCoord(),plotFinal);
        

    }
}
