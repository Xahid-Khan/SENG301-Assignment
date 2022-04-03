package uc.seng301.wordleapp.assignment4.guesser;

import java.util.Scanner;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uc.seng301.wordleapp.assignment4.dictionary.DictionaryQuery;
import uc.seng301.wordleapp.assignment4.dictionary.DictionaryQueryImpl;
import uc.seng301.wordleapp.assignment4.dictionary.DictionaryResponse;
import uc.seng301.wordleapp.assignment4.game.ColourCoder;
import uc.seng301.wordleapp.assignment4.game.Wordle;

/**
 * Provides guessing functionality for the command line
 */
public class ManualGuesser {
    private static final Logger LOGGER = LogManager.getLogger(ManualGuesser.class);

    private final Wordle wordle;
    private final Scanner cli;
    private final DictionaryQuery dictionaryQuery;
    private final ColourCoder colourCoder;
    private int numGuesses = 0;
    private String currentGuess;
    private Stack<Guess> guesses = new Stack<>();

    public ManualGuesser(Wordle wordle, Scanner cli) {
        this.cli = cli;
        this.dictionaryQuery = new DictionaryQueryImpl();
        this.wordle = wordle;
        this.colourCoder = new ColourCoder(wordle);
    }

    /**
     * Asks for and reads the next guess from the command line, also allowing for
     * hints
     * Returns null if the player exits the game
     * 
     * @return Guess made with appropriate input
     */
    public Guess getGuess() {
        LOGGER.info("Getting guess from command line with manual guesser");
        String input = "";
        boolean isPlaying = true;
        while (isPlaying) {
            System.out.println("Please enter your guess:");
            input = cli.nextLine();
            if (input.length() > 4 && input.substring(0, 5).equalsIgnoreCase("help ")) {
                String query = input.split(" ")[1];
                DictionaryResponse dictionaryResponse = dictionaryQuery.guessWord(query);
                System.out.println(dictionaryResponse.getTopFiveResults());
            } else if (input.equals("!q")) {
                return null;
            } else if (input.length() != 5) {
                System.out.println("Guesses must be 5 letter words");
            } else {
                numGuesses++;
                isPlaying = false;
            }
        }
        currentGuess = input;
        Guess guess = new GuessImpl(currentGuess, wordle);

        guesses.push(guess);
        for (Guess g : guesses)
            System.out.println(colourCoder.getColourCodedString(g.getProposition()));
        return guess;
    }

    /**
     * Gets the total number of guesses made over the life of the guesser
     * 
     * @return total number of guesses made
     */
    public int getNumGuesses() {
        return numGuesses;
    }

    /**
     * Gets the stack of guesses with more recent guesses on top
     * 
     * @return stack of guesses
     */
    public Stack<Guess> getGuesses() {
        return guesses;
    }
}
