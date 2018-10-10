package Takenoko.Deque;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InitDeckTest {
    private Deck deck;

    @Before public void InitDeckTest(){
        deck = new Deck();
    }

    @Test public void test(){
        assertTrue(deck.init());
    }
}
