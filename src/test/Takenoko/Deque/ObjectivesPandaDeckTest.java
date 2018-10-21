package Takenoko.Deque;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
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