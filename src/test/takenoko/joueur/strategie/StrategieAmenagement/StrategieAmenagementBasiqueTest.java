package takenoko.joueur.strategie.StrategieAmenagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.deck.AmenagementDecks;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.util.exceptions.EmptyDeckException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategieAmenagementBasiqueTest {

    @Autowired
    @Qualifier("defaultTakenokoAmDeck")
    private AmenagementDecks dc;

    @Test
    public void chooseAmenagement() throws EmptyDeckException {
        StrategieAmenagementBasique st = new StrategieAmenagementBasique();

        while (!dc.isEmpty(Amenagement.BASSIN)) {
            assertEquals(Amenagement.BASSIN,st.chooseAmenagement(dc));
        }

        while (!dc.isEmpty(Amenagement.ENGRAIS)) {
            assertEquals(Amenagement.ENGRAIS,st.chooseAmenagement(dc));
        }

        while (!dc.isEmpty(Amenagement.ENCLOS)) {
            assertEquals(Amenagement.ENCLOS,st.chooseAmenagement(dc));
        }

        assertEquals(Amenagement.NON,st.chooseAmenagement(dc));
    }

    @Test
    public void plotAmenagement() {
    }
}