package uc.seng301.wordleapp.assignment4.guesser;

import uc.seng301.wordleapp.assignment4.game.Wordle;

/**
 * Interface exposing the methods of a guess
 */
public abstract class Guess {
    protected String proposition;
    protected Wordle wordle;

    /**
     * Creates a new guess object
     * 
     * @param proposition Proposition is the string representation of the users
     *                    guess, this string must not be null and must be length 5
     * @param wordle      Worlde object is properly created and must have word
     *                    parameter set
     */
    protected Guess(String proposition, Wordle wordle) {
        this.proposition = proposition;
        this.wordle = wordle;
    }

    /**
     * Gets the string representation of the guess
     * Preconditions: The guess is properly created with a string representation
     * (length 5, no spaces) of the word
     * Postconditions: None
     * 
     * @return guess string with length 5, cannot be null,
     */
    public abstract String getProposition();

    /**
     * Guess object checks itself for correctness
     * Preconditions: The guess's {proposition} is properly created, The guess's
     * wordle is properly created, with a defined word
     * Postconditions: None
     * 
     * @return True if the guess proposition and wordle word match exactly, else
     *         false
     */
    public abstract boolean isCorrect();
}
