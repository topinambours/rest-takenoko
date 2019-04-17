package core.controllers;

import communication.container.ResponseContainer;
import core.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
@EnableScheduling
public class NotificationService {

    @Autowired
    GameEngine game;


    @Scheduled(fixedDelay = 1000)
    public void doNotify() {
        if (game.gameEnded()){
            System.out.println("NO MORE PLOTS GAME ENDED");
            System.exit(0);
        }
        if (game.getClients().size() == game.getGameSize()) {
            int id_notify = game.gameEnded() ? -1 : game.getClients().get(game.getCurrentPlayerIndex()).getId();
            System.out.println(String.format("NEXT PLAYER : %d", id_notify));
            game.getClients().forEach(client ->
                    client.self_request(String.format("/notify/%d", id_notify), ResponseContainer.class));
        }
    }

}