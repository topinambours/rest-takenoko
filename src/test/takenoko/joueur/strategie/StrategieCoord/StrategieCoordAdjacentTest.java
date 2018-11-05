package takenoko.joueur.strategie.StrategieCoord;

import takenoko.Game;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigBase;
import takenoko.joueur.strategie.StrategieSansPions;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
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
public class StrategieCoordAdjacentTest {
    @Autowired private Game gameTest;
    private Joueur joueur;
    private StrategieCoord strategieCoord;
    private StrategieSansPions strategie;

    @Before @Required public void StrategieAdjacentTest(){
         strategieCoord = new StrategieCoordAdjacent();
         strategie = new StrategieSansPions(strategieCoord, new StrategieIrrigBase(gameTest.getPlateau()));
         joueur = new Joueur(1, strategie);
    }

    @Test public void test(){
        Plot plot1 = new Plot(-1,0);
        Plot plot2 = new Plot(0,1);

        gameTest.getPlateau().putPlot(plot1,plot1.getCoord());
        gameTest.getPlateau().putPlot(plot2,plot2.getCoord());

        //CoordAxial plotFinal = strategieCoord.getCoord(gameTest.getPlateau());

        //assertEquals(new Plot(-1,1).getCoord(),plotFinal);
        

    }
}
