package core;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import core.takenoko.pioche.EmptyDeckException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import core.takenoko.pioche.PiocheTuile;
import takenoko.Plateau;

import java.util.ArrayList;
import java.util.List;

/**
 * GameEngine est la classe générale d'une instance d'une partie takenoko
 * Configurable via sa taille (nombre de joueurs)
 * Contient l'enssemble des composants d'une partie (pioche, plateau...)
 *
 * Un joueur n'évolue pas dans cette environnement mais peut interragir avec celui-ci via l'api rest définie dans les controllers
 *
 */

@Component
@Import(Plateau.class)
@Data
public class GameEngine {

    @Autowired
    private Environment env;

    private Plateau plateau;
    private PiocheTuile piocheTuile;
    private final int gameSize;

    private List<HTTPClient> clients;

    private int currentPlayerIndex;

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
        this.clients = new ArrayList<>();
        System.out.println(String.format("NEW GAME CREATED OF SIZE %d", gameSize));
        System.out.println(plateau.toString());
        System.out.println(piocheTuile.toContainer());
    }

    public boolean gameEnded(){
        return plateau.getTuiles().size() == 28;
    }

    @Primary
    @Bean(name = "GameEngine")
    @Scope("singleton")
    public GameEngine gameEngine(
            @Qualifier("piocheDepart") PiocheTuile piocheTuile,
            @Qualifier("plateauTakenoko") Plateau plateau) {
        return new GameEngine(env.getProperty("game.size", Integer.class), piocheTuile, plateau);
    }


    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public PiocheTuile getPiocheTuile() {
        return piocheTuile;
    }

    public void setPiocheTuile(PiocheTuile piocheTuile) {
        this.piocheTuile = piocheTuile;
    }

    public int getGameSize() {
        return gameSize;
    }

    public List<HTTPClient> getClients() {
        return clients;
    }

    public void setClients(List<HTTPClient> clients) {
        this.clients = clients;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}
