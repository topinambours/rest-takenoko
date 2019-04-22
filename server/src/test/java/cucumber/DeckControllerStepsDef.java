package cucumber;

import communication.container.TuileContainer;
import core.GameEngine;
import core.controllers.DeckController;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import core.takenoko.pioche.EmptyDeckException;
import org.springframework.beans.factory.annotation.Autowired;
import takenoko.tuile.Tuile;

import java.util.List;

import static junit.framework.TestCase.*;


@Ignore
public class DeckControllerStepsDef {

    @Autowired
    private GameEngine game;

    @Autowired
    private DeckController d;

    private TuileContainer t2;
    private Tuile selected;

    @When("Player {int} draw 3 plots")
    public void pioche_is_requested(int playerId) throws EmptyDeckException {
        t2 = d.req_pioche(playerId);
    }

    @Then("The player get {int} plots")
    public void pioche_check_count(int amount) throws EmptyDeckException {
        assertEquals(amount, t2.getContent().size());
    }

    @Then("Player choose one plot and packed remaining for the deck")
    public void playerSelectCard(){
        if (t2.getContent().size() >= 1){
            selected = t2.getContent().get(0);
            List<Tuile> out = t2.getContent();
            out.remove(selected);
            t2 = new TuileContainer(out);
        }
        assertFalse(t2.getContent().contains(selected));
    }

    @When("Player {int} send back extra plots")
    public void joueur_rend_surplus(int playerId){
        d.rendre_tuiles(playerId, t2);
    }

    @Then("Size of the deck is {int}")
    public void size_of_pioche(int size) {
        assertEquals(size, game.getPiocheTuile().size());
    }

}