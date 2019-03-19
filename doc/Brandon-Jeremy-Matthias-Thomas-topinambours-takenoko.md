# Feedback de la première livraison

Groupe https://github.com/orgs/uca-m1informatique-softeng/teams/topinambours

## Intégration continue ##
C’est bien intégré depuis le 25/09/2018 : https://travis-ci.com/uca-m1informatique-softeng/topinambours-takenoko/builds/102549371

Cependant le build est considéré comme faux car "The command "mvn exec:java@debug" exited with 1." car dans votre code GameStarter finit avec "System.exit(1);" (ce qui ne sert pas, et mettre quelquechose différent de 0 en paramètre signifie qu'il y a une erreur/un problème)

## Gestion de projet ##
vous avez utilisé une milestone, et 3  issues et un kanban (non utilisé) : vous n'avez pas fermé les tickets réalisés. Il faudrait aussi prévoir les milestones suivantes (pour la réalisation du changement architectural)

vous avez oublié de mettre un tag pour la livraison.

## TDD appliqué à Takenoko ##
il n'y a pas d'intégration de Cucumber

## Etude d’architecture sur le découpage en services REST ##
L’étude se concentre sur la façon dont le client peut interroger le serveur pour obtenir des informations, il peut aussi effectuer quelques actions (poser une parcelle, déplacer les personnages, poser une irrigation). Ces spécifications sont détaillées, montrant des exemples de JSON reçu en réponse. Il n'y a pas eu de début d'application du web service "serveur" défini ainsi.

Plusieurs remarques :
  * il n'est pas prévu le déroulement de la partie (la partie scénario est "en cours...")
  * de fait, le client ne peut pas avec ses spécifications piocher (parcelles ou objectifs)
  * l'aspect connexion (et identification, de la session) n'est pas précisé
  * le déroulement même d'un tour n'est pas abordé : comment un client sait-il que la partie est commencé, que c'est à lui de jouer ?


Pour cela, plusieurs solutions sont envisageables, dont :
  * Le client expose aussi un web service et donne son adresse lors de la connexion, le serveur peut alors le rappeler (a priori, l’adresse du serveur est connue, celles des clients non). À partir de là, le serveur peut avertir les clients. Par exemple, lors de la boucle de jeu, soit le serveur demande de jouer, puis les clients rappellent le serveur avec ce que vous avez spécifiez (en ajoutant ce qu'il manque) (c’est asynchrone), soit le client retourne (un gson avec) sa décision.
  * Une autre possibilité, déconseillée, est de faire comme js/ajax : le client scrute régulièrement s’il y a des « nouveautés ». C'est sur quoi semble s'orienté votre solution, puisque le client semble piloter le déroulement en interrogeant le serveur.

Il est conseillé d'expliciter l'ensemble des routes/échanges dans le scénario.
