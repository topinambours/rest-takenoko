package takenoko.objectives.patterns;

import org.junit.Test;
import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordRandom;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigBase;
import takenoko.joueur.strategie.StrategieSansPions;
import takenoko.objectives.GardenObjectiveCard;
import takenoko.objectives.ObjectiveCard;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;

import static org.junit.Assert.*;

public class GardenObjectiveCardTest {

    ObjectiveCard goal;
    Joueur j;

    @Test
    public void getPointValue() {
        goal = new GardenObjectiveCard(Couleur.VERT,1,5);
        assertEquals(5, goal.getPointValue());

        goal = new GardenObjectiveCard(Couleur.JAUNE,2,7);
        assertEquals(7, goal.getPointValue());
    }

    @Test
    public void isComplete() {
        goal = new GardenObjectiveCard(Couleur.VERT,1,5);
        Plateau p = new Plateau();
        j = new Joueur(1, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(p)));
        goal.instanciate(p, j);

        p.putPlot(new Plot(0, 1, Couleur.VERT));
        p.getPlot(0, 1).pousserBambou();
        p.getPlot(0, 1).pousserBambou();

        assertFalse(goal.isComplete());

        p.getPlot(0, 1).pousserBambou();

        assertTrue(goal.isComplete());

        goal = new GardenObjectiveCard(Couleur.JAUNE,2,5);
        goal.instanciate(p, j);

        p.putPlot(new Plot(0, 1, Couleur.JAUNE));
        p.getPlot(0, 1).pousserBambou();
        p.getPlot(0, 1).pousserBambou();

        p.putPlot(new Plot(1, 1, Couleur.JAUNE));
        p.getPlot(1, 1).setWater(true);
        p.getPlot(1, 1).pousserBambou();
        p.getPlot(1, 1).pousserBambou();
        assertFalse(goal.isComplete());
        p.getPlot(1, 1).pousserBambou();
        assertTrue(goal.isComplete());
    }

    @Test
    public void validate() {
        isComplete();

        assertEquals(5, goal.validate());
    }
}
