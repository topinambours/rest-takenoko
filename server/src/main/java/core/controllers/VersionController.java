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
    @GetMapping("/version/{v}/")
    public ActionContainer getVersionID(@PathVariable int version) throws VersionNotFoundException {
        if(gameEngine.getVersionning().size() <= version){
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
     * @api {get} /version/from/:f getVersionFrom
     * @apiVersion 0.4.0
     * @apiDescription Get all the versions from a version
     * @apiName getVersionFrom
     * @apiGroup Server/VersionController
     *
     * @apiParam {number} Version ID From
     *
     * @apiSuccess ActionContainer : list of actions
     *
     */
    @GetMapping("/version/from/{f}")
    public ActionContainer getVersionFrom(@PathVariable int from) throws VersionNotFoundException {
        int size = gameEngine.getVersionning().size();
        if (size <= from || from < 0){
            throw new VersionNotFoundException();
        }
        return new ActionContainer(gameEngine.getVersionning().subList(from,size-1));
    }

    /**
     * Get all the versions from a version to a version
     * @param from int
     * @param to int
     * @return ActionContainer
     * @throws VersionNotFoundException
     *
     *
     * @api {get} /version/from/:f/to/:t getVersionFromTo
     * @apiVersion 0.4.0
     * @apiDescription Get all the versions from a version to a version
     * @apiName getVersionFromTo
     * @apiGroup Server/VersionController
     *
     * @apiParam {number} Version ID From
     * @apiParam {number} Version ID To
     *
     * @apiSuccess ActionContainer : list of actions
     *
     */
    @GetMapping("/version/from/{f}/to/{t}")
    public ActionContainer getVersionFromTo(@PathVariable int from,@PathVariable int to) throws VersionNotFoundException {
        int size = gameEngine.getVersionning().size();
        if(size <= from || size <= to || from < 0){
            throw new VersionNotFoundException();
        }
        return new ActionContainer(gameEngine.getVersionning().subList(from,to));
    }


}
