package core;

import communication.HTTPClient;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import core.strategie.RandomStrategie;
import core.strategie.Strategie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import takenoko.tuile.Tuile;

@Component
public class Joueur extends HTTPClient {

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(Joueur.class);

    private Tuile current_tuile;

    private Strategie strategie;

    public boolean myTurn;

    public Joueur(){
        super(0,"","");
        this.strategie = new RandomStrategie();
    }

    public Joueur(int id, String user_port, String distant_server_url,Strategie strategie) {
        super(id, user_port, distant_server_url);
        registerGame();
        this.strategie = strategie;
    }

    public Joueur(int id, String user_port, String distant_server_url) {
        super(id, user_port, distant_server_url);
        registerGame();
    }

    public ResponseContainer turn(){
        TuileContainer tuiles = piocher_tuiles();
        logger.info(String.format("Le joueur a pioché %d tuiles : %s", tuiles.getContent().size(), tuiles.getContent()));
        return notifyEndTurn();
    }

    /**
     *
     * @return
     */

    @Primary
    @Bean(name = "joueur_1")
    public Joueur joueur_1() {
        String user_port = env.getProperty("client.port");
        String server_adress = env.getProperty("distant.server.address");
        int player_id = Integer.parseInt(env.getProperty("client.id"));
        this.strategie = new RandomStrategie();
        return new Joueur(player_id, "localhost:" + user_port, server_adress);
    }
}
