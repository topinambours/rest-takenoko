import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import core.controllers.ConnectionController;
import core.controllers.DeckController;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pioche.EmptyDeckException;
import pioche.PiocheTuile;
import takenoko.Couleur;
import takenoko.tuile.Tuile;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;


public class CucumberStepDefinitions {

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


    private DeckController d;
    private TuileContainer t2;

    /**
     * Cucumber step def
     */

    @Ignore
    @When("Un joueur pioche 3 tuiles")
    public void pioche_is_requested() throws EmptyDeckException {
        t2 = d.req_pioche();
    }

    @Ignore
    @When("le joueur rend les deux tuiles de sa pioche")
    public void joueur_rend_surplus(){
        d.req_rendre_tuiles(t2.getContent().get(1).getUnique_id(), t2.getContent().get(2).getUnique_id());
    }

    @Ignore
    @Then("La taille de la pioche est de {int}")
    public void size_of_pioche(int size) {
        assertEquals(size, d.getpTuile().size());
    }

    @Ignore
    @Given("using fresh deck controller")
    public DeckController generate_fresh_deck_controller_2(){
        ArrayList<Tuile> tuiles = new ArrayList<>();
        for (int i = 1; i <= 27; i++) {
            if (i <= 11) tuiles.add(new Tuile(i, Couleur.VERT));
            if (i > 11 && i <= 18) tuiles.add(new Tuile(i, Couleur.ROSE));
            if (i > 18) tuiles.add(new Tuile(i, Couleur.JAUNE));
        }
        d = new DeckController(new PiocheTuile(tuiles));
        return d;
    }
}