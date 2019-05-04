package core.service;

import communication.HTTPClient;
import communication.container.PoseTuileContainer;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import core.Joueur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import takenoko.irrigation.CoordIrrig;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.versionning.Action;

import java.util.Arrays;
import java.util.List;

@Service
public class ActionService {

    @Autowired
    Joueur joueur;

    private static final Logger log = LoggerFactory.getLogger(ActionService.class);

    @Async("asyncExecutor")
    public void closeApplication(String reason){
        log.info(reason);
        log.info("SERVER REQUEST TO CLOSE APPLICATION");
        System.exit(0);
    }

    @Async("asyncExecutor")
    public void turn(){
        HTTPClient httpClient = joueur.getHttpClient();

        Integer latestPlayerVersion = joueur.getLatestVersionId();
        Integer latestServerVersion = httpClient.pullLatestVersionId();

        if(! latestPlayerVersion.equals(latestServerVersion)){
            log.info(String.format("Le joueur ayant la plateau à la version %d pull la version %d",latestPlayerVersion,latestServerVersion));

            List<Action> actions = httpClient.pullVersionFrom(latestPlayerVersion).getContent();
            boolean goodApply = Action.applyAllAction(actions,joueur.getPlateau());
            if (!goodApply){
                log.warn("Erreur lors de la mise en pratique de la version sur le plateau du joueur, pull de l'intégralité du plateau ...");
                joueur.setPlateau(httpClient.pullPlateau());
            }
            joueur.setLatestVersionId(latestServerVersion);
            log.info(String.format("Nouvelle version du plateau joueur : %d",joueur.getLatestVersionId()));
        }



        TuileContainer tuiles = httpClient.piocher_tuiles();
        log.info(String.format("Le joueur a pioché %d tuiles : %s", tuiles.getContent().size(), tuiles.getContent()));

        Tuile selectedForPos = joueur.getStrategie().selectTuile(tuiles.getContent());
        tuiles.getContent().remove(selectedForPos);
        // On renvoi les tuiles dans la pioche

        log.info("LE JOUEUR RENVOI LES TUILES : " + tuiles.getContent().toString());

        if (tuiles.getContent().size() > 0) {
            httpClient.rendreTuiles(tuiles);
        }

        List<CoordAxial> legalMoves = httpClient.requestLegalMovesTuiles().getContent();

        log.info(String.format("LE JOUEUR PEUT JOUER SUR CES COORDS : %s", Arrays.deepToString(legalMoves.toArray())));

        CoordAxial pos = joueur.getStrategie().selectEmplacement(legalMoves);

        log.info(String.format("LE JOUEUR POSE SA TUILE %s en %s", selectedForPos.toString(), pos.toString()));

        ResponseContainer resp = httpClient.poserTuile(new PoseTuileContainer(pos, selectedForPos));
        log.info(resp.toString());

        List<CoordIrrig> legalIrrig = httpClient.requestLegalIrrigPositions().getContent();

        log.info(String.format("LE JOUEUR PEUT PLACER CES IRRIGATIONS : %s",Arrays.deepToString(legalIrrig.toArray())));

        if (!legalIrrig.isEmpty()){
            CoordIrrig poseIrrig = joueur.getStrategie().selectIrrigationEmplacement(legalIrrig);

            log.info(String.format("LE JOUEUR POSE UNE IRRIGATION EN %s AVEC ORIENTATION %s",poseIrrig.getCoordAxial().toString(),poseIrrig.getO().toString()));

            ResponseContainer responsePoseIrrig = httpClient.poserIrrigation(poseIrrig);
            log.info(responsePoseIrrig.toString());

        }

        List<CoordAxial> legalPanda = httpClient.requestLegalPandaPositions().getContent();

        log.info(String.format("LE JOUEUR PEUT DEPLACER LE PANDA SUR CES COORDS : %s",legalPanda.toString()));

        if(!legalPanda.isEmpty()){
            CoordAxial posePanda = joueur.getStrategie().selectPandaEmplacement(legalPanda);
            log.info(String.format("LE JOUEUR DEPLACER LE PENDA EN %s",posePanda.toString()));

            ResponseContainer responsePandaMove = httpClient.deplacerPanda(posePanda);
            log.info(responsePandaMove.toString());
        }

        httpClient.notifyEndTurn();
        joueur.myTurn = false;
    }
}
