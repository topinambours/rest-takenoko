package core.controllers;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import core.GameEngine;
import core.takenoko.pioche.PiocheTuile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Couleur;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@EnableAsync
public class ConnectionControllerTest {

    private HTTPClient testClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameEngine gameEngine;

    @Test
    public void registrationTest(){
        testClient = new HTTPClient(10, "localhost:8081", "http://localhost:8080", false);
        ResponseContainer resp = this.restTemplate.postForObject("/register/",testClient,  ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "You joined the game"), resp);

        // un client de connecté
        assertTrue(gameEngine.getClientsId().contains(10));
        assertEquals(1, gameEngine.getClientsId().size());

    }

    @Test
    public void multipleRegistrationFailTest(){

        testClient = new HTTPClient(10, "foo", "http://localhost:8080", false);

        ResponseContainer resp = this.restTemplate.postForObject("/register/",testClient, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "You joined the game"), resp);

        // Second registration with the same id
        resp = this.restTemplate.postForObject("/register/",testClient, ResponseContainer.class);
        assertEquals(new ResponseContainer(false, "Already registered"), resp);

    }

    @Test
    public void multipleRegistrationGameStart(){
        testClient = new HTTPClient(10, "foo", "http://localhost:8080", false);
        HTTPClient testClient2 = new HTTPClient(25, "foo", "http://localhost:8080", false);

        ResponseContainer resp = this.restTemplate.postForObject("/register/",testClient, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "You joined the game"), resp);

        resp = this.restTemplate.postForObject("/register/",testClient2, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "You joined the game"), resp);

        // At this point the game has started
        assertTrue(gameEngine.isGameStarted());


        // One more player want to register
        HTTPClient intru = new HTTPClient(50, "foo", "http://localhost:8080", false);
        resp = this.restTemplate.postForObject("/register/",intru, ResponseContainer.class);
        assertEquals(new ResponseContainer(false, "Game has started"), resp);

        // Let's check who is in the game
        assertEquals(2, gameEngine.getClients().size());
        assertTrue(gameEngine.getClientsId().containsAll(Arrays.asList(10,25)));

    }

    @Test
    public void gameEndedTest() {
        testClient = new HTTPClient(10, "foo", "http://localhost:8080", false);
        HTTPClient testClient2 = new HTTPClient(25, "foo", "http://localhost:8081", false);

        ResponseContainer resp = this.restTemplate.postForObject("/register/",testClient, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "You joined the game"), resp);

        resp = this.restTemplate.postForObject("/register/",testClient2, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "You joined the game"), resp);

        // At this point the game has started
        assertTrue(gameEngine.isGameStarted());


        // One more player want to register
        HTTPClient intru = new HTTPClient(50, "foo", "http://localhost:8080", false);
        resp = this.restTemplate.postForObject("/register/",intru, ResponseContainer.class);
        assertEquals(new ResponseContainer(false, "Game has started"), resp);

        // Game has started;

        resp = this.restTemplate.getForObject("/gameEnded", ResponseContainer.class);
        assertEquals(new ResponseContainer(false, ""), resp);

        int i = 1;
        // We force the game to stop
        while (!gameEngine.gameEnded()){
            gameEngine.getPlateau().poserTuile(new CoordAxial(i++, 0), new Tuile(0, Couleur.BLEU, Amenagement.NONE));
        }

        resp = this.restTemplate.getForObject("/gameEnded", ResponseContainer.class);
        assertEquals(new ResponseContainer(true, ""), resp);
    }

    @Test
    public void endTurnTest(){
        testClient = new HTTPClient(10, "foo", "http://localhost:8080", false);
        HTTPClient testClient2 = new HTTPClient(25, "foo", "http://localhost:8081", false);

        ResponseContainer resp = this.restTemplate.postForObject("/register/",testClient, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "You joined the game"), resp);

        resp = this.restTemplate.postForObject("/register/",testClient2, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "You joined the game"), resp);

        // At this point the game has started
        assertTrue(gameEngine.isGameStarted());

        assertEquals(2, gameEngine.getClients().size());
        assertTrue(gameEngine.getClientsId().containsAll(Arrays.asList(10,25)));

        int firstPlayer = gameEngine.getClients().get(gameEngine.getCurrentPlayerIndex()).getId();
        int secondPlayer = firstPlayer == 10 ? 25 : 10;

        assertEquals(10, gameEngine.getCurrentPlayer().getId());
        resp = this.restTemplate.getForObject(String.format("/end_turn?playerId=%d", firstPlayer), ResponseContainer.class);
        //assertEquals(new ResponseContainer(true, "Player 25 have to play"), resp);
        assertEquals(25, gameEngine.getCurrentPlayer().getId());


        resp = this.restTemplate.getForObject(String.format("/end_turn?playerId=%d", secondPlayer), ResponseContainer.class);
        //assertEquals(new ResponseContainer(true, "Player 10 have to play"), resp);
        assertEquals(10, gameEngine.getCurrentPlayer().getId());

        // Player send end turn. Lets check if the player 10 is still the current player
        resp = this.restTemplate.getForObject(String.format("/end_turn?playerId=%d", secondPlayer), ResponseContainer.class);
        //assertEquals(new ResponseContainer(false, "Player 10 have to play, it is not your turn."), resp);
        assertEquals(10, gameEngine.getCurrentPlayer().getId());

        // Someone without id send end turn
        resp = this.restTemplate.getForObject("/end_turn", ResponseContainer.class);
        //assertEquals(new ResponseContainer(false, "Player 10 have to play, it is not your turn."), resp);
        assertEquals(10, gameEngine.getCurrentPlayer().getId());

        // get back to legal requests and see if the turn loop hasn't changed
        resp = this.restTemplate.getForObject(String.format("/end_turn?playerId=%d", firstPlayer), ResponseContainer.class);
        //assertEquals(new ResponseContainer(true, "Player 25 have to play"), resp);
        assertEquals(25, gameEngine.getCurrentPlayer().getId());

    }





}
