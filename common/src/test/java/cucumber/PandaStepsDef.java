package cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import takenoko.Couleur;
import takenoko.Plateau;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import static junit.framework.TestCase.*;


public class PandaStepsDef {

    private Plateau plateau;
    private CoordAxial coordAxial;

    @Ignore
    @Given("Un plateau de depart")
    public void generate_board(){
        plateau = new Plateau().plateau_depart();
    }

    @Ignore
    @Then("Je ne peux pas deplacer le panda")
    public void cant_move_panda(){
        assertEquals(plateau.computePandaLegalPositions().size(),0);
    }

    @Ignore
    @When("Je place une tuile en {int},{int}")
    public void putPlot(int q,int r){
        plateau.poserTuile(new CoordAxial(q,r),new Tuile(plateau.generateTuileMap().size(), Couleur.VERT));
    }

    @Ignore
    @Then("Je peux bouger le panda")
    public void can_move_panda(){
        assertTrue(plateau.computePandaLegalPositions().size() > 0);
    }

    @Ignore
    @Then("Je peux déplacer le panda en {int},{int}")
    public void can_move_at(int q,int r){
        assertTrue(plateau.computePandaLegalPositions().contains(new CoordAxial(q,r)));
    }

    @Ignore
    @Then("Je ne peux pas déplacer le panda en {int},{int}")
    public void can_not_move_at(int q,int r){
        assertFalse(plateau.computePandaLegalPositions().contains(new CoordAxial(q,r)));
    }

    @Ignore
    @When("Je déplace le panda en {int},{int}")
    public void panda_move(int q,int r){
        coordAxial = new CoordAxial(q,r);
    }

    @Ignore
    @Then("Je ramasse des bambous de couleur {couleur}")
    public void panda_eat(Couleur couleur){
        assertEquals(plateau.movePanda(coordAxial),couleur);
    }

    @Ignore
    @Given("Une tuile de couleur {couleur} en {int},{int} avec bambou de taille {int}")
    public void given_plot(Couleur c,int q,int r,int b){
        plateau.poserTuile(new CoordAxial(q,r),new Tuile(-1,c));
        plateau.getTuileAtCoord(new CoordAxial(q,r)).setNbBambous(b);
    }

    @Ignore
    @Then("Le panda ne ramasse pas de bambou")
    public void panda_dont_eat(){
        assertEquals(plateau.movePanda(coordAxial),Couleur.BLEU);
    }

    @Ignore
    @Then("La tuile en {int},{int} contient un bambou de taille {int}")
    public void plot_bambou_number(int q,int r,int bambous){
        assertEquals(plateau.getTuileAtCoord(new CoordAxial(q,r)).getNbBambous(),bambous);
    }

    @Ignore
    @When("La tuile en {int},{int} contient l'aménagment {amenagement}")
    public void plot_amenagement(int q,int r,Amenagement amenagement){
        plateau.getTuileAtCoord(new CoordAxial(q,r)).setAmenagement(amenagement);
    }

}
