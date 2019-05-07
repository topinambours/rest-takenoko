package takenoko.versionning;

import org.junit.Before;
import org.junit.Test;
import takenoko.Couleur;
import takenoko.Plateau;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActionTest {
    private Plateau plateau;

    @Before
    public void initPlateau(){
        plateau = new Plateau().plateau_depart();
    }

    @Test
    public void testArgsPutplot(){
        ActionType actionType = ActionType.PUTPLOT;
        Tuile tuile = new Tuile(58, Couleur.VERT, Amenagement.BASSIN);
        CoordAxial coordAxial = new CoordAxial(1,0);

        Action action4 = new Action(actionType,coordAxial,tuile);

        assertTrue(Action.applyAction(action4,plateau));

        assertEquals(tuile, plateau.getTuileAtCoord(coordAxial));
    }

    @Test
    public void invalidArgumentsPlot(){
        assertFalse(Action.applyAction(new Action(ActionType.PUTPLOT, "fooo", "bbbar"), plateau));
        assertFalse(Action.applyAction(new Action(ActionType.PUTPLOT, new CoordIrrig(1,1,Orient.W)), plateau));
        assertFalse(Action.applyAction(new Action(ActionType.PUTPLOT, Couleur.BLEU), plateau));
        assertFalse(Action.applyAction(new Action(ActionType.PUTPLOT, 1,Couleur.VERT, Amenagement.BASSIN), plateau));

        assertEquals(1, plateau.generateTuileMap().size());

    }

    @Test
    public void invalidArgumentsIrrig(){

        assertFalse(Action.applyAction(new Action(ActionType.ADDIRRIG, "fooo"), plateau));
        assertFalse(Action.applyAction(new Action(ActionType.ADDIRRIG, Couleur.BLEU), plateau));
        assertFalse(Action.applyAction(new Action(ActionType.ADDIRRIG, 1,Couleur.VERT, Amenagement.BASSIN), plateau));

        assertEquals(1, plateau.generateTuileMap().size());

    }


    @Test
    public void invalidArgumentsMovePanda(){

        assertFalse(Action.applyAction(new Action(ActionType.MOOVEPANDA, "fooo"), plateau));
        assertFalse(Action.applyAction(new Action(ActionType.MOOVEPANDA, new CoordIrrig(1,1,Orient.W)), plateau));
        assertFalse(Action.applyAction(new Action(ActionType.MOOVEPANDA, Couleur.BLEU), plateau));
        assertFalse(Action.applyAction(new Action(ActionType.MOOVEPANDA, 1,Couleur.VERT, Amenagement.BASSIN), plateau));

        assertEquals(1, plateau.generateTuileMap().size());

    }

    @Test
    public void testArgsAddIrrig(){
        ActionType actionType = ActionType.ADDIRRIG;
        CoordIrrig coordIrrig = new CoordIrrig(1,0, Orient.N);
        plateau.poserTuile(new CoordAxial(2,0),new Tuile());

        Action action3 = new Action(actionType,coordIrrig);

        assertTrue(Action.applyAction(action3,plateau));

        assertTrue(plateau.getIrrigations().contains(new CoordIrrig(1,0, Orient.N)));

    }

    @Test
    public void testArgsMoovePanda(){
        ActionType actionType = ActionType.MOOVEPANDA;
        CoordAxial coordAxial = new CoordAxial(1,0);
        plateau.poserTuile(coordAxial,new Tuile());

        Action action3 = new Action(actionType,coordAxial);

        assertTrue(Action.applyAction(action3,plateau));

        assertEquals(new CoordAxial(1,0), plateau.posPanda());

    }

    @Test
    public void applyAllAction() {

        List<Action> actionBag = new ArrayList<>();

        // Construisons une suite de tuiles consécutives (horizontalement par la droite)
        // Nous ajouterons également des irrigations tout le long de ces tuiles

        for (int i = 1; i <= 500; i++){
            actionBag.add(new Action(ActionType.PUTPLOT, new CoordAxial(i, 0), new Tuile(i, Couleur.getById(i % 3), Amenagement.NONE)));
            actionBag.add(new Action(ActionType.ADDIRRIG, new CoordIrrig(i,0, Orient.S)));
            actionBag.add(new Action(ActionType.ADDIRRIG, new CoordIrrig(i,-1,Orient.N)));
            actionBag.add(new Action(ActionType.MOOVEPANDA, new CoordAxial(i, 0)));
        }

        // 600 actions à appliquer
        assertEquals(500 * 4 , actionBag.size());


        CoordAxial currentTestPlotCoord;
        // Sequential run with unit test
        int c = 0;
        for (int i = 0; i < 2000; i+=4){
            int x_plot = i+1 - (3*(c));

            currentTestPlotCoord = new CoordAxial(x_plot, 0);
            assertTrue(Action.applyAction(actionBag.get(i), plateau));
            assertEquals(c + 2, plateau.generateTuileMap().size());
            assertEquals(Couleur.getById(x_plot%3), plateau.getTuileAtCoord(currentTestPlotCoord).getCouleur());


            assertTrue(Action.applyAction(actionBag.get(i+1), plateau));
            assertTrue(Action.applyAction(actionBag.get(i+2), plateau));

            assertTrue(plateau.getIrrigations().contains(new CoordIrrig(x_plot,0, Orient.S)));
            assertTrue(plateau.getIrrigations().contains(new CoordIrrig(x_plot,-1, Orient.N)));
            assertTrue(plateau.getTuileAtCoord(currentTestPlotCoord).getHaveWater());

            assertTrue(Action.applyAction(actionBag.get(i+3), plateau));
            assertEquals(currentTestPlotCoord, plateau.posPanda());

            c+=1;

        }

        // Copie du plateau après les appels séquentiels
        Plateau tmp = plateau;

        plateau = new Plateau().plateau_depart();


        assertNotEquals(tmp, plateau);

        // On applique le sac d'action en un unique appel de méthode
        assertTrue(Action.applyAllAction(actionBag, plateau));

        assertEquals(tmp, plateau);

    }
}
