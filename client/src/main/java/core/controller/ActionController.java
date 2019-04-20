package core.controller;

import communication.container.ResponseContainer;
import core.Joueur;
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

    private static final Logger log = LoggerFactory.getLogger(Joueur.class);

    @GetMapping("/notify/{current_id}")
    public ResponseContainer current_player(@PathVariable int current_id) {

        if (current_id == joueur.getId()){
            log.info("Notification de jouer reçu");
            joueur.myTurn = true;
        }
        return new ResponseContainer(true, String.format("turn complete for player %d", joueur.getId()));
    }

}
