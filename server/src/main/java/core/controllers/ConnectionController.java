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

    @GetMapping("/end_turn")
    public ResponseContainer end_turn(){
        game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() + 1) % game.getGameSize());
        System.out.println(game.getCurrentPlayerIndex());
        HTTPClient currentPlayer = game.getClients().get(game.getCurrentPlayerIndex());
        System.out.println("CURRENT PLAYER" + currentPlayer.toString());

        return new ResponseContainer(true, String.format("Player %d have to play", game.getClients().get(game.getCurrentPlayerIndex()).getId()));
    }


    @PostMapping("/post_turn/")
    public ResponseContainer post_turn(@RequestBody TuileContainer tuiles){
        return new ResponseContainer(true, tuiles.toString());
    }

    @PostMapping("/current_player_turn")
    public ResponseContainer current_player_turn(@RequestBody HTTPClient client){
        HTTPClient currentPlayer = game.getClients().get(game.getCurrentPlayerIndex());
        return new ResponseContainer(client.getId() == currentPlayer.getId(), String.format("Turn of player %d", currentPlayer.getId()));
    }

    @PostMapping("/register/")
    public ResponseContainer register(@RequestBody HTTPClient client){
        game.getClients().add(client);
        System.out.println(game.getClients());
        if (game.getClients().size() == game.getGameSize()){
            //game.start();
            return new ResponseContainer(true, "Game started");
        }
        return new ResponseContainer(true, "You joined the game");
    }
}
