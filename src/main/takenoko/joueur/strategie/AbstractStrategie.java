package takenoko.joueur.strategie;

import takenoko.Game;
import takenoko.deck.AmenagementDecks;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieAction.Action;
import takenoko.joueur.strategie.StrategieAction.StrategieAction;
import takenoko.joueur.strategie.StrategieAmenagement.StrategieAmenagement;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoord;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrig;
import takenoko.joueur.strategie.StrategieJardinier.StrategieJardinier;
import takenoko.joueur.strategie.StrategiePanda.StrategiePanda;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.Plateau;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

import java.util.List;
import java.util.Optional;

public abstract class AbstractStrategie implements StrategieCoord, StrategieIrrig, StrategieJardinier, StrategiePanda, StrategieAction, StrategieAmenagement {

    public abstract String getStrategieLabel();

    public abstract Optional getIrrig(Plateau P);

    public abstract CoordAxial getJardinierMove(Plateau plateau, Joueur joueur);

    public abstract CoordAxial getPandaMove(Plateau plateau, Joueur joueur);

    public abstract Action firstActionType(Game game);

    public abstract Action secondActionType(Game game);

    public abstract Action thirdActionType(Game game);

    public abstract Amenagement chooseAmenagement(AmenagementDecks deckAmenagement);

    public abstract Optional<Plot> plotAmenagement(Joueur joueur, Plateau plateau);

    public abstract void setStrategieCoord(StrategieCoord strategieCoord);

    public abstract void setStrategieIrrig(StrategieIrrig strategieIrrig);

    public abstract void setStrategiePanda(StrategiePanda strategiePanda);

    public abstract void setStrategieJardinier(StrategieJardinier strategieJardinier);

    public abstract void setStrategieAction(StrategieAction strategieAction);


    public abstract void setStrategieAmenagement(StrategieAmenagement strategieAmenagement);



}
