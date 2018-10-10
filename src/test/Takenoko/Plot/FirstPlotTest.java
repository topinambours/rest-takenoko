package Takenoko.Plot;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Util.Exceptions.EmptyDeckException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FirstPlotTest {
    private Game game;
    private Joueur joueur;

    @Before public void FirstPlotTest(){
        game = new Game();
        joueur = new Joueur(1,new StrategieCoordRandom(),new StrategieIrrigBase(game.getPlateau()));


    }

    @Test public void test() throws EmptyDeckException {
        Plot plot = game.turn(joueur);
        assertTrue(plot.haveWater());
    }
}