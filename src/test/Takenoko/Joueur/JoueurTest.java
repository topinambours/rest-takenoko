package Takenoko.Joueur;

import Takenoko.Deque.Deck;
import Takenoko.Deque.ObjectivesDeck;
import Takenoko.Game;
import Takenoko.Joueur.Strategie.StrategieAction.Action;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Joueur.Strategie.StrategieSansPions;
import Takenoko.Objectives.GardenObjectiveCard;
import Takenoko.Objectives.PandaObjectiveCard;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
import Takenoko.Util.Exceptions.EmptyDeckException;
import Takenoko.Util.Exceptions.NoActionSelectedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JoueurTest {

    private final int DECK_SIZE = 120;
    private Deck dtest;
    private Joueur joueur1;
    private Joueur joueur2;
    private PandaObjectiveCard pandaObjectiveCard;
    private Plateau p;
    private GardenObjectiveCard gardenObjectiveCard;

    @Autowired
    private Game gameTest;

    @Before
    public void setUp() throws Exception {
        dtest = new Deck();
        p = new Plateau();
        joueur1 = new Joueur(1, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(p)));
        joueur2 = new Joueur(2, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(p)));

        pandaObjectiveCard = new PandaObjectiveCard(1, 1, 1, 1);
        gardenObjectiveCard = new GardenObjectiveCard(Couleur.VERT, 1, 4);
        for (int i = 0; i < DECK_SIZE; i++){
            dtest.addFirst(new Plot(i,0));
        }
    }

    @Test
    public void getId() {
        assertEquals(1, joueur1.getId());
        assertEquals(2, joueur2.getId());
    }

    @Test (expected = EmptyDeckException.class)
    public void draw() throws EmptyDeckException {
        for (int i = 0; i < DECK_SIZE; i++){
            assertEquals(new Plot(i,0), joueur1.draw(dtest));
        }
        joueur1.draw(dtest);
    }

    @Test
    public void multiDraw() throws EmptyDeckException {
        List<Plot> current;
        for (int i = 0; i < (DECK_SIZE / 3); i+=3){
            current = new ArrayList<>();
            current.add(new Plot(i,0));
            current.add(new Plot(i+1,0));
            current.add(new Plot(i+2,0));

            assertArrayEquals(current.toArray() , joueur1.multiDraw(dtest, 3).toArray());
        }
    }

    @Test
    public void multiDrawOutOfCapacity() throws EmptyDeckException {
        int UPPER_BOUND = DECK_SIZE - 5;
        List<Plot> current;
        for (int i = 0; i < UPPER_BOUND; i+=5){
            current = new ArrayList<>();
            for (int j = i; j <= i+4; j++) {
                current.add(new Plot(j, 0));
            }
            assertArrayEquals(current.toArray() , joueur1.multiDraw(dtest, 5).toArray());
        }

        // On essaye de piocher 10 cartes parmis 5 restantes
        current = new ArrayList<>();
        for (int j = UPPER_BOUND; j < DECK_SIZE; j++) {
            current.add(new Plot(j, 0));
        }
        // Résultats attendu : le joueur n'a pioché que 5 cartes, aucune exception de levée
        assertArrayEquals(current.toArray() , joueur1.multiDraw(dtest, 10).toArray());
    }

    @Test
    public void replaceInDeck() throws EmptyDeckException {
        assertEquals(new Plot(0,0), dtest.getLast());
        Plot pioche = joueur1.draw(dtest);
        assertEquals(new Plot(0,0), pioche);

        assertEquals(pioche, joueur1.replaceInDeck(dtest, pioche));

        assertEquals(pioche, dtest.getFirst());
    }

    @Test
    public void putPlot() throws EmptyDeckException {
        Plateau pTest = new Plateau();
        CoordAxial currentPlotPos;
        for (int i = 0; i < DECK_SIZE; i++){
            currentPlotPos = joueur1.putPlot(joueur1.draw(dtest), pTest);
            assertTrue(!pTest.legalPositions().contains(currentPlotPos));
        }
    }

    // @TODO tester toute les stratégies
    @Test
    public void putIrrigRandomStrategie() {
        Plateau pTest = new Plateau();
        assertEquals(Optional.empty(), joueur1.putIrrig(pTest));

    }

    @Test
    public void compareTo() {
        joueur1.addScore(100);
        joueur2.addScore(10);

        assertTrue(joueur1.compareTo(joueur2) > 0);
        assertTrue(joueur2.compareTo(joueur1) < 0);

        joueur2.addScore(90);
        assertEquals(0,joueur2.compareTo(joueur1));

        assertEquals(-1, joueur1.compareTo(new CoordAxial(0,0)));
    }

    @Test
    public void isUpper() {
        joueur1.addScore(100);
        joueur2.addScore(10);

        assertTrue(joueur1.isUpper(joueur2));
        assertFalse(joueur2.isUpper(joueur1));
    }

    @Test
    public void getPandaObjectiveCard(){
        HashSet<PandaObjectiveCard> hashSet = new HashSet<>();
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());

        joueur1.addPandaObjectiveCard(pandaObjectiveCard);
        hashSet.add(pandaObjectiveCard);
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());

        joueur1.removePandaObjectiveCard(pandaObjectiveCard);
        hashSet.remove(pandaObjectiveCard);
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());

    }

    @Test
    public void addPandaObjectiveCard(){
        HashSet<PandaObjectiveCard> hashSet = new HashSet<>();
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());

        joueur1.addPandaObjectiveCard(pandaObjectiveCard);
        hashSet.add(pandaObjectiveCard);
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());

        joueur1.removePandaObjectiveCard(pandaObjectiveCard);
        hashSet.remove(pandaObjectiveCard);
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());
    }

    @Test
    public void removePandaObjectiveCard(){
        HashSet<PandaObjectiveCard> hashSet = new HashSet<>();
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());

        joueur1.addPandaObjectiveCard(pandaObjectiveCard);
        hashSet.add(pandaObjectiveCard);
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());

        joueur1.removePandaObjectiveCard(pandaObjectiveCard);
        hashSet.remove(pandaObjectiveCard);
        assertEquals(hashSet, joueur1.getPandaObjectiveCards());
    }

    @Test
    public void addGardenObjectiveCard(){
        HashSet<GardenObjectiveCard> list = new HashSet<>();
        assertEquals(list, joueur1.getGardenObjectiveCards());

        joueur1.addGardenObjectiveCard(gardenObjectiveCard);
        list.add(gardenObjectiveCard);
        assertEquals(list, joueur1.getGardenObjectiveCards());

        joueur1.removeGardenObjectiveCard(gardenObjectiveCard);
        list.remove(gardenObjectiveCard);
        assertEquals(list, joueur1.getGardenObjectiveCards());
    }

    @Test
    public void removeGardenObjectiveCard(){
        HashSet<GardenObjectiveCard> list = new HashSet<>();
        assertEquals(list, joueur1.getGardenObjectiveCards());

        joueur1.addGardenObjectiveCard(gardenObjectiveCard);
        list.add(gardenObjectiveCard);
        assertEquals(list, joueur1.getGardenObjectiveCards());

        joueur1.removeGardenObjectiveCard(gardenObjectiveCard);
        list.remove(gardenObjectiveCard);
        assertEquals(list, joueur1.getGardenObjectiveCards());
    }

    @Test
    public void getScore(){
        assertEquals(0, joueur1.getScore());
        joueur1.addScore(3);
        assertEquals(3, joueur1.getScore());
        joueur1.addScore(2);
        assertEquals(5, joueur1.getScore());
    }

    @Test
    public void addScore(){
        assertEquals(0, joueur1.getScore());
        joueur1.addScore(3);
        assertEquals(3, joueur1.getScore());
        joueur1.addScore(2);
        assertEquals(5, joueur1.getScore());
    }

    @Test
    public void addScore1(){
        assertEquals(0, joueur1.getScore());
        joueur1.addScore1();
        assertEquals(1, joueur1.getScore());
        joueur1.addScore1();
        assertEquals(2, joueur1.getScore());
    }

    @Test
    public void setBambousVerts(){
        assertEquals(0, joueur1.getBambousVerts());
        joueur1.setBambousVerts(1);
        assertEquals(1, joueur1.getBambousVerts());
        joueur1.setBambousVerts(2);
        assertEquals(2, joueur1.getBambousVerts());
        joueur1.setBambousVerts(5);
        assertEquals(5, joueur1.getBambousVerts());
        joueur1.setBambousVerts(-5);
        assertEquals(0, joueur1.getBambousVerts());
    }

    @Test
    public void setBambousJaunes(){
        assertEquals(0, joueur1.getBambousJaunes());
        joueur1.setBambousJaunes(1);
        assertEquals(1, joueur1.getBambousJaunes());
        joueur1.setBambousJaunes(2);
        assertEquals(2, joueur1.getBambousJaunes());
        joueur1.setBambousJaunes(5);
        assertEquals(5, joueur1.getBambousJaunes());
        joueur1.setBambousJaunes(-5);
        assertEquals(0, joueur1.getBambousJaunes());

    }

    @Test
    public void setBambousRoses(){
        assertEquals(0, joueur1.getBambousRoses());
        joueur1.setBambousRoses(1);
        assertEquals(1, joueur1.getBambousRoses());
        joueur1.setBambousRoses(2);
        assertEquals(2, joueur1.getBambousRoses());
        joueur1.setBambousRoses(5);
        assertEquals(5, joueur1.getBambousRoses());
        joueur1.setBambousRoses(-5);
        assertEquals(0, joueur1.getBambousRoses());
    }

    @Test
    public void getBambousVerts(){
        assertEquals(0, joueur1.getBambousVerts());
        joueur1.setBambousVerts(1);
        assertEquals(1, joueur1.getBambousVerts());
        joueur1.setBambousVerts(2);
        assertEquals(2, joueur1.getBambousVerts());
        joueur1.setBambousVerts(5);
        assertEquals(5, joueur1.getBambousVerts());
        joueur1.setBambousVerts(-5);
        assertEquals(0, joueur1.getBambousVerts());
    }

    @Test
    public void getBambousJaunes(){
        assertEquals(0, joueur1.getBambousJaunes());
        joueur1.setBambousJaunes(1);
        assertEquals(1, joueur1.getBambousJaunes());
        joueur1.setBambousJaunes(2);
        assertEquals(2, joueur1.getBambousJaunes());
        joueur1.setBambousJaunes(5);
        assertEquals(5, joueur1.getBambousJaunes());
        joueur1.setBambousJaunes(-5);
        assertEquals(0, joueur1.getBambousJaunes());

    }

    @Test
    public void getBambousRoses(){
        assertEquals(0, joueur1.getBambousRoses());
        joueur1.setBambousRoses(1);
        assertEquals(1, joueur1.getBambousRoses());
        joueur1.setBambousRoses(2);
        assertEquals(2, joueur1.getBambousRoses());
        joueur1.setBambousRoses(5);
        assertEquals(5, joueur1.getBambousRoses());
        joueur1.setBambousRoses(-5);
        assertEquals(0, joueur1.getBambousRoses());
    }

    @Test
    public void ObjCardTurn() throws NoActionSelectedException, EmptyDeckException {
        Joueur j1 = gameTest.getJoueurs().get(0);
        int nbCarte = j1.getPandaObjectiveCards().size()+j1.getPatternObjectiveCards().size()+j1.getGardenObjectiveCards().size();
        assertEquals(3, nbCarte);

        j1.turn(gameTest, Action.ObjCard);
        nbCarte = j1.getPandaObjectiveCards().size()+j1.getPatternObjectiveCards().size()+j1.getGardenObjectiveCards().size();
        assertEquals(4, nbCarte);

    }
}