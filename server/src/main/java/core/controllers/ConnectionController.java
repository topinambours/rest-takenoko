package core.controllers;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import core.GameEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConnectionController {

    private final NotificationService service;

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
     * @apiVersion 0.3.0
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
            defaultValue = "-1") int playerId){

        HTTPClient currentPlayer = game.getClients().get(game.getCurrentPlayerIndex());

        if (playerId == currentPlayer.getId()){
            log.info(String.format("Le joueur %d a terminé son tour.", playerId));

            game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() + 1) % game.getGameSize());
            if (!game.gameEnded() && game.getClientsId().contains(playerId)) {
                log.info(String.format("C'est au tour du joueur %d", game.getClients().get(game.getCurrentPlayerIndex()).getId()));
            }
            return new ResponseContainer(true, String.format("Player %d have to play", game.getClients().get(game.getCurrentPlayerIndex()).getId()));
        }
        else{
            log.info(String.format("Le joueur %d notifie la fin de son tour alors que ce n'est pas son tour. Aucune modification apportée à l'état de la partie.", playerId));
            return new ResponseContainer(false, String.format("Player %d have to play, it is not your turn.", game.getClients().get(game.getCurrentPlayerIndex()).getId()));
        }


    }

    /**
     * Permet de check si une partie est terminée
     * @return ResponseContainer
     *
     *
     * @api {get} /gameEnded gameEnded
     * @apiVersion 0.3.0
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

    /**
     * Permet d'enregistrer un joueur
     * @param client HTTPClient
     * @return ResponseContainer
     *
     * @api {post} /register/ Register
     * @apiVersion 0.3.0
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
    public ResponseContainer register(@RequestBody HTTPClient client){
        if (!game.isGameStarted()) {

            if (!game.getClientsId().contains(client.getId())) {
                game.getClients().add(client);
                if (game.getClients().size() == game.getGameSize()) {
                    game.setGameStarted(true);
                }
                log.info(String.format("Le joueur %d@%s c'est enregistré.", client.getId(), client.getUser_adress()));
                return new ResponseContainer(true, "You joined the game");
            }
            else{
                return new ResponseContainer(false, "Already registered");
            }
        }
        else{
            log.info(String.format("Le joueur %d@%s tente de s'enregistrer", client.getId(), client.getUser_adress()));
            return new ResponseContainer(false, "Game has started");
        }
    }


}
