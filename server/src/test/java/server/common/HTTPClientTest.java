package server.common;

import communication.HTTPClient;
import communication.container.*;
import server.GameEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Couleur;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.versionning.ActionType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Testing one client using the server environment
 * (self hosted client)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HTTPClientTest {

    private HTTPClient testClient;
    private HTTPClient testClient2;

    @Autowired
    private GameEngine gameEngine;

    @Before
    public void setUp(){
        testClient = new HTTPClient(1, "localhost:8080", "http://localhost:8080");
        testClient2 = new HTTPClient(2, "localhost:8081", "http://localhost:8080");
    }

    @Test
    public void getId() {
        assertEquals(1, testClient.getId());
        assertEquals(2, testClient2.getId());
    }

    @Test
    public void getUser_adress() {
        assertEquals("localhost:8080", testClient.getUser_adress());
        assertEquals("localhost:8081", testClient2.getUser_adress());
    }

    @Test
    public void getServer_url() {
        assertEquals("http://localhost:8080", testClient.getServer_url());
        assertEquals("http://localhost:8080", testClient2.getServer_url());
    }

    @Test
    public void self_request() {
        // Client self hosted, we can try to draw plots using the client interface
        TuileContainer req = testClient.self_request("action/piocher?playerId=1", TuileContainer.class);
        assertEquals(3, req.getContent().size());
    }

    @Test
    public void request() {
        TuileContainer req = testClient.request("action/piocher", TuileContainer.class);
        assertEquals(3, req.getContent().size());
    }

    @Test
    public void post_request() {
        //Le joueur va piocher trois tuiles et en rendre deux
        assertEquals(27, gameEngine.getPiocheTuile().size());
        TuileContainer req = testClient.request("action/piocher", TuileContainer.class);
        assertEquals(3, req.getContent().size());

        List<Tuile> back = req.getContent().subList(0,2);
        assertEquals(2, back.size());

        assertEquals(24, gameEngine.getPiocheTuile().size());

        ResponseContainer resp = testClient.post_request("/action/rendre_tuiles/", new TuileContainer(back), ResponseContainer.class);

        assertTrue(resp.response);
        assertEquals("return effective", resp.message);
        assertEquals(26, gameEngine.getPiocheTuile().size());
    }

    @Test
    public void piocher_tuiles() {
        TuileContainer req;

        for (int i = 1; i <= 9 ; i++){
            req = testClient.piocher_tuiles();
            assertEquals(3, req.getContent().size());
            assertEquals(27 - (3 * i), gameEngine.getPiocheTuile().size());
        }
        assertEquals(0, gameEngine.getPiocheTuile().size());

    }

    @Test
    public void rendreTuiles() {
        //Le joueur va piocher trois tuiles et en rendre deux
        assertEquals(27, gameEngine.getPiocheTuile().size());
        TuileContainer req = testClient.request("action/piocher", TuileContainer.class);
        assertEquals(3, req.getContent().size());

        List<Tuile> back = req.getContent().subList(0,2);
        assertEquals(2, back.size());

        assertEquals(24, gameEngine.getPiocheTuile().size());

        ResponseContainer resp = testClient.post_request("/action/rendre_tuiles/", new TuileContainer(back), ResponseContainer.class);

        assertTrue(resp.response);
        assertEquals("return effective", resp.message);
        assertEquals(26, gameEngine.getPiocheTuile().size());
    }

    @Test
    public void poserTuile() {
        ResponseContainer resp = testClient.poserTuile(new PoseTuileContainer(new CoordAxial(1,0), new Tuile(1, Couleur.VERT, Amenagement.ENGRAIS)));

        assertTrue(resp.getResponse());

        // First plot aside blue one, must have water and two bambou (engrais)
        Tuile expected = new Tuile(1, Couleur.VERT, Amenagement.ENGRAIS);
        expected.setHaveWater(true);
        expected.setNbBambous(2);
        assertEquals(expected, gameEngine.getPlateau().getTuileAtCoord(new CoordAxial(1,0)));

    }

    @Test
    public void requestLegalMovesTuiles() {
        CoordContainer req = testClient.requestLegalMovesTuiles();

        List<CoordAxial> expected = new ArrayList<>();
        expected.add(new CoordAxial(1,0));
        expected.add(new CoordAxial(0,1));
        expected.add(new CoordAxial(-1,1));
        expected.add(new CoordAxial(-1,0));
        expected.add(new CoordAxial(0,-1));
        expected.add(new CoordAxial(1,-1));

        for (CoordAxial e : expected){
            assertTrue(req.getContent().contains(e));
        }

        assertEquals(expected.size(), req.getContent().size());
    }

    @Test
    public void notifyEndTurn() {
        assertEquals(testClient, gameEngine.getCurrentPlayer());


        assertTrue(testClient.notifyEndTurn().getResponse());

        assertEquals(testClient2, gameEngine.getCurrentPlayer());

        // Player 1 can't notify his end turn, it's turn of player 2
        assertFalse(testClient.notifyEndTurn().getResponse());

        assertTrue(testClient2.notifyEndTurn().getResponse());

        assertEquals(testClient, gameEngine.getCurrentPlayer());
    }

    @Test
    public void isGameEnded() {

        assertFalse(testClient.isGameEnded().getResponse());
        assertFalse(testClient2.isGameEnded().getResponse());

        // On pose les 27 tuiles
        TuileContainer req;

        for (int i = 1; i <= 27; i++){
            gameEngine.getPlateau().poserTuile(new CoordAxial(i,0), new Tuile(i,Couleur.ROSE));
        }
        assertTrue(testClient.isGameEnded().getResponse());
        assertTrue(testClient.isGameEnded().getResponse());

    }

    @Test
    public void isGameStarted() {
        assertTrue(testClient.isGameStarted().getResponse());
    }

    @Test
    public void requestLegalIrrigPositions() {
        assertEquals(0, testClient.requestLegalIrrigPositions().getContent().size());
        testClient.poserTuile(new PoseTuileContainer(new CoordAxial(1,0), new Tuile(1, Couleur.VERT, Amenagement.ENGRAIS)));
        testClient.poserTuile(new PoseTuileContainer(new CoordAxial(0,1), new Tuile(1, Couleur.ROSE, Amenagement.ENGRAIS)));

        CoordIrrigContainer req = testClient.requestLegalIrrigPositions();

        assertEquals(1, req.getContent().size());

        assertEquals(new CoordIrrig(1,0, Orient.S), req.getContent().get(0));

    }

    @Test
    public void poserIrrigation() {
        requestLegalIrrigPositions();
        assertEquals(new CoordIrrig(1,0, Orient.S), testClient.requestLegalIrrigPositions().getContent().get(0));

        testClient.poserIrrigation(new CoordIrrig(1,0, Orient.S));

        assertEquals(0, testClient.requestLegalIrrigPositions().getContent().size());
        assertTrue(gameEngine.getPlateau().getIrrigations().contains(new CoordIrrig(1,0,Orient.S)));

    }

    @Test
    public void requestLegalPandaPositions() {
        assertEquals(0, testClient.requestLegalPandaPositions().getContent().size());
        poserIrrigation();
        // Two plots on the board now

        assertEquals(2, testClient.requestLegalPandaPositions().getContent().size());

        assertTrue(testClient.requestLegalPandaPositions().getContent().contains(new CoordAxial(1,0)));
        assertTrue(testClient.requestLegalPandaPositions().getContent().contains(new CoordAxial(0,1)));

    }

    @Test
    public void deplacerPanda() {
        requestLegalPandaPositions();

        ResponseContainer resp = testClient.deplacerPanda(new CoordAxial(1,0));
        assertTrue(resp.getResponse());

        assertEquals(new CoordAxial(1,0), gameEngine.getPlateau().posPanda());
    }

    /**
     * Pull from origin
     * At this stage we will use results optain after the "deplacerPanda" method
     */
    @Test
    public void pullAllVersion() {
        assertEquals(0, testClient.pullAllVersion().getContent().size());
        deplacerPanda();
        assertEquals(4 , testClient.pullAllVersion().getContent().size());

        ActionType[] sequence = {ActionType.PUTPLOT ,ActionType.PUTPLOT, ActionType.ADDIRRIG, ActionType.MOOVEPANDA};

        for (int i = 0; i <= 3; i++){
            assertEquals(sequence[i], testClient.pullAllVersion().getContent().get(i).getActionType());
        }

    }

    @Test
    public void pullVersionId() {
        assertEquals(0, testClient.pullAllVersion().getContent().size());
        deplacerPanda();
        assertEquals(1 , testClient.pullVersionId(2).getContent().size());
    }

    @Test
    public void pullVersionFrom() {
        assertEquals(0, testClient.pullAllVersion().getContent().size());
        deplacerPanda();
        assertEquals(2 , testClient.pullVersionFrom(2).getContent().size());

        ActionType[] sequence = {ActionType.PUTPLOT ,ActionType.PUTPLOT, ActionType.ADDIRRIG, ActionType.MOOVEPANDA};

        for (int i = 0; i <= 1; i++){
            assertEquals(sequence[i+2], testClient.pullVersionFrom(2).getContent().get(i).getActionType());
        }

    }

    @Test
    public void pullVersionFromTo() {
        assertEquals(0, testClient.pullAllVersion().getContent().size());
        deplacerPanda();
        assertEquals(3 , testClient.pullVersionFromTo(1,4).getContent().size());

        ActionType[] sequence = {ActionType.PUTPLOT ,ActionType.PUTPLOT, ActionType.ADDIRRIG, ActionType.MOOVEPANDA};

        for (int i = 0; i <= 2; i++){
            assertEquals(sequence[i+1], testClient.pullVersionFromTo(1,4).getContent().get(i).getActionType());
        }

    }

    @Test
    public void pullLatestVersionId() {
        assertEquals(0,(int) testClient.pullLatestVersionId());
        deplacerPanda();
        assertEquals(4,(int) testClient.pullLatestVersionId());
    }

    @Test
    public void pullPlateau() {
        assertEquals(gameEngine.getPlateau(), testClient.pullPlateau());
    }
}