package client.controller;

import communication.container.ResponseContainer;
import client.Joueur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    Joueur joueur;

    @Test
    public void first_valid_notification() {
        ResponseContainer resp = this.restTemplate.getForObject("/notify/10",  ResponseContainer.class);

        // This is normaly send back to server
        assertEquals(new ResponseContainer(true, "Player 10 aware that he have to play."), resp);

        assertTrue(joueur.myTurn);
    }

    @Test
    public void close_app_notification() {
        ResponseContainer resp = this.restTemplate.getForObject("/closeApplication/test",  ResponseContainer.class);

        // This is normaly send back to server
        assertEquals(new ResponseContainer(true, String.format("Application of player %d will closed.", joueur.getId())), resp);
    }

    @Test
    public void ping() {
        String resp = this.restTemplate.getForObject("/ping",  String.class);

        // This is normaly send back to server
        assertEquals("pong", resp);
    }

    @Test
    public void two_notification_same_player(){
        first_valid_notification();
        // Let's make one more again
        ResponseContainer resp = this.restTemplate.getForObject("/notify/10",  ResponseContainer.class);

        // This is normaly send back to server
        assertEquals(new ResponseContainer(false, "Player 10 already know that he have to play"), resp);

        assertTrue(joueur.myTurn);
    }

    @Test
    public void other_notifications(){
        // It's the turn of player 25
        ResponseContainer resp = this.restTemplate.getForObject("/notify/25",  ResponseContainer.class);

        // This is normaly send back to server
        assertEquals(new ResponseContainer(true, "Player 10 know that it is the turn of player 25"), resp);

        assertFalse(joueur.myTurn);

        // Let's run the first test
        first_valid_notification();

    }

}