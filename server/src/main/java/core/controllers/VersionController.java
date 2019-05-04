package core.controllers;

import communication.container.ActionContainer;
import core.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * @api {get} /version getVersions
     * @apiVersion 0.4.0
     * @apiDescription Get all the versions list
     * @apiName getVersions
     * @apiGroup Server/VersionController
     *
     * @apiSuccess ActionContainer : list of actions
     *
     */
    @GetMapping("/version")
    public ActionContainer getVersions(){
        return new ActionContainer(gameEngine.getVersionning());
    }

    /**
     * Get the version with is ID
     * @param version int
     * @return ActionContainer
     * @throws VersionNotFoundException
     *
     *
     * @api {get} /version/:v/ getVersionID
     * @apiVersion 0.4.0
     * @apiDescription Get the version with is ID
     * @apiName getVersionID
     * @apiGroup Server/VersionController
     *
     * @apiParam {number} Version ID
     *
     * @apiSuccess ActionContainer : list of actions
     *
     */
    @GetMapping("/version/{version}/")
    public ActionContainer getVersionID(@PathVariable int version) throws VersionNotFoundException {
        if(gameEngine.getVersionning().size() <= version || version < 0){
            throw new VersionNotFoundException();
        }
        return new ActionContainer(gameEngine.getVersionning().get(version));
    }

    /**
     * Get all the versions from a version
     * @param from int
     * @return ActionContainer
     * @throws VersionNotFoundException
     *
     *
     * @api {get} /version/from/:from getVersionFrom
     * @apiVersion 0.4.0
     * @apiDescription Get all the versions from a version
     * @apiName getVersionFrom
     * @apiGroup Server/VersionController
     *
     * @apiParam {number} from Version ID From
     *
     * @apiSuccess ActionContainer : list of actions
     *
     */
    @GetMapping("/version/from/{from}")
    public ActionContainer getVersionFrom(@RequestParam(value = "playerId",
                                            required = false,
                                            defaultValue = "-1") int playerId,
                                          @PathVariable int from) throws VersionNotFoundException {


        int size = gameEngine.getVersionning().size();
        if (size <= from || from < 0){
            throw new VersionNotFoundException();
        }
        return new ActionContainer(gameEngine.getVersionning().subList(from,size));
    }

    /**
     * Get all the versions from a version to a version
     * @param from int
     * @param to int
     * @return ActionContainer
     * @throws VersionNotFoundException
     *
     *
     * @api {get} /version/from/:from/to/:to getVersionFromTo
     * @apiVersion 0.4.0
     * @apiDescription Get all the versions from a version to a version
     * @apiName getVersionFromTo
     * @apiGroup Server/VersionController
     *
     * @apiParam {number} from Version ID From
     * @apiParam {number} to Version ID To
     *
     * @apiSuccess ActionContainer : list of actions
     *
     */
    @GetMapping("/version/from/{from}/to/{to}")
    public ActionContainer getVersionFromTo(@PathVariable int from,@PathVariable int to) throws VersionNotFoundException {
        int size = gameEngine.getVersionning().size();
        if(size <= from || size < to || from < 0 || from > to){
            throw new VersionNotFoundException();
        }
        return new ActionContainer(gameEngine.getVersionning().subList(from,to));
    }

    /**
     * Get the latest version
     * @return ActionContainer
     *
     *
     * @api {get} /version/latest/ getLatestAction
     * @apiVersion 0.4.0
     * @apiDescription Get the latest version
     * @apiName getLatestAction
     * @apiGroup Server/VersionController
     *
     * @apiSuccess ActionContainer : actions container with the latest version
     *
     */
    @GetMapping("/version/latest/")
    public ActionContainer getLatestAction(){
        return new ActionContainer(gameEngine.getVersionning().get(gameEngine.getVersionning().size()-1));
    }

    /**
     * Get the latest version ID
     * @return int
     *
     * @api {get} /version/latest/id getLatestActionId
     * @apiVersion 0.4.0
     * @apiDescription Get the latest version ID
     * @apiName getLatestActionId
     * @apiGroup Server/VersionController
     *
     * @apiSuccess Integer : Latest version ID
     *
     */
    @GetMapping("/version/latest/id")
    public Integer getLatestActionId(){
        if (gameEngine.getVersionning().size() == 0){
            return 0;
        }
        return gameEngine.getVersionning().size() - 1;
    }


}
