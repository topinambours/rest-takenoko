package core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JoueurTest {

    @Autowired
    Joueur joueur;

    @Test
    public void context_test() {
        assertEquals(10, joueur.getId());
        assertEquals(10, joueur.getHttpClient().getId());
        assertEquals("localhost:8081", joueur.getHttpClient().getUser_adress());
    }

}