package core;

import core.controllers.ConnectionController;
import core.controllers.DeckController;
import core.controllers.PlateauController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import takenoko.Plateau;


@Import(Plateau.class)
public class Server {

    @Autowired
    private PlateauController p_control;

    @Autowired
    private DeckController pioche_control;

    @Autowired
    private ConnectionController conn_controller;

    @Bean(name = "mainServer")
    public Server mainServer() {
        return new Server();
    }

}
