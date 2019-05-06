package cucumber;

import communication.container.BambouContainer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import takenoko.Couleur;
import takenoko.tuile.Amenagement;
import takenoko.tuile.Tuile;

import static junit.framework.TestCase.*;

public class TuileStepsDef {

    private Tuile t;

    @Ignore
    @Given("Une tuile {couleur}")
    public void generate_tuile_couleur(Couleur c){
        t = new Tuile(1, c);
    }

    @Ignore
    @Given("Une tuile {couleur} avec un aménagement {amenagement}")
    public void generate_tuile_couleur_amenagement(Couleur c, Amenagement a){
        t = new Tuile(1, c, a);
    }

    @Ignore
    @Then("Ma tuile est irrigué")
    public void tuile_irrigue(){
        assertTrue(t.getHaveWater());
    }

    @Ignore
    @Then("Ma tuile n'est pas irrigué")
    public void tuile_pas_irrigue(){
        assertFalse(t.getHaveWater());
    }

    @Ignore
    @When("J'irrigue ma tuile")
    public void irrigation(){
        t.irrigate();
    }

    @Ignore
    @Then("Ma tuile ne contient aucun bambou")
    public void aucun_bambou(){
        assertEquals(0, t.getNbBambous());
    }

    @Ignore
    @Then("Je peux faire pousser des bambous")
    public void pousse_possible(){
        assertTrue(t.poussePossible());
    }

    @Ignore
    @Then("Je ne peux pas faire pousser des bambous")
    public void pousse_pas_possible(){
        assertFalse(t.poussePossible());
    }

    @Ignore
    @When("Je fais pousser un bambou")
    public void pousser(){
        t.pousserBambou();
    }

    @Ignore
    @When("Je fais pousser un bambou {int} fois")
    public void pousser_time(int n){
        for (int i = 0; i < n; i++) {
            t.pousserBambou();
        }
    }

    @Ignore
    @When("Je retire un bambou")
    public void retirer(){
        t.enleverBambou();
    }

    @Ignore
    @When("Je retire un bambou {int} fois")
    public void retirer(int n){
        for (int i = 0; i < n; i++) {
            t.enleverBambou();
        }
    }

    @Ignore
    @When("Je retire un bambou, j'obtient {int} bambou(s) de couleur {couleur} et rien d'autre")
    public void retirer_et_garder(int n, Couleur c){
        BambouContainer res = t.enleverBambou();

        for (Couleur couleur : Couleur.values()){
            if (couleur != c){
                assertEquals(0, res.getBambouByColor(couleur));
            }
            else{
                assertEquals(n, res.getBambouByColor(couleur));
            }
        }
    }

    @Ignore
    @Then("Ma tuile contient {int} bambou(s)")
    public void check_content(int val){
        assertEquals(val, t.getNbBambous());
    }




    /**
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
    **/
}
