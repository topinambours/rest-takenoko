package core;

import communication.HTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import takenoko.versionning.Action;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.stream.Collectors;

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
public class GameEngine {

    @Autowired
    private Environment env;
    private static final Logger log = LoggerFactory.getLogger(GameEngine.class);

    private Plateau plateau;
    private PiocheTuile piocheTuile;

    private int gameSize;

    private List<HTTPClient> clients;

    private int currentPlayerIndex;

    private boolean gameStarted;

    private List<Action> versionning; //TODO : add the actions into the action list

    public GameEngine(){
        this.gameSize = 4;
    }

    public GameEngine(int gameSize){
        this.gameSize = gameSize;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public GameEngine(int gameSize, @Qualifier("piocheDepart") PiocheTuile piocheTuile, @Qualifier("plateauTakenoko")  Plateau plateau){
        this.gameSize = gameSize;
        this.piocheTuile = piocheTuile;
        this.plateau = plateau;
        this.clients = new ArrayList<>();
        this.gameStarted = false;
        log.info(String.format("Nouvelle partie pour %d joueurs instanciée.", gameSize));
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

        int gameSize;
        try {
            gameSize = env.getProperty("game.size", Integer.class);
        }catch (NullPointerException e){
            gameSize = 2;
        }


        return new GameEngine(gameSize, piocheTuile, plateau);
    }


    public List<Integer> getClientsId(){
        return clients.stream().map(HTTPClient::getId).collect(Collectors.toList());
    }

    public HTTPClient getCurrentPlayer(){
        return getClients().get(currentPlayerIndex);
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
    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
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

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("GameEngine\n");
        out.append("\t gameSize : ").append(gameSize).append("\n");
        out.append("\tClients : ").append(clients.toString());
        return out.toString();
    }
}
