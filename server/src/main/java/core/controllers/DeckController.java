package core.controllers;

import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import core.takenoko.pioche.EmptyDeckException;
import core.takenoko.pioche.PiocheTuile;
import takenoko.tuile.Tuile;

import java.util.HashMap;

@RestController
@Import(PiocheTuile.class)
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    private final PiocheTuile pTuile;

    /**
     * Représente l'ensemble des tuiles disponibles durant la partie.
     */
    public HashMap<Integer, Tuile> available_tuiles;

    public DeckController(@Qualifier("piocheDepart") PiocheTuile pTuile) {
        this.available_tuiles = new HashMap<>();
        for (Tuile t : pTuile.toContainer().getContent()){
            available_tuiles.put(t.getUnique_id(), t);
        }
        this.pTuile = pTuile;
    }

    @RequestMapping("/action/piocher")
    public TuileContainer req_pioche() throws EmptyDeckException {
        return new PiocheTuile(pTuile.draw(3)).toContainer();
    }

    @PostMapping("/action/rendre_tuiles/")
    public ResponseContainer rendre_tuiles(@RequestBody TuileContainer tuiles){
        for (Tuile t : tuiles.getContent()){
            pTuile.insertBottom(t);
            log.info(t.toString() + " INSERTED TO BOTTOM OF DECK");
        }
        return new ResponseContainer(true, "return effective");
    }

    @RequestMapping("/action/rendre-tuiles/{uid_1}/{uid_2}")
    public Boolean req_rendre_tuiles(
            @PathVariable Integer uid_1,
            @PathVariable Integer uid_2){

        if (available_tuiles.containsKey(uid_1) &&
                available_tuiles.containsKey(uid_2)){
            Tuile t1 = available_tuiles.get(uid_1);
            Tuile t2 = available_tuiles.get(uid_2);

            pTuile.insertBottom(t1);
            log.info(t1.toString() + " replacée dans la core.takenoko.pioche.");

            pTuile.insertBottom(t2);
            log.info(t2.toString() + " replacée dans la core.takenoko.pioche.");

            return true;
        }

        return false;
    }

    public PiocheTuile getpTuile() {
        return pTuile;
    }
}