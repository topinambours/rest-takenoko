package Takenoko.Plot;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class BambouTest {
    private Bambou bambou;
    private Plot plot;

    @Before public void BambouTest(){
        plot = new Plot();
        bambou = new Bambou(plot);
    }

    @Test public void test(){
        assertEquals(1,bambou.getHauteur());

        bambou.addHauteur1();

        assertEquals(2,bambou.getHauteur());
    }
}
