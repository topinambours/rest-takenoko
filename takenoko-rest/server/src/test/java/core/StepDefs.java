package core;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

class StepDefs  {
    private String status;

    @When("^I ask for server status$")
    public void i_ask_for_server_status() {
        status = ConnectionController.getStatus();
        throw new cucumber.api.PendingException();
    }

    @Then("^I should be told \"([^\"]*)\"$")
    public void i_should_be_told(String expectedAnswer) {
        assertEquals(expectedAnswer, status);
        throw new cucumber.api.PendingException();
    }
}
