package takenoko.objectives;

import org.junit.Test;
import takenoko.Plateau;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordRandom;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigBase;
import takenoko.joueur.strategie.StrategieSansPions;

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