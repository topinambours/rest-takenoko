package client.service;

import client.Joueur;
import communication.HTTPClient;
import communication.container.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Couleur;
import takenoko.Plateau;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.versionning.Action;
import takenoko.versionning.ActionType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActionServiceTest {

    @Autowired
    Joueur joueur;

    @Autowired
    ActionService as;

    @Mock
    HTTPClient mockedHttp;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updatePlateauSameVersion() {
        List<Action> actions = new ArrayList<>();
        actions.add(new Action(1, ActionType.PUTPLOT, new CoordAxial(1, 0), new Tuile(1, Couleur.VERT, Amenagement.NONE)));
        actions.add(new Action(2, ActionType.PUTPLOT, new CoordAxial(2, 0), new Tuile(2, Couleur.JAUNE, Amenagement.NONE)));

        Plateau tmp = joueur.getPlateau();

        joueur.setLatestVersionId(1);
        as.updatePlateau(1, actions);

        assertEquals(tmp, joueur.getPlateau());
    }

    @Test
    public void phasePlotDraw() {
        when(mockedHttp.piocher_tuiles()).thenReturn(new TuileContainer(new Tuile(1, Couleur.ROSE, Amenagement.NONE)));
        when(mockedHttp.rendreTuiles(new TuileContainer())).thenReturn(new ResponseContainer(true, "msg"));


        assertEquals(1, mockedHttp.piocher_tuiles().getContent().size());

        assertEquals(new Tuile(1, Couleur.ROSE, Amenagement.NONE), as.phasePlotDraw(mockedHttp));
    }

    @Test
    public void phasePlotPut() {
        when(mockedHttp.requestLegalMovesTuiles()).thenReturn(new CoordContainer(new CoordAxial(1,0)));
        when(mockedHttp.poserTuile(new PoseTuileContainer(new CoordAxial(1,0), new Tuile(1, Couleur.ROSE, Amenagement.NONE))))
                .thenReturn(new ResponseContainer(true, "tuile posée."));


        assertEquals(1, mockedHttp.requestLegalMovesTuiles().getContent().size());

        assertEquals(new ResponseContainer(true, "tuile posée."), as.phasePlotPut(mockedHttp, new Tuile(1, Couleur.ROSE, Amenagement.NONE)));
    }

    @Test
    public void phasePanda() {
        when(mockedHttp.requestLegalPandaPositions()).thenReturn(new CoordContainer(new CoordAxial(1,0)));
        when(mockedHttp.deplacerPanda(new CoordAxial(1,0)))
                .thenReturn(new ResponseContainer(true, "PANDA MOOVED"));


        assertEquals(1, mockedHttp.requestLegalPandaPositions().getContent().size());

        assertEquals(new ResponseContainer(true, "PANDA MOOVED"), as.phasePanda(mockedHttp));
    }

    @Test
    public void phasePandaEmptyLegal() {
        when(mockedHttp.requestLegalPandaPositions()).thenReturn(new CoordContainer());

        assertEquals(0, mockedHttp.requestLegalPandaPositions().getContent().size());
        assertEquals(new ResponseContainer(false, "NO LEGAL MOVE FOR PANDA"), as.phasePanda(mockedHttp));
    }

    @Test
    public void phaseIrrigation() {
        when(mockedHttp.requestLegalIrrigPositions()).thenReturn(new CoordIrrigContainer(new CoordIrrig(1,0, Orient.S)));
        when(mockedHttp.poserIrrigation(new CoordIrrig(1,0, Orient.S)))
                .thenReturn(new ResponseContainer(true, "IRRIG ADDED"));


        assertEquals(1, mockedHttp.requestLegalIrrigPositions().getContent().size());

        assertEquals(new ResponseContainer(true, "IRRIG ADDED"), as.phaseIrrigation(mockedHttp));
    }

    @Test
    public void phaseIrrigationEmptyLegal() {
        when(mockedHttp.requestLegalIrrigPositions()).thenReturn(new CoordIrrigContainer());
        when(mockedHttp.poserIrrigation(new CoordIrrig(1,0, Orient.S)))
                .thenReturn(new ResponseContainer(true, "IRRIG ADDED"));


        assertEquals(0, mockedHttp.requestLegalIrrigPositions().getContent().size());

        assertEquals(new ResponseContainer(false, "NO LEGAL MOVE FOR IRRIG"), as.phaseIrrigation(mockedHttp));
    }



    @Test
    public void updatePlateau() {
        List<Action> actions = new ArrayList<>();
        actions.add(new Action(1, ActionType.PUTPLOT, new CoordAxial(1, 0), new Tuile(1, Couleur.VERT, Amenagement.NONE)));
        actions.add(new Action(2, ActionType.PUTPLOT, new CoordAxial(2, 0), new Tuile(2, Couleur.JAUNE, Amenagement.NONE)));

        assertEquals(joueur.getPlateau(), new Plateau().plateau_depart());

        assertTrue(as.updatePlateau(2, actions));

        Tuile expected = new Tuile(1, Couleur.VERT, Amenagement.NONE);
        expected.setHaveWater(true);
        expected.setNbBambous(1);
        assertEquals(expected, joueur.getPlateau().getTuileAtCoord(new CoordAxial(1,0)));

        assertEquals(new Tuile(2, Couleur.JAUNE, Amenagement.NONE), joueur.getPlateau().getTuileAtCoord(new CoordAxial(2,0)));

        assertEquals(2, joueur.getLatestVersionId());
    }

    @Test
    public void updatePlateauWrongArguments() {
        List<Action> actions = new ArrayList<>();
        actions.add(new Action(1, ActionType.PUTPLOT, new CoordAxial(1, 0), new Tuile(1, Couleur.VERT, Amenagement.NONE)));
        actions.add(new Action(2, ActionType.MOOVEPANDA, new CoordAxial(2, 0), new Tuile(2, Couleur.JAUNE, Amenagement.NONE)));

        assertEquals(joueur.getPlateau(), new Plateau().plateau_depart());

        assertFalse(as.updatePlateau(2, actions));

        Tuile expected = new Tuile(1, Couleur.VERT, Amenagement.NONE);
        expected.setHaveWater(true);
        expected.setNbBambous(1);
        assertEquals(expected, joueur.getPlateau().getTuileAtCoord(new CoordAxial(1,0)));

        // Board is not sychronized
        assertNotEquals(2, joueur.getLatestVersionId());
    }

    @Test
    public void updatePlateaufromHead() {
        List<Action> actions = new ArrayList<>();
        actions.add(new Action(1, ActionType.PUTPLOT, new CoordAxial(1, 0), new Tuile(1, Couleur.VERT, Amenagement.NONE)));
        actions.add(new Action(2, ActionType.PUTPLOT, new CoordAxial(2, 0), new Tuile(2, Couleur.JAUNE, Amenagement.NONE)));

        assertEquals(joueur.getPlateau(), new Plateau().plateau_depart());

        Plateau p = new Plateau();
        assertTrue(Action.applyAllAction(actions, p));

        as.updatePlateauFromOrigin(p, 2);

        Tuile expected = new Tuile(1, Couleur.VERT, Amenagement.NONE);
        expected.setHaveWater(true);
        expected.setNbBambous(1);
        assertEquals(expected, joueur.getPlateau().getTuileAtCoord(new CoordAxial(1,0)));

        assertEquals(new Tuile(2, Couleur.JAUNE, Amenagement.NONE), joueur.getPlateau().getTuileAtCoord(new CoordAxial(2,0)));
        assertEquals(2, joueur.getLatestVersionId());
    }


}