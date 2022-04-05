package gradle.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import uc.seng301.wordleapp.assignment4.game.ColourCoder;
import uc.seng301.wordleapp.assignment4.game.Wordle;
import uc.seng301.wordleapp.assignment4.guesser.Guess;
import uc.seng301.wordleapp.assignment4.guesser.GuessImpl;
import uc.seng301.wordleapp.assignment4.guesser.ManualGuesser;
import java.util.*;

public class ManualGuessingFeature {
    private int numOfGuesses;
    private ManualGuesser manualGuesser;
    private Scanner input;
    private Stack<Guess> guesses;
    private ColourCoder colourCoder;
    private String userInput;
    private Guess userGuess;
    private Wordle wordle;
    private Guess known;


    @Before
    public void setup() {
        input = Mockito.mock(Scanner.class);
        userInput = "These";
        numOfGuesses = 0;
    }

    @Given("I am playing with the manual guesser")
    public void i_am_playing_with_the_manual_guesser() {
        wordle = new Wordle("Goals");
        userGuess = new GuessImpl( "These", wordle);
        manualGuesser = new ManualGuesser(wordle, input);
    }

    @When("I make a guess")
    public void i_make_a_guess() {
        Mockito.when(input.nextLine()).thenReturn(userInput);
        known = manualGuesser.getGuess();
        guesses = manualGuesser.getGuesses();
        numOfGuesses = manualGuesser.getNumGuesses();

        Assertions.assertEquals(userInput, known.getProposition());
    }

    @Then("The guess count is updated correctly")
    public void the_guess_count_is_updated_correctly() {
        Assertions.assertEquals(1, numOfGuesses);
    }

    @Then("The Guess is added to the stack")
    public void the_guess_is_added_to_the_stack() {
        Assertions.assertEquals(userGuess.getProposition(), guesses.get(0).getProposition());
    }

    @When("I make a guess {string}")
    public void i_make_a_guess(String string) {
        userInput = string;
        Mockito.when(input.nextLine()).thenReturn(userInput).thenReturn("!q");

        known = manualGuesser.getGuess();
        numOfGuesses = manualGuesser.getNumGuesses();
        guesses = manualGuesser.getGuesses();
    }

    @Then("I am not allowed to make the guess")
    public void i_am_not_allowed_to_make_the_guess() {
        Assertions.assertEquals(null, known);
    }

    @Then("The guess count is not changed")
    public void the_guess_count_is_not_changed() {
        Assertions.assertEquals(0, numOfGuesses);
    }

    @Then("The Guess is not added to the stack")
    public void the_guess_is_not_added_to_the_stack() {
        Assertions.assertEquals(0, guesses.size());
    }

}
