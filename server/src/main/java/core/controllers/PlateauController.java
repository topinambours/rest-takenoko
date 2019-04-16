package core.controllers;

import communication.container.CoordContainer;
import core.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import takenoko.Plateau;
import takenoko.tuile.CoordAxial;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Import(Plateau.class)
public class PlateauController {

    private final Plateau plateau;

    @Autowired
    GameEngine game;

    public PlateauController(@Qualifier("plateau_vide") Plateau plateau,DeckController dc) {
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
        System.out.println(plateau.legalPositions().toString());
        return new CoordContainer(plateau.legalPositions());
    }

    @RequestMapping(value = "/plateau/legal/{q}/{r}", method = GET)
    @ResponseBody
    public boolean checkIfPositionIsLegal
            (@PathVariable int q, @PathVariable int r) {
        return plateau.isPositionLegal(new CoordAxial(q,r));
    }
}
