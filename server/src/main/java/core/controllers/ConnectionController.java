package core.controllers;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import core.GameEngine;
import core.service.ConnectionService;
import core.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class ConnectionController {

    private final NotificationService service;

    @Autowired
    ConnectionService connectionService;

    private static final Logger log = LoggerFactory.getLogger(ConnectionController.class);

    private final GameEngine game;

    public ConnectionController(@Qualifier("GameEngine") GameEngine game, NotificationService service) {
        this.game = game;
        this.service = service;
    }

    /**
     * Permet d'avoir la fin du tour de jeu
     * @return ResponseContainer
     *
     *
     * @api {get} /end_turn EndTurn
     * @apiVersion 0.7.0
     * @apiDescription End the turn end get the player id that have to play
     * @apiName EndTurn
     * @apiGroup Server/ConnectionController
     *
     * @apiSuccess {Boolean} response The API success response.
     * @apiSuccess {String} message The API message response, here the player that have to play.
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *         "response": "true",
     *         "message": "Player :id have to play"
     *       }
     *
     *
     *
     */
    @GetMapping("/end_turn")
    public ResponseContainer end_turn(@RequestParam(value = "playerId",
            required = false,
            defaultValue = "-1") int playerId) throws ExecutionException, InterruptedException, TimeoutException {

        CompletableFuture<ResponseContainer> res = connectionService.enTurnNotified(playerId);
        return res.get(1, TimeUnit.SECONDS);

    }

    /**
     * Permet de check si une partie est terminée
     * @return ResponseContainer
     *
     *
     * @api {get} /gameEnded gameEnded
     * @apiVersion 0.7.0
     * @apiDescription Get the status to know if the game is ended
     * @apiName gameEnded
     * @apiGroup Server/ConnectionController
     *
     * @apiSuccess {Boolean} response The API success response.
     * @apiSuccess {String} message The API message response.
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *         "response": "true",
     *         "message": ""
     *       }
     *
     *
     *
     */
    @GetMapping("/gameEnded")
    public ResponseContainer gameEnded(){
        return new ResponseContainer(game.gameEnded(), "");
    }

    @GetMapping("/gameStarted")
    public ResponseContainer gameStarted(){
        return new ResponseContainer(game.isGameStarted(), "");
    }

    /**
     * Permet d'enregistrer un joueur
     * @param client HTTPClient
     * @return ResponseContainer
     *
     * @api {post} /register/ Register
     * @apiVersion 0.7.0
     * @apiDescription Post the httpClient references to register to the game
     * @apiName Register
     * @apiGroup Server/ConnectionController
     *
     * @apiSuccess {Boolean} response The API success response.
     * @apiSuccess {String} message The API message response, here the registration status.
     *
     * @apiParam HTTPClient : client Client references
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *         "response": "true",
     *         "message": "You joined the game"
     *       }
     *
     */
    @PostMapping("/register/")
    public ResponseContainer register(@RequestBody HTTPClient client) throws ExecutionException, InterruptedException {
        CompletableFuture<ResponseContainer> res = connectionService.registerNewClient(client);
        return res.get();
    }


}
