Feature: Can i get a response if the server is up ?

  Scenario: Server is up
    When server launched
    When I ask for server status
    Then I should be told "Server is up"