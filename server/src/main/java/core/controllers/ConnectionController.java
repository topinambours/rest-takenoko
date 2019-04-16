package core.controllers;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import core.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableScheduling
public class ConnectionController {

    @Autowired
    NotificationService service;

    private final GameEngine game;

    public ConnectionController(@Qualifier("GameEngine") GameEngine game) {
        this.game = game;
    }

    /**
     * Permet d'avoir la fin du tour de jeu
     * @return ResponseContainer
     *
     *
     * @api {get} /end_turn EndTurn
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
    public ResponseContainer end_turn(){

        if (game.gameEnded()){
            game.setCurrentPlayerIndex(-1);
        }else{
            game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() + 1) % game.getGameSize());
        }
        return new ResponseContainer(true, String.format("Player %d have to play", game.getClients().get(game.getCurrentPlayerIndex()).getId()));
    }

    /**
     * Permet de check si une partie est terminée
     * @return ResponseContainer
     *
     *
     * @api {get} /gameEnded gameEnded
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
     * Permet de poser des tuiles
     * @param tuiles TuileCountainer
     * @return ResponseContainer
     *
     * @api {post} /post_turn/ PostTurn
     * @apiDescription allows to put a plot on the board
     * @apiName PostTurn
     * @apiGroup Server/ConnectionController
     *
     * @apiSuccess {Boolean} response The API success response.
     * @apiSuccess {String} message The API message response, here the plot.
     *
     * @apiParam TuileContainer : tuiles List of plots
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *         "response": "true",
     *         "message": "Tuile(unique_id=-1, couleur=Bleu(Lac), amenagement=NONE, haveWater=true, nbBambous=0)y"
     *       }
     *
     */
    @PostMapping("/post_turn/")
    public ResponseContainer post_turn(@RequestBody TuileContainer tuiles){
        return new ResponseContainer(true, tuiles.toString());
    }

    /**
     * Permet de savoir a quel joueur c'est le tour
     * @param client HTTPClient
     * @return ResponseContainer
     *
     * @api {post} /current_player_turn CurrentPlayerTurn
     * @apiDescription Post the httpClient references to get the current player turn
     * @apiName CurrentPlayerTurn
     * @apiGroup Server/ConnectionController
     *
     * @apiSuccess {Boolean} response The API success response.
     * @apiSuccess {String} message The API message response, here the player that play.
     *
     * @apiParam HTTPClient : client Client references
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *         "response": "true",
     *         "message": "Turn of player :id"
     *       }
     *
     */
    @PostMapping("/current_player_turn")
    public ResponseContainer current_player_turn(@RequestBody HTTPClient client){
        HTTPClient currentPlayer = game.getClients().get(game.getCurrentPlayerIndex());
        return new ResponseContainer(client.getId() == currentPlayer.getId(), String.format("Turn of player %d", currentPlayer.getId()));
    }

    /**
     * Permet d'enregistrer un joueur
     * @param client HTTPClient
     * @return ResponseContainer
     *
     * @api {post} /register/ Register
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
        game.getClients().add(client);
        if (game.getClients().size() == game.getGameSize()){
            //game.start();
            return new ResponseContainer(true, "Game started");
        }
        return new ResponseContainer(true, "You joined the game");
    }
}
