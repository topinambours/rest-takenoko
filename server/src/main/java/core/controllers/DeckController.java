package core.controllers;

import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import core.GameEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import core.takenoko.pioche.EmptyDeckException;
import core.takenoko.pioche.PiocheTuile;
import takenoko.tuile.Tuile;

import java.util.HashMap;

@RestController
@Import(PiocheTuile.class)
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    private GameEngine game;

    @RequestMapping("/action/piocher")
    public TuileContainer req_pioche() throws EmptyDeckException {
        System.out.println(game.getPiocheTuile().toContainer());
        return new PiocheTuile(game.getPiocheTuile().draw(3)).toContainer();
    }

    @PostMapping("/action/rendre_tuiles/")
    public ResponseContainer rendre_tuiles(@RequestBody TuileContainer tuiles){
        for (Tuile t : tuiles.getContent()){
            game.getPiocheTuile().insertBottom(t);
            log.info(t.toString() + " INSERTED TO BOTTOM OF DECK");
        }
        return new ResponseContainer(true, "return effective");
    }

}