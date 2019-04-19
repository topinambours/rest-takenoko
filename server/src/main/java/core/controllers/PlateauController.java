package core.controllers;

import communication.container.CoordContainer;
import communication.container.CoordIrrigContainer;
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
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Import(Plateau.class)
public class PlateauController {
    private final Logger log = LoggerFactory.getLogger(PlateauController.class);

    @Autowired
    GameEngine game;

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
     * @api {get} /plateau/tuile/legal/ getLegalPosition
     * @apiDescription Get the list of legals position to put plots
     * @apiName getLegalPosition
     * @apiGroup Server/PlateauController
     *
     * @apiDeprecated soon moved to /plateau/tuile/legal/
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
    @GetMapping("/plateau/tuile/legal/")
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
     * @api {get} /plateau/tuile/legal/:q/:r checkIfPositionIsLegal
     * @apiDescription check if a position is legal to put plot
     * @apiName checkIfPositionIsLegal
     * @apiGroup Server/PlateauController
     *
     * @apiDeprecated soon moved to /plateau/tuile/legal/{q}/{r}
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
    @RequestMapping(value = "/plateau/tuile/legal/{q}/{r}", method = GET)
    @ResponseBody
    public boolean checkIfPositionIsLegal
            (@PathVariable int q, @PathVariable int r) {
        return game.getPlateau().isPositionLegal(new CoordAxial(q,r));
    }


    /**
     * Permet d'avoir la liste des bordes d'une tuile
     * @param q int
     * @param r int
     * @param orient Orient
     * @return CoordContainer
     *
     *
     * @api {get} /plateau/irrigation/border/{q}/{r}/{o}/ computeBorders
     * @apiDescription compute Borders of a plot for irrigation
     * @apiName computeBorders
     * @apiGroup Server/PlateauController
     *
     *
     *
     * @apiParam {Number} q q variable of the CoordAxial of the plot.
     * @apiParam {Number} r r variable of the CoordAxial of the plot.
     * @apiParam {String} o A orient Enum {"S","N","W"}
     *
     * @apiSuccessExample Success-Response:
     *       {"content":[
     *       {"q":-1,"r":0},
     *       {"q":0,"r":0}
     *       ]}
     *
     * @apiError Bad Request : The orient need to be into {"S","N","W"}
     *
     */
    @RequestMapping(value = "/plateau/irrigation/border/{q}/{r}/{o}/", method = GET)
    @ResponseBody
    public CoordContainer computeBorders
            (@PathVariable int q, @PathVariable int r, @PathVariable(value = "o") Orient orient) {
        return new CoordContainer(new CoordIrrig(new CoordAxial(q,r),orient).borders());
    }


    /**
     * Permet d'avoir la continuité d'une irrigation
     * @param q int
     * @param r int
     * @param orient Orient
     * @return CoordIrrigContainer
     *
     * @api {get} /plateau/irrigation/border/{q}/{r}/{o}/ computeContinues
     * @apiDescription Compute continuity of a irrigation
     * @apiName computeContinues
     * @apiGroup Server/PlateauController
     *
     *
     *
     * @apiParam {Number} q q variable of the CoordAxial of the plot.
     * @apiParam {Number} r r variable of the CoordAxial of the plot.
     * @apiParam {String} o A orient Enum {"S","N","W"}
     *
     * @apiSuccessExample Success-Response:
     *       {"content":[
     *       {"coordAxial":{"q":0,"r":0},"o":"W"},
     *       {"coordAxial":{"q":0,"r":-1},"o":"S"},
     *       {"coordAxial":{"q":1,"r":-1},"o":"W"},
     *       {"coordAxial":{"q":1,"r":-1},"o":"S"}
     *       ]}
     *
     * @apiError Bad Request : The orient need to be into {"S","N","W"}
     *
     */
    @RequestMapping(value = "/plateau/irrigation/continues/{q}/{r}/{o}/", method = GET)
    @ResponseBody
    public CoordIrrigContainer computeContinues
            (@PathVariable int q, @PathVariable int r, @PathVariable(value = "o") Orient orient) {
        return new CoordIrrigContainer(new CoordIrrig(new CoordAxial(q,r),orient).continues());
    }

}
