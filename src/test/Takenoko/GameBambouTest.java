package takenoko;

import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameBambouTest {

    @Autowired
    private Game gameTest;

    @Test
    public void testPush(){
        CoordAxial coordAxial = new CoordAxial(1,0);
        Plot current = new Plot(coordAxial);

        //Irrigué par la parcelle de départ
        gameTest.getPlateau().putPlot(current);
        gameTest.grow();

        assertEquals(1, gameTest.getPlateau().getPlot(coordAxial).getBambou());

        gameTest.grow();
        gameTest.getPlateau().putPlot(new Plot(new CoordAxial(2,0)));
        assertEquals(0, gameTest.getPlateau().getPlot(new CoordAxial(2,0)).getBambou());
        assertEquals(2, gameTest.getPlateau().getPlot(coordAxial).getBambou());

        //avec de l'eau
        gameTest.getPlateau().getPlot(new CoordAxial(2,0)).setWater(true);
        gameTest.grow();

        assertEquals(1, gameTest.getPlateau().getPlot(new CoordAxial(2,0)).getBambou());


    }
}
