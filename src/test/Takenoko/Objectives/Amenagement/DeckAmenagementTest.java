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
}
