<h1 align="center">
<br>

<br>
Demo - <a href="https://github.com/uca-m1informatique-softeng/topinambours-takenoko">Takenoko</a>
<br>
</h1>

<p align="center">
<a href="#docker">Docker</a> •
<a href="#travis">Travis</a> 
</p>

> L'équipe 
>- [Thomas Mahe](https://github.com/Mahe-Thomas)
>- [Brandon Fontany-Legall](https://github.com/FontanyLegall-Brandon)

## Docker
### L'image Démo
#### Lancer l'image
Notre image Démo se lance de la façon suivante : 
```bash
bash run-demo.sh
```
En détails, le script lance le téléchargement de l'image de démo et la lance avec les paramètres suivants :
- **-it** pour que le process soit interactif
- **--network** *host* pour définir le nom d'hôte et le domaine visible pour les process
- **-v** */var/run/docker.sock:/var/run/docker.sock* permet quant à lui de définir un volume partagé. Il permet notamment de rendre accessible docker.sock à l'intérieur de l'image dans le but d'effectué un Docker In Docker



run-demo.sh
```bash
#!/bin/bash

docker pull topinambours/takenoko:demo
docker run -it --network host -v /var/run/docker.sock:/var/run/docker.sock topinambours/takenoko:demo "${@:1}"
```
#### Fonctionnement de l'image Demo

<p align="center">
<img src="https://topinambours.xyz/assets/img/projects/takenoko-rest-api/dockerINdocker-demo-image.png">
</p>

L'image Démo fonctionne sur le principe de *Docker In Docker*. En effet, l'image démo va elle-même appeler/run 2 autres images docker avec docker à l'aide d'un script python en entrypoint. Ceci permet notamment de lancer plusieurs containers dans un seul et même container et donc éviter de composer les containers.

Voici les étapes du script python : 
1. **Connexion** dans dockerhub avec un compte temporaire créer pour l'occasion (suite à un bug docker, notamment dans son cache, nous devons nous connecter même pour pull une image public car docker essaie de pull avec un token invalide)
2. **Pull** des différentes images, ici latest-server et latest-client
3. **Lancement du serveur** avec les paramètres soit donnés, soit les paramètres par défauts
4. **Lancement de N client** en fonction du paramètre défini
5. **Déroulement de la partie**
6. **Fin de démo**, on clôture les différentes taches

Voir le graphique ci-dessus pour une vision assez claire sur la structure interne de notre image démo.

------------------------------------------------------------------------------------------------------------------------------------------------

### L'image test-suite
L'image test-suite fonctionne sur le même principe que l'image démo. En effet, c'est une image Docker In Docker qui lance serveur et client. Cependant, l'image test-suite ne se content pas seulement de lancer une seule game mais en lance plusieurs en fonction des paramètres d'entrée. En effet, nous pouvons lancer n serveurs avec n clients et ce, de façon soit parallèle, soit séquentiel.

## Travis

### Notre pipeline d'intégration continue :
<p align="center">
<img src="https://i.imgur.com/4DEoopH.png">
</p>

#### Les builds conditionnels


L'un des problèmes qui s’est rapidement installer sur Travis est le temps d'exécution des jobs. Nous avons des builds qui sont excessivement long atteignant les 1h30 sur les jobs les plus longs (sans l'ajout du temps de file d'attente etc…). Ceci nous a forcer a faire des stages uniquement dans certains cas dont voici les spécifications :
* **Sur toutes les branches :** 
* **Build and pré-cache :** téléchargement et mise en cache des dépendances 
* **Unit Tests :** tests unitaires (JUnit)
* **Bdd Tests :** test Cucumbers
* **Sonar report :** génération et déploiement du rapport sonar
* **Create docker tests images :** Création et déploiement des images de test sur le dockerHub (client,server,test-suite)
* **Intégration Tests :** Tests d'intégration sur les images docker tests dont les spécifications sont présents dans le chapitre suivant
* **Deploy Tests :** Tests de déploiement minimales (car très longs, donc réservés à la branche master/PR) sur l'image test-suite 
* **Deploy to dockerhub :** 2ème phase de déploiement des images `latest` pour les composants `server`, `client` et `testSuite`. De plus, création de l'image`demo`
* **Sur les branches master et Pull Request :** 
* **Deploy Tests :** Enrichie par deux autres stages plus longues (50 sequentieal games run & 10 parallel games run)
* **Sur les branches de tags :** 
* **Release Deploy to DockerHub :** Créer et déploie les images `server`, `client` et `testSuite` avec le tag correspondant. 
ex -> `topinambours/takenoko:release-server`

Le graphique ci-dessus montre bien la séparation dans notre pipeline d'intégration continue

#### Tests d'intégration

Une version très *early* des tests d'intégrations est disponible dans le répertoire `{basePath}/integration-test`, nous avons optés pour un système de script reposant sur le framework de test unitaire Python `unittest`. 

Dans notre cas, un test unitaire au sens du Framework correspondra à un test d'intégration, une propriété que l'on souhaite tester sur l'ensemble des composants du système.

Afin de faciliter le lancement des images et d'en contrôler les logs, nous avons utilisé le [Docker SDK](https://pypi.org/project/docker/) pour Python.
Nous faisons donc des assertions sur les logs des clients ainsi que du serveur.
Un exemple type de test -> Un joueur n'effectue d'action que lorsque c'est son tour (notification reçu, le serveur à notifier le client avant de recevoir d'actions de celui-ci, etc...)

Nous avons mis la priorité sur les tests avant le lancement de la partie, cas particulier qui peuvent être bloquants pour le serveur. 
Ex : Que se passe-t-il dans le cas où aucun client ne se connecte ? Que se passe-t-il lorsqu'un client se déconnecte pendant une partie ?

<p align="center">
<img src="https://i.imgur.com/Sp9tPgK.png">
</p>


