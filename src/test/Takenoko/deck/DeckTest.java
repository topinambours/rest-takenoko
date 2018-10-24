package takenoko.deck;

import takenoko.util.exceptions.EmptyDeckException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DeckTest {

    private Deck<Integer> deck;

    @Before
    public void setUp() {
        deck = new Deck<Integer>();
    }

    @Test
    public void listConstructorTest(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1500; i++){
            list.add(i);
        }
        deck = new Deck<Integer>(list);

        assertEquals(1500, deck.size());
    }

    @Test
    public void listConstructorShuffleTest() throws EmptyDeckException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 15000; i++){
            list.add(i);
        }
        deck = new Deck<Integer>(list);

        assertEquals(15000, deck.size());

        for (int i = 0; i < 1500; i++){
            assertNotEquals(java.util.Optional.of(i).get(), deck.draw());
        }

    }

    @Test
    public void draw() throws EmptyDeckException {
        assertTrue(deck.isEmpty());

        deck.insertBottom(1);
        deck.insertBottom(4);
        assertEquals(2, deck.size());
        assertEquals(java.util.Optional.of(1).get(), deck.draw());
        assertEquals(1, deck.size());
        assertEquals(java.util.Optional.of(4).get(), deck.draw());
        assertTrue(deck.isEmpty());
    }

    @Test
    public void draw1() throws EmptyDeckException {
        deck.insertBottom(1);
        deck.insertBottom(4);
        assertEquals(2, deck.size());

        List<Integer> draws = deck.draw(2);

        System.out.println(deck.size());

        assertTrue(deck.isEmpty());

        assertTrue(draws.contains(1));
        assertTrue(draws.contains(4));

        assertTrue(deck.isEmpty());

        deck.insertBottom(1);
        deck.insertBottom(4);
        assertEquals(2, deck.size());

        draws = deck.draw(5);

        assertTrue(deck.isEmpty());
        assertEquals(2, draws.size());

        assertTrue(draws.contains(1));
        assertTrue(draws.contains(4));

    }

    @Test
    public void insertBottom() throws EmptyDeckException {
        deck.insertBottom(54);
        assertEquals(1, deck.size());
        assertEquals(java.util.Optional.of(54).get(), deck.draw());
    }

    @Test
    public void size() {
        assertEquals(0, deck.size());
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1500; i++){
            list.add(i);
        }
        deck = new Deck<Integer>(list);

        assertEquals(1500, deck.size());
    }

    @Test
    public void isEmpty() {
        assertEquals(0, deck.size());
        assertTrue(deck.isEmpty());
    }
}