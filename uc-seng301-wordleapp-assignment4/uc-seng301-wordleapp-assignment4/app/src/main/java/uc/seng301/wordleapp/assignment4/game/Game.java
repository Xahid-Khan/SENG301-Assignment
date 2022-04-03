package uc.seng301.wordleapp.assignment4.game;

import java.util.Date;

import uc.seng301.wordleapp.assignment4.guesser.Guess;
import uc.seng301.wordleapp.assignment4.guesser.ManualGuesser;
import uc.seng301.wordleapp.assignment4.model.GameRecord;
import uc.seng301.wordleapp.assignment4.model.User;

/**
 * Plays a singular Wordle game creating a GameRecord of the output
 */
public class Game {
    private final User user;
    private final ManualGuesser guesser;
    private final Wordle wordle;

    /**
     * Default constructor
     * 
     * @param user    a user (assumed not null)
     * @param guesser the guesser that plays the wordle (assumed not null)
     * @param wordle  the wordle to play the game with (assumed not null)
     */
    public Game(User user, ManualGuesser guesser, Wordle wordle) {
        this.user = user;
        this.guesser = guesser;
        this.wordle = wordle;
    }

    /**
     * Plays a game of Wordle with the given User, Guesser, and Wordle.
     * Repeats asking for guesses until complete or a quit condition is met
     * Will return null at any point if the guesser stops providing guesses,
     * assuming an exit condition is met
     * 
     * @return Gamerecord representation of the game
     */
    public GameRecord playGame() {
        boolean gameWon = false;
        while (!gameWon) {
            Guess guess = guesser.getGuess();
            if (guess == null) {
                return null;
            }
            gameWon = guess.isCorrect();
        }
        GameRecord gameRecord = new GameRecord();
        gameRecord.setWord(wordle.getWord());
        gameRecord.setUser(user);
        gameRecord.setNumGuesses(guesser.getNumGuesses());
        gameRecord.setTimestamp(new Date());
        return gameRecord;
    }
}
