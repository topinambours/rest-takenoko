package cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import takenoko.Plateau;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.versionning.Action;
import takenoko.versionning.ActionType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class VersionStepsDef<T> {
    private Plateau plateauServer;
    private Plateau plateauClient;

    private List<Action> actionList;

    //params
    private CoordAxial coordAxial;
    private ActionType actionType;


    @Ignore
    @Given("Un plateau Serveur initial ainsi qu'un plateau client initial")
    public void initPlateau(){
        plateauServer = new Plateau().plateau_depart();
        plateauClient = new Plateau().plateau_depart();
        actionList = new ArrayList<>();
    }

    /*@Ignore
    @Given("Une nouvelle version d'action {actiontype}")
    public void newVersion(ActionType type){
        actionType = type;
    }*/

    @Ignore
    @Given("De paramètre CoordAxial {int},{int}")
    public void paramsCoord(int q,int r){
        coordAxial = new CoordAxial(q,r);
    }

    /*@Ignore
    @When("Cette nouvelle version {actiontype} est présente sur le serveur")
    public void applyServer(ActionType actionType){
        switch (actionType){
            case PUTPLOT:
                plateauServer.poserTuile(coordAxial,new Tuile());
                actionList.add(new Action(ActionType.PUTPLOT,coordAxial,new Tuile()));
                break;
            default: break;
        }

    }*/


    @Ignore
    @When("Nous appliquons les versions en attente sur le plateau client")
    public void applyAll(){
        Iterator<Action> iterator = actionList.iterator();
        while (iterator.hasNext()){
            Action.applyAction(iterator.next(),plateauClient);
        }
    }

    @Ignore
    @Then("Le plateau serveur et client sont identique")
    public void equalPlateau(){
        assertEquals(plateauServer,plateauClient);
    }
}
