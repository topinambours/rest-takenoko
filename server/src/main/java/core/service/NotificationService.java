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

    private boolean cNotifiedEndOfGame = false;
    private boolean firstPlayerTurn = true;
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Scheduled(fixedDelay = 1000)
    public void doNotify() {

        if (game.gameEnded()){
            log.info("FIN DE PARTIE");
            List<HTTPClient> connectedClients = game.getClients();
            for (HTTPClient c : game.getClients()){
                ResponseContainer e = c.self_request(String.format("/closeApplication/%s", "GAME ENDED"), ResponseContainer.class);
                if (e.response){
                    connectedClients.remove(c);
                }
            }
            if (connectedClients.isEmpty()){
                log.info("ALL CLIENTS DISCONNECTED");
                System.exit(0);
            }
        }
        if (game.isGameStarted() && firstPlayerTurn) {
            firstPlayerTurn = false;
            int id_notify = game.gameEnded() ? -1 : game.getClients().get(game.getCurrentPlayerIndex()).getId();
            game.getClients().forEach(client ->
                    client.self_request(String.format("/notify/%d", id_notify), ResponseContainer.class));
        }
    }

}