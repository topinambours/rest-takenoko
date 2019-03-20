package takenoko.joueur.strategie;

import takenoko.Game;
import takenoko.Plateau;
import takenoko.deck.AmenagementDecks;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieAction.Action;
import takenoko.joueur.strategie.StrategieAction.StrategieAction;
import takenoko.joueur.strategie.StrategieAmenagement.StrategieAmenagement;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoord;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordResult;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrig;
import takenoko.joueur.strategie.StrategieJardinier.StrategieJardinier;
import takenoko.joueur.strategie.StrategiePanda.StrategiePanda;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

import java.util.List;
import java.util.Optional;

public class StrategieSansPions extends AbstractStrategie {
    private StrategieCoord strategieCoord;
    private StrategieIrrig strategieIrrig;

    public StrategieSansPions(StrategieCoord strategieCoord, StrategieIrrig strategieIrrig) {
        this.strategieCoord = strategieCoord;
        this.strategieIrrig = strategieIrrig;
    }

    public StrategieSansPions() {
        this.strategieCoord = null;
        this.strategieIrrig = null;
    }

    public void initialize(StrategieCoord strategieCoord, StrategieIrrig strategieIrrig) {
        this.strategieCoord = strategieCoord;
        this.strategieIrrig = strategieIrrig;
    }

    @Override
    public StrategieCoordResult getDecision(Joueur joueur, Plateau plateau, List<Plot> plots) {
        return strategieCoord.getDecision(joueur, plateau, plots);
    }

    @Override
    public String getStrategieLabel() {
        return getStrategieCoordLabel() + " " + getStrategieIrrigLabel();
    }

    @Override
    public String getStrategieCoordLabel() {
        return strategieCoord.getStrategieCoordLabel();
    }

    @Override
    public String getStrategieIrrigLabel() {
        return strategieIrrig.getStrategieIrrigLabel();
    }

    @Override
    public Optional getIrrig(Plateau p) {
        return strategieIrrig.getIrrig(p);
    }

    @Override
    public CoordAxial getJardinierMove(Plateau plateau, Joueur joueur) {
        return null;
    }

    @Override
    public CoordAxial getPandaMove(Plateau plateau, Joueur joueur) {
        return null;
    }

    @Override
    public String getStrategieJardinierLabel() {
        return "Dummy Jardinier";
    }

    @Override
    public String getStrategiePandaLabel() {
        return "Dummy Panda";
    }

    @Override
    public Action firstActionType(Game game) {
        return null;
    }

    @Override
    public Action secondActionType(Game game) {
        return null;
    }

    @Override
    public Action thirdActionType(Game game) {
        return null;
    }

    @Override
    public void setStrategieCoord(StrategieCoord strategieCoord) {
        this.strategieCoord = strategieCoord;
    }

    @Override
    public void setStrategieIrrig(StrategieIrrig strategieIrrig) {
        this.strategieIrrig = strategieIrrig;
    }

    @Override
    public void setStrategiePanda(StrategiePanda strategiePanda) {
    }

    @Override
    public void setStrategieJardinier(StrategieJardinier strategieJardinier) {
    }

    @Override
    public void setStrategieAction(StrategieAction strategieAction) {
    }

    @Override
    public Amenagement chooseAmenagement(AmenagementDecks deckAmenagement) {
        return null;
    }

    @Override
    public Optional<Plot> plotAmenagement(Joueur joueur, Plateau plateau) {
        return Optional.empty();
    }

    @Override
    public void setStrategieAmenagement(StrategieAmenagement strategieAmenagement) {

    }
}
