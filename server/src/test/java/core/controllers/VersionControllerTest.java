package core.controllers;

import communication.container.ActionContainer;
import core.GameEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.versionning.Action;
import takenoko.versionning.ActionType;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VersionControllerTest {

    @Autowired
    private GameEngine gameEngine;


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void badIdError(){
        int size = gameEngine.getVersionning().size();
        ResponseEntity<ActionContainer> resp = restTemplate.getForEntity("/version/"+size+"/",ActionContainer.class);
        assertEquals(new ActionContainer(), resp.getBody());
        assertEquals(resp.getStatusCode(), HttpStatus.NOT_FOUND);

        ResponseEntity<ActionContainer> resp2 = restTemplate.getForEntity("/version/from/"+size+"/",ActionContainer.class);
        assertEquals(new ActionContainer(), resp2.getBody());
        assertEquals(resp2.getStatusCode(), HttpStatus.NOT_FOUND);

        ResponseEntity<ActionContainer> resp3 = restTemplate.getForEntity("/version/from/"+size+"/to/"+size,ActionContainer.class);
        assertEquals(new ActionContainer(), resp3.getBody());
        assertEquals(resp3.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void invalideID(){
        ResponseEntity<ActionContainer> resp = restTemplate.getForEntity("/version/-1/",ActionContainer.class);
        assertEquals(new ActionContainer(), resp.getBody());
        assertEquals(resp.getStatusCode(), HttpStatus.NOT_FOUND);

        ResponseEntity<ActionContainer> resp2 = restTemplate.getForEntity("/version/from/-1/",ActionContainer.class);
        assertEquals(new ActionContainer(), resp2.getBody());
        assertEquals(resp2.getStatusCode(), HttpStatus.NOT_FOUND);

        ResponseEntity<ActionContainer> resp3 = restTemplate.getForEntity("/version/from/-1/to/-1",ActionContainer.class);
        assertEquals(new ActionContainer(), resp3.getBody());
        assertEquals(resp3.getStatusCode(), HttpStatus.NOT_FOUND);
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
        assertEquals(resp3.getBody(),actionContainer);
        assertEquals(resp3.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void multipleTest(){
        gameEngine.addVersion(new Action(ActionType.PUTPLOT,"test"));
        gameEngine.addVersion(new Action(ActionType.PUTPLOT,"test2"));
        gameEngine.addVersion(new Action(gameEngine.getVersionning().size()+1,ActionType.PUTPLOT,"test3"));

        int size = gameEngine.getVersionning().size();
        ActionContainer actionContainer = new ActionContainer(gameEngine.getVersionning());


        ResponseEntity<ActionContainer> resp = restTemplate.getForEntity("/version/from/0",ActionContainer.class);
        assertEquals(resp.getBody(),actionContainer);
        assertEquals(resp.getStatusCode(), HttpStatus.OK);


        ResponseEntity<ActionContainer> resp2 = restTemplate.getForEntity("/version/from/0/to/"+size,ActionContainer.class);
        assertEquals(resp2.getBody(),actionContainer);
        assertEquals(resp2.getStatusCode(), HttpStatus.OK);

        ActionContainer subActionContainer1 = new ActionContainer(gameEngine.getVersionning().subList(0,size-1));
        ResponseEntity<ActionContainer> resp3 = restTemplate.getForEntity("/version/from/0/to/"+(size-1),ActionContainer.class);
        assertEquals(resp3.getBody(),subActionContainer1);
        assertEquals(resp3.getStatusCode(), HttpStatus.OK);

        ActionContainer subActionContainer2 = new ActionContainer(gameEngine.getVersionning().subList(1,size));
        ResponseEntity<ActionContainer> resp4 = restTemplate.getForEntity("/version/from/1",ActionContainer.class);
        assertEquals(resp4.getBody(),subActionContainer2);
        assertEquals(resp4.getStatusCode(), HttpStatus.OK);

        ActionContainer subActionContainer3 = new ActionContainer(gameEngine.getVersionning().get(1));
        ResponseEntity<ActionContainer> resp5 = restTemplate.getForEntity("/version/from/1/to/2",ActionContainer.class);
        assertEquals(resp5.getBody(),subActionContainer3);
        assertEquals(resp5.getStatusCode(), HttpStatus.OK);

        ActionContainer subActionContainer4 = new ActionContainer(gameEngine.getVersionning().get(2));
        ResponseEntity<ActionContainer> resp6 = restTemplate.getForEntity("/version/latest/",ActionContainer.class);
        assertEquals(resp6.getBody(),subActionContainer4);
        assertEquals(resp6.getStatusCode(), HttpStatus.OK);

        Integer versionID = gameEngine.getVersionning().get(2).getId();
        ResponseEntity<Integer> resp7 = restTemplate.getForEntity("/version/latest/id",Integer.class);
        assertEquals(resp7.getBody(),versionID);
        assertEquals(resp7.getStatusCode(), HttpStatus.OK);
    }


}
