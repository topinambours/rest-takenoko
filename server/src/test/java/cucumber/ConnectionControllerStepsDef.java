package cucumber;

import communication.HTTPClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;

import static org.junit.Assert.*;

@Ignore
public class ConnectionControllerStepsDef extends SpringIntegrationTest {

    @Given("a fresh gameEngine of size {int}")
    public void fresh_cc(int size){
        // new game bean instanciated with @DirtiesContext of @SpringIntegrationTest.class
        game.setGameSize(size);
    }

    @Then("The number of registered users is {int}")
    public void check_number_registered_users(int number){
        assertEquals(number, game.getClients().size());
    }

    @When("a new client with id {int} and address {string} register to the game")
    public void ping_received(int id, String usr_url ){
        cc.register(new HTTPClient(id, usr_url, "http://localhost:8080", false));
    }

    @Then("the game has started")
    public void theGameHasStarted(){
        assertTrue(game.isGameStarted());
    }
    @Then("the game has not started")
    public void theGameHasNotStarted(){
        assertFalse(game.isGameStarted());
    }
}
