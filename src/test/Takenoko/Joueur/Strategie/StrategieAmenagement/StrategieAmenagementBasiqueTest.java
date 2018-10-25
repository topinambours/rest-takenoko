package takenoko.joueur.strategie.StrategieAmenagement;

import org.junit.Test;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.objectives.amenagement.DeckAmenagement;

import static org.junit.Assert.*;

public class StrategieAmenagementBasiqueTest {

    @Test
    public void chooseAmenagement() {
        StrategieAmenagementBasique st = new StrategieAmenagementBasique();
        DeckAmenagement dc = new DeckAmenagement();
        dc.init();

        while (dc.getAmenagementSet().contains(Amenagement.BASSIN)) {
            assertEquals(Amenagement.BASSIN,st.chooseAmenagement(dc));
        }
        assertFalse(dc.getAmenagementSet().contains(Amenagement.BASSIN));

        while (dc.getAmenagementSet().contains(Amenagement.ENGRAIS)) {
            assertEquals(Amenagement.ENGRAIS,st.chooseAmenagement(dc));
        }
        assertFalse(dc.getAmenagementSet().contains(Amenagement.ENGRAIS));

        while (dc.getAmenagementSet().contains(Amenagement.ENCLOS)) {
            assertEquals(Amenagement.ENCLOS,st.chooseAmenagement(dc));
        }
        assertFalse(dc.getAmenagementSet().contains(Amenagement.ENCLOS));


        assertEquals(Amenagement.NON,st.chooseAmenagement(dc));
    }

    @Test
    public void plotAmenagement() {
    }
}