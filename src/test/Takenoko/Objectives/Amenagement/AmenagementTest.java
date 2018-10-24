package Takenoko.Objectives.Amenagement;

import Takenoko.Plot.Plot;
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


}
