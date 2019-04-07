Feature: Scénarios relatifs aux requêtes de pioche

  Scenario: Une nouvelle pioche est crée
    Given using fresh deck controller
    Then La taille de la pioche est de 27

    Scenario: Un joueur demande à piocher des tuiles
      Given using fresh deck controller
      Then La taille de la pioche est de 27
      When Un joueur pioche 3 tuiles
      Then La taille de la pioche est de 24
      Then le joueur rend les deux tuiles de sa pioche
      Then La taille de la pioche est de 26

