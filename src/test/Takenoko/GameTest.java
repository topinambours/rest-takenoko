package Takenoko;

import Takenoko.Util.Console;
import Takenoko.Util.Exceptions.EmptyDeckException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameTest {
    private Game game;


    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test public void testGame() throws EmptyDeckException {
        Console.Log.init();
        game.play();
        assertTrue(game.end());
    }
}