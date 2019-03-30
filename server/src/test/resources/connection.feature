Feature: Related to connections with the server
  
  Scenario: client performing ping to server
    Given using fresh connection controller
    When ping received from user 10 with address "localhost:8081"
    Then the client with id 10 and address "localhost:8081" receive pong
    
  Scenario: Single client make registration
    Given using fresh connection controller
    When registration request from user 10 with address "localhost:8081"
    Then The number of registered user is 1

  Scenario: Single client make registration twice
    Given using fresh connection controller
    When registration request from user 10 with address "localhost:8081"
    Then The number of registered user is 1
    When registration request from user 10 with address "localhost:8081"
    Then The number of registered user is 1

  Scenario: Multiple client make registration
    Given using fresh connection controller
    When registration request from user 10 with address "localhost:8081"
    Then The number of registered user is 1
    When registration request from user 8 with address "localhost:8082"
    Then The number of registered user is 2
    