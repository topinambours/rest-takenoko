package cucumber.stepDef;

import communication.container.ResponseContainer;
import core.controllers.ConnectionController;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

@Ignore
public class ConnectionControllerStepDefinitions {

    @Autowired
    private ConnectionController cc;
    private ResponseContainer lastResponse;

    @Given("Using fresh connection controller")
    public void fresh_cc(){
        cc = new ConnectionController();
    }

    @When("Ping is received from client {int} with url {string}")
    public void ping_received(int id, String usr_url ){
        lastResponse = cc.ping_received(id, usr_url);
    }

    @When("Client with id {int} and address {string} request for registration")
    public void registration_req(int id, String usr_url ){
        lastResponse = cc.registration_req(id, usr_url);
    }

    @Then("The number of registered users is {int}")
    public void check_number_registered_users(int number){
        assertEquals(number, cc.getRegisteredUsers().size());
    }

    @Then("Client received {word} with the message {string}")
    public void check_response(String flag, String msg){
        Boolean flag_2 = flag.equals("true");
        assertEquals(flag_2, lastResponse.response);
        assertEquals(msg, lastResponse.message);
    }

    @Then("Server know that the client {int} have the address {string}")
    public void check_registration(int id, String address){
        assertEquals(address, cc.getRegisteredUsers().get(id).getUser_adress());
    }

    @When("Client with id {int} request for matchmaking for games of size {int}")
    public void matchmaking_req(int id, int gameSize ){
        lastResponse = cc.matchmaking_req(id, gameSize);
    }

    @Then("The number of waiting client in queue for game of size {int} is {int}")
    public void check_mutliqueue(int gamesize, int expected){
        assertEquals(expected, cc.getQueues().getQueue(gamesize).size());
    }


    /*
    private ConnectionController t;

    @When("ping received from user {int} with address {string}")
    public void ping_is_performed(int id, String url) {
        t.ping_received(id, url);
    }

    @Then("the client with id {int} and address {string} receive pong")
    public void client_receive_pong(int id, String url) {
        //ConnectionController controller = new ConnectionController();
        TestCase.assertEquals(new ResponseContainer(true, "pong"), t.ping_received(id, url));
    }

    @Given("using fresh connection controller")
    public void generate_fresh_conn_controller(){
        t = new ConnectionController();
    }

    @When("registration request from user {int} with address {string}")
    public void registration_request(int id, String url) {
        t.registration_req(id, url);
    }

    @Then("The number of registered user is {int}")
    public void number_of_registered_is(int id){
        TestCase.assertEquals(id, t.getRegisteredUsers().size());
    }
    */

}
