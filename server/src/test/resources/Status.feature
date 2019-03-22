
Feature: Can i ping the server ?
  Scenario: client performing ping to server
    When ping is performed
    Then the client receives status "server is up"
