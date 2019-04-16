package core;

import communication.HTTPClient;
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
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

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
        System.out.println(httpClient);
    }

    public int getId(){
        return httpClient.getId();
    }

    public ResponseContainer turn(){
        TuileContainer tuiles = httpClient.piocher_tuiles();
        logger.info(String.format("Le joueur a pioché %d tuiles : %s", tuiles.getContent().size(), tuiles.getContent()));

        Tuile selectedForPos = strategie.selectTuile(tuiles.getContent());
        tuiles.getContent().remove(selectedForPos);
        System.out.println(tuiles);
        System.out.println(selectedForPos);
        // On renvoi les tuiles dans la pioche
        httpClient.rendreTuiles(tuiles);

        List<CoordAxial> legalMoves = httpClient.requestLegalMovesTuiles().getContent();

        logger.info(String.format("LE JOUEUR PEUT JOUER SUR CES COORDS : %s", Arrays.deepToString(legalMoves.toArray())));

        CoordAxial pos = strategie.selectEmplacement(legalMoves);

        logger.info(String.format("LE JOUEUR POSE SA TUILE %s en %s", selectedForPos.toString(), pos.toString()));

        ResponseContainer resp = httpClient.poserTuile(new PoseTuileContainer(pos, selectedForPos));

        logger.info(resp.toString());

        return httpClient.notifyEndTurn();
    }

    @Primary
    @Bean(name = "joueur_1")
    public Joueur joueur_1() {
        return new Joueur(httpClient);
    }
}
