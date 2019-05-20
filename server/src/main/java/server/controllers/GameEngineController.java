package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import server.GameEngine;
import takenoko.Plateau;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.versionning.Action;
import takenoko.versionning.VersionNotFoundException;

import java.util.HashMap;

@Controller
public class GameEngineController {

    @Autowired
    VersionController vc;

    @Autowired
    GameEngine game;

    @GetMapping({"/{version_id}", "/"})
    public String index(Model model, @PathVariable(required = false) int version_id){

        StringBuilder tuileDrawCode = new StringBuilder();

        Plateau p = new Plateau().plateau_depart();

        try {
            Action.applyAllAction(vc.getVersionFromTo(0,version_id).getContent(), p);
        } catch (VersionNotFoundException e) {
            p = game.getPlateau();
        }

        for (HashMap.Entry<CoordAxial, Tuile> entry : p.generateTuileMap().entrySet()){
            tuileDrawCode.append(entry.getValue().generateDrawCode(entry.getKey())).append("\n");
        }

        for (HashMap.Entry<CoordAxial, Tuile> entry : p.generateTuileMap().entrySet()){
            tuileDrawCode.append(entry.getValue().generateDrawCodeIrrig(entry.getKey(), p.getIrrigations()));
        }

        model.addAttribute("script", tuileDrawCode.toString());


        String header = game.toString();
        header += "\n Panda Position :" + p.posPanda().toString();

        model.addAttribute("game",header);


        return "index";
    }

    @GetMapping("/gameSize")
    public String gameSize(){
        return game.getGameSize() + "";
    }

    @GetMapping("/plateau")
    public String plateau(){
        return game.getPlateau().toString();
    }

}
