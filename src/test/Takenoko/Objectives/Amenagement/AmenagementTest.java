package Takenoko.Objectives.Amenagement;

import Takenoko.Plot.Plot;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


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
