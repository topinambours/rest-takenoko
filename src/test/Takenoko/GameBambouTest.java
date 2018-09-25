package Takenoko;

import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBambouTest {
    private Game game;

    @Before public void GameBambouTest(){
        game = new Game();
    }

    @Test public void testPush(){
        CoordAxial coordAxial = new CoordAxial(1,0);
        Plot current = new Plot(coordAxial);

        game.getPlateau().putPlot(current);

        game.grow();

        assertEquals(1,game.getPlateau().getPlot(coordAxial).getBambou());


    }
}
