package stepsDef;

import core.App;
import core.Joueur;
import core.controller.ActionController;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Ignore
public class SpringIntegrationTest {
    @Autowired
    Joueur joueur;

    @Autowired
    ActionController ac;
}
