package Takenoko;

import Takenoko.Joueur.Joueur;
import Takenoko.Util.Console;
import Takenoko.Util.Exceptions.EmptyDeckException;
import Takenoko.Util.Exceptions.NoActionSelectedException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    @Ignore
    @Test public void testGame() throws EmptyDeckException, NoActionSelectedException {
        gameTest.play();
        assertTrue(gameTest.end());
    }

    @Test public void testInitDraw(){
        ArrayList<Joueur> joueurs = gameTest.getJoueurs();
        Iterator<Joueur> iterator = joueurs.iterator();

        while (iterator.hasNext()){
            Joueur joueur = iterator.next();
            assertEquals(1,joueur.getPandaObjectiveCards().size());
            assertEquals(1,joueur.getPatternObjectiveCards().size());
        }


    }


}