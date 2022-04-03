package uc.seng301.wordleapp.assignment4.dictionary;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Simple data class for jackson json deserialization
 */
public class DictionaryResponse {

    @JsonDeserialize
    @JsonProperty("words")
    private List<String> words;

    /**
     * Preconditions: {words} must not be null
     * Postconditions: None
     * Gets the list of words stored
     * 
     * @return list of words
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Preconditions: {words} must not be null
     * Postconditions: None
     * Gets up to the first 5 words from the list as they are stored, if fewer words
     * exist in the list all will be
     * returned
     * 
     * @return first 5 words as list
     */
    public List<String> getTopFiveResults() {
        return words.stream().limit(5).toList();
    }

    /**
     * Sets the list of words, used only for mocking and getting words offline
     * Precondition: DictionaryQuery object is created correctly and has a not null
     * list of words
     * 
     * @param words the list of 5-letter words to set
     */
    public void setWords(List<String> words) {
        this.words = words;
    }
}
