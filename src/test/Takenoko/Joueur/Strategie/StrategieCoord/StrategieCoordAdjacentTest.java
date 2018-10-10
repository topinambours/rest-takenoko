package Takenoko.Joueur.Strategie.StrategieCoord;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StrategieCoordAdjacentTest {
    private Game game;
    private Joueur joueur;
    private StrategieCoord strategieCoord;

    @Before public void StrategieAdjacentTest(){
         game = new Game();
         strategieCoord = new StrategieCoordAdjacent();
         joueur = new Joueur(1, strategieCoord,new StrategieIrrigBase(game.getPlateau()));
    }

    @Test public void test(){
        Plot plot1 = new Plot(-1,0);
        Plot plot2 = new Plot(0,1);

        game.getPlateau().putPlot(plot1,plot1.getCoord());
        game.getPlateau().putPlot(plot2,plot2.getCoord());

        CoordAxial plotFinal = strategieCoord.getCoord(game.getPlateau());

        assertEquals(new Plot(-1,1).getCoord(),plotFinal);
        

    }
}
