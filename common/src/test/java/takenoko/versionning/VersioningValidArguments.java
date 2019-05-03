package takenoko.versionning;

import org.junit.Before;
import org.junit.Test;
import takenoko.Plateau;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import static org.junit.Assert.*;

public class VersioningValidArguments {
    private Plateau plateau;

    @Before
    public void initPlateau(){
        plateau = new Plateau().plateau_depart();
    }

    @Test
    public void testArgsPutplot(){
        ActionType actionType = ActionType.PUTPLOT;
        Tuile tuile = new Tuile();
        CoordAxial coordAxial = new CoordAxial(1,0);

        Action action1 = new Action(actionType,"test");
        Action action2 = new Action(actionType,"test","test");
        Action action3 = new Action(actionType,tuile,coordAxial);

        Action action4 = new Action(actionType,coordAxial,tuile);

        assertFalse(Action.applyAction(action1,plateau));
        assertFalse(Action.applyAction(action2,plateau));
        assertFalse(Action.applyAction(action3,plateau));

        assertTrue(Action.applyAction(action4,plateau));
    }


    @Test
    public void testArgsAddIrrig(){
        ActionType actionType = ActionType.ADDIRRIG;
        CoordAxial coordAxial = new CoordAxial(1,0);
        CoordIrrig coordIrrig = new CoordIrrig(1,0, Orient.N);
        plateau.poserTuile(coordAxial,new Tuile());
        plateau.poserTuile(new CoordAxial(2,0),new Tuile());

        Action action1 = new Action(actionType,"test");
        Action action2 = new Action(actionType,coordAxial);
        Action action3 = new Action(actionType,coordIrrig);

        assertFalse(Action.applyAction(action1,plateau));
        assertFalse(Action.applyAction(action2,plateau));

        assertTrue(Action.applyAction(action3,plateau));
    }

    @Test
    public void testArgsMoovePanda(){
        ActionType actionType = ActionType.MOOVEPANDA;
        CoordAxial coordAxial = new CoordAxial(1,0);
        CoordIrrig coordIrrig = new CoordIrrig(1,0,Orient.N);
        plateau.poserTuile(coordAxial,new Tuile());

        Action action1 = new Action(actionType,"test");
        Action action2 = new Action(actionType,coordIrrig);
        Action action3 = new Action(actionType,coordAxial);

        assertFalse(Action.applyAction(action1,plateau));
        assertFalse(Action.applyAction(action2,plateau));

        assertTrue(Action.applyAction(action3,plateau));
    }

}
