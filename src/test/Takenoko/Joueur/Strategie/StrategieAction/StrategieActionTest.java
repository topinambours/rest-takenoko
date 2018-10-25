package takenoko.joueur.strategie.StrategieAction;

import org.junit.Test;

import static org.junit.Assert.*;

public class StrategieActionTest {

    @Test
    public void getLogicalNextAction() {

        StrategieAction st = new StrategieActionBasique();

        assertEquals(Action.Plot, st.getLogicalNextAction(Action.Gardener));
        assertEquals(Action.Gardener, st.getLogicalNextAction(Action.Panda));
        assertEquals(Action.Gardener, st.getLogicalNextAction(Action.Irrig));
        assertEquals(Action.Panda, st.getLogicalNextAction(Action.Plot));
        assertEquals(Action.Plot, st.getLogicalNextAction(Action.Card));
        assertEquals(Action.ObjCard, st.getLogicalNextAction(Action.ObjCard));
    }
}