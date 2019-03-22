package core.controllers;

import communication.Container.ResponseContainer;
import communication.HTTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;

@RestController
public class ConnectionController {

    private Hashtable<Integer, HTTPClient> connectedUsers;

    @Autowired
    public ConnectionController() {
        this.connectedUsers = new Hashtable<>();
    }

    @RequestMapping("/status")
    public String status_req() {
        return getStatus();
    }

    static String getStatus() {
        return "Server is up";
    }

    @RequestMapping("/action/enter_matchmaking/{id}/{user_url}")
    public ResponseContainer req_matchmaking(@PathVariable int id, @PathVariable String user_url){
        System.out.println(String.format("Player id=%d with adress : %s want to play",id, user_url));
        if (connectedUsers.containsKey(id)){
            if (connectedUsers.get(id).getInMatchmaking()){
                return new ResponseContainer(false, "User with id=%d already in matchmaking");
            }
            else {
                connectedUsers.get(id).setInMatchmaking(true);
                return new ResponseContainer(true, "Registration complete, searching for games");
            }
        }else{
            connectedUsers.put(id, new HTTPClient(id, true));
            return new ResponseContainer(true, "Registration complete, searching for games");
        }
    }

}
