package cucumber.stepDef;

import communication.container.TuileContainer;
import core.controllers.DeckController;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import pioche.EmptyDeckException;
import pioche.PiocheTuile;
import takenoko.Couleur;
import takenoko.tuile.Tuile;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;


@Ignore
public class CucumberStepDefinitions {


    private DeckController d;
    private TuileContainer t2;

    /**
     * Cucumber step def
     */

    @When("Un joueur pioche 3 tuiles")
    public void pioche_is_requested() throws EmptyDeckException {
        t2 = d.req_pioche();
    }

    @When("le joueur rend les deux tuiles de sa pioche")
    public void joueur_rend_surplus(){
        d.req_rendre_tuiles(t2.getContent().get(1).getUnique_id(), t2.getContent().get(2).getUnique_id());
    }

    @Then("La taille de la pioche est de {int}")
    public void size_of_pioche(int size) {
        assertEquals(size, d.getpTuile().size());
    }

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