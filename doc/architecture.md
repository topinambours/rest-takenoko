<h1 align="center">
<br>

<br>
Architecture - <a href="https://github.com/uca-m1informatique-softeng/topinambours-takenoko">Takenoko</a>
<br>
</h1>

<p align="center">
<a href="#architecture">Architecture</a> •
<a href="#documentation-des-routes">Documentation</a> •
<a href="#scenarios">Scenarios</a> •
<a href="#versionning">Versionning</a> 

</p>

> L'équipe 
>- [Thomas Mahe](https://github.com/Mahe-Thomas)
> - [Brandon Fontany-Legall](https://github.com/FontanyLegall-Brandon)

## Architecture
### Architecture globale du projet

<p align="center">
<img src="https://topinambours.xyz/assets/img/projects/takenoko-rest-api/app-diag.png">
</p>

Le projet se compose d'une partie client et d'une partie serveur containérisés grâce à Docker. Cette approche *container* permet d'avoir plus de souplesse concernant le passage à l'échelle de l'application (côté serveur).

Ainsi, avec notre architecture, il serait possible d'ajouter une couche entre l'application client et le serveur, voir ici un système de matchmaking et une autorité se chargeant de créer les instances de parties. Il est possible d'imaginer de pouvoir remplacer l'entité *Game Provider* du diagramme ci-dessus par un orchestrateur Docker.

Un package *common* requis par les deux acteurs du système contient l'ensemble des modèles de données utilisés par les api mais également l'ensemble des classes nécessaires à la représentation du jeu. Certains composants doivent cependant rester cachés aux yeux du client (ex : Pioche).

------------------------------------------------------------------------------------------------------------------------------------------------
### Architecture serveur
#### Vue d'ensemble : 
<p align="center">
<img src="https://i.imgur.com/e7XWvwF.png">
</p>
![](https://i.imgur.com/JiawBUI.png)


Architecture inspirée du pattern MVC mais sans la composante View, visant à séparer les données des traitements.
L'architecture est centrée autour de la classe GameEngine contenant les composants principaux nécessaire à la représentation du jeu ainsi que des variables d'états (joueur courant, gameStarted etc...).

Les classes contenues dans le package `controllers` sont quant à elles dédiées au code interagissant avec les données. L'ensemble des méthodes accessibles via l'API sont contenues dans ce package.

#### Vue rapprochée
Description plus détaillée des responsabilités des classes : 

- **App :**
Point d'entrée de l'application, ajoute des variables d'environnement Spring depuis les données passées en paramètre.
Initialise l'*Executor* chargé de gérer les tâches à effectuer en concurrence.

- **GameEngine :**
Contient l'ensemble des variables d'états de la partie (gameSize, currentPlayerId, gameStarted etc...), la liste des clients connectés, le plateau, la pioche de tuiles.
Contient également la liste des actions effectuées utile pour le versionning.
Ne dispose d'aucun code influant sur l'état de la partie.

- **Services :**
- **ConnectionService :**
Contient des tâches à effectuer de manière automatisée.
ex : Envoyer un ping aux clients toute les 250ms, mets un terme à la partie dans le cas d'un client injoignable avec demande de déconnection des clients connectés au préalable.
Contient également les traitements en cas d'enregistrement d'un client ainsi lors d'une notification de fin de tour émise par un client.

Ce service est d'une grande importance quant au déroulement d'une partie et l'ordonnancement au client de passer en phase de jeu.
- **NotificationService :**
Contient une unique méthode exécutée par intervalle de 1000ms se chargeant de demander aux clients de se déconnecter une fois la partie terminée, déclenche également le premier tour de jeu.

- **Controllers :**
L'ensemble des fonctionnalités décrites dans cette section sont couvertes par des routes.
- **VersionController :**
Permet de récupérer des informations de versionning
- Liste d'actions à appliquer à un plateau
- Plateau entier tagué avec un `versionId`
- **PlateauController :**
Permet d'intéragir avec le plateau
- Obtenir le plateau dans son intégralité
- Obtenir les positions légales pour les tuiles/irrigations
- Poser une tuile/irrigation
- Obtenir une tuile posée sur le plateau depuis son `uniqueId`
- Déplacer le panda
- Obtenir la position du panda
- **DeckController :**
Permet aux clients de piocher des tuiles comprend : 
- Piocher un ensemble de tuiles (trois maximum)
- Rendre des tuiles
- **ConnectionController :**
Contient les méthodes relatives au communications non relative au jeu
- Enregistrement à la partie (Envoie des données clients)
- Obtenir l'état de la partie (Started/Ended)
- Notifier la fin de son tour
- *Util* **AuthentificationVerification :**
Classe utilitaire permettant de vérifier si une requête est effectuée par un client enregistré.
- *Hors sujet (MVC ?)* **GameEngineController :** 
Permet de visualiser l'état du plateau pour une `versionId` donnée (graphiquement)
`localhost:port/{versionId}`
- Affichage des tuiles / irrigations
- Clients connectés
- Position du panda (non graphique)

<p align="center">
<img src="https://i.imgur.com/cFCTESE.png" height='250px'>
</p>

------------------------------------------------------------------------------------------------------------------------------------------------
### Architecture client
#### Vue d'ensemble : 
<p align="center">
<img src="https://i.imgur.com/1cc1wLD.png">
</p>

#### Vue rapprochée
- **App :**
Point d'entrée du client, importe notamment les paramètres qui sont l'ip du serveur cible ainsi que son port.
Il est aussi responsable de l'initialisation de l'*Executor* asynchrone.

- **Joueur :**
Cette classe contient l'ensemble des variables nécessaires au client et notamment l'*HTTPClient* permettant la communication du dit client       avec le serveur.
- **Controller :**
Le client nécessite d'avoir sa propre interface REST pour que le serveur est la possibilité de le contacter comme par exemple dans le cas         d'une fin de partie. Les controllers sont couverts par des routes.
- **ActionController :**
Permet la récupération des informations que nous envoie le serveur
- Possibilité au serveur de demander de clôture du client avec raison à l'appuis
- Possibilité au serveur de Ping le client
- Possibilité au serveur de notifier le client dans le cas où c'est son tour
- **ActionService :**
Classe dans laquelle le client va effectuer ses actions. Notamment, et de façon asynchrone, il va pouvoir : 
- Se clôturer en cas de demande serveur
- Jouer son tour
- Phase de pioche
- Phase de pose de tuile
- Phase de maniement du panda
- Phase d'irrigation
- Phase de mise à jour de son plateau interne

------------------------------------------------------------------------------------------------------------------------------------------------      
#### Architecture partagée

- **Communication :**
- **HTTPClient :**
Classe principale permettant d'implémenter le *RestTemplate* utilisé par le client pour communiqué avec l'API du serveur
- **Container :**
Multiples containers permettant l'échange d'informations entre le serveur et le client
- **ActionCountainer** pour l'échange du versionning
- **BambouCountainer** pour l'échange des bambous
- **ColorCountainer** pour l'échange des couleurs
- **CoordCountainer** pour l'échange des coordonnées
- **CoordCubeCountainer** pour l'échange des coordonnées cubiques
- **CoordIrrigCountainer** pour l'échange des coordonnées d'irrigation
- **PoseTuileCountainer** pour la pose (*POST*) d'une tuile à une position données
- **ResponseCountainer** pour les réponses du serveur avec statuts et message
- **TuileCountainer** pour l'échange de tuiles
- **Takenoko :**
Toutes les classes permettant d'implémenter le jeu.

## Documentation des routes

La documentation complète des routes est visible ici :
[https://docs.topinambours.xyz/rest-takenoko/](https://docs.topinambours.xyz/rest-takenoko/)
<p align="center">
<img src="https://i.imgur.com/CALhLJm.png">
</p>



## Scenarios

<p align="center">
<img src="https://topinambours.xyz/assets/img/projects/takenoko-rest-api/flow-client-game.png" width="600px">
</p>

### Démarrage d'une partie

Avant de démarrer la partie, le serveur est en attente de l'enregistrement des clients. Aucun filtrage n'est appliqué aux clients qui se connecte.
On suppose que les clients disposent d'un identifiant unique pour leur enregistrement sur le serveur. 

Le serveur est coupé dans le cas où il ne reçoit aucun enregistrement sous 30 secondes, dans le cas d'un nouvel enregistrement, ce timeout est remis à zéro.
Dans le cas où un ou plusieurs clients seraient déjà connecté, si le dernier opposant ne se connecte pas sous 30 secondes à partir du dernier enregistrement, le serveur demandera au client connecté de fermer l'application avant de se couper lui-même.

Dans un scénario normal (tous les clients sont connectés dans les temps), la partie démarre. Le serveur demandera à un client spécifique de jouer.

<p align="center">
<img src="https://i.imgur.com/11V4GCD.png">
</p>


### Déroulement d'une partie
Une fois la partie démarrée, les clients joues de façon alternée les uns après les autres. Une fois qu'un client notifie la fin de son tour, le serveur envoie une notification au prochain client pour qu'il démarre son tour.

En l'état un client effectue autant d'action que le serveur le permet. Il n'y a pas de décision quant aux actions que les clients feront pendant leur tour. Bien qu'il serait possible d'implémenter relativement facilement une contrainte sur le nombre d'actions qu'effectue un client au sein de son tour.

Le client n'effectue aucun calcul concernant les positions légales, il doit expressément demander au serveur de générer les placements légaux en fonctions de l'état du plateau du serveur. Cette stratégie évite de devoir vérifier les actions du client, sachant celles-ci préfiltrées par le serveur.

Pour chaque action envoyée par le client au serveur, le serveur enregistre cette action dans un registre utile pour le versionning (synchronisation des plateaux entre le client et le serveur)

Un client doit synchroniser le plateau avant le début de son tour s’il souhaite utiliser ses stratégies avec les dernières données du plateau.
Une mauvaise synchronisation ne peut entrainer des requêtes de coup illégaux de la part du client avec l'architecture proposée.


<p align="center">
Boucle de jeu : <br>
<img src="https://i.imgur.com/otf3haB.png">
</p>

Les transitions représentent des requêtes du client au serveur excepté pour les transitions contenants le mot *select*, correspondant à des appels à la stratégie du client. 


## Versionning
Nous avons choisi de versionner le plateau avec un système grandement inspiré des gits. En effet, nous ne renvoyons pas la totalité du plateau à chaque fois que le client en a besoin mais lui renvoyons les changements qui ont été effectués entre sa version local et la version du serveur.

Dans un souci d'optimisation du volume de données envoyés, lorsqu'un client demande une mise à jour de son plateau, celui-ci reçoit une liste d'action à appliquer à son plateau. Cette liste est construite par le serveur en fonction de la version renseigné par le client.
Lors de l'application de ces actions, si le plateau du client de retrouve dans un état incohérent, l'intégralité du plateau sera demandée au serveur afin de remplacer le plateau du client malformé.

### Fonctionnement
Pour stocker les différentes versions, nous avons introduit la notion d'*actions*. Chaque action effectuée sur un plateau à son propre type (ex : ActionType.PUT_PLOT) ainsi que ses arguments qui lui sont propres. Toutes ses actions sont alors stockées avec une id de type int et non hash pour des raisons de simplicité dans le gameEngine. 

Lorsque le client décide de récupérer la version la plus récente, il effectue un appel API dans lequel il spécifie sa version courante. Le serveur lui renvoie donc une liste d'actions dans un *ActionContainer* que le client va appliquer dans l'ordre pour avoir son plateau à jour. Dans le cas où il y aurait une erreur/incohérence dans l'application des actions, le client fait alors, et uniquement dans ce cas, un pull complet du plateau.
<p align="center">
<img src="https://topinambours.xyz/assets/img/projects/takenoko-rest-api/versionning.png" height="500px">
</p>

### Visuellement
Voici un aperçu visuel du versionning qui pourra être !
retrouvé dans la démo notamment
<p align="center">
<img src="https://i.imgur.com/3blfq6P.png" >
</p>


