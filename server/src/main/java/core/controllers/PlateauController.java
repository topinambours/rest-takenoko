package core.controllers;

import communication.container.CoordContainer;
import communication.container.PoseTuileContainer;
import communication.container.ResponseContainer;
import core.GameEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import takenoko.Plateau;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Import(Plateau.class)
public class PlateauController {
    private final Logger log = LoggerFactory.getLogger(PlateauController.class);

    private final Plateau plateau;

    @Autowired
    GameEngine game;

    public PlateauController(@Qualifier("plateau_vide") Plateau plateau) {
        this.plateau = plateau;
    }
    /*

    @RequestMapping("/plateau/first")
    public Tuile status_req() {
        return new Tuile(-1,Couleur.BLEU);
    }

    @RequestMapping("/action/poser_tuile/{tuile_id}/{q}/{r}")
    public ResponseContainer poser_tuile(@PathVariable int tuile_id, @PathVariable int q, @PathVariable int r){
        plateau.poserTuile(new CoordAxial(q,r), dc.available_tuiles.get(tuile_id));
        System.out.println(plateau.toString());
        return new ResponseContainer(true, "blabla");
    }
    */

    @GetMapping("/plateau/")
    public Plateau getPlateau(){
        return plateau;
    }

    @GetMapping("/plateau/legal/")
    public CoordContainer getLegalPosition(){
        return new CoordContainer(plateau.legalPositions());
    }

    @PostMapping("/action/poser-tuile/")
    public ResponseContainer poser_tuile(
            @RequestBody PoseTuileContainer poseTuileContainer){

        plateau.poserTuile(poseTuileContainer.getPos(), poseTuileContainer.getTuile());

        log.info(String.format("%s posée en %s",
                poseTuileContainer.getTuile().toString(),
                poseTuileContainer.getPos().toString()));

        return new ResponseContainer(true, "Tuile posée");
    }

    @RequestMapping(value = "/plateau/legal/{q}/{r}", method = GET)
    @ResponseBody
    public boolean checkIfPositionIsLegal
            (@PathVariable int q, @PathVariable int r) {
        return plateau.isPositionLegal(new CoordAxial(q,r));
    }
}
