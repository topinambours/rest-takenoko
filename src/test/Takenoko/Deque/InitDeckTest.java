package Takenoko.Deque;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InitDeckTest {
    private Deck deck;

    @Before public void InitDeckTest(){
        deck = new Deck();
    }

    @Test public void test(){
        assertTrue(deck.init());
    }
}
