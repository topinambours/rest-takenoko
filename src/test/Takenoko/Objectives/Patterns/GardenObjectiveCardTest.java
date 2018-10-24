package Takenoko.Objectives.Patterns;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Joueur.Strategie.StrategieSansPions;
import Takenoko.Objectives.GardenObjectiveCard;
import Takenoko.Objectives.ObjectiveCard;
import Takenoko.Plateau;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
import org.junit.Test;

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
        p.getPlot(0, 1).setWater(true);
        p.getPlot(0, 1).pousserBambou();
        p.getPlot(0, 1).pousserBambou();
        p.getPlot(0, 1).pousserBambou();

        assertFalse(goal.isComplete());

        p.getPlot(0, 1).pousserBambou();

        assertTrue(goal.isComplete());

        goal = new GardenObjectiveCard(Couleur.JAUNE,2,5);
        goal.instanciate(p, j);

        p.putPlot(new Plot(0, 1, Couleur.JAUNE));
        p.getPlot(0, 1).setWater(true);
        p.getPlot(0, 1).pousserBambou();
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
