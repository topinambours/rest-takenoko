package Takenoko.Joueur.Strategie;

import Takenoko.Joueur.Joueur;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Couleur;
import Takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StrategieColorTest {

    StrategieColor strategieColor;
    Plateau p;
    Joueur joueur;

    @Before
    public void setup() throws Exception{
        strategieColor = new StrategieColor();
        p = new Plateau();
        joueur = new Joueur(1, strategieColor);
    }

    @Test
    public void getCoord(){
        p.addStartingPlot(new Plot(0, 0, Couleur.NOIR));

        p.putPlot(new Plot(1,0));
        p.putPlot(new Plot(1,1));
        p.putPlot(new Plot(1,-1));

        //assertEquals(new CoordAxial(0,1), strategieColor.getCoord(p, new Plot()));

        p.putPlot(new Plot(2,0));
        p.putPlot(new Plot(3,-1));
        p.putPlot(new Plot(3,-2));
        p.putPlot(new Plot(2,-2));

        boolean b = p.legalPositions().contains(new CoordAxial(2,-1));
        System.out.println(b);
        boolean c = p.legalPositions().contains(new CoordAxial(2,-2));
        System.out.println(c);
        assertEquals(new CoordAxial(2,-1), strategieColor.getCoord(p, new Plot()));
    }

    @Test
    public void getCoords(){
        p.addStartingPlot(new Plot(0, 0, Couleur.NOIR));

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
        assertEquals(name, strategieColor.getStrategieLabel());
    }

}
