package core;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import core.takenoko.pioche.PiocheTuile;
import takenoko.Plateau;

/**
 * GameEngine est la classe générale d'une instance d'une partie takenoko
 * Configurable via sa taille (nombre de joueurs)
 * Contient l'enssemble des composants d'une partie (pioche, plateau...)
 *
 * Un joueur n'évolue pas dans cette environnement mais peut interragir avec celui-ci via l'api rest définie dans les controllers
 *
 */

@Component
@Data
@Import(Plateau.class)
public class GameEngine {

    @Autowired
    private Environment env;

    private Plateau plateau;
    private PiocheTuile piocheTuile;
    private final int gameSize;

    public GameEngine(){
        this.gameSize = 4;
    }

    public GameEngine(int gameSize){
        this.gameSize = gameSize;
        System.out.println(String.format("NEW GAME CREATED OF SIZE %d", gameSize));
        System.out.println(piocheTuile.toContainer());
    }

    public GameEngine(int gameSize, PiocheTuile piocheTuile, Plateau plateau){
        this.gameSize = gameSize;
        this.piocheTuile = piocheTuile;
        this.plateau = plateau;
        System.out.println(String.format("NEW GAME CREATED OF SIZE %d", gameSize));
        System.out.println(plateau.toString());
        System.out.println(piocheTuile.toContainer());
    }

    @Primary
    @Bean(name = "GameEngine")
    @Scope("singleton")
    public GameEngine gameEngine(
            @Qualifier("piocheDepart") PiocheTuile piocheTuile,
            @Qualifier("plateauTakenoko") Plateau plateau) {
        return new GameEngine(env.getProperty("game.size", Integer.class), piocheTuile, plateau);
    }

    public PiocheTuile getPioche() {
        return piocheTuile;
    }

    public void setPioche(PiocheTuile pioche) {
        this.piocheTuile = pioche;
    }
}
