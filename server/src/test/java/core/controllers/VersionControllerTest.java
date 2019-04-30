package core.controllers;

import communication.container.ActionContainer;
import communication.container.Container;
import core.GameEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.tuile.CoordAxial;
import takenoko.versionning.Action;
import takenoko.versionning.ActionType;
import takenoko.versionning.VersionNotFoundException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VersionControllerTest {

    @Autowired
    private GameEngine gameEngine;


    @Autowired
    private TestRestTemplate restTemplate;



    @Test
    public void test(){
        gameEngine.addVersion(new Action(ActionType.ADDIRRIG,"tamere"));
        System.out.println(restTemplate.getForObject("/version", ActionContainer.class).toString());
    }

    @Test
    public void badIdError(){
        int size = gameEngine.getVersionning().size();
        ResponseEntity<ActionContainer> resp = restTemplate.getForEntity("/version/"+size+"/",ActionContainer.class);
        assertEquals(new ActionContainer(), resp.getBody());
        assertEquals(resp.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<ActionContainer> resp2 = restTemplate.getForEntity("/version/from/"+size+"/",ActionContainer.class);
        assertEquals(new ActionContainer(), resp2.getBody());
        assertEquals(resp2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<ActionContainer> resp3 = restTemplate.getForEntity("/version/from/"+size+"/to/"+size,ActionContainer.class);
        assertEquals(new ActionContainer(), resp3.getBody());
        assertEquals(resp3.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void invalideID(){
        ResponseEntity<ActionContainer> resp = restTemplate.getForEntity("/version/-1/",ActionContainer.class);
        assertEquals(new ActionContainer(), resp.getBody());
        assertEquals(resp.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<ActionContainer> resp2 = restTemplate.getForEntity("/version/from/-1/",ActionContainer.class);
        assertEquals(new ActionContainer(), resp2.getBody());
        assertEquals(resp2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<ActionContainer> resp3 = restTemplate.getForEntity("/version/from/-1/to/-1",ActionContainer.class);
        assertEquals(new ActionContainer(), resp3.getBody());
        assertEquals(resp3.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void gettingTest(){
        gameEngine.addVersion(new Action(ActionType.PUTPLOT,"test"));
        ActionContainer actionContainer = new ActionContainer(gameEngine.getVersionning().get(0));

        ResponseEntity<ActionContainer> resp = restTemplate.getForEntity("/version/0/",ActionContainer.class);
        assertEquals(resp.getBody(),actionContainer);
        assertEquals(resp.getStatusCode(), HttpStatus.OK);

        ResponseEntity<ActionContainer> resp2 = restTemplate.getForEntity("/version/from/0",ActionContainer.class);
        assertEquals(resp2.getBody(),actionContainer);
        assertEquals(resp2.getStatusCode(), HttpStatus.OK);

        ResponseEntity<ActionContainer> resp3 = restTemplate.getForEntity("/version/from/0/to/1",ActionContainer.class);
        System.out.println(resp3.getBody());
        assertEquals(resp3.getBody(),actionContainer);
        assertEquals(resp3.getStatusCode(), HttpStatus.OK);



    }


}
