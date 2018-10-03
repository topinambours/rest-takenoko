package Takenoko;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.StrategieIrrig.StrategieIrigBase;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Util.Console;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    private static Game game;
    private static Joueur joueur;

    @BeforeClass public static void ScoreTest(){
        Console.Log.init();
        game = new Game();
        joueur = new Joueur(1, new StrategieCoordRandom(),new StrategieIrigBase(game.getPlateau()));
    }

    @Test public void test(){
        CoordAxial coordAxial = new CoordAxial(1,0);
        Plot plot = new Plot(coordAxial);

        joueur.putPlot(plot,game.getPlateau());

        game.evaluate(joueur,coordAxial);

        assertEquals(1,joueur.getScore());
    }
}
