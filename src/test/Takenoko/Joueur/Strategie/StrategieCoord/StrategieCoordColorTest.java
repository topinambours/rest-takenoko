package takenoko.joueur.strategie.StrategieCoord;

import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigBase;
import takenoko.joueur.strategie.StrategieSansPions;
import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import takenoko.properties.Couleur;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StrategieCoordColorTest {

    StrategieCoordColor strategieColor;
    StrategieSansPions strategie;
    Plateau p;
    Joueur joueur;

    @Before
    public void setup() throws Exception{
        strategieColor = new StrategieCoordColor();
        strategie = new StrategieSansPions(strategieColor, new StrategieIrrigBase(p));

        p = new Plateau();
        joueur = new Joueur(1, strategie);
    }

    @Test
    public void getCoord(){
        p.addStartingPlot(new Plot(0, 0, Couleur.VERT));

        p.putPlot(new Plot(1,0));
        p.putPlot(new Plot(1,1));
        p.putPlot(new Plot(1,-1));

        //assertEquals(new CoordAxial(0,1), strategieColor.getCoord(p, new Plot()));

        p.putPlot(new Plot(2,0));
        p.putPlot(new Plot(3,-1));
        p.putPlot(new Plot(3,-2));
        p.putPlot(new Plot(2,-2));

        boolean b = p.legalPositions().contains(new CoordAxial(2,-1));
        boolean c = p.legalPositions().contains(new CoordAxial(2,-2));
        assertEquals(new CoordAxial(2,-1), strategieColor.getCoord(p, new Plot()));
    }

    @Test
    public void getCoords(){
        p.addStartingPlot(new Plot(0, 0, Couleur.VERT));

        p.putPlot(new Plot(1,0));
        p.putPlot(new Plot(1,1));
        p.putPlot(new Plot(1,-1));

        List<CoordAxial> liste = new ArrayList<>();
        liste.add(new CoordAxial(0,1));

        //assertArrayEquals(liste.toArray(), strategieColor.getCoords(p, new Plot()).toArray());
    }


    @Test
    public void getStrategieLabel(){
        String name = "Max Color Adj";
        assertEquals(name, strategieColor.getStrategieCoordLabel());
    }

}
