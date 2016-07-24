package com.example.android.miwok;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn,
 * It contains a default and Miwok translation for a word
 */
public class Word {
    //Default Language word
    private String defaultTranslation;

    //Miwok word
    private String miwokTranslation;

    /**
     * Constructs Word object with default empty words
     */
    public Word(String defaultWord, String miwokWord){
        defaultTranslation = defaultWord;
        miwokTranslation = miwokWord;
    }

    /**
     * Gets the miwok word
     */

    public String getMiwok(){
        return miwokTranslation;
    }

    /**
     * Gets default language word
     */

    public String getDefaultWord(){
        return defaultTranslation;
    }



}
