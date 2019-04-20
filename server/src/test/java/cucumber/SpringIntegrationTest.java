package cucumber;

import core.App;
import core.GameEngine;
import core.controllers.ConnectionController;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class , webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@Ignore
public class SpringIntegrationTest {
    @Autowired
    GameEngine game;

    @Autowired
    ConnectionController cc;
}