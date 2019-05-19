package server.controllers;

import server.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.HashMap;

@Controller
public class GameEngineController {

    @Autowired
    GameEngine game;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("game",game.toString());
        StringBuilder tuileDrawCode = new StringBuilder();
        for (HashMap.Entry<CoordAxial, Tuile> entry : game.getPlateau().generateTuileMap().entrySet()){
            tuileDrawCode.append(entry.getValue().generateDrawCode(entry.getKey())).append("\n");
        }
        model.addAttribute("script", tuileDrawCode.toString());
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
