package gradle.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import uc.seng301.wordleapp.assignment4.game.ColourCoder;
import uc.seng301.wordleapp.assignment4.game.Wordle;

public class ColourCodedReplayFeature {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GRAY = "\u001B[90m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private Wordle wordle;
    private String query;
    private ColourCoder colourCoder;
    private String guess;
    private StringBuilder allGreen;
    private StringBuilder incorrect;

    @Before
    public void setup() {
        allGreen = new StringBuilder();
        allGreen.append(ANSI_GREEN).append("G").append(ANSI_RESET);
        allGreen.append(ANSI_GREEN).append("o").append(ANSI_RESET);
        allGreen.append(ANSI_GREEN).append("a").append(ANSI_RESET);
        allGreen.append(ANSI_GREEN).append("l").append(ANSI_RESET);
        allGreen.append(ANSI_GREEN).append("s").append(ANSI_RESET);
        incorrect = new StringBuilder();
        incorrect.append(ANSI_GREEN).append("G").append(ANSI_RESET);
        incorrect.append(ANSI_YELLOW).append("a").append(ANSI_RESET);
        incorrect.append(ANSI_YELLOW).append("o").append(ANSI_RESET);
        incorrect.append(ANSI_GREEN).append("l").append(ANSI_RESET);
        incorrect.append(ANSI_GRAY).append("d").append(ANSI_RESET);
    }


    @Given("Player makes a guess")
    public void player_makes_a_guess() {
        wordle = new Wordle("Goals");
        colourCoder = new ColourCoder(wordle);
        query = "Goals";
    }

    @When("The guess made is valid")
    public void the_guess_made_is_valid() {
        guess = colourCoder.getColourCodedString(query);
        Assertions.assertNotNull(guess);
    }

    @Then("The whole word is coloured green")
    public void the_whole_word_is_coloured_green() {
        Assertions.assertEquals(allGreen.toString(), guess);
    }

    @When("Guess is unsuccessful but some letters of the guess are in the solution")
    public void guess_is_unsuccessful_but_some_letters_of_the_guess_are_in_the_solution() {
        query = "Gaold";
        guess = colourCoder.getColourCodedString(query);
        Assertions.assertNotNull(guess);
    }

    @Then("letters in solution and are in right place are coloured green")
    public void letters_in_solution_and_are_in_right_place_are_coloured_green() {
        Assertions.assertEquals(incorrect.toString(), guess);
    }

    @Then("letters in solution and are not in right place are coloured yellow")
    public void letters_in_solution_and_are_not_in_right_place_are_coloured_yellow() {
        Assertions.assertEquals(incorrect.toString(), guess);
    }

    @Then("letters not in solution are coloured gray")
    public void letters_not_in_solution_are_coloured_gray() {
        Assertions.assertEquals(incorrect.toString(), guess);
    }

}
