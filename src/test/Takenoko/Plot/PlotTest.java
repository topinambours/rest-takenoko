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
    public void getCoord(){
        Plot p = new Plot(new CoordAxial(0,0));
        Plot p2 = new Plot(new CoordAxial(1,1));
        assertEquals(new CoordAxial(0,0), p.getCoord());
        assertEquals(new CoordAxial(1,1), p2.getCoord());
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

    @Test
    public void haveWater(){
        Plot p = new Plot(0, 0);
        assertEquals(false, p.haveWater());
        p.setWater(true);
        assertEquals(true, p.haveWater());
    }

    @Test
    public void setWater(){
        Plot p = new Plot(0, 0);
        p.setWater(true);
        assertEquals(true, p.haveWater());
        p.setWater(false);
        assertEquals(false, p.haveWater());
    }

    @Test public void haveBambou(){
        Plot p = new Plot();
        assertFalse(p.haveBambou());

        p.setWater(true);

        p.pousserBambou();

        assertTrue(p.haveBambou());
        assertEquals(1,p.getBambou());

    }

    @Test public void haveBambouWater(){
        Plot p = new Plot();
        assertFalse(p.haveBambou());
        p.setWater(true);

        p.pousserBambou();

        assertTrue(p.haveBambou());
        assertEquals(1,p.getBambou());
    }

    @Test public void getBambou(){
        Plot p = new Plot();
        assertEquals(0, p.getBambou());
        p.setWater(true);
        p.pousserBambou();
        assertEquals(1,p.getBambou());
    }

    @Test public void removeBambou(){
        Plot p = new Plot();
        p.pousserBambou();
        p.pousserBambou();
        p.removeBamboo();
        assertEquals(0, p.getBambou());
    }

    @Test public void pousserBambou(){
        Plot p = new Plot();
        p.pousserBambou();
        p.setWater(true);
        p.pousserBambou();
        assertEquals(1, p.getBambou());
        p.pousserBambou();
        p.pousserBambou();
        assertEquals(3, p.getBambou());
    }
}