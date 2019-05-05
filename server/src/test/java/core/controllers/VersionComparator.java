package core.controllers;

import communication.container.ActionContainer;
import communication.container.PoseTuileContainer;
import communication.container.ResponseContainer;
import core.GameEngine;
import cucumber.api.java.sl.In;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import takenoko.Plateau;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.versionning.Action;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VersionComparator {
    @Autowired
    private GameEngine gameEngine;


    @Autowired
    private TestRestTemplate restTemplate;

    private final String server_url = "http://localhost:8080";
    private final int id = 0;

    @Ignore
    public <Req,Res> Res post_request(String uri,Req postObject, Class<Res> responseType){

        HttpEntity<Req> request = new HttpEntity<>(postObject, new HttpHeaders());
        URI uri_req = null;
        try {
            uri_req = new URI(String.format("%s/%s?playerId=%d", server_url, uri, id));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new RestTemplate().postForObject(uri_req, request, responseType);

    }

    @Test
    public void basicTest(){
        String jsonPlateau = restTemplate.getForEntity("/version/0/plateau",String.class).getBody();
        Plateau plateau = Plateau.fromJson(jsonPlateau);

        assertEquals(new Plateau().plateau_vide(),plateau);
    }

    @Ignore
    @Test
    public void addPlot(){
        CoordAxial coordAxial = new CoordAxial(0,1);
        Tuile tuile = new Tuile();
        assertTrue(post_request("/action/poser-tuile/", new PoseTuileContainer(coordAxial,tuile), ResponseContainer.class).getResponse());

        Integer versionID = restTemplate.getForEntity("/version/latest/id", Integer.class).getBody();

        String jsonPlateau = restTemplate.getForEntity("/version/"+versionID+"/plateau",String.class).getBody();
        Plateau plateau = Plateau.fromJson(jsonPlateau);

        System.out.println("Plateau json :");
        System.out.println(jsonPlateau);

        System.out.println("After Plateau.fromJson");
        System.out.println(Plateau.fromJson(jsonPlateau));
        //System.out.println("versionID : "+versionID);

        ActionContainer actionContainer = restTemplate.getForEntity("/version/latest/",ActionContainer.class).getBody();
        //System.out.println(actionContainer.toString());

        Plateau expected = new Plateau().plateau_vide();
        expected.poserTuile(coordAxial,tuile);

        //Action.applyAction(actionContainer.getContent().get(0),plateau);

        assertEquals(expected, plateau);
    }

}
