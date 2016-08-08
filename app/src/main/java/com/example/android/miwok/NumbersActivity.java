/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer sayWord;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();}
    };

    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //If loss is transient pause the word
                sayWord.pause();
                sayWord.seekTo(0);

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //if you get the focus back, resume playing
                sayWord.start();

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //If you lose focus all together release the resources
                releaseMediaPlayer();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.word_list);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti",R.raw.number_one,R.drawable.number_one));
        words.add(new Word("two", "otiiko",R.raw.number_two,R.drawable.number_two));
        words.add(new Word("three", "tolookosu",R.raw.number_three,R.drawable.number_three));
        words.add(new Word("four", "oyyisa",R.raw.number_four,R.drawable.number_four));
        words.add(new Word("five", "massokka",R.raw.number_five,R.drawable.number_five));
        words.add(new Word("six", "temmokka",R.raw.number_six,R.drawable.number_six));
        words.add(new Word("seven", "kenekaku",R.raw.number_seven,R.drawable.number_seven));
        words.add(new Word("eight", "kawinta",R.raw.number_eight,R.drawable.number_eight));
        words.add(new Word("nine", "wo’e",R.raw.number_nine,R.drawable.number_nine));
        words.add(new Word("ten", "na’aacha",R.raw.number_ten,R.drawable.number_ten));


        WordAdapter adapter = new WordAdapter(this,words,R.color.category_numbers);


        ListView listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int wordAudioID = words.get(i).getAudioResource();
                releaseMediaPlayer();

                audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                int audioFocusResult = audioManager.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(audioFocusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    sayWord = MediaPlayer.create(NumbersActivity.this,wordAudioID);
                    sayWord.start();
                    sayWord.setOnCompletionListener(mCompletionListener);
                }

            }
        });


        listView.setAdapter(adapter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (sayWord != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            sayWord.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            sayWord = null;

            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
}
