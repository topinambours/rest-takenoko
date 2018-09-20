package Takenoko;

import Takenoko.Deque.Deck;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;

    @BeforeClass public void test(){
        game = new Game();
    }

    @Test public void testDeck(){
        Deck deck = game.getDeck();
        assertTrue(true);
    }
}