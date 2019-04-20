Feature: Comportement des tuiles

  Scenario: La tuile de départ est irrigué mais il est impossible de faire pousser des bambous sur celle-ci
    Given Une tuile BLEU
    Then Ma tuile est irrigué
    Then Je ne peux pas faire pousser des bambous
    When Je fais pousser un bambou
    Then Ma tuile contient 0 bambous

  Scenario: Il est impossible de faire pousser un bambou sur une tuile non irrigué
    Given Une tuile ROSE
    Then Ma tuile n'est pas irrigué
    Then Je ne peux pas faire pousser des bambous
    Then J'irrigue ma tuile
    Then Je peux faire pousser des bambous


  Scenario: J'irrigue une tuile pour faire pousser des bambous
    Given Une tuile VERT
    Then Ma tuile n'est pas irrigué
    Then Ma tuile ne contient aucun bambou
    Then Je ne peux pas faire pousser des bambous
    When J'irrigue ma tuile
    Then Je peux faire pousser des bambous
    When Je fais pousser un bambou
    Then Ma tuile contient 1 bambou

  Scenario: Je fais pousser des bambous sans irriguer une tuile contenant l'aménagement bassin
    Given Une tuile ROSE avec un aménagement BASSIN
    Then Ma tuile est irrigué
    And Ma tuile ne contient aucun bambou
    When Je fais pousser un bambou 3 fois
    Then Ma tuile contient 3 bambous
    
  Scenario: Je fais pousser des bambous sur une tuile contenant l'aménagement engrais
    Given Une tuile VERT avec un aménagement ENGRAIS
    When J'irrigue ma tuile
    And Je fais pousser un bambou
    Then Ma tuile contient 2 bambous
    When Je retire un bambou, j'obtient 1 bambous de couleur VERT et rien d'autre
    And Ma tuile contient 1 bambous
    Then Je fais pousser un bambou 2 fois
    Then Ma tuile contient 4 bambous
    
  Scenario: Je fais pousser plus de quatres bambous
    Given Une tuile ROSE avec un aménagement BASSIN
    Then Je fais pousser un bambou 5 fois
    Then Ma tuile contient 4 bambous

  Scenario: Je fait pousser plus de 4 bambous et j'en retire plus de 4
    Given Une tuile JAUNE avec un aménagement BASSIN
    When Je fais pousser un bambou 80 fois
    Then Ma tuile contient 4 bambous
    When Je retire un bambou 1 fois
    Then Ma tuile contient 3 bambous
    When Je retire un bambou 45 fois
    Then Ma tuile contient 0 bambous


