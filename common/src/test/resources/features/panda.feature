Feature: Comportement du panda

  Scenario: Le plateau est initialisé mais le panda ne peut pas être déplacer
    Given Un plateau de depart
    Then Je ne peux pas deplacer le panda
    When Je place une tuile en 1,0
    Then Je peux bouger le panda

  Scenario: Le panda ne se déplace qu'en ligne droite
    Given Un plateau de depart
    When Je place une tuile en 1,0
    Then Je peux déplacer le panda en 1,0
    When Je place une tuile en 2,0
    Then Je peux déplacer le panda en 2,0
    When Je place une tuile en 2,1
    Then Je ne peux pas déplacer le panda en 2,1

  Scenario: Le panda ramasse les tuiles de la dernière tuile visitée
    Given Un plateau de depart
    Given Une tuile de couleur VERT en 1,0 avec bambou de taille 1
    When Je déplace le panda en 1,0
    Then Je ramasse des bambous de couleur VERT
    Then La tuile en 1,0 contient un bambou de taille 0

  Scenario: Le panda ne peut ramasser quand il n'y a pas de bombou sur la tuile d'arrivé
    Given Un plateau de depart
    Given Une tuile de couleur VERT en 1,0 avec bambou de taille 0
    When Je déplace le panda en 1,0
    Then Le panda ne ramasse pas de bambou

  Scenario: Le panda ne mange pas sur une tuile avec aménagement enclos
    Given Un plateau de depart
    Given Une tuile de couleur VERT en 1,0 avec bambou de taille 1
    Given La tuile en 1,0 contient l'aménagment ENCLOS
    When Je déplace le panda en 1,0
    Then Le panda ne ramasse pas de bambou
    Then La tuile en 1,0 contient un bambou de taille 1