package Takenoko.Deque;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDeckTest {
    private Deck deck;

    @Before public void InitDeckTest(){
        deck = new Deck();
    }

    @Test public void test(){
        assertTrue(deck.init());
    }
}
