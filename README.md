
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

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=topinambours&metric=alert_status)](https://sonarcloud.io/dashboard?id=topinambours)  [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=topinambours&metric=coverage)](https://sonarcloud.io/dashboard?id=topinambours)

<p align="center">
  <a href="#presentation">Presentation</a> •
  <a href="#api">API</a> •
  <a href="#architecture-globale-du-projet">Architecture</a> •
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

## Architecture globale du projet
<p align="center">
<img src="https://topinambours.xyz/assets/img/projects/takenoko-rest-api/app-diag.png">
</p>
Le projet se compose d'une partie client et d'une partie serveur containérisés grâce à Docker. Cette approche permet d'avoir plus de souplesse concernant le passage à l'échelle de l'application. 

Ainsi, avec notre architecture, il serait possible d'ajouter une couche entre client et le serveur, voir ici un système de matchmaking et une autorité se chargeant de créer les parties. Ainsi un joueur peut jouer une partie sans pour autant spécifier une instance de partie. Du côté serveur il est imaginable de proposer une grappe de *Game Provider* afin d'augmenter le nombre de parties jouables simultanément avec des systèmes pouvant surveiller l'utilisation des serveurs afin d'ajuster les ressources à mettre en œuvre pour les clients.

Une solution existe déjà pour ajouter ces abstractions : [kubernetes](https://kubernetes.io)

## Installer les containers 
Remplacer `<Version>` par le numéro de version ou bien par le tag `latest` pour obtenir les dernière images stables.
```console
# Serveur
$ docker pull topinambours/takenoko:<Version>-server
# Client
$ docker pull topinambours/takenoko:<Version>-client
```

## Lancer une partie
#### 1. Lancer une instance serveur
```console
$ docker run --network host topinambours/takenoko:<Version>-server <port> <gameSize>
```

 - Paramètres serveur
	 - `port` Port sur lequel la partie est accessible
	 - `gameSize` Le nombre de participants à la partie 

Une partie démarre une fois le nombre maximal de participants atteint.

Example d'utilisation : 
```console
$ docker run --network host topinambours/takenoko:latest-server 8080 3
```
Fournira une instance de partie de pouvant accueillir `3` joueurs qui sera accessible depuis le port `8080`.

#### 2. Lancer des instances clients

```console
$ docker run --network host topinambours/takenoko:<Version>-client <port> <server_host> <user_id>
```

 - Paramètres client
	 - `port` Port sur lequel le joueur joue
	 - `server_host` Adresse de la partie ex : *http://127.0.0.1:8080*
	 - `user_id` Identifiant unique (dans le cadre d'une unique instance)

Example d'utilisation : 
```console
$ docker run --network host topinambours/takenoko:<Version>-client 8081 http://127.0.0.1:8080 15
```
Fournit un client avec pour id `15` accessible depuis son adresse via le port `8081` et contactera le serveur localisé à l'adresse `http://127.0.0.1:8080` pour joueur sa partie.

## Plan de route

 - [x] **Version 0.1 :** Redémarrage du projet from scratch avec un takenoko minimaliste pour son adaptation à l'API REST.
 - [x] **Version 0.2 :** Ajout de l'irrigation dans le takenoko. Ce qui comprend l'ajout d'un nouveau système de coordonnées ainsi qu'une maj de l'API-Rest. De plus, une modification des tuiles s'impose ainsi qu'une stratégie de base.
 - [x] **Version 0.3 :** Ajout du panda dans le takenoko.Ce qui comprend l'ajout d'un nouveau objectif principale avec son lot de cartes objectifs pandas. De plus, les bambous vont être revus ainsi qu'une stratégie de base ajoutée. Pour finir, toutes ces modifications seront accessibles via l'API-Rest. 
 - [x] **Version 0.4 :** Les plateaux entre le joueur et le serveur sont maintenant synchronisés. C'est-à-dire que maintenant le joueur à la possibilité de pull les différences de plateau entre 2 moments données, par exemple, entre son précédent tour et celui qu'il est en train de préparer. L'utilité principale étant la possibilité au joueurs d'avoir accès à plus d'informations l'aidant à la conception de stratégie plus perfectionnées.
 - [x] **Version 0.5 :** Recast general pour correctif : correction du plateau permettant d'avoir correctement les datas via l'API; correction des codes d'erreur http; correction d'une faille sécurité permettant de poser des tuiles sur des emplacements illegaux.
 - [x] **Version 0.6 :** Patch de sécurité au niveau de l'api visant notamment à protéger les parties contre des éléments extérieurs via le système d'authentification. Toute requête post n'étant pas ou mal authentifié est maintenant refusée. Pour finir, les accès sont maintenant logs

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
