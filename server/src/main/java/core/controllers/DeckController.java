package core.controllers;

import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import core.GameEngine;
import core.controllers.exception.AuthentificationRequiredException;
import core.controllers.verification.AuthentificationVerification;
import core.takenoko.pioche.EmptyDeckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import takenoko.tuile.Tuile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    private GameEngine game;

    @Autowired
    private HttpServletRequest request;

    /**
     * Permet d'avoir la pioche
     * @return TuileContainer
     * @throws EmptyDeckException
     *
     * @api {get} /action/piocher Piocher
     * @apiVersion 0.6.0
     * @apiDescription Get new plots for the deck
     * @apiName Piocher
     * @apiGroup Server/DeckController
     *
     * @apiSuccess PiocheTuile A deck response
     *
     * @apiError EmptyDeckException
     * @apiError AuthentificationRequiredException
     *
     *
     */
    @RequestMapping("/action/piocher")
    public TuileContainer req_pioche(
            @RequestParam(value = "playerId",
                    required = false,
                    defaultValue = "-1") int playerId)
            throws EmptyDeckException, AuthentificationRequiredException {
        AuthentificationVerification.verify("/action/piocher",playerId,request.getRemoteHost(),log);
        if (playerId == game.getCurrentPlayer().getId() && game.isGameStarted()) {
            TuileContainer out = new TuileContainer(game.getPiocheTuile().draw(3));
            log.info(String.format("Le joueur %d a pioché %s", playerId, out));
            return out;
        }else{
            log.info(String.format("Le joueur %d veut piocher alors que ce n'est pas son tour", playerId));
            return new TuileContainer();
        }
    }

    /**
     * Permet de rendre une tuile non utilisé
     * @param tuiles TuileContainer
     * @return ResponseContainer
     *
     * @api {post} /action/rendre_tuiles/ RendreTuiles
     * @apiVersion 0.6.0
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
     * @apiError AuthentificationRequiredException
     */
    @PostMapping("/action/rendre_tuiles/")
    public ResponseContainer rendre_tuiles(
            @RequestParam(value = "playerId",
            required = false,
            defaultValue = "-1") int playerId,

            @RequestBody TuileContainer tuiles) throws AuthentificationRequiredException {
        AuthentificationVerification.verify("/action/rendre_tuiles/",playerId,request.getRemoteHost(),log);
        log.info(String.format("Le joueur %d a rendu : %s", playerId, tuiles));
        for (Tuile t : tuiles.getContent()){
            game.getPiocheTuile().insertBottom(t);
            log.info(t.toString() + " remise dans la pioche");
        }
        return new ResponseContainer(true, "return effective");
    }

}