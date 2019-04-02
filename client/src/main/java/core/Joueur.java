package core;

import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import communication.HTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.Arrays;


public class Joueur extends HTTPClient {

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(Joueur.class);

    private Tuile current_tuile;

    private Joueur() {
        super(-1);
    }

    public Joueur(int id) {
        super(id);
        ResponseContainer resp = pingServer();
        if (resp.response) {
            logger.info("Pong received from server");
            ResponseContainer register_resp = req_register();
            logger.info(register_resp.toString());
        }
        piocher();

        poser_tuile(current_tuile.getUnique_id(), new CoordAxial(0,1));
    }

    public Joueur(int id, String user_port, String distant_server_url) {
        super(id, user_port, distant_server_url);
        ResponseContainer resp = pingServer();
        if (resp.response) {
            logger.info("Pong received from server");
            ResponseContainer register_resp = req_register();
            logger.info(register_resp.toString());
        }
        piocher();

        poser_tuile(current_tuile.getUnique_id(), new CoordAxial(0,1));
    }


    public void piocher() {
        // Ensemble de trois tuiles
        TuileContainer result = piocher_tuiles();

        //TODO, garder la meilleure des trois en accord avec la stratégie du joueur.
        current_tuile = result.getContent().get(0);

        rendre_tuiles(
                result.getContent().get(1).getUnique_id(),
                result.getContent().get(2).getUnique_id()
        );

        System.out.println("Le joueur a pioché :" + Arrays.deepToString(result.getContent().toArray()));
        System.out.println("Le joueur garde la tuile : " + current_tuile);
    }

    @Bean(name = "joueur_1")
    public Joueur joueur_1() {
        String user_port = env.getProperty("client.port");
        String server_adress = env.getProperty("distant.server.address");
        return new Joueur(1, "localhost:" + user_port, server_adress);
    }
}
