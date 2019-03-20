
# Projet [takenoko](http://jeuxstrategie1.free.fr/jeu_takenoko/regle.pdf "takenoko")

![Build Status](https://travis-ci.com/uca-m1informatique-softeng/topinambours-takenoko.svg?token=ddDp96SuTBDWqbwuapYh&branch=master) ![](http://sonarqube.hamlab.fr/api/project_badges/measure?project=BambooMaster%3ATakenoko&metric=alert_status) ![](http://sonarqube.hamlab.fr/api/project_badges/measure?project=BambooMaster%3ATakenoko&metric=reliability_rating) ![](http://sonarqube.hamlab.fr/api/project_badges/measure?project=BambooMaster%3ATakenoko&metric=code_smells) ![](http://sonarqube.hamlab.fr/api/project_badges/measure?project=BambooMaster%3ATakenoko&metric=coverage)  ![](http://sonarqube.hamlab.fr/api/project_badges/measure?project=BambooMaster%3ATakenoko&metric=sqale_rating)

> ##### SonarQube : [sonarqube.hamlab.fr](http://sonarqube.hamlab.fr/sessions/new)
> Login : admin\
> Pass : admin2906


## Master 1 Informatique - Université Nice Sophia Antipolis

> L'équipe 
> - [Jeremy Bonsaudo](https://github.com/JeremyBonsaudo)
 >- [Matthias Percelay](https://github.com/MatthiasPercelay)
> - [Brandon Fontany-Legall](https://github.com/FontanyLegall-Brandon)
 >- [Thomas Mahe](https://github.com/Mahe-Thomas)

Le projet consiste à réaliser en Java une version numérique du jeu takenoko créé par [Antoine Bauza](http://www.antoinebauza.fr/?tag=takenoko).
Version textuel n'étant pas destinée à être jouée par des êtres humains mais par des robots jouants de façon autonome.

## Lancer une partie

    git clone https://github.com/Mahe-Thomas/takenoko.git
    cd ./takenoko && mvn exec:java@release

## Plan de route

 - [x] **Version 1 :** un robot pioche et place les parcelles aléatoirement sur le plateau jusqu’à épuisement de la pioche.
 - [x] **Version 2 :** Le placement des tuiles est encadré par les règles takenoko (adjacences). Ajout d'une stratégie aux robots leurs permettant de placer une parcelle en maximisant son nombre de parcelle adjacente. Un robot marque autant de point que d'adjacence dont dispose sa parcelle.
 
 - [x] **Version 3** : Ajout de composants liés à l’irrigation des parcelles, les bambous ne poussent que si la parcelle est irrigué. A chaque tour, les parcelles se dotent d'un bambous supplémentaire. Un robot ne marque des points qu'en plaçant une parcelle adjacent à une parcelle disposant de bambous, récolte les bambous et marque 1 point par bambou récolté.
 Une nouvelle stratégie est alors possible, chercher l'emplacement maximisant le nombre de bambous adjacents. Il est alors possible de faire jouer trois robots avec une stratégie qui lui est propre.
 - [x] **Version 4** : Ajouter la possibilité aux robots d'ajouter des canaux d'irrigations. ~~Réaliser plusieurs stratégies liées aux canaux~~. Ajout des couleurs pour les parcelles, les robots marquent des points supplémentaires si la parcelle est posé en bordure d'autres parcelles de la même couleurs.
 - [x] **Version 5** : Les joueurs recoivent des cartes objectifs panda, les joueurs gagnent des points pour chaque carte objectif complétée. Les joueurs adaptent leurs stratégies pour compléter leurs objectifs.
 - [x] **Version 6** : Les joueurs recoivent des cartes objectifs parcelle, un joueur gagne des points en complétant ses cartes. Un joueur va donc adapter ses placements de parcelles pour achever ses objectifs.
 - [x] **Version 7** : Les joueurs ont maintenant la posibilité de jouer avec le jardinier, les cartes jardinier, et le Panda. ~~De plus, le joueur respecte la règle des 2 actions maximum par tours.~~
 - [x] **Version 8** : Les joueurs peuvent piocher ~~et placer~~ des aménagements. Le dé météo affecte le déroulement du tour, les joueurs doivent effectuer deux actions par tours.~~ Les aménagements sont désormais intégrés dans le moteur du jeu. Une partie se termine dès lors qu'un joueur à complété un certain nombre d'objectifs selon les règles takenoko. Ajout du dé météo, les joueurs lancent le dé au début de leur tour SANS que cela n'affecte le déroulement du tour. Les joueurs disposent maintenant de stratégies pour les aménagements (décision au moment de la pioche selon le type).
 - [x] **Version 9** : Limiter le nombre d'action des joueurs, améliorer les robots pour que leurs coups complètent en priorité leurs objectifs.
 - [ ] **Version 10** : La météo influe sur les décisions des robots.
## Y'a quoi dans la boite ?

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

### Les stratégies 
- **La stratégie Adjacente :** consiste à poser des parcelles en maximisant le nombre de voisins
- **La stratégie Bambou :** Le robot prend le parti de placer sa parcelle au placement maximisant le nombre de bambous adjacents
- **La stratégie Couleur :** consiste à placer les parcelles en maximisant le nombre de voisins de la même couleur qui la parcelle
- **La stratégie irrigation :** faite pour la pose des irrigations
- **La stratégie random :** Stratégie de base. Elle effectue les actions de façon aléatoire
