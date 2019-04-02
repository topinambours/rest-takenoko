package core.controllers;

import communication.container.TuileContainer;
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
import pioche.EmptyDeckException;
import pioche.PiocheTuile;
import takenoko.Couleur;
import takenoko.tuile.Tuile;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeckControllerTest extends CucumberStepDefinitions {

    private DeckController d;
    private TuileContainer t;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void req_pioche() throws EmptyDeckException {
        d = generate_fresh_deck_controller();
        List<Tuile> backup = d.getpTuile().toContainer().getContent();

        String req = "/action/piocher";
        TuileContainer resp = this.restTemplate.getForObject(req, TuileContainer.class);
        // Le joueur a bien pioché 3 tuiles ?
        assertEquals(3,resp.getContent().size());

        resp = d.req_pioche();
        List<Tuile> after = d.getpTuile().toContainer().getContent();

        for (Tuile t : resp.getContent()){
            assertTrue(backup.contains(t));
            assertFalse(after.contains(t));
        }

    }

    @Test
    public void req_rendre_tuiles() throws EmptyDeckException {
        d = generate_fresh_deck_controller();
        assertEquals(27, d.getpTuile().toContainer().getContent().size());
        List<Tuile> backup = d.getpTuile().toContainer().getContent();

        String req = "/action/piocher";
        TuileContainer resp = this.restTemplate.getForObject(req, TuileContainer.class);
        // Le joueur a bien pioché 3 tuiles ?
        assertEquals(3,resp.getContent().size());

        resp = d.req_pioche();
        List<Tuile> after = d.getpTuile().toContainer().getContent();

        for (Tuile t : resp.getContent()){
            assertTrue(backup.contains(t));
            assertFalse(after.contains(t));
        }
        // Le joueur rend les tuiles ici

        d.req_rendre_tuiles(resp.getContent().get(1).getUnique_id(), resp.getContent().get(2).getUnique_id());

        assertTrue(d.getpTuile().toContainer().getContent()
                .contains(resp.getContent().get(1)));

        assertTrue(d.getpTuile().toContainer().getContent()
                .contains(resp.getContent().get(2)));

        assertEquals(26, d.getpTuile().toContainer().getContent().size());

    }


    /**
     * Cucumber step def
     */

    @Ignore
    @When("Un joueur pioche 3 tuiles")
    public void pioche_is_requested() throws EmptyDeckException {
        t = d.req_pioche();
    }

    @Ignore
    @When("le joueur rend les deux tuiles de sa pioche")
    public void joueur_rend_surplus(){
        d.req_rendre_tuiles(t.getContent().get(1).getUnique_id(), t.getContent().get(2).getUnique_id());
    }

    @Ignore
    @Then("La taille de la pioche est de {int}")
    public void size_of_pioche(int size) {
        assertEquals(size, d.getpTuile().size());
    }

    @Ignore
    @Given("using fresh deck controller")
    public DeckController generate_fresh_deck_controller(){
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