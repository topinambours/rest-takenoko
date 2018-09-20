package Takenoko.Deque;

import Takenoko.Parcel.Parcel;
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
            dtest.addFirst(new Parcel(i,0));
        }
    }

    @Test
    public void get_pop_First() {
        for (int i = DECK_SIZE; i >= 0; i--){
            assertEquals(i, dtest.getFirst().getq());
            assertEquals(i, dtest.popFirst().getq());
        }
    }

    @Test
    public void get_pop_Last() {
        for (int i = 0; i <= DECK_SIZE; i++){
            assertEquals(i, dtest.getLast().getq());
            assertEquals(i, dtest.popLast().getq());
        }
    }

    @Test
    public void addFirst() {
        for (int i = DECK_SIZE + 1; i <= DECK_SIZE * 2; i++){
            dtest.addFirst(new Parcel(i, 1));
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
    public void addLast() {
        for (int i = -1; i >= DECK_SIZE * -1 ; i--){
            dtest.addLast(new Parcel(i, 1));
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
    public void getSize() {
        //@ToDo
    }
}