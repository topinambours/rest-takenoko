package core.game;

import communication.HTTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;

@Service
public class GameManager {

    private int currentGameId;

    Hashtable<Integer, Game> games;

    @Autowired
    public GameManager(){
        this.currentGameId = 1;
        this.games = new Hashtable<>();
    }

    public Game requestNewGame(List<HTTPClient> clients){
        games.put(currentGameId, new Game(currentGameId, clients));
        System.out.println(games.get(currentGameId));
        return games.get(currentGameId++);
    }
}
