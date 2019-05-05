package core.controller;

import communication.container.ResponseContainer;
import core.Joueur;
import core.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActionController {

    @Autowired
    Joueur joueur;

    @Autowired
    ActionService actionService;

    private static final Logger log = LoggerFactory.getLogger(ActionController.class);

    @GetMapping("/closeApplication/{reason}")
    public ResponseContainer closeApplication(@PathVariable String reason){
        actionService.closeApplication(reason);
        return new ResponseContainer(true, String.format("Application of player %d will closed.", joueur.getId()));
    }

    @GetMapping("/notify/{current_id}")
    public ResponseContainer current_player(@PathVariable int current_id) {

        if (current_id == joueur.getId()){
            if (joueur.myTurn){
                return new ResponseContainer(false, String.format("Player %d already know that he have to play", current_id));
            }

            log.info("Notification de jouer reçu");
            joueur.myTurn = true;
            actionService.turn();
            return new ResponseContainer(true, String.format("Player %d aware that he have to play.", joueur.getId()));
        }
        return new ResponseContainer(true, String.format("Player %d know that it is the turn of player %d", joueur.getId(), current_id));
    }

}
