package takenoko.util.comparators;

import org.junit.Test;
import takenoko.objectives.PandaObjectiveCard;

import static org.junit.Assert.assertTrue;

public class ComparateurCarteObjectifPandaTest {

    @Test
    public void compare() {
        ComparateurCarteObjectifPanda cp = new ComparateurCarteObjectifPanda(1,0,1);

        PandaObjectiveCard first = new PandaObjectiveCard(1,0,0, 5);

        PandaObjectiveCard second = new PandaObjectiveCard(1,0,0,4);

        PandaObjectiveCard third =  new PandaObjectiveCard(0,0,0,4);

        assertTrue(cp.compare(first, second) == 0);
        assertTrue(cp.compare(first, third) > 0);
        assertTrue(cp.compare(third, first) < 0);

    }
}