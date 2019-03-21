package core;

import core.controllers.DeckController;
import core.controllers.PlateauController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import takenoko.Plateau;

@SpringBootApplication
@Import(Plateau.class)
public class App {

    @Autowired
    private PlateauController p_control;

    @Autowired
    private DeckController pioche_control;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

