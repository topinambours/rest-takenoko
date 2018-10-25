package takenoko.joueur.strategie.StrategieAction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Game;
import takenoko.util.exceptions.EmptyDeckException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategieActionBasiqueTest {

    @Autowired
    private Game gameTest;

    private StrategieAction st;
    @Before
    public void setUp(){
        st = new StrategieActionBasique();
    }

    @Test
    public void firstActionType() throws EmptyDeckException {
        assertEquals(Action.Card, st.firstActionType(gameTest));
        // On vide la pile de tuile
        gameTest.getPlotsDeck().draw(27);
        int nbIrrig = 0;
        int nbPanda = 0;

        for (int i = 0; i < 5000; i++) {
            if(st.firstActionType(gameTest) == Action.Irrig){
                nbIrrig += 1;
            }
            if(st.firstActionType(gameTest) == Action.Panda){
                nbPanda += 1;
            }
        }
        assertEquals(0.5, nbIrrig / 5000.0, 0.05);
        assertEquals(0.5, nbPanda / 5000.0, 0.05);
    }

    @Test
    public void secondActionType() throws EmptyDeckException {
        assertEquals(Action.Card, st.firstActionType(gameTest));
        assertEquals(Action.Plot, st.secondActionType(gameTest));
        // On vide la pile de tuile
        gameTest.getPlotsDeck().draw(27);

        if(st.firstActionType(gameTest) == Action.Irrig){
                assertEquals(Action.Gardener, st.secondActionType(gameTest));
        }
        if(st.firstActionType(gameTest) == Action.Panda){
            assertEquals(Action.Gardener, st.secondActionType(gameTest));
        }
    }

    @Test
    public void thirdActionType() {
        assertNull(st.thirdActionType(gameTest));
    }

}