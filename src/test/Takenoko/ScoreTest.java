package takenoko;

import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordRandom;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigBase;
import takenoko.joueur.strategie.StrategieSansPions;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreTest {

    @Autowired private Game gameTest;
    private static Joueur joueur;

    @Before
    @Required
    public void ScoreTest(){
        Console.Log.init();
        joueur = new Joueur(1, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(gameTest.getPlateau())));
    }

    @Test public void test() throws EmptyDeckException {
        CoordAxial coordAxial = new CoordAxial(1,0);
        Plot plot = new Plot(coordAxial, Couleur.BLEU);
        joueur.setPlot(plot);
        CoordAxial pose = joueur.putPlot(plot,gameTest.getPlateau());

        gameTest.evaluate(joueur,pose);

        assertEquals(0,joueur.getScore());
        //@TODO faire une vrai classe de test pour le gain en score
    }
}
