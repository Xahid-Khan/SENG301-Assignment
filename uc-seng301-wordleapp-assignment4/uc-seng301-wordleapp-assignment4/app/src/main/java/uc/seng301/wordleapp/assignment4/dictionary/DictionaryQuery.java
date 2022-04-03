package uc.seng301.wordleapp.assignment4.dictionary;

/**
 * This class exposes an interface for getting possible words from a '.'
 * replaced string
 */
public interface DictionaryQuery {
    /**
     * Get all possible words that match the query
     * Preconditions: The guess string is length 5 with no spaces and properly
     * formatted with '.'s replacing missing characters
     * Postconditions: None
     * 
     * @param query '.' replaced string of length 5 e.g. 'cr.n.'
     * @return A dictionary object containing the matching words
     */
    DictionaryResponse guessWord(String query);
}
