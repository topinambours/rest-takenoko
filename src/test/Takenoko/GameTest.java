package Takenoko;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;


    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test public void testGame(){
        game.play();
        assertTrue(game.end());
    }
}