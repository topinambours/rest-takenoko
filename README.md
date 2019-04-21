<h1 align="center">
  <br>
  <a href="https://github.com/uca-m1informatique-softeng/topinambours-takenoko"><img src="http://takenoko.hamlab.fr/img/logo.png" alt="Takenoko" width="50px"></a>
  <br>
Takenoko
  <br>
</h1>

<h4 align="center">Un  <a href="http://jeuxstrategie1.free.fr/jeu_takenoko/regle.pdf" target="_blank">Takenoko</a> en Java avec une API rest</h4>

<p align="center">
<img src="https://travis-ci.com/uca-m1informatique-softeng/topinambours-takenoko.svg?token=ddDp96SuTBDWqbwuapYh&branch=master">
<a href="https://cloud.docker.com/u/topinambours/repository/docker/topinambours/takenoko" target="_blank"><img src="https://img.shields.io/docker/automated/topinambours/takenoko.svg"></a>
<a href="https://cloud.docker.com/u/topinambours/repository/docker/topinambours/takenoko" target="_blank"><img src="https://img.shields.io/docker/pulls/topinambours/takenoko.svg"></a>
  
</p>

<p align="center">
  <a href="#presentation">Presentation</a> •
  <a href="#api">API</a> •
  <a href="#application">App</a> •
  <a href="#lancer-une-partie">Lancer une partie</a> •
  <a href="#plan-de-route">Plan de route</a> •
   <a href="#la-boite">La boite</a> •
  <a href="#notes-de-conception">Conception</a> 
</p>


## Presentation

> L'équipe 
> - [Jeremy Bonsaudo](https://github.com/JeremyBonsaudo)
 >- [Matthias Percelay](https://github.com/MatthiasPercelay)
> - [Brandon Fontany-Legall](https://github.com/FontanyLegall-Brandon)
 >- [Thomas Mahe](https://github.com/Mahe-Thomas)

Le projet consiste à réaliser en Java une version numérique du jeu takenoko créé par [Antoine Bauza](http://www.antoinebauza.fr/?tag=takenoko).
Version textuel n'étant pas destinée à être jouée par des êtres humains mais par des robots jouants de façon autonome.

## API
**API Documentation :** https://docs.topinambours.xyz/rest-takenoko/

API First conception : https://github.com/uca-m1informatique-softeng/topinambours-takenoko/blob/master/doc/etude.md

## Application
<p align="center">
<img src="https://topinambours.xyz/assets/img/projects/takenoko-rest-api/app-diag.png">
</p>
L'application se compose d'une partie client et d'une partie serveur dans des images Docker. La partie client se connectant à un serveur via son id. Le serveur lui host une seule partie, mais pourquoi faire comme ça ? <br>
L'objectif final est la création d'une structure type kubernetes. C'est-à-dire comme vue dans le diagramme ci-dessus, la création d'un système de matchmaking qui dynamiquement est en fonction des besoins, pull des images Docker pour créer des serveurs de jeu. C'est pourquoi, nos serveurs sont des serveurs hébergent uniquement une game avec un système d'authentification par id.

## Lancer une partie

    Server : 
    docker pull topinambours/takenoko:latest-server
    docker run --network host topinambours/takenoko:latest-server [PORTSERVER] [NBCLIENT]
    
    Client : 
    docker pull topinambours/takenoko:latest-server
    docker run --network host topinambours/takenoko:latest-client [IDCLIENT] http://[IP]:[PORTSERVER]/ [PORTCLIENT] 


Exemple :

    Server : 
    docker pull topinambours/takenoko:latest-server
    docker run --network host topinambours/takenoko:latest-server 8080 2
    
    Client : 
    docker pull topinambours/takenoko:latest-server
    docker run --network host topinambours/takenoko:latest-client 1 http://localhost:8080/ 8081
    docker run --network host topinambours/takenoko:latest-client 2 http://localhost:8080/ 8082
    
    

## Plan de route

 - [x] **Version 0.1 :** Redémarrage du projet from scratch avec un takenoko minimaliste pour son adaptation à l'API REST.
 - [x] **Version 0.2 :** Ajout de l'irrigation dans le takenoko. Ce qui comprend l'ajout d'un nouveau système de coordonnées ainsi qu'une maj de l'API-Rest. De plus, une modification des tuiles s'impose ainsi qu'une stratégie de base.
 - [ ] **Version 0.3 :** Ajout du panda dans le takenoko.Ce qui comprend l'ajout d'un nouveau objectif principale avec son lot de cartes objectifs pandas. De plus, les bambous vont être revus ainsi qu'une stratégie de base ajoutée. Pour finir, toutes ces modifications seront accessibles via l'API-Rest. 
 
## La boite

|Nombre| Nom | Image 
|--|--|--|
| 1 | Figurine Jardinier |![](https://image.ibb.co/g8mXE9/1.jpg)  
| 1 | Figurine Panda |  ![](https://image.ibb.co/cvs3nU/penda_1.jpg) 
| 1 |Carte Empereur|  ![](https://image.ibb.co/dkt17U/carte1_1.jpg)
| 20 | Bâtonnet irrigation|  ![](http://jeuxstrategieter.free.fr/jeu_takenoko/pion3.jpg) 
| 8 (4*4) |Jetons action|  ![](http://jeuxstrategieter.free.fr/jeu_takenoko/pion4.jpg)
| 1 | Dé spécial | ![](https://image.ibb.co/fLOb7U/de_1.jpg) ![](http://jeuxstrategieter.free.fr/jeu_takenoko/dev_de.jpg)  
| 26 (9 + 7 + 10)| Socle de bambou|  ![](https://image.ibb.co/kpDhgp/pion1_1.jpg) 
|64 (21 + 17 + 26)| sections de bambou|  ![](https://image.ibb.co/cGW7E9/pion2_1.jpg) 
|9 (3 + 3 + 3)| Tuiles aménagement| ![](https://image.ibb.co/eUMcgp/tuile_1.jpg)
|15| Cartes objectif panda | ![](https://image.ibb.co/i1S3nU/carte4_1.jpg)  
|15| Cartes objectif parcelle | ![](https://image.ibb.co/bwrw7U/carteb_1.jpg) 
|15| Cartes objectif jardinier |![](https://image.ibb.co/ehOb7U/cartef_1.jpg)  
|1| Tuile étant tuile de départ| ![](https://image.ibb.co/cKkTMp/tuile1_1.jpg)  
|27 |Tuiles parcelle  |![](https://image.ibb.co/fZfOnU/tuile2_1.jpg)  
|11 |Tuiles vertes  |![](https://image.ibb.co/cd1USU/tuile3_1.jpg)  
|7| Tuiles roses|  ![](https://image.ibb.co/n3jQj9/tuile5_1.jpg)  
|9|Tuiles jaunes| ![](https://image.ibb.co/m7P2Wp/tuile7_1.jpg) 


## Notes de conception

### Système de coordonnées
![](https://image.ibb.co/c04o6p/Capture_de_2018_09_26_14_25_13.png)![Représentation des arrêtes](https://image.ibb.co/c87Y6p/Screenshot_at_Sep_26_14_15_37.png)

