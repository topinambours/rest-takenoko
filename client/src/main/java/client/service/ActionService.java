package client.service;

import communication.HTTPClient;
import communication.container.PoseTuileContainer;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import client.Joueur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import takenoko.Plateau;
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

    @Autowired
    HTTPClient httpClient;

    @Async("asyncExecutor")
    public void closeApplication(String reason){
        log.info(reason);
        log.info("SERVER REQUEST TO CLOSE APPLICATION");
        if (!reason.equals("test")) {
            System.exit(0);
        }
    }

    @Async("asyncExecutor")
    public void turn(){

        if (joueur.getLatestVersionId() != httpClient.pullLatestVersionId()) {
            // UPDATING BOARD
            boolean updated = updatePlateau(
                    httpClient.pullLatestVersionId(),
                    httpClient.pullVersionFrom(joueur.getLatestVersionId()).getContent()
            );

            if (!updated) {
                updatePlateauFromOrigin(
                        httpClient.pullPlateau(),
                        httpClient.pullLatestVersionId());
            }
        }else{
            log.info("Le joueur a la dernière version du plateau");
        }
        // BOARD IS UPDATED HERE

        // Player draw and select one plot
        Tuile selectedForPos = phasePlotDraw(httpClient);

        // Player put his plot on the board
        ResponseContainer resp = phasePlotPut(httpClient, selectedForPos);
        log.info(resp.toString());

        ResponseContainer responsePoseIrrig = phaseIrrigation(httpClient);
        log.info(responsePoseIrrig.toString());

        ResponseContainer responsePandaMove = phasePanda(httpClient);
        log.info(responsePandaMove.toString());

        httpClient.notifyEndTurn();
        joueur.myTurn = false;
    }

    /**
     * During this phase player draw, decide which plot he has to keep
     * Send back extra plots to the server
     * @param httpClient client that make the request
     * @return Plot selected by the player
     */
    public Tuile phasePlotDraw(HTTPClient httpClient){
        // Le joueur pioche
        TuileContainer tuiles = httpClient.piocher_tuiles();
        log.info("Le joueur a pioché {} tuiles : {}", tuiles.getContent().size(), tuiles.getContent());

        // Player choose one plot to put on the board
        Tuile selectedForPos = joueur.getStrategie().selectTuile(tuiles.getContent());
        tuiles.getContent().remove(selectedForPos);
        // On renvoi les tuiles dans la pioche

        log.info("LE JOUEUR RENVOI LES TUILES : " + tuiles.getContent().toString());

        if (tuiles.getContent().size() > 0) {
            httpClient.rendreTuiles(tuiles);
        }
        return selectedForPos;
    }

    public ResponseContainer phasePanda(HTTPClient httpClient){
        List<CoordAxial> legalPanda = httpClient.requestLegalPandaPositions().getContent();

        log.info("LE JOUEUR PEUT DEPLACER LE PANDA SUR CES COORDS : {}",legalPanda.toString());

        if(!legalPanda.isEmpty()){
            CoordAxial posePanda = joueur.getStrategie().selectPandaEmplacement(legalPanda);
            log.info("LE JOUEUR DEPLACER LE PENDA EN {}",posePanda.toString());

            return httpClient.deplacerPanda(posePanda);
        }
        return new ResponseContainer(false, "NO LEGAL MOVE FOR PANDA");
    }

    public ResponseContainer phaseIrrigation(HTTPClient httpClient){
        List<CoordIrrig> legalIrrig = httpClient.requestLegalIrrigPositions().getContent();

        log.info("LE JOUEUR PEUT PLACER CES IRRIGATIONS : {}",Arrays.deepToString(legalIrrig.toArray()));

        if (!legalIrrig.isEmpty()){
            CoordIrrig poseIrrig = joueur.getStrategie().selectIrrigationEmplacement(legalIrrig);

            log.info("LE JOUEUR POSE UNE IRRIGATION EN {} AVEC ORIENTATION {}",poseIrrig.getCoordAxial().toString(),poseIrrig.getO().toString());

            return httpClient.poserIrrigation(poseIrrig);
        }
        return new ResponseContainer(false, "NO LEGAL MOVE FOR IRRIG");
    }

    /**
     * During this phase the player ask to put his plot on a single coordinate
     * @param httpClient client that make the request
     * @param selectedForPos plot selected by the player to place
     * @return response from the server
     */
    public ResponseContainer phasePlotPut(HTTPClient httpClient, Tuile selectedForPos){
        List<CoordAxial> legalMoves = httpClient.requestLegalMovesTuiles().getContent();

        log.info("LE JOUEUR PEUT JOUER SUR CES COORDS : {}", Arrays.deepToString(legalMoves.toArray()));

        CoordAxial pos = joueur.getStrategie().selectEmplacement(legalMoves);

        log.info("LE JOUEUR POSE SA TUILE {} en {}", selectedForPos.toString(), pos.toString());

        return httpClient.poserTuile(new PoseTuileContainer(pos, selectedForPos));
    }

    /**
     * Update board of the player to versionId given in parameter using a list of action
     * @param versionId id of the last action to perform
     * @param actions list of action given by the server
     * @return true if the board is correctly updated else false
     */
    public boolean updatePlateau(int versionId, List<Action> actions){
        Integer latestPlayerVersion = joueur.getLatestVersionId();
        Integer latestServerVersion = versionId;

        if(!latestPlayerVersion.equals(latestServerVersion)) {
            log.info("Le joueur ayant la plateau à la version {} pull la version {}", latestPlayerVersion, latestServerVersion);

            Boolean goodApply =  Action.applyAllAction(actions, joueur.getPlateau());

            if (goodApply){
                joueur.setLatestVersionId(versionId);
                log.info("Mise en pratique effectuée, nouvelle version du plateau joueur : {}", joueur.getLatestVersionId());
                return true;
            }
            else{
                return false;
            }

        }
        else{
            log.info("Le joueur a la dernière version du plateau");
            return true;
        }
    }

    /**
     * Update the board of the player given an other board
     * Complete board copy
     * @param plateau board given by the server
     * @param headId id of the head of the given board
     */
    public void updatePlateauFromOrigin(Plateau plateau, int headId){
        log.warn("Erreur lors de la mise en pratique de la version sur le plateau du joueur, pull de l'intégralité du plateau ...");

        joueur.setPlateau(plateau);

        joueur.setLatestVersionId(headId);
        log.info("Nouvelle version du plateau joueur : {}", joueur.getLatestVersionId());
    }

}
