package Takenoko.Plot;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.StrategieCoord.StrategieRandom;
import Takenoko.Joueur.StrategieIrrig.StrategieIrigBase;
import Takenoko.Util.Exceptions.EmptyDeckException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FirstPlotTest {
    private Game game;
    private Joueur joueur;

    @Before public void FirstPlotTest(){
        game = new Game();
        joueur = new Joueur(1,new StrategieRandom(),new StrategieIrigBase(game.getPlateau()));


    }

    @Test public void test() throws EmptyDeckException {
        Plot plot = game.turn(joueur);
        assertTrue(plot.haveWater());
    }
}
