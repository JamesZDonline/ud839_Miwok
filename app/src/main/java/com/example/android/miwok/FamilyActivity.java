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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer sayWord;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
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

        words.add(new Word("father", "әpә",R.raw.family_father,R.drawable.family_father));
        words.add(new Word("mother", "әṭa",R.raw.family_mother,R.drawable.family_mother));
        words.add(new Word("son", "angsi",R.raw.family_son,R.drawable.family_son));
        words.add(new Word("daughter", "tune",R.raw.family_daughter,R.drawable.family_daughter));
        words.add(new Word("older brother", "taachi",R.raw.family_older_brother,R.drawable.family_older_brother));
        words.add(new Word("younger brother", "chalitti",R.raw.family_younger_brother,R.drawable.family_younger_brother));
        words.add(new Word("older sister", "teṭe",R.raw.family_older_sister,R.drawable.family_older_sister));
        words.add(new Word("younger sister", "kolliti",R.raw.family_younger_sister,R.drawable.family_younger_sister));
        words.add(new Word("grandmother", "ama",R.raw.family_grandmother,R.drawable.family_grandmother));
        words.add(new Word("grandfather", "paapa",R.raw.family_grandfather,R.drawable.family_grandfather));


        WordAdapter adapter = new WordAdapter(this,words,R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int wordAudioID = words.get(i).getAudioResource();
                releaseMediaPlayer();
                audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                int audioFocusResult = audioManager.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(audioFocusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    sayWord = MediaPlayer.create(FamilyActivity.this, wordAudioID);
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
