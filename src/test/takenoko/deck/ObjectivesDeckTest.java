package takenoko.deck;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.util.exceptions.EmptyDeckException;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectivesDeckTest {

    @Autowired
    @Qualifier("gardenObjDeckTest")
    private Deck objJardinierDeck;

    @Autowired
    private Deck pandObjDeck;

    @Autowired
    private Deck patternObjDeckTest;

    @Test
    public void testBeanConstructorJardinier() throws EmptyDeckException {
        assertEquals(15, objJardinierDeck.size());
    }

    @Test
    public void testBeanConstructorPanda() throws EmptyDeckException {
        assertEquals(15, pandObjDeck.size());
    }

    @Test
    public void testBeanConstructorPattern() throws EmptyDeckException {
        assertEquals(15, patternObjDeckTest.size());
    }

    public void testEmptyConstructor(){
        assertTrue(new ObjectivesDeck().isEmpty());
    }

    @Test
    public void toStringJardinierTest() {
        assertEquals("Pioche d'objectifs Jardinier : Hauteur 15", objJardinierDeck.toString());
    }

    @Test
    public void toStringPandaTest() {
        assertEquals("Pioche d'objectifs Panda : Hauteur 15", pandObjDeck.toString());
    }

    @Test
    public void toStringPatternTest() {
        assertEquals("Pioche d'objectifs Parcelles : Hauteur 15", patternObjDeckTest.toString());
    }

    @Test
    public void toStringEmptyTest() {
        Deck emptyDeck = new ObjectivesDeck();
        assertEquals("Pioche d'objectifs ? : Hauteur 0", emptyDeck.toString());

        emptyDeck = new ObjectivesDeck(new ArrayList<>());
        assertEquals("Pioche d'objectifs ? : Hauteur 0", emptyDeck.toString());
    }
}