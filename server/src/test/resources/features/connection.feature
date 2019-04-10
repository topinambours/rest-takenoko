Feature: Related to connections with the server
  
  Scenario: client performing ping to server
    Given Using fresh connection controller
    When Ping is received from client 10 with url "localhost:8081"
    Then Client received true with the message "pong"

  Scenario: One client registering on the server
    Given Using fresh connection controller
    Then The number of registered users is 0
    When Client with id 10 and address "localhost:8081" request for registration
    Then The number of registered users is 1
    And Server know that the client 10 have the address "localhost:8081"
    And Client received true with the message "Registration complete"

  Scenario: Two clients with the same id registering from different addresses
    Given Using fresh connection controller
    Then The number of registered users is 0
    When Client with id 10 and address "localhost:8081" request for registration
    Then The number of registered users is 1
    And Server know that the client 10 have the address "localhost:8081"
    And Client received true with the message "Registration complete"
    # second user
    When Client with id 10 and address "localhost:9999" request for registration
    Then The number of registered users is 1
    And Client received false with the message "Already registered"
    # server still have the first address registered
    And Server know that the client 10 have the address "localhost:8081"

  Scenario: Multiple clients registering
    Given Using fresh connection controller
    Then The number of registered users is 0
    When Client with id 10 and address "localhost:8081" request for registration
    Then The number of registered users is 1
    And Server know that the client 10 have the address "localhost:8081"
    And Client received true with the message "Registration complete"

    When Client with id 2 and address "localhost:8082" request for registration
    Then The number of registered users is 2
    And Server know that the client 10 have the address "localhost:8081"
    And Server know that the client 2 have the address "localhost:8082"
    And Client received true with the message "Registration complete"
    
  Scenario: Client request for matchmaking without registering
    Given Using fresh connection controller
    When Client with id 10 request for matchmaking for games of size 2
    Then Client received false with the message "You must be logged before enter matchmaking"
  
  Scenario: One client request for matchmaking
    Given Using fresh connection controller
    When Client with id 50 and address "localhost:8081" request for registration
    Then Client received true with the message "Registration complete"
    Then The number of registered users is 1
    When Client with id 50 request for matchmaking for games of size 2
    Then Client received true with the message "Performing matchmaking"
    And The number of waiting client in queue for game of size 2 is 1


  Scenario: One client request for matchmaking with invalid size of game
    Given Using fresh connection controller
    When Client with id 10 and address "localhost:8081" request for registration
    Then Client received true with the message "Registration complete"
    When Client with id 10 request for matchmaking for games of size 5
    Then Client received false with the message "Invalid game size"

  Scenario: Two clients searching for game of the same size (2)
    Given Using fresh connection controller
    When Client with id 10 and address "localhost:8081" request for registration
    When Client with id 25 and address "localhost:8082" request for registration
    When Client with id 10 request for matchmaking for games of size 2
    Then Client received true with the message "Performing matchmaking"
    When Client with id 25 request for matchmaking for games of size 2
    Then Client received true with the message "Enterring new game with 1 other players."
    # At this stage both player will receive id of the game they have to play
    Then The number of waiting client in queue for game of size 2 is 0

  Scenario: Multiples clients on multiple game size
    Given Using fresh connection controller
    When Client with id 1 and address "localhost:8081" request for registration
    And Client with id 1 request for matchmaking for games of size 2
    When Client with id 2 and address "localhost:8082" request for registration
    And Client with id 2 request for matchmaking for games of size 2
    When Client with id 3 and address "localhost:8083" request for registration
    And Client with id 3 request for matchmaking for games of size 2
    
    When Client with id 4 and address "localhost:8084" request for registration
    And Client with id 4 request for matchmaking for games of size 3
    
    When Client with id 5 and address "localhost:8085" request for registration
    And Client with id 5 request for matchmaking for games of size 3
    
    When Client with id 6 and address "localhost:8086" request for registration
    And Client with id 6 request for matchmaking for games of size 3
    
    When Client with id 7 and address "localhost:8085" request for registration
    And Client with id 7 request for matchmaking for games of size 3
    When Client with id 8 and address "localhost:8086" request for registration
    And Client with id 8 request for matchmaking for games of size 4
    When Client with id 9 and address "localhost:8087" request for registration
    And Client with id 9 request for matchmaking for games of size 4

    # One game have been setup for 2 players
    Then The number of waiting client in queue for game of size 2 is 1
    # And one game have been setup for 3 players
    And The number of waiting client in queue for game of size 3 is 1
    And The number of waiting client in queue for game of size 4 is 2
    
    