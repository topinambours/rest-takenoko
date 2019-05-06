package core.controllers;

import core.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GameEngineController {

    @Autowired
    GameEngine game;

    @GetMapping("/showViewPage")
    public String passParametersWithModel(Model model) {
        Map<String, String> map = new HashMap<>();
        map.put("spring", "mvc");
        model.addAttribute("message", "Baeldung");
        model.mergeAttributes(map);
        return "viewPage";
    }

    @GetMapping("/printViewPage")
    public String passParametersWithModelMap(ModelMap map) {
        map.addAttribute("welcomeMessage", "welcome");
        map.addAttribute("message", "Baeldung");
        return "viewPage";
    }

    @GetMapping("/goToViewPage")
    public ModelAndView passParametersWithModelAndView() {
        ModelAndView modelAndView = new ModelAndView("viewPage");
        modelAndView.addObject("message", "Baeldung");
        return modelAndView;
    }


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
