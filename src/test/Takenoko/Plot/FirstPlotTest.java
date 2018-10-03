package Takenoko.Plot;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieRandom;
import Takenoko.Plateau;
import Takenoko.Util.Exceptions.EmptyDeckException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FirstPlotTest {
    private Game game;
    private Joueur joueur;

    @Before public void FirstPlotTest(){
        game = new Game();
        joueur = new Joueur(1,new StrategieRandom());


    }

    @Test public void test() throws EmptyDeckException {
        Plot plot = game.turn(joueur);
        assertTrue(plot.haveWater());
    }
}
