package Takenoko;

import Takenoko.Joueur.Joueur;
import Takenoko.Util.Console;
import Takenoko.Util.Exceptions.EmptyDeckException;
import Takenoko.Util.Exceptions.NoActionSelectedException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private Game game;


    @Before
    public void setUp() throws Exception {
        game = new Game();
        Console.Log.init();
    }

    @Test public void testGame() throws EmptyDeckException, NoActionSelectedException {
        game.play();
        assertTrue(game.end());
    }

    @Test public void testInitDraw(){
        ArrayList<Joueur> joueurs = game.getJoueurs();
        Iterator<Joueur> iterator = joueurs.iterator();

        while (iterator.hasNext()){
            Joueur joueur = iterator.next();
            assertEquals(1,joueur.getPandaObjectiveCards().size());
            assertEquals(1,joueur.getPatternObjectiveCards().size());
        }


    }


}