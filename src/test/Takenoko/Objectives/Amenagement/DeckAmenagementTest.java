package Takenoko.Objectives.Amenagement;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class DeckAmenagementTest {
    private DeckAmenagement deckAmenagement;

    @Before public void Amenagement(){
        deckAmenagement = new DeckAmenagement();
        deckAmenagement.init();
    }

    @Test public void goodInit(){
        assertEquals(9,deckAmenagement.size());
    }

    @Test public void getter(){
        assertEquals(deckAmenagement.getAmenagements().get(0),deckAmenagement.drawAmenagement());
    }

    @Test public void specialDraw(){
        int size = deckAmenagement.size();
        deckAmenagement.drawAmenagement(Amenagement.BASSIN);

        assertEquals(--size,deckAmenagement.size());
    }

    @Test public void specialDrawOver(){
        deckAmenagement.drawAmenagement(Amenagement.BASSIN);
        deckAmenagement.drawAmenagement(Amenagement.BASSIN);
        deckAmenagement.drawAmenagement(Amenagement.BASSIN);

        assertEquals(false,deckAmenagement.drawAmenagement(Amenagement.BASSIN));

    }

    @Test public void testGetSet(){
        assertEquals(3,deckAmenagement.getAmenagementSet().size());

    }
}
