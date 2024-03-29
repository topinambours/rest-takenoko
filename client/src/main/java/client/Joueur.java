package client;

import communication.HTTPClient;
import client.strategie.RandomStrategie;
import client.strategie.Strategie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import takenoko.Plateau;
import takenoko.tuile.Tuile;

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

    private Strategie strategie;

    private Plateau plateau;

    private int latestVersionId = 0;

    public boolean myTurn = false;

    public Joueur(@Qualifier("http_client") HTTPClient httpClient){
        this.httpClient = httpClient;
        this.strategie = new RandomStrategie();
        this.plateau = new Plateau().plateau_depart();
    }

    public int getId(){
        return httpClient.getId();
    }

    public int getLatestVersionId() {
        return latestVersionId;
    }

    public Strategie getStrategie() {
        return strategie;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public void setLatestVersionId(int latestVersionId) {
        this.latestVersionId = latestVersionId;
    }

    @Primary
    @Bean(name = "joueur_1")
    public Joueur joueur_1() {
        return new Joueur(httpClient);
    }
}
