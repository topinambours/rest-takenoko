package takenoko.objectives.amenagement;

import takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AmenagementTest {
    private Plot plot;

    @Before public void Amenagement(){
        plot = new Plot();
    }

    @Test public void setter(){
        plot.setAmenagement(Amenagement.BASSIN);
        assertEquals(Amenagement.BASSIN,plot.getAmenagement());
    }

    @Test public void getString(){
        assertEquals("BASSIN", Amenagement.BASSIN.getString());
        assertEquals("ENCLOS", Amenagement.ENCLOS.getString());
        assertEquals("NON", Amenagement.NON.getString());
        assertEquals("ENGRAIS", Amenagement.ENGRAIS.getString());
    }

}
