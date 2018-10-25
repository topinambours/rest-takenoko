package takenoko.deck;

import takenoko.util.exceptions.EmptyDeckException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DeckTest {

    private DeckTestInteger deck;

    private class DeckTestInteger extends Deck {
        public DeckTestInteger(List<Integer> list){
            super(list);
        }

        public DeckTestInteger(){
            super();
        }
    }


    @Before
    public void setUp() {
        deck = new DeckTestInteger();
    }

    @Test
    public void listConstructorTest(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1500; i++){
            list.add(i);
        }
        deck = new DeckTestInteger(list);

        assertEquals(1500, deck.size());
    }

    @Test
    public void listConstructorShuffleBigSizeTest() throws EmptyDeckException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 15000; i++){
            list.add(i);
        }
        deck = new DeckTestInteger(list);

        assertEquals(15000, deck.size());

        int atSamePlace = 0;
        for (int i = 0; i < 15000; i++){
            if (java.util.Optional.of(i).get().equals(deck.draw())){
                atSamePlace += 1;
            }
        }
        assertEquals(0.01, atSamePlace / 15000.0, 0.01);

    }

    /**
     * On souhaite garantir un maximum de 25% d'élément à la même place pour une pioche de 27 elements.
     * @throws EmptyDeckException
     */
    @Test
    public void listConstructorShuffleTakenokoSizeTest() throws EmptyDeckException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 27; i++){
            list.add(i);
        }

        for (int j =0; j < 1000; j++) {
            deck = new DeckTestInteger(list);
            assertEquals(27, deck.size());
            int atSamePlace = 0;
            for (int i = 0; i < 27; i++) {
                if (java.util.Optional.of(i).get().equals(deck.draw())) {
                    atSamePlace += 1;
                }
            }
            assertEquals(0.125, atSamePlace / 27.0, 0.125);
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

        deck.insertBottom(1);
        deck.insertBottom(4);

        draws = deck.draw(1);

        assertEquals(1, draws.size());
        assertTrue(draws.contains(1));

        assertEquals(1, deck.size());

    }

    @Test(expected=EmptyDeckException.class)
    public void drawEmptyDeck() throws EmptyDeckException {
        deck.draw();
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
        deck = new DeckTestInteger(list);

        assertEquals(1500, deck.size());
    }

    @Test
    public void isEmpty() {
        assertEquals(0, deck.size());
        assertTrue(deck.isEmpty());
    }
}