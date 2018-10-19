package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Joueur.Strategie.StrategieSansPions;
import Takenoko.Plateau;
import org.junit.Test;

import static org.junit.Assert.*;

public class PandaObjectiveCardTest {

    ObjectiveCard goal;
    Joueur j;

    @Test
    public void getPointValue() {
        goal = new PandaObjectiveCard(5,2,1,5);
        assertEquals(5, goal.getPointValue());

        goal = new PandaObjectiveCard(5,2,1,-50000);
        assertEquals(-50000, goal.getPointValue());
    }

    @Test
    public void isComplete() {
        goal = new PandaObjectiveCard(5,2,1,5);
        Plateau p = new Plateau();
        j = new Joueur(1, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(p)));
        goal.instanciate(p, j);

        j.setBambousJaunes(2);
        j.setBambousRoses(1);
        j.setBambousVerts(4);

        assertFalse(goal.isComplete());

        j.setBambousVerts(5);
        assertTrue(goal.isComplete());
    }

    @Test
    public void validate() {
        isComplete();

        assertEquals(5, goal.validate());
    }
}