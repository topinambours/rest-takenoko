package core.controllers;

import communication.container.ActionContainer;
import core.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import takenoko.versionning.Action;
import takenoko.versionning.VersionNotFoundException;

import java.util.List;

@RestController
public class VersionController {

    @Autowired
    GameEngine gameEngine;

    /**
     * Get all the versions
     * @return ActionContainer
     *
     *
     * @api {get} /plateau/ getPlateau
     * @apiVersion 0.4.0
     * @apiDescription Get the deck status with all the plots positions
     * @apiName getPlateau
     * @apiGroup Server/PlateauController
     *
     * @apiSuccess Plateau : list of plots
     *
     */
    @GetMapping("/version")
    public ActionContainer getVersions(){
        return new ActionContainer(gameEngine.getVersionning());
    }

    @GetMapping("/version/{v}/")
    public ActionContainer getVersionID(@PathVariable int version) throws VersionNotFoundException {
        if(gameEngine.getVersionning().size() <= version){
            throw new VersionNotFoundException();
        }
        return new ActionContainer(gameEngine.getVersionning().get(version));
    }

    @GetMapping("/version/from/{f}")
    public ActionContainer getVersionFrom(@PathVariable int from) throws VersionNotFoundException {
        int size = gameEngine.getVersionning().size();
        if (size <= from || from < 0){
            throw new VersionNotFoundException();
        }
        return new ActionContainer(gameEngine.getVersionning().subList(from,size-1));
    }

    @GetMapping("/version/from/{f}/to/{t}")
    public ActionContainer getVersionFromTo(@PathVariable int from,@PathVariable int to) throws VersionNotFoundException {
        int size = gameEngine.getVersionning().size();
        if(size <= from || size <= to || from < 0){
            throw new VersionNotFoundException();
        }
        return new ActionContainer(gameEngine.getVersionning().subList(from,to));
    }


}
