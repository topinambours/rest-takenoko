package takenoko.joueur.strategie;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
import takenoko.joueur.strategie.StrategiePanda.StrategiePandaBasique;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;

import java.util.List;
import java.util.Optional;

@Component
public class StrategieConcrete extends AbstractStrategie {
    private StrategieCoord strategieCoord;
    private StrategieIrrig strategieIrrig;
    private StrategiePanda strategiePanda;
    private StrategieJardinier strategieJardinier;
    private StrategieAction strategieAction;
    private StrategieAmenagement strategieAmenagement;

    public StrategieConcrete(StrategieCoord strategieCoord,
                             StrategieIrrig strategieIrrig,
                             StrategiePanda strategiePanda,
                             StrategieJardinier strategieJardinier,
                             StrategieAction strategieAction,StrategieAmenagement strategieAmenagement) {
        this.strategieCoord = strategieCoord;
        this.strategieIrrig = strategieIrrig;
        this.strategiePanda = strategiePanda;
        this.strategieJardinier = strategieJardinier;
        this.strategieAction = strategieAction;
        this.strategieAmenagement = strategieAmenagement;
    }

    public StrategieConcrete(StrategieCoord strategieCoord, StrategieIrrig strategieIrrig) {
        this.strategieCoord = strategieCoord;
        this.strategieIrrig = strategieIrrig;
        this.strategiePanda = null;
        this.strategieJardinier = null;
        this.strategieAction = null;
    }


    public StrategieConcrete() {
        this.strategieCoord = null;
        this.strategieIrrig = null;
        this.strategiePanda = null;
        this.strategieJardinier = null;
        this.strategieAction = null;
    }

    public void initialize(StrategieCoord strategieCoord,
                           StrategieIrrig strategieIrrig,
                           StrategiePanda strategiePanda,
                           StrategieJardinier strategieJardinier,
                           StrategieAction strategieAction) {
        this.strategieCoord = strategieCoord;
        this.strategieIrrig = strategieIrrig;
        this.strategiePanda = strategiePanda;
        this.strategieJardinier = strategieJardinier;
        this.strategieAction = strategieAction;
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
        return getStrategieCoordLabel();
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
        return strategieJardinier.getJardinierMove(plateau, joueur);
    }

    @Override
    public CoordAxial getPandaMove(Plateau plateau, Joueur joueur) {
        return strategiePanda.getPandaMove(plateau, joueur);
    }

    @Override
    public String getStrategieJardinierLabel() {
        return strategieJardinier.getStrategieJardinierLabel();
    }

    @Override
    public String getStrategiePandaLabel() {
        return strategiePanda.getStrategiePandaLabel();
    }

    @Override
    public Action firstActionType(Game game) {
        return strategieAction.firstActionType(game);
    }

    @Override
    public Action secondActionType(Game game) {
        return strategieAction.secondActionType(game);
    }

    @Override
    public Action thirdActionType(Game game) {
        return strategieAction.thirdActionType(game);
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
        this.strategiePanda = strategiePanda;
    }

    @Override
    public void setStrategieJardinier(StrategieJardinier strategieJardinier) {
        this.strategieJardinier = strategieJardinier;
    }

    @Override
    public void setStrategieAction(StrategieAction strategieAction) {
        this.strategieAction = strategieAction;
    }

    @Override
    public void setStrategieAmenagement(StrategieAmenagement strategieAmenagement) {
        this.strategieAmenagement = strategieAmenagement;
    }

    @Override
    public Amenagement chooseAmenagement(AmenagementDecks deckAmenagement) {
        Amenagement choice = null;
        try {
            choice = strategieAmenagement.chooseAmenagement(deckAmenagement);
        } catch (EmptyDeckException e) {
            Console.Log.debugPrint(e.getMessage());
        }
        return choice;
    }

    @Override
    public Optional<Plot> plotAmenagement(Joueur joueur, Plateau plateau) {
        return this.strategieAmenagement.plotAmenagement(joueur, plateau);
    }

    @Bean
    @Scope("prototype")
    public StrategieConcrete createStratRandom() {
        StrategieConcrete strategieConcrete = new StrategieConcrete();
        strategieConcrete.setStrategiePanda(new StrategiePandaBasique());
        return strategieConcrete;
    }
}
