package core.controllers;

import communication.container.ResponseContainer;
import core.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@EnableScheduling
public class NotificationService {

    @Autowired
    GameEngine game;

    @Async
    @Scheduled(fixedRate = 500)
    public void doNotify() {
        if (game.getClients().size() == game.getGameSize()) {
            int id_notify = game.gameEnded() ? -1 : game.getClients().get(game.getCurrentPlayerIndex()).getId();
            game.getClients().forEach(client ->
                    client.self_request(String.format("/notify/%d", id_notify), ResponseContainer.class));
        }
        if (game.gameEnded()){
            System.out.println("NO MORE PLOTS GAME ENDED");
            System.exit(0);
        }
    }

}