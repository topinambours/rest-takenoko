package Takenoko.Deque;

import Takenoko.Plot.Plot;
import Takenoko.Util.Exceptions.EmptyDeckException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    private final int DECK_SIZE = 100;
    private Deck dtest;

    @Before
    public void setUp() throws Exception {
        dtest = new Deck();

        for (int i = 0; i <= DECK_SIZE; i++){
            dtest.addFirst(new Plot(i,0));
        }
    }

    @Test (expected = EmptyDeckException.class)
    public void get_pop_First() throws EmptyDeckException {
        for (int i = DECK_SIZE; i >= 0; i--){
            assertEquals(i, dtest.getFirst().getq());
            assertEquals(i, dtest.popFirst().getq());
        }
        dtest.popFirst();
    }

    @Test (expected = EmptyDeckException.class)
    public void get_FirstException() throws EmptyDeckException {
        dtest = new Deck();
        dtest.getFirst();
    }

    @Test  (expected = EmptyDeckException.class)
    public void get_pop_Last() throws EmptyDeckException {
        for (int i = 0; i <= DECK_SIZE; i++){
            assertEquals(i, dtest.getLast().getq());
            assertEquals(i, dtest.popLast().getq());
        }
        dtest.popLast();
    }

    @Test  (expected = EmptyDeckException.class)
    public void get_LastException() throws EmptyDeckException {
        dtest = new Deck();
        dtest.popLast();
    }

    @Test
    public void addFirst() throws EmptyDeckException {
        for (int i = DECK_SIZE + 1; i <= DECK_SIZE * 2; i++){
            dtest.addFirst(new Plot(i, 1));
        }

        for (int i = DECK_SIZE * 2; i >= 0; i--){
            assertEquals(i, dtest.getFirst().getq());
            if (i > 100){
                assertEquals(1, dtest.getFirst().getr());
            }
            assertEquals(i, dtest.popFirst().getq());
        }
    }

    @Test
    public void addLast() throws EmptyDeckException {
        for (int i = -1; i >= DECK_SIZE * -1 ; i--){
            dtest.addLast(new Plot(i, 1));
        }

        for (int i = DECK_SIZE * -1; i <= DECK_SIZE; i++){
            assertEquals(i, dtest.getLast().getq());

            if (i < 0){
                assertEquals(1, dtest.getLast().getr());
            }

            assertEquals(i, dtest.popLast().getq());
        }
    }

    @Test
    public void getSize() throws EmptyDeckException {
        assertEquals(101, dtest.getSize());
        for(int i = 0 ; i<50; i++){
            dtest.popLast();
        }
        assertEquals(51, dtest.getSize());
    }

    @Test
    public void isEmpty(){
        assertFalse(dtest.isEmpty());
        Deck d = new Deck();
        assertTrue(d.isEmpty());
    }
}