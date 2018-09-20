package Takenoko;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;

    @BeforeClass public void GameTestInit(){
        game = new Game();
    }

    @Test public void testGame(){
        game.play();
        assertTrue(game.end());
    }
}