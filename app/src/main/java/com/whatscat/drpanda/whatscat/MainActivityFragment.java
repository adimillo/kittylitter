package com.whatscat.drpanda.whatscat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        final ImageView mainCat = (ImageView)mainView.findViewById(R.id.mainCat);

        //Happy Cat
        ImageButton happyCat = (ImageButton) mainView.findViewById(R.id.happyCat);
        happyCat.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    CatLoader catLoader = new CatLoader(mainView);
                    catLoader.execute("happy");
                }
                return false;
            }
         });

        //Sad Cat
        ImageButton sadCat = (ImageButton) mainView.findViewById(R.id.sadCat);
        sadCat.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    CatLoader catLoader = new CatLoader(mainView);
                    catLoader.execute("sad");
                }
                return false;
            }
        });

        //Angry Cat
        ImageButton angryCat = (ImageButton) mainView.findViewById(R.id.angryCat);
        angryCat.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    CatLoader catLoader = new CatLoader(mainView);
                    catLoader.execute("angry");
                }
                return false;
            }
        });

        //Funny Cat
        ImageButton funnyCat = (ImageButton) mainView.findViewById(R.id.funnyCat);
        funnyCat.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    CatLoader catLoader = new CatLoader(mainView);
                    catLoader.execute("funny");
                }
                return false;
            }
        });

        return mainView;
    }

}
