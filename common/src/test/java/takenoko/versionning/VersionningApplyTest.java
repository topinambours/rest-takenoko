package takenoko.versionning;

import org.junit.Before;
import org.junit.Test;
import takenoko.Plateau;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import static org.junit.Assert.*;

public class VersionningApplyTest {
    private Plateau plateau;

    @Before
    public void initPlateau(){
        plateau = new Plateau().plateau_depart();
    }

    @Test
    public void testApplyPutplot(){
        ActionType actionType = ActionType.PUTPLOT;
        Tuile tuile = new Tuile();
        CoordAxial coordAxial = new CoordAxial(1,0);

        Action action = new Action(actionType,coordAxial,tuile);

        assertTrue(Action.applyAction(action,plateau));
        assertEquals(plateau.getTuileAtCoord(coordAxial),tuile);
    }

    @Test
    public void testApplyAddIrrig(){
        ActionType actionType = ActionType.ADDIRRIG;
        CoordAxial coordAxial = new CoordAxial(1,0);
        CoordIrrig coordIrrig = new CoordIrrig(1,0, Orient.N);
        plateau.poserTuile(coordAxial,new Tuile());
        plateau.poserTuile(new CoordAxial(2,0),new Tuile());

        Action action = new Action(actionType,coordIrrig);

        assertTrue(Action.applyAction(action,plateau));
        assertTrue(plateau.irrigationsList().contains(coordIrrig));
    }

    @Test
    public void testApplyMoovePanda(){
        ActionType actionType = ActionType.MOOVEPANDA;
        CoordAxial coordAxial = new CoordAxial(1,0);
        CoordIrrig coordIrrig = new CoordIrrig(1,0,Orient.N);
        plateau.poserTuile(coordAxial,new Tuile());

        Action action = new Action(actionType,coordAxial);

        assertTrue(Action.applyAction(action,plateau));
        assertEquals(plateau.posPanda(),coordAxial);
    }
}
