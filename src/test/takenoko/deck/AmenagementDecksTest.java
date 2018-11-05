package takenoko.deck;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.util.exceptions.EmptyDeckException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(AmenagementDecks.class)
public class AmenagementDecksTest {

    @Autowired
    AmenagementDecks defaultTakenokoAmDeck;

    @Test
    public void defaultTakenokoAmDeck() {
        assertEquals(3, defaultTakenokoAmDeck.size(Amenagement.BASSIN));
        assertEquals(3, defaultTakenokoAmDeck.size(Amenagement.ENGRAIS));
        assertEquals(3, defaultTakenokoAmDeck.size(Amenagement.ENCLOS));
    }

    @Test
    public void draw() throws EmptyDeckException {
        assertEquals(3, defaultTakenokoAmDeck.size(Amenagement.BASSIN));
        assertEquals(Amenagement.BASSIN, defaultTakenokoAmDeck.draw(Amenagement.BASSIN));
        assertEquals(2, defaultTakenokoAmDeck.size(Amenagement.BASSIN));

        assertEquals(3, defaultTakenokoAmDeck.size(Amenagement.ENGRAIS));
        assertEquals(Amenagement.ENGRAIS, defaultTakenokoAmDeck.draw(Amenagement.ENGRAIS));
        assertEquals(2, defaultTakenokoAmDeck.size(Amenagement.ENGRAIS));

        assertEquals(3, defaultTakenokoAmDeck.size(Amenagement.ENCLOS));
        assertEquals(Amenagement.ENCLOS, defaultTakenokoAmDeck.draw(Amenagement.ENCLOS));
        assertEquals(2, defaultTakenokoAmDeck.size(Amenagement.ENCLOS));
    }

    @Test(expected = EmptyDeckException.class)
    public void drawEmpty() throws EmptyDeckException {
        while (!defaultTakenokoAmDeck.isEmpty(Amenagement.BASSIN)){
            assertEquals(Amenagement.BASSIN, defaultTakenokoAmDeck.draw(Amenagement.BASSIN));
        }
        assertEquals(0, defaultTakenokoAmDeck.size(Amenagement.BASSIN));
        // Expected emptyDeckException here
        defaultTakenokoAmDeck.draw(Amenagement.BASSIN);
    }

    @Test
    public void toStringTest() throws EmptyDeckException {
        assertEquals("Pioches aménagements :\n" +
                "\t Pioche Enclos : 3\n" +
                "\t Pioche Engrais : 3\n" +
                "\t Pioche Bassin : 3", defaultTakenokoAmDeck.toString());

        while (!defaultTakenokoAmDeck.isEmpty(Amenagement.ENGRAIS)){
            assertEquals(Amenagement.ENGRAIS, defaultTakenokoAmDeck.draw(Amenagement.ENGRAIS));
        }

        assertEquals("Pioches aménagements :\n" +
                "\t Pioche Enclos : 3\n" +
                "\t Pioche Engrais : Vide\n" +
                "\t Pioche Bassin : 3", defaultTakenokoAmDeck.toString());
    }
}