package server.service;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import server.GameEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.concurrent.CompletableFuture;

@Service
@EnableScheduling
public class ConnectionService {
    @Autowired
    GameEngine game;

    private int loopCount = 0;

    private static final Logger log = LoggerFactory.getLogger(ConnectionService.class);

    @Scheduled(fixedDelay = 250)
    public void pingUsers(){
        if (game.getClients().size() == game.getGameSize() && loopCount > 60){
            for (HTTPClient c : game.getClients()){
                try {
                c.self_request("/ping", String.class);
                }
                catch (ResourceAccessException e){
                    game.getClients().remove(c);
                    log.info("CLIENT {} DISCONNECTED", c.getId());
                }
            }
        }
    }

    @Scheduled(fixedDelay = 500)
    public void checkConnectedUsers(){
        if (!game.gameEnded() && !game.testMode) {
            // 30 seconds
            if (loopCount >= 60 && game.getClients().isEmpty()) {
                log.info("No clients after 30 seconds, closing server app");
                System.exit(0);
            }
            if (!game.isGameEndedFlag()) {
                if (game.getClients().size() != game.getGameSize() && loopCount >= 60) {
                    log.info("Missing one or more clients, closing server");
                    game.setGameEndedFlag(true);
                }

                if (game.isGameStarted() && game.getClients().size() != game.getGameSize()) {
                    log.info("Missing one or more clients, closing server");
                    game.setGameEndedFlag(true);
                }
            }
        }

        loopCount += 1;
    }

    @Async("asyncExecutor")
    public CompletableFuture<ResponseContainer> registerNewClient(HTTPClient client){
        if (!game.isGameStarted()) {

            if (!game.getClientsId().contains(client.getId())) {
                game.getClients().add(client);
                if (game.getClients().size() == game.getGameSize()) {
                    game.setGameStarted(true);
                }
                log.info("Le joueur {}@{} c'est enregistré.", client.getId(), client.getUser_adress());
                loopCount = 0;
                return CompletableFuture.completedFuture(new ResponseContainer(true, "You joined the game"));
            }
            else{
                return CompletableFuture.completedFuture(new ResponseContainer(false, "Already registered"));
            }
        }
        else{
            log.info("Le joueur {}@{} tente de s'enregistrer", client.getId(), client.getUser_adress());
            return CompletableFuture.completedFuture(new ResponseContainer(false, "Game has started"));
        }
    }

    @Async("asyncExecutor")
    public CompletableFuture<ResponseContainer> enTurnNotified(int playerId) {
        HTTPClient currentPlayer = game.getClients().get(game.getCurrentPlayerIndex());

        if (playerId == currentPlayer.getId()){
            log.info("Le joueur {} a terminé son tour.", playerId);

            game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() + 1) % game.getGameSize());
            if (!game.gameEnded() && game.getClientsId().contains(playerId)) {
                log.info("C'est au tour du joueur {}", game.getClients().get(game.getCurrentPlayerIndex()).getId());
                int id_notify = game.getClients().get(game.getCurrentPlayerIndex()).getId();
                for (HTTPClient c : game.getClients()){
                    if (c.getId() != playerId){
                        CompletableFuture.runAsync(() -> c.self_request(String.format("/notify/%d", id_notify), ResponseContainer.class));
                    }
                }
            }
            return CompletableFuture.completedFuture(new ResponseContainer(true, String.format("Player %d have to play", game.getClients().get(game.getCurrentPlayerIndex()).getId())));
        }
        else{
            log.info("Le joueur {} notifie la fin de son tour alors que ce n'est pas son tour. Aucune modification apportée à l'état de la partie.", playerId);
            return CompletableFuture.completedFuture(new ResponseContainer(false, String.format("Player %d have to play, it is not your turn.", game.getClients().get(game.getCurrentPlayerIndex()).getId())));
        }
    }
}
