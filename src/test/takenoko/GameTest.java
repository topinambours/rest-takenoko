package takenoko;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.joueur.Joueur;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTest {
    @Autowired
    private Game gameTest;


    @Before
    @Required
    public void setUp() throws Exception {
        Console.Log.init();
    }

     //@TODO améliorer les ia pour qu'elles complètes plus facilement les objectifs
    // test bloquant dans le cas ou la partie ne se termine jamais
    @Test public void testGame() throws EmptyDeckException, NoActionSelectedException {
        gameTest.play();
        assertTrue(gameTest.end());
    }

    @Test public void testInitDraw() throws EmptyDeckException {
        gameTest.firstDrawObjectives();
        ArrayList<Joueur> joueurs = gameTest.getJoueurs();
        Iterator<Joueur> iterator = joueurs.iterator();

        while (iterator.hasNext()){
            Joueur joueur = iterator.next();
            assertEquals(1,joueur.getPatternObjectiveCards().size());
            assertEquals(1,joueur.getPandaObjectiveCards().size());
            assertEquals(1,joueur.getGardenObjectiveCards().size());
        }


    }


}