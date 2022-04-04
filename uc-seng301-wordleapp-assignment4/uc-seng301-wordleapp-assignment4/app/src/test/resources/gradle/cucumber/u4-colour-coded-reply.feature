Feature: U4 - Colour Coded reply
  Scenario: AC1 - A correct guess will return all letters in green
    Given I am playing the game
    When I make a valid guess
    Then Every letter of my word is coloured green

  Scenario: AC2 - I am playing the game
    Given I am playing the game
    When When Player guess is not correct
    Then correctly guessed and placed letters are coloured green
    Then correctly guessed but not placed letters are coloured yellow
    Then Incorrectly guessed letters are coloured gray