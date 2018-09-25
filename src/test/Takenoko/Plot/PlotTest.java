package Takenoko.Plot;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlotTest {

    @Test
    public void getq() {
        Plot p = new Plot(10,15);
        assertEquals(10, p.getq());
    }

    @Test
    public void getr() {
        Plot p = new Plot(10,15);
        assertEquals(15, p.getr());
    }

    @Test
    public void setCoord() {
        Plot p = new Plot(10,15);
        assertEquals(10, p.getq());
        assertEquals(15, p.getr());

        p.setCoord(15,10);
        assertEquals(15, p.getq());
        assertEquals(10, p.getr());
    }

    @Test public void haveBambou(){
        Plot p = new Plot();
        assertFalse(p.haveBambou());

        p.pousserBambou();

        assertTrue(p.haveBambou());
        assertEquals(1,p.getBambou());

    }
}