Feature: U4 - Colour Coded reply
  Scenario: AC1 - While playing the game, if player's guess is correct then it will return all letters in green
    Given Player makes a guess
    When The guess made is valid
    Then The whole word is coloured green

  Scenario: AC2 - While playing the game, if player's guess is incorrect then it will return Green, Yellow, and Gray coloured letters
    Given Player makes a guess
    When Guess is unsuccessful but some letters of the guess are in the solution
    Then letters in solution and are in right place are coloured green
    Then letters in solution and are not in right place are coloured yellow
    Then letters not in solution are coloured gray