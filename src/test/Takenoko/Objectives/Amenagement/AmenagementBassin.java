package Takenoko.Objectives.Amenagement;

import Takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

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
