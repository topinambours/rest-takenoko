package Takenoko.Deque;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObjectivesPatternDeckTest {

    @Test
    public void importFromJsonFile() {
        ObjectivesDeck deck = new ObjectivesPatternDeck();
        assertEquals(15, deck.getSize());
    }

    @Test
    public void popTest() {
        ObjectivesDeck deck = new ObjectivesPatternDeck();

        assertEquals(15, deck.getSize());

        for (int i = 0; i < 15; i++){
            deck.pop();
        }
        assertTrue(deck.isEmpty());
    }
}