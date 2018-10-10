package Takenoko;

import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameBambouTest {
    private Game game;

    @Before public void GameBambouTest(){
        game = new Game();
    }

    @Test public void testPush(){
        CoordAxial coordAxial = new CoordAxial(1,0);
        Plot current = new Plot(coordAxial);

        //Irrigué par la parcelle de départ
        game.getPlateau().putPlot(current);
        game.grow();

        assertEquals(1,game.getPlateau().getPlot(coordAxial).getBambou());

        game.grow();
        game.getPlateau().putPlot(new Plot(new CoordAxial(2,0)));
        assertEquals(0,game.getPlateau().getPlot(new CoordAxial(2,0)).getBambou());
        assertEquals(2,game.getPlateau().getPlot(coordAxial).getBambou());

        //avec de l'eau
        game.getPlateau().getPlot(new CoordAxial(2,0)).setWater(true);
        game.grow();

        assertEquals(1,game.getPlateau().getPlot(new CoordAxial(2,0)).getBambou());


    }
}
