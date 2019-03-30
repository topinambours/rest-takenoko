package core.controllers;

import communication.Container.ResponseContainer;
import communication.HTTPClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConnectionControllerTest extends CucumberStepDefinitions {

    private HTTPClient testClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void pingTest() {
        testClient = new HTTPClient(0);
        String req = String.format("/ping/%s/%s", testClient.getId(), testClient.getUser_adress());
        ResponseContainer resp = this.restTemplate.getForObject(req, ResponseContainer.class);
        assertEquals(resp, new ResponseContainer(true, "pong"));
    }

    @Test
    public void registrationTest(){
        testClient = new HTTPClient(1);
        String req = String.format("/register/%s/%s", testClient.getId(), testClient.getUser_adress());
        ResponseContainer resp = this.restTemplate.getForObject(req, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "Registration complete"), resp);
    }

    @Test
    public void multipleRegistrationTest(){

        testClient = new HTTPClient(2);
        String req = String.format("/register/%s/%s", testClient.getId(), testClient.getUser_adress());
        ResponseContainer resp = this.restTemplate.getForObject(req, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "Registration complete"), resp);

        // Second registration with the same id
        resp = this.restTemplate.getForObject(req, ResponseContainer.class);
        assertEquals(new ResponseContainer(false, "Already registered"), resp);

    }


    /**
     * Cucumber step def
     */

    private ConnectionController t;

    @Ignore
    @When("ping received from user {int} with address {string}")
    public void ping_is_performed(int id, String url) {
        t.ping_received(id, url);
    }

    @Ignore
    @Then("the client with id {int} and address {string} receive pong")
    public void client_receive_pong(int id, String url) {
        //ConnectionController controller = new ConnectionController();
        assertEquals(new ResponseContainer(true, "pong"), t.ping_received(id, url));
    }

    @Ignore
    @Given("using fresh connection controller")
    public void generate_fresh_conn_controller(){
        t = new ConnectionController();
    }

    @Ignore
    @When("registration request from user {int} with address {string}")
    public void registration_request(int id, String url) {
        t.registration_req(id, url);
    }

    @Ignore
    @Then("The number of registered user is {int}")
    public void number_of_registered_is(int id){
        assertEquals(id, t.getRegisteredUsers().size());
    }


}
