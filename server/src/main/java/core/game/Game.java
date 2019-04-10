package core.game;

import communication.HTTPClient;

import java.util.List;

public class Game {

    private int id;

    private List<HTTPClient> clients;

    public Game(int id, List<HTTPClient> clients){
        this.id = id;
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", clients=" + clients +
                '}';
    }
}
