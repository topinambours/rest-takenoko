package core.service;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import core.GameEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@EnableScheduling
public class NotificationService {

    @Autowired
    GameEngine game;

    private boolean firstPlayerTurn = true;
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    /**
     * Check if game is ended every 1000ms
     * & Trigger the first turn call
     */
    @Scheduled(fixedDelay = 1000)
    public void doNotify() throws InterruptedException {
        if (game.gameEnded()){
            log.info("GAME ENDED DISCONNECTING CLIENTS");

            List<HTTPClient> connectedClients = game.getClients();

            for (HTTPClient c : game.getClients()){
                ResponseContainer e = c.self_request(String.format("/closeApplication/%s", "GAME ENDED"), ResponseContainer.class);
                // response true if player receive the notification
                if (e.response){
                    connectedClients.remove(c);
                }
            }
            if (connectedClients.isEmpty()){
                log.info("ALL CLIENTS DISCONNECTED");
                System.exit(0);
            }
        }
        // Trigger the first notification of turn
        if (game.isGameStarted() && firstPlayerTurn) {
            Thread.sleep(1000);
            firstPlayerTurn = false;
            int id_notify = game.getClients().get(game.getCurrentPlayerIndex()).getId();
            log.info(String.format("C'est au tour du joueur %d", id_notify));
            game.getClients().forEach(client ->
                    client.self_request(String.format("/notify/%d", id_notify), ResponseContainer.class));
        }
    }

}