package Takenoko;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieRandom;
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
        game = new Game();
        joueur = new Joueur(1, new StrategieRandom());
    }

    @Test public void test(){
        CoordAxial coordAxial = new CoordAxial(1,0);
        Plot plot = new Plot(coordAxial);

        joueur.putPlot(plot,game.getPlateau());

        game.evaluate(joueur,coordAxial);

        assertEquals(1,joueur.getScore());
    }
}
