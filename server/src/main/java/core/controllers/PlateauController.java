package core.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takenoko.Plateau;
import takenoko.Tuile;

@RestController
public class PlateauController {

    private final Plateau plateau;

    public PlateauController(@Qualifier("plateau_vide") Plateau plateau){
        this.plateau = plateau;
    }

    @RequestMapping("/plateau/first")
    public Tuile status_req() {
        return plateau.getFirst();
    }
}
