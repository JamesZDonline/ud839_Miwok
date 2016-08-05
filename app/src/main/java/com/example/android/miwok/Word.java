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

    //Image to go with word
    private int mImageResourceID;

    //Sound to go with word
    private int mAudioResourceID;

    /**
     * Constructs Word object with default empty words
     */
    public Word(String defaultWord, String miwokWord, int audioResourceID){
        defaultTranslation = defaultWord;
        miwokTranslation = miwokWord;
        mAudioResourceID = audioResourceID;
    }

    public Word(String defaultWord, String miwokWord, int audioResourceID, int imageResourceID){
        defaultTranslation = defaultWord;
        miwokTranslation = miwokWord;
        mImageResourceID = imageResourceID;
        mAudioResourceID = audioResourceID;
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

    public int getImageResource() {return mImageResourceID;}

    public int getAudioResource() {return mAudioResourceID;}



}
