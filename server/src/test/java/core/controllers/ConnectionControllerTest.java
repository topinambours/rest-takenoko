package core.controllers;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

@Ignore
public class ConnectionControllerTest extends CucumberStepDefinitions {

    @Autowired
    ConnectionController controller;

    @When("^ping is performed$")
    public void ping_is_performed(){
        controller.status_req();
    }

    @Then("^the client receives status \"server is up\"$")
    public void receive_snek_snek()  {
        assertEquals("Server is up", controller.status_req());
    }



}
