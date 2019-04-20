Feature: Checking state of the deck

  Scenario: Game of size 2 have 27 plots on the deck
    Given a fresh gameEngine of size 2
    Then Size of the deck is 27

  Scenario: Game of size 3 have 27 plots on the deck
    Given a fresh gameEngine of size 3
    Then Size of the deck is 27

  Scenario: Game of size 4 have 27 plots on the deck
    Given a fresh gameEngine of size 4
    Then Size of the deck is 27

  Scenario: Game has not started, player try to draw
    Given a fresh gameEngine of size 3
    When a new client with id 25 and address "localhost:8081" register to the game
    When Player 25 draw 3 plots
    Then The player get 0 plots
    Then Size of the deck is 27

  Scenario: First draw when two players join the game
    Then Size of the deck is 27
    When a new client with id 25 and address "localhost:8081" register to the game
    When a new client with id 10 and address "localhost:8082" register to the game
    When Player 25 draw 3 plots
    Then Size of the deck is 24
    Then Player choose one plot and packed remaining for the deck
    Then Player 25 send back extra plots
    Then Size of the deck is 26

  Scenario: Two players playing with one trying to cheat
    Given a fresh gameEngine of size 2
    Then The number of registered users is 0
    When a new client with id 10 and address "localhost:8081" register to the game
    When a new client with id 25 and address "localhost:8081" register to the game
    Then The number of registered users is 2
    Then the game has started
    Then Size of the deck is 27
    # Player 10 is the first player
    When Player 10 draw 3 plots
    Then The player get 3 plots
    Then Player choose one plot and packed remaining for the deck
    Then Player 10 send back extra plots
    Then Size of the deck is 26
    # It is not the turn of player 25, he must get 0 plots
    When Player 25 draw 3 plots
    Then The player get 0 plots
    Then Size of the deck is 26
    # Player outside from the game
    Then Player 15888 draw 3 plots
    Then The player get 0 plots
    Then Size of the deck is 26

    # Now Player 10 said that he finished his turn
    Then Player 10 notify that he finished his turn
    # He can't draw after that
    When Player 10 draw 3 plots
    Then The player get 0 plots

    When Player 25 draw 3 plots
    Then The player get 3 plots
    Then Size of the deck is 23
    Then Player choose one plot and packed remaining for the deck
    Then Player 25 send back extra plots
    Then Size of the deck is 25

    Then Player 25 notify that he finished his turn

    When Player 10 draw 3 plots
    Then The player get 3 plots
    # ... loop ...

  Scenario: Four players reach the end of the deck
    Given a fresh gameEngine of size 4
    When a new client with id 1 and address "localhost:8081" register to the game
    When a new client with id 2 and address "localhost:8082" register to the game
    When a new client with id 3 and address "localhost:8083" register to the game
    When a new client with id 4 and address "localhost:8084" register to the game
    Then The number of registered users is 4
    Then the game has started
    Then Size of the deck is 27

    When Player 1 draw 3 plots
    Then The player get 3 plots
    Then Player 1 notify that he finished his turn
    When Player 2 draw 3 plots
    Then The player get 3 plots
    Then Player 2 notify that he finished his turn
    When Player 3 draw 3 plots
    Then The player get 3 plots
    Then Player 3 notify that he finished his turn
    When Player 4 draw 3 plots
    Then The player get 3 plots
    Then Player 4 notify that he finished his turn

    # Second game loop
    When Player 1 draw 3 plots
    Then The player get 3 plots
    Then Player 1 notify that he finished his turn
    When Player 2 draw 3 plots
    Then The player get 3 plots
    Then Player 2 notify that he finished his turn
    When Player 3 draw 3 plots
    Then The player get 3 plots
    Then Player 3 notify that he finished his turn
    When Player 4 draw 3 plots
    Then The player get 3 plots
    Then Player 4 notify that he finished his turn
    
    Then Size of the deck is 3

    When Player 1 draw 3 plots
    Then The player get 3 plots
    Then Player choose one plot and packed remaining for the deck
    Then Player 1 send back extra plots
    Then Player 1 notify that he finished his turn
    Then Size of the deck is 2

    When Player 2 draw 3 plots
    Then The player get 2 plots
    Then Player choose one plot and packed remaining for the deck
    Then Player 2 send back extra plots
    Then Player 2 notify that he finished his turn

    When Player 3 draw 3 plots
    Then The player get 1 plots
    Then Player choose one plot and packed remaining for the deck
    Then Player 3 send back extra plots
    Then Player 3 notify that he finished his turn

    Then Size of the deck is 0