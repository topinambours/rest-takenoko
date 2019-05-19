package server.controllers.exception;

import communication.container.*;
import server.GameEngine;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExceptionControler{

    @Autowired
    private GameEngine gameEngine;


    @Autowired
    private TestRestTemplate restTemplate;

    private final String server_url = "http://localhost:8080";
    private final int id = 0;

    @Ignore
    public <Req,Res> ResponseEntity post_request(String uri, Req postObject, Class<Res> responseType){

        HttpEntity<Req> request = new HttpEntity<>(postObject, new HttpHeaders());
        URI uri_req = null;
        try {
            uri_req = new URI(String.format("%s/%s?playerId=%d", server_url, uri, id));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new RestTemplate().postForEntity(uri_req, request, responseType);

    }


    @Test
    public void illegalPutPlot(){
        try {
            post_request("/action/poser-tuile/",new PoseTuileContainer(new CoordAxial(0,2),new Tuile()), ResponseContainer.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.FORBIDDEN,e.getStatusCode());
        }
    }

    @Test
    public void illegalPandaMoove(){
        try {
            post_request("/action/bouger-panda/",new CoordAxial(1,0),ResponseContainer.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.FORBIDDEN,e.getStatusCode());
        }
    }

    @Test
    public void illegalIrrig(){
        try {
            post_request("/action/poser-irrigation/",new CoordIrrig(1,0, Orient.W),ResponseContainer.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.FORBIDDEN,e.getStatusCode());
        }
    }

    @Test
    public void badGetTuileFromID(){
        try {
            restTemplate.getForEntity("/platea/tuile/2", TuileContainer.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void badGetTuileCoordFromID(){
        try {
            restTemplate.getForEntity("/platea/tuile/2/coord", CoordContainer.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void badGetVersionID(){
        try {
            restTemplate.getForEntity("/version/1/", ActionContainer.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void badGetVersionFrom(){
        try {
            restTemplate.getForEntity("/version/from/-1",ActionContainer.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void badGetVersionFromTo(){
        try {
            restTemplate.getForEntity("/version/from/-1/to/-1",ActionContainer.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void badBoardAtVersion(){
        try {
            restTemplate.getForEntity("/version/1/plateau",String.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.NOT_FOUND,e.getStatusCode());
        }
    }


}
