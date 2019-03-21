package core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionController {

    @Autowired
    public ConnectionController() {
        super();
    }

    @RequestMapping("/status")
    public String status_req() {
        return getStatus();
    }

    static String getStatus() {
        return "Server is up";
    }

    @RequestMapping("/action/enter_matchmaking/{user_adress}")
    public String req_matchmaking(@PathVariable String user_adress){
        System.out.println(String.format("Player with adress : %s want to play", user_adress));
        return "Registration OK, searching for games";
    }
}
