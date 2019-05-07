package core.controllers;

import communication.container.*;
import core.GameEngine;
import core.controllers.exception.IllegalArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import takenoko.Couleur;
import takenoko.Plateau;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.tuile.TuileNotFoundException;
import takenoko.versionning.Action;
import takenoko.versionning.ActionType;

import java.util.List;

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
     * @api {get} /plateau/ pullPlateau
     * @apiVersion 0.6.0
     * @apiDescription Get the deck status with all the plots positions
     * @apiName pullPlateau
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

    @RequestMapping(value = "/plateau/")
    public Plateau getPlateau(){
        return game.getPlateau();
    }

    /**
     * Permet d'avoir la liste des positions légales
     * @return CoordContainer
     *
     * @api {get} /plateau/tuile/legal/ getLegalPosition
     * @apiVersion 0.6.0
     * @apiDescription Get the list of legals position to put plots
     * @apiName getLegalPosition
     * @apiGroup Server/PlateauController
     *
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
     * @apiVersion 0.6.0
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
     * @apiError IllegalArgumentException La position de la tuile n'est pas une position légale
     *
     */
    @PostMapping("/action/poser-tuile/")
    public ResponseContainer poser_tuile(@RequestParam(value = "playerId",
            required = false,
            defaultValue = "-1") int playerId,
            @RequestBody PoseTuileContainer poseTuileContainer) throws CloneNotSupportedException, IllegalArgumentException {

        if(! game.getPlateau().legalPositions().contains(poseTuileContainer.getPos())){
            log.warn("IllegalArgumentException : La position de la tuile n'est pas une position légale");
            throw new IllegalArgumentException("La position de la tuile n'est pas une position légale");
        }else{
            Tuile clone = poseTuileContainer.getTuile().clone();

            game.getPlateau().poserTuile(poseTuileContainer.getPos(), poseTuileContainer.getTuile());
            Action action = new Action(game.getVersionning().size()+1,ActionType.PUTPLOT,poseTuileContainer.getPos(), clone);
            game.addVersion(action);
            log.info("Nouvelle version : "+ action.toString());

            log.info(String.format("Le joueur %d pose %s en %s",
                    playerId,
                    poseTuileContainer.getTuile().toString(),
                    poseTuileContainer.getPos().toString()));

            return new ResponseContainer(true, "Tuile posée");
        }
    }

    /**
     * Permet de savoir si une position est légale
     * @param q int
     * @param r int
     * @return boolean
     *
     *
     * @api {get} /plateau/tuile/legal/:q/:r checkIfPositionIsLegal
     * @apiVersion 0.6.0
     * @apiDescription check if a position is legal to put plot
     * @apiName checkIfPositionIsLegal
     * @apiGroup Server/PlateauController
     *
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
     * Permet d'avoir
     * @param id int
     * @return TuileContainer
     * @throws TuileNotFoundException
     *
     *
     * @api {get} /platea/tuile/:id getTuileFromID
     * @apiVersion 0.6.0
     * @apiDescription get the tuile from his ID
     * @apiName getTuileFromID
     * @apiGroup Server/PlateauController
     *
     *
     * @apiParam {Number} id Unique ID.
     *
     * @apiError TuileNotFoundException
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {"content":[
     *       {"unique_id":-1,"couleur":"BLEU","amenagement":"NONE","haveWater":true,"nbBambous":0}
     *       ]}
     *
     */
    @RequestMapping(value = "/platea/tuile/{id}")
    @ResponseBody
    public TuileContainer getTuileFromID(@PathVariable int id) throws TuileNotFoundException {
        return new TuileContainer(game.getPlateau().getTuileFromId(id));
    }

    /**
     * Permet d'avoir la coordonnée d'une tuile via son id
     * @param id int
     * @return CoordContainer
     * @throws TuileNotFoundException
     *
     *
     * @api {get} /platea/tuile/:id/coord getTuileCoordFromID
     * @apiVersion 0.6.0
     * @apiDescription get the tuile coord from his ID
     * @apiName getTuileCoordFromID
     * @apiGroup Server/PlateauController
     *
     *
     * @apiParam {Number} id Unique ID.
     *
     * @apiError TuileNotFoundException
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {"content":[
     *       {"q":0,"r":0}
     *       ]}
     *
     */
    @RequestMapping(value = "/platea/tuile/{id}/coord")
    @ResponseBody
    public CoordContainer getTuileCoordFromID(@PathVariable int id) throws TuileNotFoundException {
        return new CoordContainer(game.getPlateau().getTuileFormId(id).getKey());
    }


    /**
     * Permet de savoir si une tuile a de l'eau
     * @param q int
     * @param r int
     * @return boolean
     *
     *
     * @api {get} /plateau/tuile/water/:q/:r checkTuileWater
     * @apiVersion 0.6.0
     * @apiDescription check if a plot have water on it
     * @apiName checkTuileWater
     * @apiGroup Server/PlateauController
     *
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
    @RequestMapping(value = "/plateau/tuile/water/{q}/{r}", method = GET)
    @ResponseBody
    public boolean checkTuileWater
            (@PathVariable int q, @PathVariable int r) {
        return game.getPlateau().getTuileAtCoord(new CoordAxial(q,r)).getHaveWater();
    }

    /**
     * Permet d'avoir la hauteur d'un bambou
     * @param q int
     * @param r int
     * @return int
     *
     *
     * @api {get} /plateau/tuile/bambou/:q/:r checkBambouHeight
     * @apiVersion 0.6.0
     * @apiDescription check the height of the bamboo of the plot
     * @apiName checkBambouHeight
     * @apiGroup Server/PlateauController
     *
     *
     * @apiSuccess {Integer} height of the bamboo.
     *
     * @apiParam {Number} q q variable of the CoordAxial of the plot.
     * @apiParam {Number} r r variable of the CoordAxial of the plot.
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       1
     *
     */
    @RequestMapping(value = "/plateau/tuile/bambou/{q}/{r}", method = GET)
    @ResponseBody
    public int checkBambouHeight
            (@PathVariable int q, @PathVariable int r) {
        return game.getPlateau().getTuileAtCoord(new CoordAxial(q,r)).getNbBambous();
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
     * @apiVersion 0.6.0
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
     * @apiVersion 0.6.0
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

    /**
     * Permet d'avoir la liste des positions d'irrigation acceptés
     * @return CoordIrrigContainer
     *
     * @api {get} /plateau/irrigation/legal/ computeLegalIrrigPositions
     * @apiVersion 0.6.0
     * @apiDescription Compute the legal posotions to push irrigations
     * @apiName computeLegalIrrigPositions
     * @apiGroup Server/PlateauController
     *
     *
     * @apiSuccessExample Success-Response:
     *       {"content":[]}
     *
     *
     */
    @GetMapping("/plateau/irrigation/legal/")
    public CoordIrrigContainer computeLegalIrrigPositions(){
        return new CoordIrrigContainer(game.getPlateau().legalIrrigPositions());
    }


    /**
     * Permet d'avoir la liste des irrigations
     * @return CoordIrrigContainer
     *
     * @api {get} /plateau/irrigation/ listOfIrrigation
     * @apiVersion 0.6.0
     * @apiDescription Get the list of irrigations on the board
     * @apiName listOfIrrigation
     * @apiGroup Server/PlateauController
     *
     *
     * @apiSuccessExample Success-Response:
     *       {"content":[
     *       {"coordAxial":{"q":0,"r":1},"o":"N"},
     *       {"coordAxial":{"q":0,"r":0},"o":"W"},
     *       {"coordAxial":{"q":0,"r":0},"o":"S"},
     *       {"coordAxial":{"q":1,"r":0},"o":"W"},
     *       {"coordAxial":{"q":1,"r":-1},"o":"S"},
     *       {"coordAxial":{"q":0,"r":0},"o":"N"}
     *       ]}
     *
     */
    @GetMapping("/plateau/irrigation/")
    public CoordIrrigContainer listOfIrrigation(){
        return new CoordIrrigContainer(List.copyOf(game.getPlateau().irrigationsList()));
    }

    /**
     * Permet de poser une irrigation
     * @param coordIrrig CoordIrrig
     * @return boolean true|false
     *
     *
     * @api {post} /action/poser-irrigation/ PoserIrrigation
     * @apiVersion 0.6.0
     * @apiDescription Post a irrigation to be put on the board
     * @apiName PoserIrrigation
     * @apiGroup Server/PlateauController
     *
     * @apiSuccess {Boolean} response The API success response.
     * @apiSuccess {String} message The API message response, here the registration status.
     *
     * @apiParam CoordIrrig : a irrigation coord
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *         "response": "true",
     *         "message": "Pose d'irrigation effectué en {"coordAxial":{"q":0,"r":0},"o":"W"}"
     *       }
     *
     * @apiErrorExample Error-Response:
     *       {
     *         "response": "false",
     *         "message": "Erreur lors de la pose de l'irrigation en {"coordAxial":{"q":0,"r":0},"o":"W"}"
     *       }
     *
     * @apiError IllegalArgumentException L'irrigation passée en paramètres n'est pas une irrigation légale
     *
     */
    @PostMapping("/action/poser-irrigation/")
    public ResponseContainer poserIrrigation(
            @RequestBody CoordIrrig coordIrrig,
            @RequestParam(value = "playerId",
            required = false,
            defaultValue = "-1") int playerId) throws IllegalArgumentException {

        boolean res;
        if (game.getPlateau().legalIrrigPositions().contains(coordIrrig)){
            res = game.getPlateau().addIrrigation(coordIrrig);
        }else{
            log.warn("IllegalArgumentException : L'irrigation passée en paramètres n'est pas une irrigation légale");
            throw new IllegalArgumentException("L'irrigation passée en paramètres n'est pas une irrigation légale");
        }
        if (res){
            log.info(String.format("Le joueur %d pose une irrigation en %s", playerId, coordIrrig));

            Action action = new Action(game.getVersionning().size()+1,ActionType.ADDIRRIG,coordIrrig);
            game.addVersion(action);
            log.info("Nouvelle version : "+ action.toString());
            return new ResponseContainer(res,"Pose d'irrigation effectué en " + coordIrrig.toString());
        }else{
            return new ResponseContainer(res, "Erreur lors de la pose de l'irrigation en "+coordIrrig.toString());
        }

    }

    /**
     * Permet d'avoir la position actuelle du panda
     * @return CoordContainer
     *
     *
     * @api {get} /plateau/panda/ pandaPosition
     * @apiVersion 0.6.0
     * @apiDescription Get the actual position of the panda on the board
     * @apiName pandaPosition
     * @apiGroup Server/PlateauController
     *
     *
     * @apiSuccessExample Success-Response:
     *       {"content":[
     *       {"q":0,"r":0}
     *       ]}
     *
     */
    @GetMapping("/plateau/panda/")
    public CoordContainer pandaPosition(){
        return new CoordContainer(game.getPlateau().posPanda());
    }

    /**
     * Permet d'avoir la liste des positions legal pour le panda
     * @return CoordContainer
     *
     * @api {get} /plateau/panda/legal/ legalPandaPosition
     * @apiVersion 0.6.0
     * @apiDescription Get the legal position to push the panda on the board
     * @apiName legalPandaPosition
     * @apiGroup Server/PlateauController
     *
     *
     * @apiSuccessExample Success-Response:
     *       {"content":[
     *       {"q":0,"r":0}
     *       ]}
     *
     */
    @GetMapping("/plateau/panda/legal/")
    public CoordContainer legalPandaPosition(){
        return new CoordContainer(game.getPlateau().computePandaLegalPositions());
    }


    /**
     * Permet de bouger le panda à une position donnée
     * @param coordAxial CoordAxial
     * @return ColorContainer la couleur du bambou récupéré
     *
     *
     * @api {post} /action/bouger-panda/ BougerPanda
     * @apiVersion 0.6.0
     * @apiDescription Post a coordaxial to move the panda on the board
     * @apiName BougerPanda
     * @apiGroup Server/PlateauController
     *
     *
     * @apiParam CoordAxial : a board coord
     *
     * @apiError IllegalArgumentException La position du panda n'est pas une position légale
     *
     */
    @PostMapping("/action/bouger-panda/")
    public ResponseContainer bougerPanda(@RequestBody CoordAxial coordAxial) throws IllegalArgumentException {
        //Todo : récupérer les bambous #61
        if (! game.getPlateau().computePandaLegalPositions().contains(coordAxial)){
            log.warn("IllegalArgumentException : La position du panda n'est pas une position légale");
            throw new IllegalArgumentException("La position du panda n'est pas une position légale");
        }else{
            Couleur couleur = game.getPlateau().movePanda(coordAxial);
            //return new ColorContainer(couleur);
            Action action = new Action(game.getVersionning().size()+1,ActionType.MOOVEPANDA,coordAxial);
            game.addVersion(action);
            log.info("Nouvelle version : "+ action.toString());
            return new ResponseContainer(true,String.format("LE PANDA C'EST DEPLACE EN %s ET A MANGE UN BAMBOU DE COULEUR %s",coordAxial.toString(),couleur.toString()));
        }
    }




}
