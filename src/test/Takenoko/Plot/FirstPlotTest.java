package Takenoko.Plot;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieAction.Action;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Joueur.Strategie.StrategieSansPions;
import Takenoko.Util.Exceptions.EmptyDeckException;
import Takenoko.Util.Exceptions.NoActionSelectedException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FirstPlotTest {
    private Game game;
    private Joueur joueur;

    @Before public void FirstPlotTest(){
        game = new Game();
        joueur = new Joueur(1, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(game.getPlateau())));


    }

    @Test public void test() throws EmptyDeckException, NoActionSelectedException {
        joueur.turn(game, Action.Card);
        joueur.turn(game,Action.Plot);
        assertTrue(joueur.getPlot().haveWater());
    }
}
