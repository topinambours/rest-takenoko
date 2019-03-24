package core.controllers;

import communication.Container.ResponseContainer;
import communication.HTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;

@RestController
public class ConnectionController {

    Logger logger = LoggerFactory.getLogger(ConnectionController.class);

    public Hashtable<Integer, HTTPClient> getRegisteredUsers() {
        return registeredUsers;
    }

    private Hashtable<Integer, HTTPClient> registeredUsers;

    @Autowired
    public ConnectionController() {
        this.registeredUsers = new Hashtable<>();
    }

    @RequestMapping("/ping/{id}/{user_url}")
    public ResponseContainer ping_received(
            @PathVariable int id,
            @PathVariable String user_url)
    {
        logger.info(String.format("Ping received from %d@%s", id, user_url));
        return new ResponseContainer(true, "pong");
    }

    @RequestMapping("/register/{id}/{user_url}")
    public ResponseContainer registration_req(
            @PathVariable int id,
            @PathVariable String user_url){
        logger.info(String.format("Registration request from %d@%s", id, user_url));

        if (registeredUsers.containsKey(id)){
            logger.warn(String.format("User with id %d already registered", id));
            return new ResponseContainer(false, "Already registered");
        }
        else{
            registeredUsers.put(id, new HTTPClient(id, user_url));
            logger.info(String.format("User with id %d registered with address %s", id,user_url));
            return new ResponseContainer(true, "Registration complete");
        }

    }

}
