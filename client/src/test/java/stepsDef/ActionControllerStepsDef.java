package stepsDef;

import communication.HTTPClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;

import static org.junit.Assert.*;

@Ignore
public class ActionControllerStepsDef extends SpringIntegrationTest{

    private int lastIdReceive;

    @Given("I am a client with the id {int}")
    public void set_id(int playerId){
        joueur.setHttpClient(
                new HTTPClient(
                        playerId,
                joueur.getHttpClient().getUser_adress(),
                joueur.getHttpClient().getServer_url(), false));
        assertEquals(playerId, joueur.getId());
    }

    @When("Client receive notification that it's the turn of player {int}")
    public void notif_receive(int playerId){
        lastIdReceive = playerId;
        ac.current_player(playerId);
    }

    @Then("Client know that the current player is the player {int}")
    public void notif_other(int playerId){
        assertEquals(lastIdReceive, playerId);
    }

    @Then("It is the turn of the client")
    public void it_is_my_turn(){
        assertTrue(joueur.myTurn);
    }

    @Then("It is not the turn of the client")
    public void it_is_not_my_turn(){
        assertFalse(joueur.myTurn);
    }
}
