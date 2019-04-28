package core.service;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import core.GameEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ConnectionService {
    @Autowired
    GameEngine game;

    private static final Logger log = LoggerFactory.getLogger(ConnectionService.class);

    @Async("asyncExecutor")
    public CompletableFuture<ResponseContainer> registerNewClient(HTTPClient client){
        if (!game.isGameStarted()) {

            if (!game.getClientsId().contains(client.getId())) {
                game.getClients().add(client);
                if (game.getClients().size() == game.getGameSize()) {
                    game.setGameStarted(true);
                }
                log.info(String.format("Le joueur %d@%s c'est enregistré.", client.getId(), client.getUser_adress()));
                return CompletableFuture.completedFuture(new ResponseContainer(true, "You joined the game"));
            }
            else{
                return CompletableFuture.completedFuture(new ResponseContainer(false, "Already registered"));
            }
        }
        else{
            log.info(String.format("Le joueur %d@%s tente de s'enregistrer", client.getId(), client.getUser_adress()));
            return CompletableFuture.completedFuture(new ResponseContainer(false, "Game has started"));
        }
    }

    @Async("asyncExecutor")
    public CompletableFuture<ResponseContainer> enTurnNotified(int playerId) {
        HTTPClient currentPlayer = game.getClients().get(game.getCurrentPlayerIndex());

        if (playerId == currentPlayer.getId()){
            log.info(String.format("Le joueur %d a terminé son tour.", playerId));

            game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() + 1) % game.getGameSize());
            if (!game.gameEnded() && game.getClientsId().contains(playerId)) {
                log.info(String.format("C'est au tour du joueur %d", game.getClients().get(game.getCurrentPlayerIndex()).getId()));
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
            log.info(String.format("Le joueur %d notifie la fin de son tour alors que ce n'est pas son tour. Aucune modification apportée à l'état de la partie.", playerId));
            return CompletableFuture.completedFuture(new ResponseContainer(false, String.format("Player %d have to play, it is not your turn.", game.getClients().get(game.getCurrentPlayerIndex()).getId())));
        }

    }


}
