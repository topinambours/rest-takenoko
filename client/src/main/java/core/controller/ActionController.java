package core.controller;

import communication.container.ResponseContainer;
import core.Joueur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class ActionController {

    @Autowired
    Joueur joueur;

    @GetMapping("/notify/{current_id}")
    public ResponseContainer current_player(@PathVariable int current_id) {
        if (current_id == -1){
            System.out.println("NO MORE PLOTS GAME ENDED");
        }

        if (current_id == joueur.getId()){
            System.out.println("GET NOTIFIED IT IS MY TURN");
            joueur.myTurn = true;
        }
        return new ResponseContainer(true, String.format("turn complete for player %d", joueur.getId()));
    }

}
