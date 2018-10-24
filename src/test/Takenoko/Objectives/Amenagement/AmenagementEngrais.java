package takenoko.objectives.amenagement;

import takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AmenagementEngrais {
    private Plot plot;

    @Before public void Amenagement(){
        plot = new Plot();
        plot.setWater(true);
    }

    @Test public void engrais(){
        plot.setAmenagement(Amenagement.ENGRAIS);
        plot.pousserBambou();

        assertEquals(2,plot.getBambou());

    }

    @Test public void noEngrais(){
        plot.pousserBambou();

        assertEquals(1,plot.getBambou());
    }
}
