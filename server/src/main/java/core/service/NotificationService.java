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
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
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
    public void doNotify() throws InterruptedException, IOException {
        if (game.gameEnded()){
            log.info("GAME ENDED DISCONNECTING CLIENTS");

            List<HTTPClient> connectedClients = game.getClients();
            for (HTTPClient c : game.getClients()){
                ResponseContainer e;
                try {
                    e = c.self_request(String.format("/closeApplication/%s", "GAME ENDED"), ResponseContainer.class);

                    // response true if player receive the notification
                    if (e.response) {
                        connectedClients.remove(c);
                    }
                }
                catch (ResourceAccessException ex){
                    connectedClients.remove(c);
                    log.info(String.format("CLIENT %d DISCONNECTED", c.getId()));
                }
            }
            if (connectedClients.isEmpty()){
                log.info("ALL CLIENTS DISCONNECTED");
                if (game.waitToClose){
                    log.info("WaitToClose mode ON : WAITING INPUT TO CLOSE SERVER");
                    System.in.read();
                    System.exit(0);
                }else{
                    System.exit(0);
                }

            }
        }
        // Trigger the first notification of turn
        if (game.isGameStarted() && firstPlayerTurn) {
            Thread.sleep(2500);
            firstPlayerTurn = false;
            int id_notify = game.getClients().get(game.getCurrentPlayerIndex()).getId();
            log.info(String.format("C'est au tour du joueur %d", id_notify));
            game.getClients().forEach(client ->
                    client.self_request(String.format("/notify/%d", id_notify), ResponseContainer.class));
        }
    }

}