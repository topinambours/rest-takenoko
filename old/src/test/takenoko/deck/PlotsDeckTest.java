package takenoko.deck;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;
import takenoko.util.exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlotsDeckTest {

    @Autowired
    private PlotsDeck plotsDeckTest;

    @Test
    public void testBeanConstructor() throws EmptyDeckException {
        Assert.assertEquals(27, plotsDeckTest.size());

        List<Plot> list = new ArrayList<>();
        while (!plotsDeckTest.isEmpty()){
            list.add(plotsDeckTest.draw());
        }
        assertTrue(plotsDeckTest.isEmpty());
    }

    @Test
    public void testEmptyConstructor(){
        PlotsDeck deck = new PlotsDeck();
        assertTrue(deck.isEmpty());
    }

    @Test
    public void toStringTest() {
        assertEquals("Pioche de parcelles : Hauteur 27", plotsDeckTest.toString());
        plotsDeckTest.insertBottom(new Plot(Couleur.BLEU, Amenagement.BASSIN));
        assertEquals("Pioche de parcelles : Hauteur 28", plotsDeckTest.toString());
    }
}