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
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    private GameEngine game;

    /**
     * Permet d'avoir la pioche
     * @return TuileContainer
     * @throws EmptyDeckException
     *
     * @api {get} /action/piocher Piocher
     * @apiVersion 0.2.0
     * @apiDescription Get new plots for the deck
     * @apiName Piocher
     * @apiGroup Server/DeckController
     *
     * @apiSuccess PiocheTuile A deck response
     *
     * @apiError EmptyDeckException
     *
     *
     */
    @RequestMapping("/action/piocher")
    public TuileContainer req_pioche() throws EmptyDeckException {
        System.out.println("PIOCHE " + game.getPiocheTuile().toString());

        TuileContainer out = new TuileContainer(game.getPiocheTuile().draw(3));
        System.out.println("ENVOIE DE " + out);
        return out;
    }

    /**
     * Permet de rendre une tuile non utilisé
     * @param tuiles TuileContainer
     * @return ResponseContainer
     *
     * @api {post} /action/rendre_tuiles/ RendreTuiles
     * @apiVersion 0.2.0
     * @apiDescription Get back non-used plots
     * @apiName RendreTuiles
     * @apiGroup Server/DeckController
     *
     * @apiSuccess {Boolean} response The API success response.
     * @apiSuccess {String} message The API message response, here the deck status.
     *
     * @apiParam TuileContainer : plots to return
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *         "response": "true",
     *         "message": "return effective"
     *       }
     *
     */
    @PostMapping("/action/rendre_tuiles/")
    public ResponseContainer rendre_tuiles(@RequestBody TuileContainer tuiles){
        for (Tuile t : tuiles.getContent()){
            game.getPiocheTuile().insertBottom(t);
            log.info(t.toString() + " INSERTED TO BOTTOM OF DECK");
        }
        return new ResponseContainer(true, "return effective");
    }

}