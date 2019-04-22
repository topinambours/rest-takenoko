// ------------------------------------------------------------------------------------------
// General apiDoc documentation blocks and old history blocks.
// ------------------------------------------------------------------------------------------

// ------------------------------------------------------------------------------------------
// History.
// ------------------------------------------------------------------------------------------

//======
// 0.1.1
//======
/**
 * Permet d'avoir la fin du tour de jeu
 * @return ResponseContainer
 *
 *
 * @api {get} /end_turn EndTurn
 * @apiVersion 0.1.0
 * @apiDescription End the turn end get the player id that have to play
 * @apiName EndTurn
 * @apiGroup Server/ConnectionController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response, here the player that have to play.
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": "Player :id have to play"
 *       }
 *
 *
 *
 */
/**
 * Permet de check si une partie est terminée
 * @return ResponseContainer
 *
 *
 * @api {get} /gameEnded gameEnded
 * @apiVersion 0.1.0
 * @apiDescription Get the status to know if the game is ended
 * @apiName gameEnded
 * @apiGroup Server/ConnectionController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response.
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": ""
 *       }
 *
 *
 *
 */
/**
 * Permet de poser des tuiles
 * @param tuiles TuileCountainer
 * @return ResponseContainer
 *
 * @api {post} /post_turn/ PostTurn
 * @apiVersion 0.1.0
 * @apiDescription allows to put a plot on the board
 * @apiName PostTurn
 * @apiGroup Server/ConnectionController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response, here the plot.
 *
 * @apiParam TuileContainer : tuiles List of plots
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": "Tuile(unique_id=-1, couleur=Bleu(Lac), amenagement=NONE, haveWater=true, nbBambous=0)y"
 *       }
 *
 */
/**
 * Permet de savoir a quel joueur c'est le tour
 * @param client HTTPClient
 * @return ResponseContainer
 *
 * @api {post} /current_player_turn CurrentPlayerTurn
 * @apiVersion 0.1.0
 * @apiDescription Post the httpClient references to get the current player turn
 * @apiName CurrentPlayerTurn
 * @apiGroup Server/ConnectionController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response, here the player that play.
 *
 * @apiParam HTTPClient : client Client references
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": "Turn of player :id"
 *       }
 *
 */
/**
 * Permet d'enregistrer un joueur
 * @param client HTTPClient
 * @return ResponseContainer
 *
 * @api {post} /register/ Register
 * @apiVersion 0.1.0
 * @apiDescription Post the httpClient references to register to the game
 * @apiName Register
 * @apiGroup Server/ConnectionController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response, here the registration status.
 *
 * @apiParam HTTPClient : client Client references
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": "You joined the game"
 *       }
 *
 */
/**
 * Permet d'avoir la pioche
 * @return TuileContainer
 * @throws EmptyDeckException
 *
 * @api {get} /action/piocher Piocher
 * @apiVersion 0.1.0
 * @apiDescription Get new plots for the deck
 * @apiName Piocher
 * @apiGroup Server/DeckController
 *
 * @apiSuccess PiocheTuile A deck response
 *
 * @apiError EmptyDeckException
 *
 *
 */
/**
 * Permet de rendre une tuile non utilisé
 * @param tuiles TuileContainer
 * @return ResponseContainer
 *
 * @api {post} /action/rendre_tuiles/ RendreTuiles
 * @apiVersion 0.1.0
 * @apiDescription Get back non-used plots
 * @apiName RendreTuiles
 * @apiGroup Server/DeckController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response, here the deck status.
 *
 * @apiParam TuileContainer : plots to return
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": "return effective"
 *       }
 *
 */
/**
 * Permet d'avoir le plateau actuel
 * @return Plateau
 *
 *
 * @api {get} /plateau/ getPlateau
 * @apiVersion 0.1.0
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
/**
 * Permet d'avoir la liste des positions légales
 * @return CoordContainer
 *
 * @api {get} /plateau/legal/ getLegalPosition
 * @apiVersion 0.1.0
 * @apiDescription Get the list of legals position to put plots
 * @apiName getLegalPosition
 * @apiGroup Server/PlateauController
 *
 * @apiDeprecated Gonna to be moved to /plateau/tuile/legal/
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
/**
 * Permet de poser une tuile
 * @param poseTuileContainer PoseTuileContainer
 * @return ResponseContainer
 *
 * @api {post} /action/poser-tuile/ PoserTuile
 * @apiVersion 0.1.0
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
/**
 * Permet de savoir si une position est légale
 * @param q int
 * @param r int
 * @return boolean
 *
 *
 * @api {get} /plateau/legal/:q/:r checkIfPositionIsLegal
 * @apiVersion 0.1.0
 * @apiDescription check if a position is legal to put plot
 * @apiName checkIfPositionIsLegal
 * @apiGroup Server/PlateauController
 *
 * @apiDeprecated Gonna to be moved to /plateau/tuile/legal/:q/:r
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

//======
// 0.2.0
//======

/**
 * Permet d'avoir la fin du tour de jeu
 * @return ResponseContainer
 *
 *
 * @api {get} /end_turn EndTurn
 * @apiVersion 0.2.0
 * @apiDescription End the turn end get the player id that have to play
 * @apiName EndTurn
 * @apiGroup Server/ConnectionController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response, here the player that have to play.
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": "Player :id have to play"
 *       }
 *
 *
 *
 */
/**
 * Permet de check si une partie est terminée
 * @return ResponseContainer
 *
 *
 * @api {get} /gameEnded gameEnded
 * @apiVersion 0.2.0
 * @apiDescription Get the status to know if the game is ended
 * @apiName gameEnded
 * @apiGroup Server/ConnectionController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response.
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": ""
 *       }
 *
 *
 *
 */
/**
 * Permet d'enregistrer un joueur
 * @param client HTTPClient
 * @return ResponseContainer
 *
 * @api {post} /register/ Register
 * @apiVersion 0.2.0
 * @apiDescription Post the httpClient references to register to the game
 * @apiName Register
 * @apiGroup Server/ConnectionController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response, here the registration status.
 *
 * @apiParam HTTPClient : client Client references
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": "You joined the game"
 *       }
 *
 */
/**
 * Permet d'avoir la pioche
 * @return TuileContainer
 * @throws EmptyDeckException
 *
 * @api {get} /action/piocher Piocher
 * @apiVersion 0.2.0
 * @apiDescription Get new plots for the deck
 * @apiName Piocher
 * @apiGroup Server/DeckController
 *
 * @apiSuccess PiocheTuile A deck response
 *
 * @apiError EmptyDeckException
 *
 *
 */
/**
 * Permet de rendre une tuile non utilisé
 * @param tuiles TuileContainer
 * @return ResponseContainer
 *
 * @api {post} /action/rendre_tuiles/ RendreTuiles
 * @apiVersion 0.2.0
 * @apiDescription Get back non-used plots
 * @apiName RendreTuiles
 * @apiGroup Server/DeckController
 *
 * @apiSuccess {Boolean} response The API success response.
 * @apiSuccess {String} message The API message response, here the deck status.
 *
 * @apiParam TuileContainer : plots to return
 *
 * @apiSuccessExample Success-Response:
 *       HTTP/1.1 200 OK
 *       {
 *         "response": "true",
 *         "message": "return effective"
 *       }
 *
 */
/**
 * Permet d'avoir le plateau actuel
 * @return Plateau
 *
 *
 * @api {get} /plateau/ getPlateau
 * @apiVersion 0.2.0
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
/**
 * Permet d'avoir la liste des positions légales
 * @return CoordContainer
 *
 * @api {get} /plateau/tuile/legal/ getLegalPosition
 * @apiVersion 0.2.0
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
/**
 * Permet de poser une tuile
 * @param poseTuileContainer PoseTuileContainer
 * @return ResponseContainer
 *
 * @api {post} /action/poser-tuile/ PoserTuile
 * @apiVersion 0.2.0
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
/**
 * Permet de savoir si une position est légale
 * @param q int
 * @param r int
 * @return boolean
 *
 *
 * @api {get} /plateau/tuile/legal/:q/:r checkIfPositionIsLegal
 * @apiVersion 0.2.0
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
/**
 * Permet de savoir si une tuile a de l'eau
 * @param q int
 * @param r int
 * @return boolean
 *
 *
 * @api {get} /plateau/tuile/water/:q/:r checkTuileWater
 * @apiVersion 0.2.0
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
/**
 * Permet d'avoir la hauteur d'un bambou
 * @param q int
 * @param r int
 * @return int
 *
 *
 * @api {get} /plateau/tuile/bambou/:q/:r checkBambouHeight
 * @apiVersion 0.2.0
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
/**
 * Permet d'avoir la liste des bordes d'une tuile
 * @param q int
 * @param r int
 * @param orient Orient
 * @return CoordContainer
 *
 *
 * @api {get} /plateau/irrigation/border/{q}/{r}/{o}/ computeBorders
 * @apiVersion 0.2.0
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
/**
 * Permet d'avoir la continuité d'une irrigation
 * @param q int
 * @param r int
 * @param orient Orient
 * @return CoordIrrigContainer
 *
 * @api {get} /plateau/irrigation/border/{q}/{r}/{o}/ computeContinues
 * @apiVersion 0.2.0
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
/**
 * Permet d'avoir la liste des positions d'irrigation acceptés
 * @return CoordIrrigContainer
 *
 * @api {get} /plateau/irrigation/legal/ computeLegalIrrigPositions
 * @apiVersion 0.2.0
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
/**
 * Permet d'avoir la liste des irrigations
 * @return CoordIrrigContainer
 *
 * @api {get} /plateau/irrigation/ listOfIrrigation
 * @apiVersion 0.2.0
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
/**
 * Permet de poser une irrigation
 * @param coordIrrig CoordIrrig
 * @return boolean true|false
 *
 *
 * @api {post} /action/poser-irrigation/ PoserIrrigation
 * @apiVersion 0.2.0
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
 */