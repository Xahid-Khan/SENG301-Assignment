package gradle.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import uc.seng301.wordleapp.assignment4.dictionary.DictionaryQuery;
import uc.seng301.wordleapp.assignment4.dictionary.DictionaryResponse;
import uc.seng301.wordleapp.assignment4.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ManualGuessingFeature {
    private List<String> guesses;
    private int numOfGuesses;
    private DictionaryQuery mockDictionaryQuery;


    @Before
    public void setup() {
        mockDictionaryQuery = Mockito.mock(DictionaryQuery.class);
        guesses = new ArrayList<String>();
        numOfGuesses = 0;
    }

    @Given("I am playing with the manual guesser")
    public void i_am_playing_with_the_manual_guesser() {
        Pattern pattern = Pattern.compile("SOMEWORDS");
        guesses.add("");
        numOfGuesses += 1;
    }

    @When("I make a guess")
    public void i_make_a_guess() {
        mockDictionaryQuery.guessWord("which");
        Assertions.assertEquals(1, numOfGuesses);
    }

    @Then("The guess count is updated correctly")
    public void the_guess_count_is_updated_correctly() {
        mockDictionaryQuery.guessWord("which");
        Assertions.assertEquals(1, numOfGuesses);

        mockDictionaryQuery.guessWord("other");
        Assertions.assertEquals(2, numOfGuesses);
    }

    @Then("The Guess is added to the stack")
    public void the_guess_is_added_to_the_stack() {
        // TODO
    }

    @When("I make a guess {string}")
    public void i_make_a_guess(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I am not allowed to make the guess")
    public void i_am_not_allowed_to_make_the_guess() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The guess count is not changed")
    public void the_guess_count_is_not_changed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The Guess is not added to the stack")
    public void the_guess_is_not_added_to_the_stack() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
