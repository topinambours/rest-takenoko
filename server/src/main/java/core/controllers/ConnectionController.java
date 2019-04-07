package core.controllers;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import core.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionController extends Server {

    Logger logger = LoggerFactory.getLogger(ConnectionController.class);

    @RequestMapping("/ping/{id}/{user_url}")
    public ResponseContainer ping_received(
            @PathVariable int id,
            @PathVariable String user_url)
    {
        logger.info(String.format("Ping received from %d@%s", id, user_url));
        return new ResponseContainer(true, "pong");
    }

    @RequestMapping("/register/{id}/{user_url}")
    public ResponseContainer registration_req(
            @PathVariable int id,
            @PathVariable String user_url){
        logger.info(String.format("Registration request from %d@%s", id, user_url));

        if (registeredUsers.containsKey(id)){
            logger.warn(String.format("User with id %d already registered", id));
            return new ResponseContainer(false, "Already registered");
        }
        else{
            registeredUsers.put(id, new HTTPClient(id, user_url));
            logger.info(String.format("User with id %d registered with address %s", id,user_url));
            return new ResponseContainer(true, "Registration complete");
        }

    }

    @RequestMapping("/matchmaking/{id}/{gameSize}")
    public ResponseContainer matchmaking_req(
            @PathVariable int id,
            @PathVariable int gameSize
    ){
        logger.info(String.format("Player %d request matchmaking of size %d", id, gameSize));
        if (registeredUsers.containsKey(id)){
            if (gameSize >= 2 & gameSize <= 4){
                queues.addClient(gameSize, registeredUsers.get(id));

                // @TODO demander une game à une entité compétente.
                if (queues.enoughPlayerForGame(gameSize)){
                    return new ResponseContainer(true, String.format("Enterring new game with %d other players.", gameSize - 1));
                }
                else{
                    return new ResponseContainer(true, "Performing matchmaking");
                }
            }else{
                return new ResponseContainer(false, "Invalid game size");
            }

            // Le joueur n'est pas enregistré, son adresse est donc inconnu, impossible de démarrer une partie.
        }else{
            return new ResponseContainer(false, "You must be logged before enter matchmaking");
        }
    }


}
