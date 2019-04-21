Feature: Testing ActionController class

  Scenario: Player receive the notification that it's his turn
    Given I am a client with the id 10
    And It is not the turn of the client
    When Client receive notification that it's the turn of player 10
    Then It is the turn of the client

  Scenario: Player receive the notification that said that it's the turn of an other client
    Given I am a client with the id 10
    Then It is not the turn of the client
    When Client receive notification that it's the turn of player 25
    Then It is not the turn of the client
    But Client know that the current player is the player 25
    Then Client receive notification that it's the turn of player 10
    Then It is the turn of the client