# Etude d'architecture - Topinambours Takenoko
## Etude des ressources
Nous envisageons d'utiliser la structure JSON pour les réponses du serveur.
De son côté, l'utilisateur aura accès à des requêtes avec les verbes correspondant :
-   Créer (create) =>  **POST**
-   Afficher (read) =>  **GET**
-   Mettre à jour (update) =>  **PUT**
-   Supprimer (delete) =>  **DELETE** 


### Ressource Plateau
#### L'objet plateau

Structure du plateau
| Variable | Type | Description |
| plots | HashMap | Structure contenant l'ensemble des tuiles constituantes le plateau |
| irrigations | HashSet | Contient l'ensembles des canaux d'irrigations déjà posés |
| lastPlot | Plot | Dernière tuile ayant été posée |
| posPanda | CoordAxial | Position du Panda sur le plateau |
| posJardinier | CoordAxial | Position du Jardinier sur le plateau |
| canalIrrigation | int | Nombre de canaux d'irrigation restants |

Exemple de plateau

    {
    	"plots" : [
	    	{
		    	"coord" : { "q" : 0, "r" : 0},
				"bambou": 0,
				"water": true,
				"couleur": blue,
				"amenagement": BASSIN,
		    }
		]
		
    	"irrigations" : [],
    	"lastPlot" : {
		    	"coord" : { "q" : 0, "r" : 0},
				"bambou": 0,
				"water": true,
				"couleur": blue,
				"amenagement": BASSIN,
			    },
    	"posPanda" : { "q" : 0, "r" : 0}
    	"posJardinier" : { "q" : 0, "r" : 0}
    	"canalIrrigation" : 6,
    }


#### Avoir le plateau
> GET /plateau/

retourne le plateau de la partie

#### Avoir la liste des parcelles
> GET /plateau/parcelles

retourne la liste des parcelles

    {
	    "plots" : [
		    	{
			    	"coord" : { "q" : 0, "r" : 0},
					"bambou": 0,
					"water": true,
					"couleur": blue,
					"amenagement": BASSIN,
			    },
			    {
			    	"coord" : { "q" : 1, "r" : 0},
					"bambou": 0,
					"water": true,
					"couleur": jaune,
					"amenagement": NON,
			    }
			]
	}

#### Avoir les informations d'une parcelle spécifique
> GET /plateau/parcelles/{parcelle.coord}

    {
		"coord" : { "q" : 0, "r" : 0},
		"bambou": 0,
		"water": true,
		"couleur": blue,
		"amenagement": BASSIN,
	}

___

### Ressource Utilisateur
#### L'objet utilisateur
Structure d'un utilisateur
| Variable | Type | Description |
|--|--| -- |
| id | int | identifiant |
| plotsDeck | PlotsDeck | Main du joueur, liste de ses plots en attentes d'être posés |
| pandObjDeck | ObjectivesDeck | Liste des objectifs pandas à compléter |
| patternObjDeck | ObjectivesDeck | Liste des objectifs patterns à compléter |
| gardenObjDeck | ObjectivesDeck | Liste des objectifs jardiniers à compléter |
| score | int | permet d'avoir le score de l'utilisateur |

Exemple d'utilisateur

    {
	    "id" : 1 ,
	    "plotsDeck" : [
		    {
				"coord" : null,
				"bambou": 0,
				"water": false,
				"couleur": jaune,
				"amenagement": NON,
			},
			{
				"coord" : null,
				"bambou": 0,
				"water": false,
				"couleur": vert,
				"amenagement": ENGRAIS,
			}
	    ],
	    "pandObjDeck" : [],
	    "patternObjDeck" : [],
	    "gardenObjDeck" : [],
	    "score" : 0,
	}

#### Avoir ses informations utilisateurs
> GET /users/@me/

Permet d'avoir toutes ses informations utilisateurs

#### Possibilité d'avoir son score
> GET /users/@me/

Permet d'avoir le score d'un utilisateur

    {
	    "score" : 0,
	}

#### Possibilité d'avoir le score d'un utilisateur 
> GET /users/{user.id}/

Permet d'avoir le score d'un utilisateur

    {
	    "score" : 5,
	}
#### Possibilité d'avoir ses parcelles en mains
> GET /users/@me/plots/

Permet d'avoir les informations des parcelles que nous avons en main

    {
	    "plots" : [
		    {
				"coord" : null,
				"bambou": 0,
				"water": false,
				"couleur": jaune,
				"amenagement": NON,
			},
			{
				"coord" : null,
				"bambou": 0,
				"water": false,
				"couleur": vert,
				"amenagement": ENGRAIS,
			}
	    ],
    }
    
#### Possibilité d'avoir une de ses parcelles en mains
> GET /users/@me/plots/id

Permet d'avoir les informations d'une de ses parcelles que nous avons en main via son identifiant

    {
	    "plots" :{
			"coord" : null,
			"bambou": 0,
			"water": false,
			"couleur": jaune,
			"amenagement": NON,
				},
    }

#### Possibilité d'avoir accès à ses objectifs
> GET /users/@me/objectifs/

Permet d'avoir accès à ses objectifs

	{
	    "pandObjDeck" : [],
	    "patternObjDeck" : [],
	    "gardenObjDeck" : [],
	}

#### Possibilité d'avoir accès à ses objectifs spécifiques
> GET /users/@me/objectifs/{sous-objectif}

Permet d'avoir accès à ses objectifs spécifiques (pandObjDeck / patternObjDeck / gardenObjDeck)

	{
	    "{sous-objectif}" : [],
	}
	
## Explication d'un scénario de jeu

en cours ...
