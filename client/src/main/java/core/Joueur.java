package core;

import communication.HTTPClient;
import communication.container.ColorContainer;
import communication.container.PoseTuileContainer;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import core.strategie.RandomStrategie;
import core.strategie.Strategie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import takenoko.irrigation.CoordIrrig;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Component
@Import(HTTPClient.class)
public class Joueur {

    public HTTPClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    private HTTPClient httpClient;

    private final Logger logger = LoggerFactory.getLogger(Joueur.class);

    private Tuile current_tuile;

    private Strategie strategie;

    public boolean myTurn = false;

    public Joueur(@Qualifier("http_client") HTTPClient httpClient){
        this.httpClient = httpClient;
        this.strategie = new RandomStrategie();
    }

    public int getId(){
        return httpClient.getId();
    }

    public Tuile getCurrent_tuile() {
        return current_tuile;
    }

    public Strategie getStrategie() {
        return strategie;
    }

    @Primary
    @Bean(name = "joueur_1")
    public Joueur joueur_1() {
        return new Joueur(httpClient);
    }
}
