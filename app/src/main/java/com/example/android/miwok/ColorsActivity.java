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

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer sayWord;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("red", "weṭeṭṭi",R.raw.color_red,R.drawable.color_red));
        words.add(new Word("green", "chokokki",R.raw.color_green,R.drawable.color_green));
        words.add(new Word("brown", "ṭakaakki",R.raw.color_brown,R.drawable.color_brown));
        words.add(new Word("gray", "ṭopoppi",R.raw.color_gray,R.drawable.color_gray));
        words.add(new Word("black", "kululli",R.raw.color_black,R.drawable.color_black));
        words.add(new Word("white", "kelelli",R.raw.color_white,R.drawable.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә",R.raw.color_dusty_yellow,R.drawable.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә",R.raw.color_mustard_yellow,R.drawable.color_mustard_yellow));



        WordAdapter adapter = new WordAdapter(this,words,R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int wordAudioID = words.get(i).getAudioResource();
                releaseMediaPlayer();
                sayWord = MediaPlayer.create(ColorsActivity.this,wordAudioID);
                sayWord.start();
                sayWord.setOnCompletionListener(mCompletionListener);

            }
        });

        listView.setAdapter(adapter);





    }

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
        }
    }
}
