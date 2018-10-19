package Takenoko.Deque;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ObjectivesPandaDeckTest {

    @Test
    public void fromJsonFile() {
        ObjectivesPandaDeck test = new ObjectivesPandaDeck();

        for (int i = 0; i < 15; i++){
            test.pop();
        }
        assertTrue(test.isEmpty());
    }
}