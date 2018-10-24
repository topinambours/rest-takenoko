package Takenoko.Joueur.Strategie;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieAction.Action;
import Takenoko.Joueur.Strategie.StrategieAction.StrategieAction;
import Takenoko.Joueur.Strategie.StrategieAmenagement.StrategieAmenagement;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoord;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrig;
import Takenoko.Joueur.Strategie.StrategieJardinier.StrategieJardinier;
import Takenoko.Joueur.Strategie.StrategiePanda.StrategiePanda;
import Takenoko.Objectives.Amenagement.Amenagement;
import Takenoko.Objectives.Amenagement.DeckAmenagement;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

public abstract class AbstractStrategie implements StrategieCoord, StrategieIrrig, StrategieJardinier, StrategiePanda, StrategieAction, StrategieAmenagement {

    public abstract List<CoordAxial> getCoords(Plateau p, Plot plot);

    public abstract List<CoordAxial> getCoords(Plateau p);

    public abstract CoordAxial getCoord(Plateau p, Plot plot);

    public abstract CoordAxial getCoord(Plateau p);

    public abstract String getStrategieLabel();

    public abstract Optional getIrrig(Plateau P);

    public abstract CoordAxial getJardinierMove(Plateau plateau, Joueur joueur);

    public abstract CoordAxial getPandaMove(Plateau plateau, Joueur joueur);

    public abstract Action firstActionType(Game game);

    public abstract Action secondActionType(Game game);

    public abstract Action thirdActionType(Game game);

    public abstract Amenagement chooseAmenagement(DeckAmenagement deckAmenagement);

    public abstract Optional<Plot> plotAmenagement(Joueur joueur, Plateau plateau);

    public abstract void setStrategieCoord(StrategieCoord strategieCoord);

    public abstract void setStrategieIrrig(StrategieIrrig strategieIrrig);

    public abstract void setStrategiePanda(StrategiePanda strategiePanda);

    public abstract void setStrategieJardinier(StrategieJardinier strategieJardinier);

    public abstract void setStrategieAction(StrategieAction strategieAction);


    public abstract void setStrategieAmenagement(StrategieAmenagement strategieAmenagement);



}
