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

    @Autowired
    GameEngine game;

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

    /**
     * Permet d'avoir le plateau actuel
     * @return Plateau
     *
     *
     * @api {get} /plateau/ getPlateau
     * @apiDescription Get the deck status with all the plots positions
     * @apiName getPlateau
     * @apiGroup Server/PlateauController
     *
     * @apiSuccess Plateau : list of plots
     *
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {"tuiles":
     *          {"(0,0)":
     *              {
     *                  "unique_id":-1,
     *                  "couleur":"BLEU",
     *                  "amenagement":"NONE",
     *                  "haveWater":true,
     *                  "nbBambous":0
     *              }
     *          }
     *        }
     *
     */
    @GetMapping("/plateau/")
    public Plateau getPlateau(){
        return game.getPlateau();
    }


    /**
     * Permet d'avoir la liste des positions légales
     * @return CoordContainer
     *
     * @api {get} /plateau/legal/ getLegalPosition
     * @apiDescription Get the list of legals position to put plots
     * @apiName getLegalPosition
     * @apiGroup Server/PlateauController
     *
     * @apiSuccess CoordContainer : list of CoordAxial
     *
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {"content":[
     *          {"q":0,"r":-1},
     *          {"q":0,"r":1},
     *          {"q":-1,"r":1},
     *          {"q":1,"r":-1},
     *          {"q":-1,"r":0},
     *          {"q":1,"r":0}
     *          ]}
     *
     */
    @GetMapping("/plateau/legal/")
    public CoordContainer getLegalPosition(){
        return new CoordContainer(game.getPlateau().legalPositions());
    }

    /**
     * Permet de poser une tuile
     * @param poseTuileContainer PoseTuileContainer
     * @return ResponseContainer
     *
     * @api {post} /action/poser-tuile/ PoserTuile
     * @apiDescription Post a plot to be put on the board
     * @apiName PoserTuile
     * @apiGroup Server/PlateauController
     *
     * @apiSuccess {Boolean} response The API success response.
     * @apiSuccess {String} message The API message response, here the registration status.
     *
     * @apiParam PoseTuileContainer : a countainer with the plot
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *         "response": "true",
     *         "message": "Tuile posée"
     *       }
     *
     */
    @PostMapping("/action/poser-tuile/")
    public ResponseContainer poser_tuile(
            @RequestBody PoseTuileContainer poseTuileContainer){

        game.getPlateau().poserTuile(poseTuileContainer.getPos(), poseTuileContainer.getTuile());

        log.info(String.format("%s posée en %s",
                poseTuileContainer.getTuile().toString(),
                poseTuileContainer.getPos().toString()));

        return new ResponseContainer(true, "Tuile posée");
    }

    /**
     * Permet de savoir si une position est légale
     * @param q int
     * @param r int
     * @return boolean
     *
     *
     * @api {get} /plateau/legal/:q/:r checkIfPositionIsLegal
     * @apiDescription check if a position is legal to put plot
     * @apiName checkIfPositionIsLegal
     * @apiGroup Server/PlateauController
     *
     * @apiSuccess {Boolean} response true|false depending of the situation.
     *
     * @apiParam {Number} q q variable of the CoordAxial of the plot.
     * @apiParam {Number} r r variable of the CoordAxial of the plot.
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       true
     *
     */
    @RequestMapping(value = "/plateau/legal/{q}/{r}", method = GET)
    @ResponseBody
    public boolean checkIfPositionIsLegal
            (@PathVariable int q, @PathVariable int r) {
        return game.getPlateau().isPositionLegal(new CoordAxial(q,r));
    }
}
