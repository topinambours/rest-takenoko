package Takenoko.Deque;

import Takenoko.Objectives.ObjectiveCard;
import Takenoko.Objectives.PandaObjectiveCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectivesDeckTest {

    @Test
    public void init() {
        ObjectivesDeck test = new ObjectivesDeck();
        List<ObjectiveCard> cards = new ArrayList<>();
        for (int i = 0; i < 150000; i++){
            cards.add(new PandaObjectiveCard(i, 0,0,0));
        }
        test.init(cards);

        // La liste est normalement mélangé avant de se transformer en pile. la dernière carte insérée ne devrait pas être la première à être tirée.
        assertNotEquals(new PandaObjectiveCard(149999, 0,0,0), test.pop());
    }

    @Test
    public void isEmpty() {
        ObjectivesDeck test = new ObjectivesDeck();
        List<ObjectiveCard> cards = new ArrayList<>();
        cards.add(new PandaObjectiveCard(1, 0,0,0));

        test.init(cards);
        assertEquals(new PandaObjectiveCard(1, 0,0,0), test.pop());
        assertTrue(test.isEmpty());
    }

    @Test
    public void pop() {
        ObjectivesDeck test = new ObjectivesDeck();
        List<ObjectiveCard> cards = new ArrayList<>();
        for (int i = 0; i < 150000; i++){
            cards.add(new PandaObjectiveCard(i, 0,0,0));
        }
        test.init(cards);

        for (int i = 0; i < 150000; i++){
            test.pop();
        }
        assertTrue(test.isEmpty());
    }
}