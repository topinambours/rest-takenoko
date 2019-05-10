// ------------------------------------------------------------------------------------------
// General apiDoc documentation blocks and old history blocks.
// ------------------------------------------------------------------------------------------

/**
 * @apiDefine Authentificated to authenticate, please add the following parameter at end of url : "?playerId=<code>id</code>"
 */

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

//======
// 0.3.0
//======

/**
 * Permet d'avoir la fin du tour de jeu
 * @return ResponseContainer
 *
 *
 * @api {get} /end_turn EndTurn
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * Permet d'avoir
 * @param id int
 * @return TuileContainer
 * @throws TuileNotFoundException
 *
 *
 * @api {get} /platea/tuile/:id getTuileFromID
 * @apiVersion 0.3.0
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
/**
 * Permet d'avoir la coordonnée d'une tuile via son id
 * @param id int
 * @return CoordContainer
 * @throws TuileNotFoundException
 *
 *
 * @api {get} /platea/tuile/:id/coord getTuileCoordFromID
 * @apiVersion 0.3.0
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
/**
 * Permet de savoir si une tuile a de l'eau
 * @param q int
 * @param r int
 * @return boolean
 *
 *
 * @api {get} /plateau/tuile/water/:q/:r checkTuileWater
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
 * @apiVersion 0.3.0
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
/**
 * Permet d'avoir la position actuelle du panda
 * @return CoordContainer
 *
 *
 * @api {get} /plateau/panda/ pandaPosition
 * @apiVersion 0.3.0
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
/**
 * Permet d'avoir la liste des positions legal pour le panda
 * @return CoordContainer
 *
 * @api {get} /plateau/panda/legal/ legalPandaPosition
 * @apiVersion 0.3.0
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
/**
 * Permet de bouger le panda à une position donnée
 * @param coordAxial CoordAxial
 * @return ColorContainer la couleur du bambou récupéré
 *
 *
 * @api {post} /action/bouger-panda/ BougerPanda
 * @apiVersion 0.3.0
 * @apiDescription Post a coordaxial to move the panda on the board
 * @apiName BougerPanda
 * @apiGroup Server/PlateauController
 *
 *
 * @apiParam CoordAxial : a board coord
 *
 */

//======
// 0.4.0
//======
/**
 * Permet d'avoir la fin du tour de jeu
 * @return ResponseContainer
 *
 *
 * @api {get} /end_turn EndTurn
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @api {get} /plateau/ pullPlateau
 * @apiVersion 0.4.0
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
/**
 * Permet d'avoir la liste des positions légales
 * @return CoordContainer
 *
 * @api {get} /plateau/tuile/legal/ getLegalPosition
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * Permet d'avoir
 * @param id int
 * @return TuileContainer
 * @throws TuileNotFoundException
 *
 *
 * @api {get} /platea/tuile/:id getTuileFromID
 * @apiVersion 0.4.0
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
/**
 * Permet d'avoir la coordonnée d'une tuile via son id
 * @param id int
 * @return CoordContainer
 * @throws TuileNotFoundException
 *
 *
 * @api {get} /platea/tuile/:id/coord getTuileCoordFromID
 * @apiVersion 0.4.0
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
/**
 * Permet de savoir si une tuile a de l'eau
 * @param q int
 * @param r int
 * @return boolean
 *
 *
 * @api {get} /plateau/tuile/water/:q/:r checkTuileWater
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
 * @apiVersion 0.4.0
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
/**
 * Permet d'avoir la position actuelle du panda
 * @return CoordContainer
 *
 *
 * @api {get} /plateau/panda/ pandaPosition
 * @apiVersion 0.4.0
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
/**
 * Permet d'avoir la liste des positions legal pour le panda
 * @return CoordContainer
 *
 * @api {get} /plateau/panda/legal/ legalPandaPosition
 * @apiVersion 0.4.0
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
/**
 * Permet de bouger le panda à une position donnée
 * @param coordAxial CoordAxial
 * @return ColorContainer la couleur du bambou récupéré
 *
 *
 * @api {post} /action/bouger-panda/ BougerPanda
 * @apiVersion 0.4.0
 * @apiDescription Post a coordaxial to move the panda on the board
 * @apiName BougerPanda
 * @apiGroup Server/PlateauController
 *
 *
 * @apiParam CoordAxial : a board coord
 *
 */
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
/**
 * Get the board at a specific version
 * @param id int
 * @return String json
 * @throws VersionNotFoundException
 *
 *
 * @api {get} /version/:id/plateau boardAtVersion
 * @apiVersion 0.4.0
 * @apiDescription Get the board at a specific version
 * @apiName boardAtVersion
 * @apiGroup Server/VersionController
 *
 * @apiParam {number} Version ID
 *
 * @apiSuccess String : Board json
 *
 */

//======
// 0.5.0
//======

/**
 * Permet d'avoir la fin du tour de jeu
 * @return ResponseContainer
 *
 *
 * @api {get} /end_turn EndTurn
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @api {get} /plateau/ pullPlateau
 * @apiVersion 0.5.0
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
/**
 * Permet d'avoir la liste des positions légales
 * @return CoordContainer
 *
 * @api {get} /plateau/tuile/legal/ getLegalPosition
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
/**
 * Permet de savoir si une position est légale
 * @param q int
 * @param r int
 * @return boolean
 *
 *
 * @api {get} /plateau/tuile/legal/:q/:r checkIfPositionIsLegal
 * @apiVersion 0.5.0
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
 * Permet d'avoir
 * @param id int
 * @return TuileContainer
 * @throws TuileNotFoundException
 *
 *
 * @api {get} /platea/tuile/:id getTuileFromID
 * @apiVersion 0.5.0
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
/**
 * Permet d'avoir la coordonnée d'une tuile via son id
 * @param id int
 * @return CoordContainer
 * @throws TuileNotFoundException
 *
 *
 * @api {get} /platea/tuile/:id/coord getTuileCoordFromID
 * @apiVersion 0.5.0
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
/**
 * Permet de savoir si une tuile a de l'eau
 * @param q int
 * @param r int
 * @return boolean
 *
 *
 * @api {get} /plateau/tuile/water/:q/:r checkTuileWater
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
 * @apiVersion 0.5.0
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
/**
 * Permet d'avoir la position actuelle du panda
 * @return CoordContainer
 *
 *
 * @api {get} /plateau/panda/ pandaPosition
 * @apiVersion 0.5.0
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
/**
 * Permet d'avoir la liste des positions legal pour le panda
 * @return CoordContainer
 *
 * @api {get} /plateau/panda/legal/ legalPandaPosition
 * @apiVersion 0.5.0
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
/**
 * Permet de bouger le panda à une position donnée
 * @param coordAxial CoordAxial
 * @return ColorContainer la couleur du bambou récupéré
 *
 *
 * @api {post} /action/bouger-panda/ BougerPanda
 * @apiVersion 0.5.0
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
/**
 * Get all the versions
 * @return ActionContainer
 *
 *
 * @api {get} /version getVersions
 * @apiVersion 0.5.0
 * @apiDescription Get all the versions list
 * @apiName getVersions
 * @apiGroup Server/VersionController
 *
 * @apiSuccess ActionContainer : list of actions
 *
 */
/**
 * Get the version with is ID
 * @param version int
 * @return ActionContainer
 * @throws VersionNotFoundException
 *
 *
 * @api {get} /version/:v/ getVersionID
 * @apiVersion 0.5.0
 * @apiDescription Get the version with is ID
 * @apiName getVersionID
 * @apiGroup Server/VersionController
 *
 * @apiParam {number} Version ID
 *
 * @apiSuccess ActionContainer : list of actions
 *
 */
/**
 * Get all the versions from a version
 * @param from int
 * @return ActionContainer
 * @throws VersionNotFoundException
 *
 *
 * @api {get} /version/from/:from getVersionFrom
 * @apiVersion 0.5.0
 * @apiDescription Get all the versions from a version
 * @apiName getVersionFrom
 * @apiGroup Server/VersionController
 *
 * @apiParam {number} from Version ID From
 *
 * @apiSuccess ActionContainer : list of actions
 *
 */
/**
 * Get all the versions from a version to a version
 * @param from int
 * @param to int
 * @return ActionContainer
 * @throws VersionNotFoundException
 *
 *
 * @api {get} /version/from/:from/to/:to getVersionFromTo
 * @apiVersion 0.5.0
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
/**
 * Get the latest version
 * @return ActionContainer
 *
 *
 * @api {get} /version/latest/ getLatestAction
 * @apiVersion 0.5.0
 * @apiDescription Get the latest version
 * @apiName getLatestAction
 * @apiGroup Server/VersionController
 *
 * @apiSuccess ActionContainer : actions container with the latest version
 *
 */
/**
 * Get the latest version ID
 * @return int
 *
 * @api {get} /version/latest/id getLatestActionId
 * @apiVersion 0.5.0
 * @apiDescription Get the latest version ID
 * @apiName getLatestActionId
 * @apiGroup Server/VersionController
 *
 * @apiSuccess Integer : Latest version ID
 *
 */
/**
 * Get the board at a specific version
 * @param id int
 * @return String json
 * @throws VersionNotFoundException
 *
 *
 * @api {get} /version/:id/plateau boardAtVersion
 * @apiVersion 0.5.0
 * @apiDescription Get the board at a specific version
 * @apiName boardAtVersion
 * @apiGroup Server/VersionController
 *
 * @apiParam {number} Version ID
 *
 * @apiSuccess String : Board json
 *
 */
