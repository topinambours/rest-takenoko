Feature: Check registration of users for the game
  
  Scenario: Checking for game of size 2, game does not start until there is no 2 players
    Given a fresh gameEngine of size 2
    Then The number of registered users is 0
    When a new client with id 10 and address "localhost:8081" register to the game
    Then The number of registered users is 1
    Then the game has not started

  Scenario: Checking for game of size 3, game does not start until there is no 3 players
    Given a fresh gameEngine of size 3
    Then The number of registered users is 0
    When a new client with id 10 and address "localhost:8081" register to the game
    Then The number of registered users is 1
    When a new client with id 15 and address "localhost:8082" register to the game
    Then The number of registered users is 2
    Then the game has not started

  Scenario: Checking for game of size 4, game does not start until there is no 4 players
    Given a fresh gameEngine of size 4
    Then The number of registered users is 0
    When a new client with id 10 and address "localhost:8081" register to the game
    Then The number of registered users is 1
    When a new client with id 15 and address "localhost:8082" register to the game
    Then The number of registered users is 2
    When a new client with id 20 and address "localhost:8083" register to the game
    Then The number of registered users is 3
    Then the game has not started

    # TRIGGERING GAME START
  Scenario: Checking for game of size 2, game start when there is 2 players
    Given a fresh gameEngine of size 2
    Then The number of registered users is 0
    When a new client with id 10 and address "localhost:8081" register to the game
    Then The number of registered users is 1
    Then the game has not started
    When a new client with id 25 and address "localhost:8081" register to the game
    Then The number of registered users is 2
    Then the game has started

  Scenario: Checking for game of size 3, game does not start until there is no 3 players
    Given a fresh gameEngine of size 3
    Then The number of registered users is 0
    When a new client with id 10 and address "localhost:8081" register to the game
    Then The number of registered users is 1
    When a new client with id 15 and address "localhost:8082" register to the game
    Then The number of registered users is 2
    Then the game has not started
    When a new client with id 75 and address "localhost:8083" register to the game
    Then The number of registered users is 3
    Then the game has started

  Scenario: Checking for game of size 4, game does not start until there is no 4 players
    Given a fresh gameEngine of size 4
    Then The number of registered users is 0
    When a new client with id 10 and address "localhost:8081" register to the game
    Then The number of registered users is 1
    When a new client with id 15 and address "localhost:8082" register to the game
    Then The number of registered users is 2
    When a new client with id 20 and address "localhost:8083" register to the game
    Then The number of registered users is 3
    Then the game has not started
    When a new client with id 78 and address "localhost:8085" register to the game
    Then The number of registered users is 4
    Then the game has started

    # TESTING OVER REGISTRATION
  Scenario: Checking for game of size 2, game start when there is 2 players, no more registration accepted
    Given a fresh gameEngine of size 2
    Then The number of registered users is 0
    When a new client with id 10 and address "localhost:8081" register to the game
    Then The number of registered users is 1
    Then the game has not started
    When a new client with id 25 and address "localhost:8081" register to the game
    Then The number of registered users is 2
    Then the game has started
    When a new client with id 38 and address "localhost:8082" register to the game
    Then The number of registered users is 2
