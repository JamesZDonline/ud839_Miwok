package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom Word adapter
 */
public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceID;

    /**
     * Constructor for WordAdapters
     */
    public WordAdapter(Activity context, ArrayList<Word> words, int colorId){

        super(context,0, words);
        mColorResourceID = colorId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);

        }

        //Get the word located at the given position
        Word currentWord = getItem(position);

        //find Text linear layout
        LinearLayout textLinear = (LinearLayout) listItemView.findViewById(R.id.text_linear_layout);

        int color = ContextCompat.getColor(getContext(),mColorResourceID);
        textLinear.setBackgroundColor(color);

        //Find the TextView in the layout with the ID default_text_view in the list_item.xml
        TextView defaultLangText = (TextView) listItemView.findViewById(R.id.default_text_view);

        //Find the TextView in the layout with the ID miwok_text_view in the list_item.xml
        TextView miwokLangText = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        //Find the ImageView in the layout with the ID  in the list_item.xml
        ImageView miwokImage = (ImageView) listItemView.findViewById(R.id.word_image_view);

        //Set the default lang text view and miwok lang text views to the correct text
        defaultLangText.setText(currentWord.getDefaultWord());
        miwokLangText.setText(currentWord.getMiwok());

        miwokImage.setImageResource(currentWord.getImageResource());

        if(miwokImage.getDrawable() == null){
            miwokImage.setVisibility(View.GONE);
        }else{
            miwokImage.setVisibility(View.VISIBLE);
        }

        //Return the whole list item layout so it can be shown in the ListView
        return listItemView;
    }



}
