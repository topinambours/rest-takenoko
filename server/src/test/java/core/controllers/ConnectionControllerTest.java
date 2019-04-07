package core.controllers;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConnectionControllerTest {

    private HTTPClient testClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void pingTest() {
        testClient = new HTTPClient(0);
        String req = String.format("/ping/%s/%s", testClient.getId(), testClient.getUser_adress());
        ResponseContainer resp = this.restTemplate.getForObject(req, ResponseContainer.class);
        assertEquals(resp, new ResponseContainer(true, "pong"));
    }

    @Test
    public void registrationTest(){
        testClient = new HTTPClient(1);
        String req = String.format("/register/%s/%s", testClient.getId(), testClient.getUser_adress());
        ResponseContainer resp = this.restTemplate.getForObject(req, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "Registration complete"), resp);
    }

    @Test
    public void multipleRegistrationTest(){

        testClient = new HTTPClient(2);
        String req = String.format("/register/%s/%s", testClient.getId(), testClient.getUser_adress());
        ResponseContainer resp = this.restTemplate.getForObject(req, ResponseContainer.class);
        assertEquals(new ResponseContainer(true, "Registration complete"), resp);

        // Second registration with the same id
        resp = this.restTemplate.getForObject(req, ResponseContainer.class);
        assertEquals(new ResponseContainer(false, "Already registered"), resp);

    }



}
