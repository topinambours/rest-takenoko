package Takenoko.Joueur.Strategie;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieAction.Action;
import Takenoko.Joueur.Strategie.StrategieAction.StrategieAction;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoord;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrig;
import Takenoko.Joueur.Strategie.StrategieJardinier.StrategieJardinier;
import Takenoko.Joueur.Strategie.StrategiePanda.StrategiePanda;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

public class StrategieConcrete extends AbstractStrategie {
    private StrategieCoord strategieCoord;
    private StrategieIrrig strategieIrrig;
    private StrategiePanda strategiePanda;
    private StrategieJardinier strategieJardinier;
    private StrategieAction strategieAction;

    public StrategieConcrete(StrategieCoord strategieCoord,
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
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        return strategieCoord.getCoords(p, plot);
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        return strategieCoord.getCoords(p);
    }

    @Override
    public CoordAxial getCoord(Plateau p, Plot plot) {
        return strategieCoord.getCoord(p, plot);
    }

    @Override
    public CoordAxial getCoord(Plateau p) {
        return strategieCoord.getCoord(p);
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
}
