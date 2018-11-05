package takenoko.objectives.amenagement;

import org.junit.Before;
import org.junit.Test;
import takenoko.plot.Plot;

import static org.junit.Assert.assertEquals;

public class AmenagementBassin {
    private Plot plot;

    @Before public void Amenagement(){
        plot = new Plot();
    }

    @Test public void bassin(){
        plot.setAmenagement(Amenagement.BASSIN);
        plot.pousserBambou();

        assertEquals(1,plot.getBambou());
    }

    @Test public void noBassin(){
        plot.pousserBambou();

        assertEquals(0,plot.getBambou());
    }
}
