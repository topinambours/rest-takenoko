package Takenoko;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
import Takenoko.Util.Console;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    private static Game game;
    private static Joueur joueur;

    @Before
    public void ScoreTest(){
        Console.Log.init();
        game = new Game();
        joueur = new Joueur(1, new StrategieCoordRandom(),new StrategieIrrigBase(game.getPlateau()));
    }

    @Test public void test(){
        CoordAxial coordAxial = new CoordAxial(1,0);
        Plot plot = new Plot(coordAxial, Couleur.BLEU);

        CoordAxial pose = joueur.putPlot(plot,game.getPlateau());

        game.evaluate(joueur,pose);

        assertEquals(1,joueur.getScore());
    }
}
