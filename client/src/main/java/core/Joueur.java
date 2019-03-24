package core;

import communication.Container.ResponseContainer;
import communication.Container.TuileContainer;
import communication.HTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;


public class Joueur extends HTTPClient {

    private final Logger logger = LoggerFactory.getLogger(Joueur.class);

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
    }

    public void piocher() {
        TuileContainer result = piocher_tuiles();

        System.out.println("Le joueur a pioché :" + Arrays.deepToString(result.getContent().toArray()));
    }


    @Bean(name = "joueur_1")
    public Joueur joueur_1() {
        return new Joueur(1);
    }
}
